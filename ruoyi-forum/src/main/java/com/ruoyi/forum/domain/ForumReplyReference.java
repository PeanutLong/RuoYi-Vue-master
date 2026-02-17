package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 回复引用对象 forum_reply_reference
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public class ForumReplyReference extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 引用ID */
    private Long referenceId;

    /** 回复ID */
    @Excel(name = "回复ID")
    private Long replyId;

    /** 被引用的回复ID */
    @Excel(name = "被引用的回复ID")
    private Long referencedReplyId;

    /** 被引用用户ID */
    @Excel(name = "被引用用户ID")
    private Long referencedUserId;

    /** 被引用的内容（快照） */
    @Excel(name = "被引用的内容", readConverterExp = "快=照")
    private String referencedContent;

    public void setReferenceId(Long referenceId)
    {
        this.referenceId = referenceId;
    }

    public Long getReferenceId()
    {
        return referenceId;
    }

    public void setReplyId(Long replyId)
    {
        this.replyId = replyId;
    }

    public Long getReplyId()
    {
        return replyId;
    }

    public void setReferencedReplyId(Long referencedReplyId)
    {
        this.referencedReplyId = referencedReplyId;
    }

    public Long getReferencedReplyId()
    {
        return referencedReplyId;
    }

    public void setReferencedUserId(Long referencedUserId)
    {
        this.referencedUserId = referencedUserId;
    }

    public Long getReferencedUserId()
    {
        return referencedUserId;
    }

    public void setReferencedContent(String referencedContent)
    {
        this.referencedContent = referencedContent;
    }

    public String getReferencedContent()
    {
        return referencedContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("referenceId", getReferenceId())
                .append("replyId", getReplyId())
                .append("referencedReplyId", getReferencedReplyId())
                .append("referencedUserId", getReferencedUserId())
                .append("referencedContent", getReferencedContent())
                .append("createTime", getCreateTime())
                .toString();
    }
}