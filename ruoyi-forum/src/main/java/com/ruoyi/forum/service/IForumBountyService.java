package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumBounty;

import java.util.List;

/**
 * 悬赏Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumBountyService
{
    /**
     * 查询悬赏
     *
     * @param bountyId 悬赏主键
     * @return 悬赏
     */
    public ForumBounty selectForumBountyByBountyId(Long bountyId);

    /**
     * 查询悬赏列表
     *
     * @param forumBounty 悬赏
     * @return 悬赏集合
     */
    public List<ForumBounty> selectForumBountyList(ForumBounty forumBounty);

    /**
     * 新增悬赏
     *
     * @param forumBounty 悬赏
     * @return 结果
     */
    public int insertForumBounty(ForumBounty forumBounty);

    /**
     * 修改悬赏
     *
     * @param forumBounty 悬赏
     * @return 结果
     */
    public int updateForumBounty(ForumBounty forumBounty);

    /**
     * 批量删除悬赏
     *
     * @param bountyIds 需要删除的悬赏主键集合
     * @return 结果
     */
    public int deleteForumBountyByBountyIds(Long[] bountyIds);

    /**
     * 删除悬赏信息
     *
     * @param bountyId 悬赏主键
     * @return 结果
     */
    public int deleteForumBountyByBountyId(Long bountyId);

    int acceptAnswer(Long postId, Long replyId, Long replyUserId);

}