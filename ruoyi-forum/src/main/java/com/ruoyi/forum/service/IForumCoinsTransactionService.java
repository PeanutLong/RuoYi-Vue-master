package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumCoinsTransaction;

import java.util.List;

/**
 * 金币流水Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumCoinsTransactionService
{
    /**
     * 查询金币流水
     *
     * @param transactionId 金币流水主键
     * @return 金币流水
     */
    public ForumCoinsTransaction selectForumCoinsTransactionByTransactionId(Long transactionId);

    /**
     * 查询金币流水列表
     *
     * @param forumCoinsTransaction 金币流水
     * @return 金币流水集合
     */
    public List<ForumCoinsTransaction> selectForumCoinsTransactionList(ForumCoinsTransaction forumCoinsTransaction);

    /**
     * 新增金币流水
     *
     * @param forumCoinsTransaction 金币流水
     * @return 结果
     */
    public int insertForumCoinsTransaction(ForumCoinsTransaction forumCoinsTransaction);

    /**
     * 修改金币流水
     *
     * @param forumCoinsTransaction 金币流水
     * @return 结果
     */
    public int updateForumCoinsTransaction(ForumCoinsTransaction forumCoinsTransaction);

    /**
     * 批量删除金币流水
     *
     * @param transactionIds 需要删除的金币流水主键集合
     * @return 结果
     */
    public int deleteForumCoinsTransactionByTransactionIds(Long[] transactionIds);

    /**
     * 删除金币流水信息
     *
     * @param transactionId 金币流水主键
     * @return 结果
     */
    public int deleteForumCoinsTransactionByTransactionId(Long transactionId);
}