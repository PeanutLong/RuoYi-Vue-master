package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 回复图片对象 forum_reply_image
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public class ForumReplyImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long imageId;

    /** 回复ID */
    @Excel(name = "回复ID")
    private Long replyId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String imageUrl;

    /** 缩略图URL */
    @Excel(name = "缩略图URL")
    private String thumbnailUrl;

    /** 图片大小（字节） */
    @Excel(name = "图片大小", readConverterExp = "字=节")
    private Long imageSize;

    /** 原文件名 */
    @Excel(name = "原文件名")
    private String imageName;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    public void setImageId(Long imageId)
    {
        this.imageId = imageId;
    }

    public Long getImageId()
    {
        return imageId;
    }

    public void setReplyId(Long replyId)
    {
        this.replyId = replyId;
    }

    public Long getReplyId()
    {
        return replyId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public void setImageSize(Long imageSize)
    {
        this.imageSize = imageSize;
    }

    public Long getImageSize()
    {
        return imageSize;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setSortOrder(Long sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder()
    {
        return sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("imageId", getImageId())
                .append("replyId", getReplyId())
                .append("userId", getUserId())
                .append("imageUrl", getImageUrl())
                .append("thumbnailUrl", getThumbnailUrl())
                .append("imageSize", getImageSize())
                .append("imageName", getImageName())
                .append("sortOrder", getSortOrder())
                .append("createTime", getCreateTime())
                .toString();
    }
}