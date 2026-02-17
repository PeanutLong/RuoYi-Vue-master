package com.ruoyi.forum.mapper;

import java.util.List;
import com.ruoyi.forum.domain.ForumBoard;

/**
 * 论坛板块Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface ForumBoardMapper
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
     * 删除论坛板块
     *
     * @param boardId 论坛板块主键
     * @return 结果
     */
    public int deleteForumBoardByBoardId(Long boardId);

    /**
     * 批量删除论坛板块
     *
     * @param boardIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumBoardByBoardIds(Long[] boardIds);
}