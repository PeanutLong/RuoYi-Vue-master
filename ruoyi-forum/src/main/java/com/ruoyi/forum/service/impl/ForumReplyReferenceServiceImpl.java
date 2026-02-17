package com.ruoyi.forum.service.impl;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumReplyReference;
import com.ruoyi.forum.mapper.ForumReplyReferenceMapper;
import com.ruoyi.forum.service.IForumReplyReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回复引用Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@Service
public class ForumReplyReferenceServiceImpl implements IForumReplyReferenceService
{
    @Autowired
    private ForumReplyReferenceMapper forumReplyReferenceMapper;

    /**
     * 查询回复引用
     *
     * @param referenceId 回复引用主键
     * @return 回复引用
     */
    @Override
    public ForumReplyReference selectForumReplyReferenceByReferenceId(Long referenceId)
    {
        return forumReplyReferenceMapper.selectForumReplyReferenceByReferenceId(referenceId);
    }

    /**
     * 查询回复引用列表
     *
     * @param forumReplyReference 回复引用
     * @return 回复引用
     */
    @Override
    public List<ForumReplyReference> selectForumReplyReferenceList(ForumReplyReference forumReplyReference)
    {
        return forumReplyReferenceMapper.selectForumReplyReferenceList(forumReplyReference);
    }

    /**
     * 新增回复引用
     *
     * @param forumReplyReference 回复引用
     * @return 结果
     */
    @Override
    public int insertForumReplyReference(ForumReplyReference forumReplyReference)
    {
        forumReplyReference.setCreateTime(DateUtils.getNowDate());
        return forumReplyReferenceMapper.insertForumReplyReference(forumReplyReference);
    }

    /**
     * 修改回复引用
     *
     * @param forumReplyReference 回复引用
     * @return 结果
     */
    @Override
    public int updateForumReplyReference(ForumReplyReference forumReplyReference)
    {
        return forumReplyReferenceMapper.updateForumReplyReference(forumReplyReference);
    }

    /**
     * 批量删除回复引用
     *
     * @param referenceIds 需要删除的回复引用主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyReferenceByReferenceIds(Long[] referenceIds)
    {
        return forumReplyReferenceMapper.deleteForumReplyReferenceByReferenceIds(referenceIds);
    }

    /**
     * 删除回复引用信息
     *
     * @param referenceId 回复引用主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyReferenceByReferenceId(Long referenceId)
    {
        return forumReplyReferenceMapper.deleteForumReplyReferenceByReferenceId(referenceId);
    }
}