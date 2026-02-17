package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumPost;

import java.util.List;

/**
 * 帖子Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumPostService
{
    /**
     * 查询帖子
     *
     * @param postId 帖子主键
     * @return 帖子
     */
    public ForumPost selectForumPostByPostId(Long postId);

    /**
     * 查询帖子列表
     *
     * @param forumPost 帖子
     * @return 帖子集合
     */
    public List<ForumPost> selectForumPostList(ForumPost forumPost);

    /**
     * 新增帖子
     *
     * @param forumPost 帖子
     * @return 结果
     */
    public int insertForumPost(ForumPost forumPost);

    /**
     * 修改帖子
     *
     * @param forumPost 帖子
     * @return 结果
     */
    public int updateForumPost(ForumPost forumPost);

    /**
     * 批量删除帖子
     *
     * @param postIds 需要删除的帖子主键集合
     * @return 结果
     */
    public int deleteForumPostByPostIds(Long[] postIds);

    /**
     * 删除帖子信息
     *
     * @param postId 帖子主键
     * @return 结果
     */
    public int deleteForumPostByPostId(Long postId);

    List<ForumPost> selectHotThreads();
}