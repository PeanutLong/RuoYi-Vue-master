package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.forum.domain.ForumCheckIn;
import com.ruoyi.forum.domain.ForumCoinsTransaction;
import com.ruoyi.forum.domain.ForumUserCoins;
import com.ruoyi.forum.mapper.ForumCheckInMapper;
import com.ruoyi.forum.service.IForumCheckInService;
import com.ruoyi.forum.service.IForumCoinsTransactionService;
import com.ruoyi.forum.service.IForumUserCoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 签到Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */


@Service
public class ForumCheckInServiceImpl implements IForumCheckInService
{
    @Autowired
    private ForumCheckInMapper forumCheckInMapper;

    @Autowired
    private IForumUserCoinsService userCoinsService; // 用户金币服务

    @Autowired
    private IForumCoinsTransactionService transactionService; // 金币流水服务

    /**
     * 查询签到
     */
    @Override
    public ForumCheckIn selectForumCheckInByCheckId(Long checkId)
    {
        return forumCheckInMapper.selectForumCheckInByCheckId(checkId);
    }

    /**
     * 查询签到列表
     */
    @Override
    public List<ForumCheckIn> selectForumCheckInList(ForumCheckIn forumCheckIn)
    {
        return forumCheckInMapper.selectForumCheckInList(forumCheckIn);
    }

    /**
     * 新增签到（核心业务逻辑：签到 + 加金币 + 记流水）
     * * @param forumCheckIn 签到信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，任何一步失败都会回滚
    public int insertForumCheckIn(ForumCheckIn forumCheckIn)
    {
        // 0. 准备基础数据
        Long userId = forumCheckIn.getUserId();
        // 如果Controller没传userId，这里尝试获取（安全起见）
        if (userId == null) {
            userId = SecurityUtils.getUserId();
            forumCheckIn.setUserId(userId);
        }

        Long rewardAmount = 5L; // 固定奖励 5 金币

        // 1. 填充签到实体数据
        if (forumCheckIn.getCheckDate() == null) {
            forumCheckIn.setCheckDate(DateUtils.getNowDate()); // 使用 checkDate
        }
        forumCheckIn.setCoinsAwarded(rewardAmount); // 记录本次获得了多少金币

        // TODO: 这里暂时默认为1天。如果需要计算“连续签到”，需要查询上一次签到日期与昨天对比
        forumCheckIn.setStreakDays(1L);

        forumCheckIn.setCreateTime(DateUtils.getNowDate());

        // 2. 插入签到记录
        int rows = forumCheckInMapper.insertForumCheckIn(forumCheckIn);
        if (rows <= 0) {
            return 0; // 插入失败
        }

        // 3. 更新用户金币表 (ForumUserCoins)
        ForumUserCoins userCoins = userCoinsService.selectCoinsByUserId(userId);

        // 记录更新后的余额，用于写入流水
        Long balanceAfter = 0L;

        if (userCoins == null) {
            // 如果用户从未有过金币记录，初始化一条
            userCoins = new ForumUserCoins(userId, rewardAmount, rewardAmount, 0L);
            userCoins.setCreateTime(DateUtils.getNowDate());
            userCoinsService.insertForumUserCoins(userCoins);
            balanceAfter = rewardAmount;
        } else {
            // 如果已有记录，累加金币
            userCoins.setTotalCoins(userCoins.getTotalCoins() + rewardAmount);
            userCoins.setAvailableCoins(userCoins.getAvailableCoins() + rewardAmount);
            userCoinsService.updateForumUserCoins(userCoins);
            balanceAfter = userCoins.getAvailableCoins();
        }

        // 4. 插入金币流水 (ForumCoinsTransaction)
        ForumCoinsTransaction transaction = new ForumCoinsTransaction();
        transaction.setUserId(userId);
        transaction.setAmount(rewardAmount); // +5
        transaction.setBalanceAfter(balanceAfter); // 当前余额
        transaction.setTransactionType("1"); // 1 代表“签到”
        transaction.setRelatedId(forumCheckIn.getCheckId()); // 关联刚才生成的签到ID
        transaction.setDescription("每日签到奖励");
        transaction.setCreateTime(DateUtils.getNowDate());

        transactionService.insertForumCoinsTransaction(transaction);

        return rows;
    }

    /**
     * 修改签到
     */
    @Override
    public int updateForumCheckIn(ForumCheckIn forumCheckIn)
    {
        return forumCheckInMapper.updateForumCheckIn(forumCheckIn);
    }

    /**
     * 批量删除签到
     */
    @Override
    public int deleteForumCheckInByCheckIds(Long[] checkIds)
    {
        return forumCheckInMapper.deleteForumCheckInByCheckIds(checkIds);
    }

    /**
     * 删除签到信息
     */
    @Override
    public int deleteForumCheckInByCheckId(Long checkId)
    {
        return forumCheckInMapper.deleteForumCheckInByCheckId(checkId);
    }
}