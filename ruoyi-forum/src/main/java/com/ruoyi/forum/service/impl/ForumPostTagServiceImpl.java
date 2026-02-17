package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumPostTag;
import com.ruoyi.forum.mapper.ForumPostTagMapper;
import com.ruoyi.forum.service.IForumPostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帖子标签关联Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumPostTagServiceImpl implements IForumPostTagService
{
    @Autowired
    private ForumPostTagMapper forumPostTagMapper;

    /**
     * 查询帖子标签关联
     *
     * @param id 帖子标签关联主键
     * @return 帖子标签关联
     */
    @Override
    public ForumPostTag selectForumPostTagById(Long id)
    {
        return forumPostTagMapper.selectForumPostTagById(id);
    }

    /**
     * 查询帖子标签关联列表
     *
     * @param forumPostTag 帖子标签关联
     * @return 帖子标签关联
     */
    @Override
    public List<ForumPostTag> selectForumPostTagList(ForumPostTag forumPostTag)
    {
        return forumPostTagMapper.selectForumPostTagList(forumPostTag);
    }

    /**
     * 新增帖子标签关联
     *
     * @param forumPostTag 帖子标签关联
     * @return 结果
     */
    @Override
    public int insertForumPostTag(ForumPostTag forumPostTag)
    {
        forumPostTag.setCreateTime(DateUtils.getNowDate());
        return forumPostTagMapper.insertForumPostTag(forumPostTag);
    }

    /**
     * 修改帖子标签关联
     *
     * @param forumPostTag 帖子标签关联
     * @return 结果
     */
    @Override
    public int updateForumPostTag(ForumPostTag forumPostTag)
    {
        return forumPostTagMapper.updateForumPostTag(forumPostTag);
    }

    /**
     * 批量删除帖子标签关联
     *
     * @param ids 需要删除的帖子标签关联主键
     * @return 结果
     */
    @Override
    public int deleteForumPostTagByIds(Long[] ids)
    {
        return forumPostTagMapper.deleteForumPostTagByIds(ids);
    }

    /**
     * 删除帖子标签关联信息
     *
     * @param id 帖子标签关联主键
     * @return 结果
     */
    @Override
    public int deleteForumPostTagById(Long id)
    {
        return forumPostTagMapper.deleteForumPostTagById(id);
    }
}