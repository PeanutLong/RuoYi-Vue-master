package com.ruoyi.forum.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 点赞对象 forum_like
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumLike extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点赞ID */
    private Long likeId;

    /** 帖子ID */
    @Excel(name = "帖子ID")
    private Long postId;

    /** 点赞用户ID */
    @Excel(name = "点赞用户ID")
    private Long userId;

    /** 点赞类型（0帖子 1回答） */
    @Excel(name = "点赞类型", readConverterExp = "0=帖子,1=回答")
    private String likeType;

    /** 目标ID（当like_type=1时，为answer_id） */
    @Excel(name = "目标ID", readConverterExp = "当=like_type=1时，为answer_id")
    private Long targetId;

    public void setLikeId(Long likeId)
    {
        this.likeId = likeId;
    }

    public Long getLikeId()
    {
        return likeId;
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

    public void setLikeType(String likeType)
    {
        this.likeType = likeType;
    }

    public String getLikeType()
    {
        return likeType;
    }

    public void setTargetId(Long targetId)
    {
        this.targetId = targetId;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("likeId", getLikeId())
                .append("postId", getPostId())
                .append("userId", getUserId())
                .append("likeType", getLikeType())
                .append("targetId", getTargetId())
                .append("createTime", getCreateTime())
                .toString();
    }
}