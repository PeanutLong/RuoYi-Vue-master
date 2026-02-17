package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumBounty;
import com.ruoyi.forum.mapper.ForumBountyMapper;
import com.ruoyi.forum.service.IForumBountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 悬赏Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@Service
public class ForumBountyServiceImpl implements IForumBountyService
{
    @Autowired
    private ForumBountyMapper forumBountyMapper;

    /**
     * 查询悬赏
     *
     * @param bountyId 悬赏主键
     * @return 悬赏
     */
    @Override
    public ForumBounty selectForumBountyByBountyId(Long bountyId)
    {
        return forumBountyMapper.selectForumBountyByBountyId(bountyId);
    }

    /**
     * 查询悬赏列表
     *
     * @param forumBounty 悬赏
     * @return 悬赏
     */
    @Override
    public List<ForumBounty> selectForumBountyList(ForumBounty forumBounty)
    {
        return forumBountyMapper.selectForumBountyList(forumBounty);
    }

    /**
     * 新增悬赏
     *
     * @param forumBounty 悬赏
     * @return 结果
     */
    @Override
    public int insertForumBounty(ForumBounty forumBounty)
    {
        forumBounty.setCreateTime(DateUtils.getNowDate());
        return forumBountyMapper.insertForumBounty(forumBounty);
    }

    /**
     * 修改悬赏
     *
     * @param forumBounty 悬赏
     * @return 结果
     */
    @Override
    public int updateForumBounty(ForumBounty forumBounty)
    {
        forumBounty.setUpdateTime(DateUtils.getNowDate());
        return forumBountyMapper.updateForumBounty(forumBounty);
    }

    /**
     * 批量删除悬赏
     *
     * @param bountyIds 需要删除的悬赏主键
     * @return 结果
     */
    @Override
    public int deleteForumBountyByBountyIds(Long[] bountyIds)
    {
        return forumBountyMapper.deleteForumBountyByBountyIds(bountyIds);
    }

    /**
     * 删除悬赏信息
     *
     * @param bountyId 悬赏主键
     * @return 结果
     */
    @Override
    public int deleteForumBountyByBountyId(Long bountyId)
    {
        return forumBountyMapper.deleteForumBountyByBountyId(bountyId);
    }
}