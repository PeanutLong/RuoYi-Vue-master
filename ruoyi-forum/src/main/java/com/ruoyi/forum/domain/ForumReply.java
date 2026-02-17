package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 回复对象 forum_reply
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public class ForumReply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 回复ID */
    private Long replyId;

    /** 帖子ID */
    @Excel(name = "帖子ID")
    private Long postId;

    /** 回复用户ID */
    @Excel(name = "回复用户ID")
    private Long userId;

    /** 父回复ID（0表示直接回复帖子） */
    @Excel(name = "父回复ID", readConverterExp = "0=表示直接回复帖子")
    private Long parentReplyId;

    /** 根回复ID（用于构建回复树） */
    @Excel(name = "根回复ID", readConverterExp = "用=于构建回复树")
    private Long rootReplyId;

    /** 回复内容 */
    @Excel(name = "回复内容")
    private String content;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 子回复数 */
    @Excel(name = "子回复数")
    private Long replyCount;

    /** 状态（0正常 1删除） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=删除")
    private String status;

    /** 是否是楼主回复（0否 1是） */
    @Excel(name = "是否是楼主回复", readConverterExp = "0=否,1=是")
    private String isAuthor;

    /** 楼层号 */
    @Excel(name = "楼层号")
    private Long floorNum;

    public void setReplyId(Long replyId)
    {
        this.replyId = replyId;
    }

    public Long getReplyId()
    {
        return replyId;
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

    public void setParentReplyId(Long parentReplyId)
    {
        this.parentReplyId = parentReplyId;
    }

    public Long getParentReplyId()
    {
        return parentReplyId;
    }

    public void setRootReplyId(Long rootReplyId)
    {
        this.rootReplyId = rootReplyId;
    }

    public Long getRootReplyId()
    {
        return rootReplyId;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount()
    {
        return likeCount;
    }

    public void setReplyCount(Long replyCount)
    {
        this.replyCount = replyCount;
    }

    public Long getReplyCount()
    {
        return replyCount;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setIsAuthor(String isAuthor)
    {
        this.isAuthor = isAuthor;
    }

    public String getIsAuthor()
    {
        return isAuthor;
    }

    public void setFloorNum(Long floorNum)
    {
        this.floorNum = floorNum;
    }

    public Long getFloorNum()
    {
        return floorNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("replyId", getReplyId())
                .append("postId", getPostId())
                .append("userId", getUserId())
                .append("parentReplyId", getParentReplyId())
                .append("rootReplyId", getRootReplyId())
                .append("content", getContent())
                .append("likeCount", getLikeCount())
                .append("replyCount", getReplyCount())
                .append("status", getStatus())
                .append("isAuthor", getIsAuthor())
                .append("floorNum", getFloorNum())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}