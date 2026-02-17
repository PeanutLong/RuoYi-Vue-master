package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumAnswer;

import java.util.List;

/**
 * 回答Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface ForumAnswerMapper
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
     * 删除回答
     *
     * @param answerId 回答主键
     * @return 结果
     */
    public int deleteForumAnswerByAnswerId(Long answerId);

    /**
     * 批量删除回答
     *
     * @param answerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumAnswerByAnswerIds(Long[] answerIds);
}
