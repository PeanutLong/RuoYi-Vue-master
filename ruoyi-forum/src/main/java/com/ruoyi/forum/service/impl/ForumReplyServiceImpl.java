package com.ruoyi.forum.service.impl;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.forum.domain.ForumPost;
import com.ruoyi.forum.domain.ForumReply;
import com.ruoyi.forum.domain.ForumReplyReference;
import com.ruoyi.forum.mapper.ForumPostMapper;
import com.ruoyi.forum.mapper.ForumReplyMapper;
import com.ruoyi.forum.mapper.ForumReplyReferenceMapper;
import com.ruoyi.forum.service.IForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 回复Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@Service
public class ForumReplyServiceImpl implements IForumReplyService
{
    @Autowired
    private ForumReplyMapper forumReplyMapper;

    @Autowired
    private ForumPostMapper forumPostMapper;

    @Autowired
    private ForumReplyReferenceMapper forumReplyReferenceMapper;

    /**
     * 查询回复
     *
     * @param replyId 回复主键
     * @return 回复
     */
    @Override
    public ForumReply selectForumReplyByReplyId(Long replyId)
    {
        return forumReplyMapper.selectForumReplyByReplyId(replyId);
    }

    /**
     * 查询回复列表
     *
     * @param forumReply 回复
     * @return 回复
     */
    @Override
    public List<ForumReply> selectForumReplyList(ForumReply forumReply)
    {
        return forumReplyMapper.selectForumReplyList(forumReply);
    }

    /**
     * 新增回复
     * * @param forumReply 回复信息
     * @return 结果
     */
    @Override
    @Transactional // 开启事务，保证数据一致性
    public int insertForumReply(ForumReply forumReply)
    {
        // 1. 获取当前用户信息
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();

        forumReply.setUserId(userId);
        forumReply.setCreateBy(username);
        forumReply.setCreateTime(DateUtils.getNowDate());
        forumReply.setLikeCount(0L);
        forumReply.setReplyCount(0L);
        forumReply.setStatus("0"); // 0=正常

        // 2. 检查并设置楼主标识 (isAuthor)
        ForumPost post = forumPostMapper.selectForumPostByPostId(forumReply.getPostId());
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }
        forumReply.setIsAuthor(post.getUserId().equals(userId) ? "1" : "0");

        // 3. 计算楼层号 (如果是回复帖子本身)
        // 注意：楼中楼通常不占用主楼层号，或者你可以设计为所有回复都占楼层。
        // 这里假设所有回复都占楼层
        Long maxFloor = forumReplyMapper.selectMaxFloorByPostId(forumReply.getPostId());
        forumReply.setFloorNum(maxFloor + 1);

        // 4. 处理父子回复关系 (Root ID Logic)
        ForumReply parentReply = null;
        if (forumReply.getParentReplyId() != null && forumReply.getParentReplyId() > 0) {
            // 查询父回复
            parentReply = forumReplyMapper.selectForumReplyByReplyId(forumReply.getParentReplyId());
            if (parentReply != null) {
                // 如果父回复已经是子回复，则继承它的 rootId；否则父回复就是 root
                Long rootId = (parentReply.getRootReplyId() == null || parentReply.getRootReplyId() == 0)
                        ? parentReply.getReplyId()
                        : parentReply.getRootReplyId();
                forumReply.setRootReplyId(rootId);

                // 更新父回复的子回复数量 (可选)
                // forumReplyMapper.incrementReplyCount(parentReply.getReplyId());
            }
        } else {
            // 直接回复帖子
            forumReply.setParentReplyId(0L);
            forumReply.setRootReplyId(0L);
        }

        // 5. 插入回复表
        int rows = forumReplyMapper.insertForumReply(forumReply);

        // 6. 处理引用关系 (ForumReplyReference)
        // 只有当回复的是具体的某个人（即有父回复）时才插入引用表
        if (parentReply != null) {
            ForumReplyReference reference = new ForumReplyReference();
            reference.setReplyId(forumReply.getReplyId()); // 刚插入生成的ID
            reference.setReferencedReplyId(parentReply.getReplyId());
            reference.setReferencedUserId(parentReply.getUserId());

            // 生成内容快照 (截取前100字，防止太长)
            String snapshot = parentReply.getContent();
            // 如果是富文本，建议先去除HTML标签再截取（这里简单处理）
            if (StringUtils.isNotEmpty(snapshot) && snapshot.length() > 200) {
                snapshot = snapshot.substring(0, 200) + "...";
            }
            reference.setReferencedContent(snapshot);
            reference.setCreateTime(new Date());

            forumReplyReferenceMapper.insertForumReplyReference(reference);
        }

        // 7. 更新帖子统计数据 (回复数+1，更新最后回复时间)
        forumPostMapper.updatePostReplyStats(forumReply.getPostId());

        return rows;
    }

    /**
     * 修改回复
     *
     * @param forumReply 回复
     * @return 结果
     */
    @Override
    public int updateForumReply(ForumReply forumReply)
    {
        forumReply.setUpdateTime(DateUtils.getNowDate());
        return forumReplyMapper.updateForumReply(forumReply);
    }

    /**
     * 批量删除回复
     *
     * @param replyIds 需要删除的回复主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyByReplyIds(Long[] replyIds)
    {
        return forumReplyMapper.deleteForumReplyByReplyIds(replyIds);
    }

    /**
     * 删除回复信息
     *
     * @param replyId 回复主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyByReplyId(Long replyId)
    {
        return forumReplyMapper.deleteForumReplyByReplyId(replyId);
    }
}