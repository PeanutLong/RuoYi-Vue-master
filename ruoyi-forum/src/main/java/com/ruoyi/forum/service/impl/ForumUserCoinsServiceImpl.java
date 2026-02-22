package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumUserCoins;
import com.ruoyi.forum.mapper.ForumUserCoinsMapper;
import com.ruoyi.forum.service.IForumUserCoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户金币Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-08
 */
@Service
public class ForumUserCoinsServiceImpl implements IForumUserCoinsService
{
    @Autowired
    private ForumUserCoinsMapper forumUserCoinsMapper;

    /**
     * 查询用户金币
     *
     * @param coinsId 用户金币主键
     * @return 用户金币
     */
    @Override
    public ForumUserCoins selectForumUserCoinsByCoinsId(Long coinsId)
    {
        return forumUserCoinsMapper.selectForumUserCoinsByCoinsId(coinsId);
    }

    /**
     * 查询用户金币列表
     *
     * @param forumUserCoins 用户金币
     * @return 用户金币
     */
    @Override
    public List<ForumUserCoins> selectForumUserCoinsList(ForumUserCoins forumUserCoins)
    {
        return forumUserCoinsMapper.selectForumUserCoinsList(forumUserCoins);
    }

    /**
     * 新增用户金币
     *
     * @param forumUserCoins 用户金币
     * @return 结果
     */
    @Override
    public int insertForumUserCoins(ForumUserCoins forumUserCoins)
    {
        return forumUserCoinsMapper.insertForumUserCoins(forumUserCoins);
    }

    /**
     * 修改用户金币
     *
     * @param forumUserCoins 用户金币
     * @return 结果
     */
    @Override
    public int updateForumUserCoins(ForumUserCoins forumUserCoins)
    {
        forumUserCoins.setUpdateTime(DateUtils.getNowDate());
        return forumUserCoinsMapper.updateForumUserCoins(forumUserCoins);
    }

    /**
     * 批量删除用户金币
     *
     * @param coinsIds 需要删除的用户金币主键
     * @return 结果
     */
    @Override
    public int deleteForumUserCoinsByCoinsIds(Long[] coinsIds)
    {
        return forumUserCoinsMapper.deleteForumUserCoinsByCoinsIds(coinsIds);
    }

    /**
     * 删除用户金币信息
     *
     * @param coinsId 用户金币主键
     * @return 结果
     */
    @Override
    public int deleteForumUserCoinsByCoinsId(Long coinsId)
    {
        return forumUserCoinsMapper.deleteForumUserCoinsByCoinsId(coinsId);
    }

    @Override
    public ForumUserCoins selectCoinsByUserId(Long userId) {
        return forumUserCoinsMapper.selectCoinsByUserId(userId);
    }

    @Override
    public int deductCoins(Long userId, Long bountyCoins) {
        return forumUserCoinsMapper.deductCoins(userId,bountyCoins);
    }
}