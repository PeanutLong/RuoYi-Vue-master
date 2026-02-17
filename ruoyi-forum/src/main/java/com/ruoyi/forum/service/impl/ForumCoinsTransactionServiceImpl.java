package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumCoinsTransaction;
import com.ruoyi.forum.mapper.ForumCoinsTransactionMapper;
import com.ruoyi.forum.service.IForumCoinsTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金币流水Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumCoinsTransactionServiceImpl implements IForumCoinsTransactionService
{
    @Autowired
    private ForumCoinsTransactionMapper forumCoinsTransactionMapper;

    /**
     * 查询金币流水
     *
     * @param transactionId 金币流水主键
     * @return 金币流水
     */
    @Override
    public ForumCoinsTransaction selectForumCoinsTransactionByTransactionId(Long transactionId)
    {
        return forumCoinsTransactionMapper.selectForumCoinsTransactionByTransactionId(transactionId);
    }

    /**
     * 查询金币流水列表
     *
     * @param forumCoinsTransaction 金币流水
     * @return 金币流水
     */
    @Override
    public List<ForumCoinsTransaction> selectForumCoinsTransactionList(ForumCoinsTransaction forumCoinsTransaction)
    {
        return forumCoinsTransactionMapper.selectForumCoinsTransactionList(forumCoinsTransaction);
    }

    /**
     * 新增金币流水
     *
     * @param forumCoinsTransaction 金币流水
     * @return 结果
     */
    @Override
    public int insertForumCoinsTransaction(ForumCoinsTransaction forumCoinsTransaction)
    {
        forumCoinsTransaction.setCreateTime(DateUtils.getNowDate());
        return forumCoinsTransactionMapper.insertForumCoinsTransaction(forumCoinsTransaction);
    }

    /**
     * 修改金币流水
     *
     * @param forumCoinsTransaction 金币流水
     * @return 结果
     */
    @Override
    public int updateForumCoinsTransaction(ForumCoinsTransaction forumCoinsTransaction)
    {
        return forumCoinsTransactionMapper.updateForumCoinsTransaction(forumCoinsTransaction);
    }

    /**
     * 批量删除金币流水
     *
     * @param transactionIds 需要删除的金币流水主键
     * @return 结果
     */
    @Override
    public int deleteForumCoinsTransactionByTransactionIds(Long[] transactionIds)
    {
        return forumCoinsTransactionMapper.deleteForumCoinsTransactionByTransactionIds(transactionIds);
    }

    /**
     * 删除金币流水信息
     *
     * @param transactionId 金币流水主键
     * @return 结果
     */
    @Override
    public int deleteForumCoinsTransactionByTransactionId(Long transactionId)
    {
        return forumCoinsTransactionMapper.deleteForumCoinsTransactionByTransactionId(transactionId);
    }
}