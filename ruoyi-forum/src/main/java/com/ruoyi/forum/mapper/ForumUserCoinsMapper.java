package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumUserCoins;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户金币Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-08
 */
public interface ForumUserCoinsMapper
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
     * 删除用户金币
     *
     * @param coinsId 用户金币主键
     * @return 结果
     */
    public int deleteForumUserCoinsByCoinsId(Long coinsId);

    /**
     * 批量删除用户金币
     *
     * @param coinsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumUserCoinsByCoinsIds(Long[] coinsIds);

    ForumUserCoins selectCoinsByUserId(Long userId);

    int deductCoins(@Param("userId") Long userId, @Param("bountyCoins") Long bountyCoins);

}