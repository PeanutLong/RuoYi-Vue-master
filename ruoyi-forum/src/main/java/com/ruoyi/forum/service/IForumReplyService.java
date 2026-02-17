package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumReply;

import java.util.List;

/**
 * 回复Service接口
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public interface IForumReplyService
{
    /**
     * 查询回复
     *
     * @param replyId 回复主键
     * @return 回复
     */
    public ForumReply selectForumReplyByReplyId(Long replyId);

    /**
     * 查询回复列表
     *
     * @param forumReply 回复
     * @return 回复集合
     */
    public List<ForumReply> selectForumReplyList(ForumReply forumReply);

    /**
     * 新增回复
     *
     * @param forumReply 回复
     * @return 结果
     */
    public int insertForumReply(ForumReply forumReply);

    /**
     * 修改回复
     *
     * @param forumReply 回复
     * @return 结果
     */
    public int updateForumReply(ForumReply forumReply);

    /**
     * 批量删除回复
     *
     * @param replyIds 需要删除的回复主键集合
     * @return 结果
     */
    public int deleteForumReplyByReplyIds(Long[] replyIds);

    /**
     * 删除回复信息
     *
     * @param replyId 回复主键
     * @return 结果
     */
    public int deleteForumReplyByReplyId(Long replyId);
}