package com.ruoyi.forum.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.forum.domain.ForumReplyImage;
import com.ruoyi.forum.mapper.ForumReplyImageMapper;
import com.ruoyi.forum.service.IForumReplyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回复图片Service业务层处理
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@Service
public class ForumReplyImageServiceImpl implements IForumReplyImageService
{
    @Autowired
    private ForumReplyImageMapper forumReplyImageMapper;

    /**
     * 查询回复图片
     *
     * @param imageId 回复图片主键
     * @return 回复图片
     */
    @Override
    public ForumReplyImage selectForumReplyImageByImageId(Long imageId)
    {
        return forumReplyImageMapper.selectForumReplyImageByImageId(imageId);
    }

    /**
     * 查询回复图片列表
     *
     * @param forumReplyImage 回复图片
     * @return 回复图片
     */
    @Override
    public List<ForumReplyImage> selectForumReplyImageList(ForumReplyImage forumReplyImage)
    {
        return forumReplyImageMapper.selectForumReplyImageList(forumReplyImage);
    }

    /**
     * 新增回复图片
     *
     * @param forumReplyImage 回复图片
     * @return 结果
     */
    @Override
    public int insertForumReplyImage(ForumReplyImage forumReplyImage)
    {
        forumReplyImage.setCreateTime(DateUtils.getNowDate());
        return forumReplyImageMapper.insertForumReplyImage(forumReplyImage);
    }

    /**
     * 修改回复图片
     *
     * @param forumReplyImage 回复图片
     * @return 结果
     */
    @Override
    public int updateForumReplyImage(ForumReplyImage forumReplyImage)
    {
        return forumReplyImageMapper.updateForumReplyImage(forumReplyImage);
    }

    /**
     * 批量删除回复图片
     *
     * @param imageIds 需要删除的回复图片主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyImageByImageIds(Long[] imageIds)
    {
        return forumReplyImageMapper.deleteForumReplyImageByImageIds(imageIds);
    }

    /**
     * 删除回复图片信息
     *
     * @param imageId 回复图片主键
     * @return 结果
     */
    @Override
    public int deleteForumReplyImageByImageId(Long imageId)
    {
        return forumReplyImageMapper.deleteForumReplyImageByImageId(imageId);
    }
}