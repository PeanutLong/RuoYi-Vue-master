package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 金币流水对象 forum_coins_transaction
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumCoinsTransaction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流水ID */
    private Long transactionId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 变动金额 */
    @Excel(name = "变动金额")
    private Long amount;

    /** 变动后余额 */
    @Excel(name = "变动后余额")
    private Long balanceAfter;

    /** 交易类型（1签到 2悬赏支出 3悬赏收入 4系统奖励） */
    @Excel(name = "交易类型", readConverterExp = "1=签到,2=悬赏支出,3=悬赏收入,4=系统奖励")
    private String transactionType;

    /** 关联ID（如签到ID、悬赏ID等） */
    @Excel(name = "关联ID", readConverterExp = "如=签到ID、悬赏ID等")
    private Long relatedId;

    /** 交易描述 */
    @Excel(name = "交易描述")
    private String description;

    public void setTransactionId(Long transactionId)
    {
        this.transactionId = transactionId;
    }

    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setAmount(Long amount)
    {
        this.amount = amount;
    }

    public Long getAmount()
    {
        return amount;
    }

    public void setBalanceAfter(Long balanceAfter)
    {
        this.balanceAfter = balanceAfter;
    }

    public Long getBalanceAfter()
    {
        return balanceAfter;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setRelatedId(Long relatedId)
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId()
    {
        return relatedId;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("transactionId", getTransactionId())
                .append("userId", getUserId())
                .append("amount", getAmount())
                .append("balanceAfter", getBalanceAfter())
                .append("transactionType", getTransactionType())
                .append("relatedId", getRelatedId())
                .append("description", getDescription())
                .append("createTime", getCreateTime())
                .toString();
    }
}