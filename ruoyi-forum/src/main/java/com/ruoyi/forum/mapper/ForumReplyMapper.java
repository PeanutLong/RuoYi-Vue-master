package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 回复Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public interface ForumReplyMapper
{
    /**
     * 查询回复
     *
     * @param replyId 回复主键
     * @return 回复
     */
    public ForumReply selectForumReplyByReplyId(Long replyId);

    /**
     * 查询回复列表
     *
     * @param forumReply 回复
     * @return 回复集合
     */
    public List<ForumReply> selectForumReplyList(ForumReply forumReply);

    /**
     * 新增回复
     *
     * @param forumReply 回复
     * @return 结果
     */
    public int insertForumReply(ForumReply forumReply);

    /**
     * 修改回复
     *
     * @param forumReply 回复
     * @return 结果
     */
    public int updateForumReply(ForumReply forumReply);

    public Long selectMaxFloorByPostId(Long postId);

    public int updateLikeCount(@Param("replyId")Long replyId, @Param("amount")Integer amount);

    /**
     * 删除回复
     *
     * @param replyId 回复主键
     * @return 结果
     */
    public int deleteForumReplyByReplyId(Long replyId);

    /**
     * 批量删除回复
     *
     * @param replyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumReplyByReplyIds(Long[] replyIds);
}