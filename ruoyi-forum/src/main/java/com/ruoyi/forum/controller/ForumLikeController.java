package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumLikeService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 点赞Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/system/like")
public class ForumLikeController extends BaseController
{
    @Autowired
    private IForumLikeService forumLikeService;

    /**
     * 查询点赞列表
     */
    @PreAuthorize("@ss.hasPermi('system:like:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumLike forumLike)
    {
        startPage();
        List<ForumLike> list = forumLikeService.selectForumLikeList(forumLike);
        return getDataTable(list);
    }

    /**
     * 导出点赞列表
     */
    @PreAuthorize("@ss.hasPermi('system:like:export')")
    @Log(title = "点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumLike forumLike)
    {
        List<ForumLike> list = forumLikeService.selectForumLikeList(forumLike);
        ExcelUtil<ForumLike> util = new ExcelUtil<ForumLike>(ForumLike.class);
        util.exportExcel(response, list, "点赞数据");
    }

    /**
     * 获取点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:like:query')")
    @GetMapping(value = "/{likeId}")
    public AjaxResult getInfo(@PathVariable("likeId") Long likeId)
    {
        return success(forumLikeService.selectForumLikeByLikeId(likeId));
    }

    /**
     * 新增点赞
     */
    @PreAuthorize("@ss.hasPermi('system:like:add')")
    @Log(title = "点赞", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumLike forumLike)
    {
        return toAjax(forumLikeService.insertForumLike(forumLike));
    }

    /**
     * 修改点赞
     */
    @PreAuthorize("@ss.hasPermi('system:like:edit')")
    @Log(title = "点赞", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumLike forumLike)
    {
        return toAjax(forumLikeService.updateForumLike(forumLike));
    }

    /**
     * 删除点赞
     */
    @PreAuthorize("@ss.hasPermi('system:like:remove')")
    @Log(title = "点赞", businessType = BusinessType.DELETE)
    @DeleteMapping("/{likeIds}")
    public AjaxResult remove(@PathVariable Long[] likeIds)
    {
        return toAjax(forumLikeService.deleteForumLikeByLikeIds(likeIds));
    }

    /**
     * 点赞/取消点赞切换
     */
    @PreAuthorize("@ss.hasAnyRoles('common')") // 只要是登录用户即可
    @Log(title = "点赞", businessType = BusinessType.INSERT)
    @PostMapping("/toggle")
    public AjaxResult toggle(@RequestBody ForumLike forumLike)
    {
        return forumLikeService.toggleLike(forumLike);
    }
}