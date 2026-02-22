package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumUserCoins;

import java.util.List;

/**
 * 用户金币Service接口
 *
 * @author ruoyi
 * @date 2026-01-08
 */
public interface IForumUserCoinsService
{
    /**
     * 查询用户金币
     *
     * @param coinsId 用户金币主键
     * @return 用户金币
     */
    public ForumUserCoins selectForumUserCoinsByCoinsId(Long coinsId);

    /**
     * 查询用户金币列表
     *
     * @param forumUserCoins 用户金币
     * @return 用户金币集合
     */
    public List<ForumUserCoins> selectForumUserCoinsList(ForumUserCoins forumUserCoins);

    /**
     * 新增用户金币
     *
     * @param forumUserCoins 用户金币
     * @return 结果
     */
    public int insertForumUserCoins(ForumUserCoins forumUserCoins);

    /**
     * 修改用户金币
     *
     * @param forumUserCoins 用户金币
     * @return 结果
     */
    public int updateForumUserCoins(ForumUserCoins forumUserCoins);

    /**
     * 批量删除用户金币
     *
     * @param coinsIds 需要删除的用户金币主键集合
     * @return 结果
     */
    public int deleteForumUserCoinsByCoinsIds(Long[] coinsIds);

    /**
     * 删除用户金币信息
     *
     * @param coinsId 用户金币主键
     * @return 结果
     */
    public int deleteForumUserCoinsByCoinsId(Long coinsId);

    ForumUserCoins selectCoinsByUserId(Long userId);

    int deductCoins(Long userId, Long bountyCoins);

}