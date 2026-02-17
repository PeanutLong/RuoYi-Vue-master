package com.ruoyi.forum.service;

import com.ruoyi.forum.domain.ForumBoard;

import java.util.List;

/**
 * 论坛板块Service接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface IForumBoardService
{
    /**
     * 查询论坛板块
     *
     * @param boardId 论坛板块主键
     * @return 论坛板块
     */
    public ForumBoard selectForumBoardByBoardId(Long boardId);

    /**
     * 查询论坛板块列表
     *
     * @param forumBoard 论坛板块
     * @return 论坛板块集合
     */
    public List<ForumBoard> selectForumBoardList(ForumBoard forumBoard);

    /**
     * 新增论坛板块
     *
     * @param forumBoard 论坛板块
     * @return 结果
     */
    public int insertForumBoard(ForumBoard forumBoard);

    /**
     * 修改论坛板块
     *
     * @param forumBoard 论坛板块
     * @return 结果
     */
    public int updateForumBoard(ForumBoard forumBoard);

    /**
     * 批量删除论坛板块
     *
     * @param boardIds 需要删除的论坛板块主键集合
     * @return 结果
     */
    public int deleteForumBoardByBoardIds(Long[] boardIds);

    /**
     * 删除论坛板块信息
     *
     * @param boardId 论坛板块主键
     * @return 结果
     */
    public int deleteForumBoardByBoardId(Long boardId);
}