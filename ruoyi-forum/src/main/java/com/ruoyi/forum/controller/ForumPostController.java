package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumPostService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 帖子Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/post")
public class ForumPostController extends BaseController
{
    @Autowired
    private IForumPostService forumPostService;

    /**
     * 查询帖子列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ForumPost forumPost)
    {
        startPage();
        List<ForumPost> list = forumPostService.selectForumPostList(forumPost);
        return getDataTable(list);
    }


    /**
     * 导出帖子列表
     */
    @PreAuthorize("@ss.hasPermi('forum:post:export')")
    @Log(title = "帖子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumPost forumPost)
    {
        List<ForumPost> list = forumPostService.selectForumPostList(forumPost);
        ExcelUtil<ForumPost> util = new ExcelUtil<ForumPost>(ForumPost.class);
        util.exportExcel(response, list, "帖子数据");
    }

    /**
     * 获取帖子详细信息
     */
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable("postId") Long postId)
    {
        return success(forumPostService.selectForumPostByPostId(postId));
    }

    /**
     * 新增帖子
     */
    @PreAuthorize("@ss.hasPermi('forum:post:add')")
    @Log(title = "帖子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumPost forumPost)
    {
        return toAjax(forumPostService.insertForumPost(forumPost));
    }

    /**
     * 修改帖子
     */
    @PreAuthorize("@ss.hasPermi('forum:post:edit')")
    @Log(title = "帖子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumPost forumPost)
    {
        return toAjax(forumPostService.updateForumPost(forumPost));
    }

    /**
     * 删除帖子
     */
    @PreAuthorize("@ss.hasPermi('forum:post:remove')")
    @Log(title = "帖子", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(forumPostService.deleteForumPostByPostIds(postIds));
    }

    @GetMapping("/hotThread")
    public TableDataInfo HotThreads()
    {
        List<ForumPost> list = forumPostService.selectHotThreads();
        return getDataTable(list);
    }

}