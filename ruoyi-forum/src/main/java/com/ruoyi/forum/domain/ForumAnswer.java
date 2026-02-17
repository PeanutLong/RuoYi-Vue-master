package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 回答对象 forum_answer
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 回答ID */
    private Long answerId;

    /** 求助帖ID */
    @Excel(name = "求助帖ID")
    private Long postId;

    /** 回答用户ID */
    @Excel(name = "回答用户ID")
    private Long userId;

    /** 回答内容 */
    @Excel(name = "回答内容")
    private String content;

    /** 是否被采纳（0否 1是） */
    @Excel(name = "是否被采纳", readConverterExp = "0=否,1=是")
    private String isAccepted;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    public void setAnswerId(Long answerId)
    {
        this.answerId = answerId;
    }

    public Long getAnswerId()
    {
        return answerId;
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

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setIsAccepted(String isAccepted)
    {
        this.isAccepted = isAccepted;
    }

    public String getIsAccepted()
    {
        return isAccepted;
    }

    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount()
    {
        return likeCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("answerId", getAnswerId())
                .append("postId", getPostId())
                .append("userId", getUserId())
                .append("content", getContent())
                .append("isAccepted", getIsAccepted())
                .append("likeCount", getLikeCount())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}