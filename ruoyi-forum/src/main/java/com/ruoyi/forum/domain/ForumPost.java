package com.ruoyi.forum.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 帖子对象 forum_post
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 悬赏金额（非数据库字段，仅用于传参） */
    private Long bountyCoins;

    /** 接受的答案ID （非数据库字段）*/
    private Long acceptedAnswerId;

    public Long getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(Long acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    /** 帖子ID */
    private Long postId;

    /** 所属板块ID */
    @Excel(name = "所属板块ID")
    private Long boardId;

    /** 发布用户ID */
    @Excel(name = "发布用户ID")
    private Long userId;

    /** 帖子标题 */
    @Excel(name = "帖子标题")
    private String title;

    /** 帖子内容 */
    @Excel(name = "帖子内容")
    private String content;

    /** 帖子类型（0普通 1求助） */
    @Excel(name = "帖子类型", readConverterExp = "0=普通,1=求助")
    private String postType;

    /** 浏览数 */
    @Excel(name = "浏览数")
    private Long viewCount;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 评论数 */
    @Excel(name = "评论数")
    private Long commentCount;

    /** 是否置顶（0否 1是） */
    @Excel(name = "是否置顶", readConverterExp = "0=否,1=是")
    private String isTop;

    /** 是否精华（0否 1是） */
    @Excel(name = "是否精华", readConverterExp = "0=否,1=是")
    private String isEssence;

    /** 状态（0正常 1关闭 2删除） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=关闭,2=删除")
    private String status;

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public void setBountyCoins(Long bountyCoins)
    {
        this.bountyCoins = bountyCoins;
    }

    public Long getBountyCoins()
    {
        return bountyCoins;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setBoardId(Long boardId)
    {
        this.boardId = boardId;
    }

    public Long getBoardId()
    {
        return boardId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setPostType(String postType)
    {
        this.postType = postType;
    }

    public String getPostType()
    {
        return postType;
    }

    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount()
    {
        return viewCount;
    }

    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount()
    {
        return likeCount;
    }

    public void setCommentCount(Long commentCount)
    {
        this.commentCount = commentCount;
    }

    public Long getCommentCount()
    {
        return commentCount;
    }

    public void setIsTop(String isTop)
    {
        this.isTop = isTop;
    }

    public String getIsTop()
    {
        return isTop;
    }

    public void setIsEssence(String isEssence)
    {
        this.isEssence = isEssence;
    }

    public String getIsEssence()
    {
        return isEssence;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("postId", getPostId())
                .append("boardId", getBoardId())
                .append("userId", getUserId())
                .append("title", getTitle())
                .append("content", getContent())
                .append("postType", getPostType())
                .append("viewCount", getViewCount())
                .append("likeCount", getLikeCount())
                .append("commentCount", getCommentCount())
                .append("isTop", getIsTop())
                .append("isEssence", getIsEssence())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}