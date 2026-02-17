package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumTag;

import java.util.List;

/**
 * 标签Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface ForumTagMapper
{
    /**
     * 查询标签
     *
     * @param tagId 标签主键
     * @return 标签
     */
    public ForumTag selectForumTagByTagId(Long tagId);

    /**
     * 查询标签列表
     *
     * @param forumTag 标签
     * @return 标签集合
     */
    public List<ForumTag> selectForumTagList(ForumTag forumTag);

    /**
     * 新增标签
     *
     * @param forumTag 标签
     * @return 结果
     */
    public int insertForumTag(ForumTag forumTag);

    /**
     * 修改标签
     *
     * @param forumTag 标签
     * @return 结果
     */
    public int updateForumTag(ForumTag forumTag);

    /**
     * 删除标签
     *
     * @param tagId 标签主键
     * @return 结果
     */
    public int deleteForumTagByTagId(Long tagId);

    /**
     * 批量删除标签
     *
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumTagByTagIds(Long[] tagIds);
}