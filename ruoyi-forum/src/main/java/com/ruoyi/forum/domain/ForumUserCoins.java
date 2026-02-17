package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户金币对象 forum_user_coins
 *
 * @author ruoyi
 * @date 2026-01-08
 */
public class ForumUserCoins extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 金币ID */
    private Long coinsId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 总金币数 */
    @Excel(name = "总金币数")
    private Long totalCoins;

    /** 可用金币数 */
    @Excel(name = "可用金币数")
    private Long availableCoins;

    /** 冻结金币数 */
    @Excel(name = "冻结金币数")
    private Long frozenCoins;

    public void setCoinsId(Long coinsId)
    {
        this.coinsId = coinsId;
    }

    public Long getCoinsId()
    {
        return coinsId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setTotalCoins(Long totalCoins)
    {
        this.totalCoins = totalCoins;
    }

    public Long getTotalCoins()
    {
        return totalCoins;
    }

    public void setAvailableCoins(Long availableCoins)
    {
        this.availableCoins = availableCoins;
    }

    public Long getAvailableCoins()
    {
        return availableCoins;
    }

    public void setFrozenCoins(Long frozenCoins)
    {
        this.frozenCoins = frozenCoins;
    }

    public Long getFrozenCoins()
    {
        return frozenCoins;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("coinsId", getCoinsId())
                .append("userId", getUserId())
                .append("totalCoins", getTotalCoins())
                .append("availableCoins", getAvailableCoins())
                .append("frozenCoins", getFrozenCoins())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public ForumUserCoins(Long userId, Long totalCoins, Long availableCoins, Long frozenCoins) {
        this.userId = userId;
        this.totalCoins = totalCoins;
        this.availableCoins = availableCoins;
        this.frozenCoins = frozenCoins;
    }
}