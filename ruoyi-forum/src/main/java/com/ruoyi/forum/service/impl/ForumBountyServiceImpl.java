package com.ruoyi.forum.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumBounty;
import com.ruoyi.forum.domain.ForumUserCoins;
import com.ruoyi.forum.mapper.ForumBountyMapper;
import com.ruoyi.forum.mapper.ForumUserCoinsMapper;
import com.ruoyi.forum.service.IForumBountyService;
import com.ruoyi.forum.service.IForumUserCoinsService;
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
    @Autowired
    private ForumUserCoinsMapper forumUserCoinsMapper;

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

    @Override
    public int acceptAnswer(Long postId, Long replyId, Long replyUserId) {
        // 1. 查询该帖子的悬赏信息
        ForumBounty query = new ForumBounty();
        query.setPostId(postId);
        List<ForumBounty> bounties = forumBountyMapper.selectForumBountyList(query);
        if (bounties.isEmpty()) {
            throw new ServiceException("未找到对应的悬赏信息");
        }

        ForumBounty bounty = bounties.get(0);

        // 2. 检查悬赏状态是否已经是“已解决(1)”
        if ("1".equals(bounty.getStatus())) {
            throw new ServiceException("该悬赏已经采纳过最佳答案");
        }

        // 3. 更新悬赏记录状态
        bounty.setStatus("1"); // 1-已解决
        bounty.setAcceptedAnswerId(replyId);
        bounty.setUpdateTime(DateUtils.getNowDate());
        forumBountyMapper.updateForumBounty(bounty);
        ForumUserCoins forumUserCoins = forumUserCoinsMapper.selectCoinsByUserId(replyUserId);
        if (forumUserCoins == null) {
            // 如果该用户之前没有金币账户，则初始化一个并加上悬赏金
            forumUserCoins = new ForumUserCoins(replyUserId, bounty.getCoins(), bounty.getCoins(), 0L);
            forumUserCoins.setCreateTime(DateUtils.getNowDate());
            forumUserCoinsMapper.insertForumUserCoins(forumUserCoins);
        } else {
            forumUserCoins.setTotalCoins(forumUserCoins.getTotalCoins() + bounty.getCoins());
            forumUserCoinsMapper.updateForumUserCoins(forumUserCoins);
        }

        return 1;
    }
}