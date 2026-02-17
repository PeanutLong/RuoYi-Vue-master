package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumAnswer;

import java.util.List;

/**
 * 回答Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumAnswerService
{
    /**
     * 查询回答
     *
     * @param answerId 回答主键
     * @return 回答
     */
    public ForumAnswer selectForumAnswerByAnswerId(Long answerId);

    /**
     * 查询回答列表
     *
     * @param forumAnswer 回答
     * @return 回答集合
     */
    public List<ForumAnswer> selectForumAnswerList(ForumAnswer forumAnswer);

    /**
     * 新增回答
     *
     * @param forumAnswer 回答
     * @return 结果
     */
    public int insertForumAnswer(ForumAnswer forumAnswer);

    /**
     * 修改回答
     *
     * @param forumAnswer 回答
     * @return 结果
     */
    public int updateForumAnswer(ForumAnswer forumAnswer);

    /**
     * 批量删除回答
     *
     * @param answerIds 需要删除的回答主键集合
     * @return 结果
     */
    public int deleteForumAnswerByAnswerIds(Long[] answerIds);

    /**
     * 删除回答信息
     *
     * @param answerId 回答主键
     * @return 结果
     */
    public int deleteForumAnswerByAnswerId(Long answerId);
}