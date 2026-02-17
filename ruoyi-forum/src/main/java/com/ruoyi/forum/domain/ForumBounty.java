package com.ruoyi.forum.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 悬赏对象 forum_bounty
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumBounty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 悬赏ID */
    private Long bountyId;

    /** 求助帖ID */
    @Excel(name = "求助帖ID")
    private Long postId;

    /** 悬赏用户ID */
    @Excel(name = "悬赏用户ID")
    private Long userId;

    /** 悬赏金币数 */
    @Excel(name = "悬赏金币数")
    private Long coins;

    /** 状态（0待解决 1已解决 2已过期） */
    @Excel(name = "状态", readConverterExp = "0=待解决,1=已解决,2=已过期")
    private String status;

    /** 采纳的回答ID */
    @Excel(name = "采纳的回答ID")
    private Long acceptedAnswerId;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    public void setBountyId(Long bountyId)
    {
        this.bountyId = bountyId;
    }

    public Long getBountyId()
    {
        return bountyId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setCoins(Long coins)
    {
        this.coins = coins;
    }

    public Long getCoins()
    {
        return coins;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setAcceptedAnswerId(Long acceptedAnswerId)
    {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    public Long getAcceptedAnswerId()
    {
        return acceptedAnswerId;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("bountyId", getBountyId())
                .append("postId", getPostId())
                .append("userId", getUserId())
                .append("coins", getCoins())
                .append("status", getStatus())
                .append("acceptedAnswerId", getAcceptedAnswerId())
                .append("expireTime", getExpireTime())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}