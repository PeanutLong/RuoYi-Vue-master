package com.ruoyi.forum.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.forum.domain.ForumLike;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 点赞Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumLikeService
{
    @Transactional
    public AjaxResult toggleLike(ForumLike forumLike);

    /**
     * 查询点赞
     *
     * @param likeId 点赞主键
     * @return 点赞
     */
    public ForumLike selectForumLikeByLikeId(Long likeId);

    /**
     * 查询点赞列表
     *
     * @param forumLike 点赞
     * @return 点赞集合
     */
    public List<ForumLike> selectForumLikeList(ForumLike forumLike);

    /**
     * 新增点赞
     *
     * @param forumLike 点赞
     * @return 结果
     */
    public int insertForumLike(ForumLike forumLike);

    /**
     * 修改点赞
     *
     * @param forumLike 点赞
     * @return 结果
     */
    public int updateForumLike(ForumLike forumLike);

    /**
     * 批量删除点赞
     *
     * @param likeIds 需要删除的点赞主键集合
     * @return 结果
     */
    public int deleteForumLikeByLikeIds(Long[] likeIds);

    /**
     * 删除点赞信息
     *
     * @param likeId 点赞主键
     * @return 结果
     */
    public int deleteForumLikeByLikeId(Long likeId);
}