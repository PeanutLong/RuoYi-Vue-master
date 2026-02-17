package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.forum.domain.ForumBounty;
import com.ruoyi.forum.domain.ForumPost;
import com.ruoyi.forum.mapper.ForumPostMapper;
import com.ruoyi.forum.service.IForumBountyService;
import com.ruoyi.forum.service.IForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帖子Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumPostServiceImpl implements IForumPostService
{
    @Autowired
    private ForumPostMapper forumPostMapper;

    @Autowired
    private IForumBountyService forumBountyService;

    /**
     * 查询帖子
     *
     * @param postId 帖子主键
     * @return 帖子
     */
    @Override
    public ForumPost selectForumPostByPostId(Long postId)
    {
        return forumPostMapper.selectForumPostByPostId(postId);
    }

    /**
     * 查询帖子列表
     *
     * @param forumPost 帖子
     * @return 帖子
     */
    @Override
    public List<ForumPost> selectForumPostList(ForumPost forumPost)
    {
        return forumPostMapper.selectForumPostList(forumPost);
    }

    /**
     * 新增帖子 (包含悬赏逻辑)
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务
    public int insertForumPost(ForumPost forumPost)
    {
        // 1. 设置基础信息
        forumPost.setUserId(SecurityUtils.getUserId()); // 强制使用当前登录用户
        forumPost.setCreateTime(DateUtils.getNowDate());

        // 设置默认统计数据
        forumPost.setViewCount(0L);
        forumPost.setLikeCount(0L);
        forumPost.setCommentCount(0L);
        forumPost.setStatus("0"); // 正常
        forumPost.setIsTop("0");
        forumPost.setIsEssence("0");

        // 2. 插入帖子 (MyBatis 会将生成的 postId 回填到 forumPost 对象中)
        int rows = forumPostMapper.insertForumPost(forumPost);

        // 3. 如果是求助帖(postType='1') 并且有悬赏金额，插入悬赏表
        if ("1".equals(forumPost.getPostType()) && forumPost.getBountyCoins() != null && forumPost.getBountyCoins() > 0) {
            ForumBounty bounty = new ForumBounty();
            bounty.setPostId(forumPost.getPostId()); // 关联刚才生成的帖子ID
            bounty.setUserId(forumPost.getUserId());
            bounty.setCoins(forumPost.getBountyCoins());
            bounty.setStatus("0"); // 0=待解决

             //设置过期时间，例如默认7天后过期
             bounty.setExpireTime(DateUtils.addDays(DateUtils.getNowDate(), 30));

            forumBountyService.insertForumBounty(bounty);

            // TODO: 在这里调用 UserCoinsService 扣除用户金币余额
            // userCoinsService.deductCoins(forumPost.getUserId(), forumPost.getBountyCoins());
        }

        return rows;
    }

    /**
     * 修改帖子
     *
     * @param forumPost 帖子
     * @return 结果
     */
    @Override
    public int updateForumPost(ForumPost forumPost)
    {
        forumPost.setUpdateTime(DateUtils.getNowDate());
        return forumPostMapper.updateForumPost(forumPost);
    }

    /**
     * 批量删除帖子
     *
     * @param postIds 需要删除的帖子主键
     * @return 结果
     */
    @Override
    public int deleteForumPostByPostIds(Long[] postIds)
    {
        return forumPostMapper.deleteForumPostByPostIds(postIds);
    }

    /**
     * 删除帖子信息
     *
     * @param postId 帖子主键
     * @return 结果
     */
    @Override
    public int deleteForumPostByPostId(Long postId)
    {
        return forumPostMapper.deleteForumPostByPostId(postId);
    }

    @Override
    public List<ForumPost> selectHotThreads() {
        return forumPostMapper.selectHotThreads();
    }
}