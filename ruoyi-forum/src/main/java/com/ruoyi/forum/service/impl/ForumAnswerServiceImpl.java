package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumAnswer;
import com.ruoyi.forum.mapper.ForumAnswerMapper;
import com.ruoyi.forum.service.IForumAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回答Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumAnswerServiceImpl implements IForumAnswerService
{
    @Autowired
    private ForumAnswerMapper forumAnswerMapper;

    /**
     * 查询回答
     *
     * @param answerId 回答主键
     * @return 回答
     */
    @Override
    public ForumAnswer selectForumAnswerByAnswerId(Long answerId)
    {
        return forumAnswerMapper.selectForumAnswerByAnswerId(answerId);
    }

    /**
     * 查询回答列表
     *
     * @param forumAnswer 回答
     * @return 回答
     */
    @Override
    public List<ForumAnswer> selectForumAnswerList(ForumAnswer forumAnswer)
    {
        return forumAnswerMapper.selectForumAnswerList(forumAnswer);
    }

    /**
     * 新增回答
     *
     * @param forumAnswer 回答
     * @return 结果
     */
    @Override
    public int insertForumAnswer(ForumAnswer forumAnswer)
    {
        forumAnswer.setCreateTime(DateUtils.getNowDate());
        return forumAnswerMapper.insertForumAnswer(forumAnswer);
    }

    /**
     * 修改回答
     *
     * @param forumAnswer 回答
     * @return 结果
     */
    @Override
    public int updateForumAnswer(ForumAnswer forumAnswer)
    {
        forumAnswer.setUpdateTime(DateUtils.getNowDate());
        return forumAnswerMapper.updateForumAnswer(forumAnswer);
    }

    /**
     * 批量删除回答
     *
     * @param answerIds 需要删除的回答主键
     * @return 结果
     */
    @Override
    public int deleteForumAnswerByAnswerIds(Long[] answerIds)
    {
        return forumAnswerMapper.deleteForumAnswerByAnswerIds(answerIds);
    }

    /**
     * 删除回答信息
     *
     * @param answerId 回答主键
     * @return 结果
     */
    @Override
    public int deleteForumAnswerByAnswerId(Long answerId)
    {
        return forumAnswerMapper.deleteForumAnswerByAnswerId(answerId);
    }
}