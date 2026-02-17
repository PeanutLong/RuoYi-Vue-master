package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumReplyReference;

import java.util.List;

/**
 * 回复引用Service接口
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public interface IForumReplyReferenceService
{
    /**
     * 查询回复引用
     *
     * @param referenceId 回复引用主键
     * @return 回复引用
     */
    public ForumReplyReference selectForumReplyReferenceByReferenceId(Long referenceId);

    /**
     * 查询回复引用列表
     *
     * @param forumReplyReference 回复引用
     * @return 回复引用集合
     */
    public List<ForumReplyReference> selectForumReplyReferenceList(ForumReplyReference forumReplyReference);

    /**
     * 新增回复引用
     *
     * @param forumReplyReference 回复引用
     * @return 结果
     */
    public int insertForumReplyReference(ForumReplyReference forumReplyReference);

    /**
     * 修改回复引用
     *
     * @param forumReplyReference 回复引用
     * @return 结果
     */
    public int updateForumReplyReference(ForumReplyReference forumReplyReference);

    /**
     * 批量删除回复引用
     *
     * @param referenceIds 需要删除的回复引用主键集合
     * @return 结果
     */
    public int deleteForumReplyReferenceByReferenceIds(Long[] referenceIds);

    /**
     * 删除回复引用信息
     *
     * @param referenceId 回复引用主键
     * @return 结果
     */
    public int deleteForumReplyReferenceByReferenceId(Long referenceId);
}