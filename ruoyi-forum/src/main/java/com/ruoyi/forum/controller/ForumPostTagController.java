package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumPostTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumPostTagService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 帖子标签关联Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/system/tag")
public class ForumPostTagController extends BaseController
{
    @Autowired
    private IForumPostTagService forumPostTagService;

    /**
     * 查询帖子标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumPostTag forumPostTag)
    {
        startPage();
        List<ForumPostTag> list = forumPostTagService.selectForumPostTagList(forumPostTag);
        return getDataTable(list);
    }

    /**
     * 导出帖子标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:export')")
    @Log(title = "帖子标签关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumPostTag forumPostTag)
    {
        List<ForumPostTag> list = forumPostTagService.selectForumPostTagList(forumPostTag);
        ExcelUtil<ForumPostTag> util = new ExcelUtil<ForumPostTag>(ForumPostTag.class);
        util.exportExcel(response, list, "帖子标签关联数据");
    }

    /**
     * 获取帖子标签关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(forumPostTagService.selectForumPostTagById(id));
    }

    /**
     * 新增帖子标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "帖子标签关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumPostTag forumPostTag)
    {
        return toAjax(forumPostTagService.insertForumPostTag(forumPostTag));
    }

    /**
     * 修改帖子标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "帖子标签关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumPostTag forumPostTag)
    {
        return toAjax(forumPostTagService.updateForumPostTag(forumPostTag));
    }

    /**
     * 删除帖子标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "帖子标签关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(forumPostTagService.deleteForumPostTagByIds(ids));
    }
}