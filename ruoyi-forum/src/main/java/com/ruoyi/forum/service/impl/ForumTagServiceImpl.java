package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumTag;
import com.ruoyi.forum.mapper.ForumTagMapper;
import com.ruoyi.forum.service.IForumTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumTagServiceImpl implements IForumTagService
{
    @Autowired
    private ForumTagMapper forumTagMapper;

    /**
     * 查询标签
     *
     * @param tagId 标签主键
     * @return 标签
     */
    @Override
    public ForumTag selectForumTagByTagId(Long tagId)
    {
        return forumTagMapper.selectForumTagByTagId(tagId);
    }

    /**
     * 查询标签列表
     *
     * @param forumTag 标签
     * @return 标签
     */
    @Override
    public List<ForumTag> selectForumTagList(ForumTag forumTag)
    {
        return forumTagMapper.selectForumTagList(forumTag);
    }

    /**
     * 新增标签
     *
     * @param forumTag 标签
     * @return 结果
     */
    @Override
    public int insertForumTag(ForumTag forumTag)
    {
        forumTag.setCreateTime(DateUtils.getNowDate());
        return forumTagMapper.insertForumTag(forumTag);
    }

    /**
     * 修改标签
     *
     * @param forumTag 标签
     * @return 结果
     */
    @Override
    public int updateForumTag(ForumTag forumTag)
    {
        forumTag.setUpdateTime(DateUtils.getNowDate());
        return forumTagMapper.updateForumTag(forumTag);
    }

    /**
     * 批量删除标签
     *
     * @param tagIds 需要删除的标签主键
     * @return 结果
     */
    @Override
    public int deleteForumTagByTagIds(Long[] tagIds)
    {
        return forumTagMapper.deleteForumTagByTagIds(tagIds);
    }

    /**
     * 删除标签信息
     *
     * @param tagId 标签主键
     * @return 结果
     */
    @Override
    public int deleteForumTagByTagId(Long tagId)
    {
        return forumTagMapper.deleteForumTagByTagId(tagId);
    }
}