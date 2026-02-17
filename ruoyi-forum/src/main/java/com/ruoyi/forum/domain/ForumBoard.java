package com.ruoyi.forum.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 论坛板块对象 forum_board
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public class ForumBoard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 板块ID */
    private Long boardId;

    /** 板块名称 */
    @Excel(name = "板块名称")
    private String boardName;

    /** 板块描述 */
    @Excel(name = "板块描述")
    private String description;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setBoardId(Long boardId)
    {
        this.boardId = boardId;
    }

    public Long getBoardId()
    {
        return boardId;
    }

    public void setBoardName(String boardName)
    {
        this.boardName = boardName;
    }

    public String getBoardName()
    {
        return boardName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setSortOrder(Long sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder()
    {
        return sortOrder;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("boardId", getBoardId())
                .append("boardName", getBoardName())
                .append("description", getDescription())
                .append("sortOrder", getSortOrder())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}