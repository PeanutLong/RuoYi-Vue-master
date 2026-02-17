package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumPostTag;

import java.util.List;

/**
 * 帖子标签关联Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumPostTagService
{
    /**
     * 查询帖子标签关联
     *
     * @param id 帖子标签关联主键
     * @return 帖子标签关联
     */
    public ForumPostTag selectForumPostTagById(Long id);

    /**
     * 查询帖子标签关联列表
     *
     * @param forumPostTag 帖子标签关联
     * @return 帖子标签关联集合
     */
    public List<ForumPostTag> selectForumPostTagList(ForumPostTag forumPostTag);

    /**
     * 新增帖子标签关联
     *
     * @param forumPostTag 帖子标签关联
     * @return 结果
     */
    public int insertForumPostTag(ForumPostTag forumPostTag);

    /**
     * 修改帖子标签关联
     *
     * @param forumPostTag 帖子标签关联
     * @return 结果
     */
    public int updateForumPostTag(ForumPostTag forumPostTag);

    /**
     * 批量删除帖子标签关联
     *
     * @param ids 需要删除的帖子标签关联主键集合
     * @return 结果
     */
    public int deleteForumPostTagByIds(Long[] ids);

    /**
     * 删除帖子标签关联信息
     *
     * @param id 帖子标签关联主键
     * @return 结果
     */
    public int deleteForumPostTagById(Long id);
}