package com.ruoyi.forum.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 签到对象 forum_check_in
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumCheckIn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签到ID */
    private Long checkId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 签到日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签到日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /** 获得金币数 */
    @Excel(name = "获得金币数")
    private Long coinsAwarded;

    /** 连续签到天数 */
    @Excel(name = "连续签到天数")
    private Long streakDays;

    public void setCheckId(Long checkId)
    {
        this.checkId = checkId;
    }

    public Long getCheckId()
    {
        return checkId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setCoinsAwarded(Long coinsAwarded)
    {
        this.coinsAwarded = coinsAwarded;
    }

    public Long getCoinsAwarded()
    {
        return coinsAwarded;
    }

    public void setStreakDays(Long streakDays)
    {
        this.streakDays = streakDays;
    }

    public Long getStreakDays()
    {
        return streakDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("checkId", getCheckId())
                .append("userId", getUserId())
                .append("checkDate", getCheckDate())
                .append("coinsAwarded", getCoinsAwarded())
                .append("streakDays", getStreakDays())
                .append("createTime", getCreateTime())
                .toString();
    }
}