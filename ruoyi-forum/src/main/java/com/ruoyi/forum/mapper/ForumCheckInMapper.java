package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumCheckIn;

import java.util.List;

/**
 * 签到Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-05
 */
public interface ForumCheckInMapper
{
    /**
     * 查询签到
     *
     * @param checkId 签到主键
     * @return 签到
     */
    public ForumCheckIn selectForumCheckInByCheckId(Long checkId);

    /**
     * 查询签到列表
     *
     * @param forumCheckIn 签到
     * @return 签到集合
     */
    public List<ForumCheckIn> selectForumCheckInList(ForumCheckIn forumCheckIn);

    /**
     * 新增签到
     *
     * @param forumCheckIn 签到
     * @return 结果
     */
    public int insertForumCheckIn(ForumCheckIn forumCheckIn);

    /**
     * 修改签到
     *
     * @param forumCheckIn 签到
     * @return 结果
     */
    public int updateForumCheckIn(ForumCheckIn forumCheckIn);

    /**
     * 删除签到
     *
     * @param checkId 签到主键
     * @return 结果
     */
    public int deleteForumCheckInByCheckId(Long checkId);

    /**
     * 批量删除签到
     *
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumCheckInByCheckIds(Long[] checkIds);
}