package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumBoard;
import com.ruoyi.forum.mapper.ForumBoardMapper;
import com.ruoyi.forum.service.IForumBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 论坛板块Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumBoardServiceImpl implements IForumBoardService
{
    @Autowired
    private ForumBoardMapper forumBoardMapper;

    /**
     * 查询论坛板块
     *
     * @param boardId 论坛板块主键
     * @return 论坛板块
     */
    @Override
    public ForumBoard selectForumBoardByBoardId(Long boardId)
    {
        return forumBoardMapper.selectForumBoardByBoardId(boardId);
    }

    /**
     * 查询论坛板块列表
     *
     * @param forumBoard 论坛板块
     * @return 论坛板块
     */
    @Override
    public List<ForumBoard> selectForumBoardList(ForumBoard forumBoard)
    {
        return forumBoardMapper.selectForumBoardList(forumBoard);
    }

    /**
     * 新增论坛板块
     *
     * @param forumBoard 论坛板块
     * @return 结果
     */
    @Override
    public int insertForumBoard(ForumBoard forumBoard)
    {
        forumBoard.setCreateTime(DateUtils.getNowDate());
        return forumBoardMapper.insertForumBoard(forumBoard);
    }

    /**
     * 修改论坛板块
     *
     * @param forumBoard 论坛板块
     * @return 结果
     */
    @Override
    public int updateForumBoard(ForumBoard forumBoard)
    {
        forumBoard.setUpdateTime(DateUtils.getNowDate());
        return forumBoardMapper.updateForumBoard(forumBoard);
    }

    /**
     * 批量删除论坛板块
     *
     * @param boardIds 需要删除的论坛板块主键
     * @return 结果
     */
    @Override
    public int deleteForumBoardByBoardIds(Long[] boardIds)
    {
        return forumBoardMapper.deleteForumBoardByBoardIds(boardIds);
    }

    /**
     * 删除论坛板块信息
     *
     * @param boardId 论坛板块主键
     * @return 结果
     */
    @Override
    public int deleteForumBoardByBoardId(Long boardId)
    {
        return forumBoardMapper.deleteForumBoardByBoardId(boardId);
    }
}
