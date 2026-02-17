package com.ruoyi.forum.mapper;

import com.ruoyi.forum.domain.ForumReplyImage;

import java.util.List;

/**
 * 回复图片Mapper接口
 *
 * @author ruoyi
 * @date 2026-01-17
 */
public interface ForumReplyImageMapper
{
    /**
     * 查询回复图片
     *
     * @param imageId 回复图片主键
     * @return 回复图片
     */
    public ForumReplyImage selectForumReplyImageByImageId(Long imageId);

    /**
     * 查询回复图片列表
     *
     * @param forumReplyImage 回复图片
     * @return 回复图片集合
     */
    public List<ForumReplyImage> selectForumReplyImageList(ForumReplyImage forumReplyImage);

    /**
     * 新增回复图片
     *
     * @param forumReplyImage 回复图片
     * @return 结果
     */
    public int insertForumReplyImage(ForumReplyImage forumReplyImage);

    /**
     * 修改回复图片
     *
     * @param forumReplyImage 回复图片
     * @return 结果
     */
    public int updateForumReplyImage(ForumReplyImage forumReplyImage);

    /**
     * 删除回复图片
     *
     * @param imageId 回复图片主键
     * @return 结果
     */
    public int deleteForumReplyImageByImageId(Long imageId);

    /**
     * 批量删除回复图片
     *
     * @param imageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteForumReplyImageByImageIds(Long[] imageIds);
}