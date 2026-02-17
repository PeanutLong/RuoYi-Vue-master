package com.ruoyi.forum.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.forum.domain.ForumLike;
import com.ruoyi.forum.mapper.ForumLikeMapper;
import com.ruoyi.forum.mapper.ForumPostMapper;
import com.ruoyi.forum.mapper.ForumReplyMapper;
import com.ruoyi.forum.service.IForumLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 点赞Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumLikeServiceImpl implements IForumLikeService
{
    @Autowired private ForumLikeMapper forumLikeMapper;
    @Autowired private ForumPostMapper forumPostMapper;
    @Autowired private ForumReplyMapper forumReplyMapper;

    /**
     * 查询点赞
     *
     * @param likeId 点赞主键
     * @return 点赞
     */
    @Override
    public ForumLike selectForumLikeByLikeId(Long likeId)
    {
        return forumLikeMapper.selectForumLikeByLikeId(likeId);
    }

    /**
     * 查询点赞列表
     *
     * @param forumLike 点赞
     * @return 点赞
     */
    @Override
    public List<ForumLike> selectForumLikeList(ForumLike forumLike)
    {
        return forumLikeMapper.selectForumLikeList(forumLike);
    }

    /**
     * 新增点赞
     *
     * @param forumLike 点赞
     * @return 结果
     */
    @Override
    public int insertForumLike(ForumLike forumLike)
    {
        forumLike.setCreateTime(DateUtils.getNowDate());
        return forumLikeMapper.insertForumLike(forumLike);
    }

    /**
     * 修改点赞
     *
     * @param forumLike 点赞
     * @return 结果
     */
    @Override
    public int updateForumLike(ForumLike forumLike)
    {
        return forumLikeMapper.updateForumLike(forumLike);
    }

    /**
     * 批量删除点赞
     *
     * @param likeIds 需要删除的点赞主键
     * @return 结果
     */
    @Override
    public int deleteForumLikeByLikeIds(Long[] likeIds)
    {
        return forumLikeMapper.deleteForumLikeByLikeIds(likeIds);
    }

    /**
     * 删除点赞信息
     *
     * @param likeId 点赞主键
     * @return 结果
     */
    @Override
    public int deleteForumLikeByLikeId(Long likeId)
    {
        return forumLikeMapper.deleteForumLikeByLikeId(likeId);
    }
    @Override
    @Transactional
    public AjaxResult toggleLike(ForumLike forumLike) {
        // 1. 设置当前用户ID
        Long userId = SecurityUtils.getUserId();
        forumLike.setUserId(userId);

        // 2. 利用现有的 selectForumLikeList 查询
        // 这个方法会根据你传入的 userId, likeType, targetId 进行筛选
        List<ForumLike> list = forumLikeMapper.selectForumLikeList(forumLike);

        if (list != null && list.size() > 0) {
            // 3. 如果列表不为空，说明已点赞：执行取消点赞
            ForumLike existLike = list.get(0);
            forumLikeMapper.deleteForumLikeByLikeId(existLike.getLikeId());

            updateTargetLikeCount(forumLike, -1);
            return AjaxResult.success("取消点赞成功", -1);
        } else {
            // 4. 如果列表为空，说明未点赞：执行点赞
            forumLike.setCreateTime(DateUtils.getNowDate());
            forumLikeMapper.insertForumLike(forumLike);

            updateTargetLikeCount(forumLike, 1);
            return AjaxResult.success("点赞成功", 1);
        }
    }

    private void updateTargetLikeCount(ForumLike forumLike, int amount) {
        if ("0".equals(forumLike.getLikeType())) {
            // 更新帖子点赞数
            forumPostMapper.updateLikeCount(forumLike.getTargetId(), amount);
        } else if ("1".equals(forumLike.getLikeType())) {
            // 更新回复点赞数
            forumReplyMapper.updateLikeCount(forumLike.getTargetId(), amount);
        }
    }
}