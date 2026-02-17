package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumTagService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 标签Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/tag")
public class ForumTagController extends BaseController
{
    @Autowired
    private IForumTagService forumTagService;

    /**
     * 查询标签列表
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumTag forumTag)
    {
        startPage();
        List<ForumTag> list = forumTagService.selectForumTagList(forumTag);
        return getDataTable(list);
    }

    /**
     * 导出标签列表
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:export')")
    @Log(title = "标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumTag forumTag)
    {
        List<ForumTag> list = forumTagService.selectForumTagList(forumTag);
        ExcelUtil<ForumTag> util = new ExcelUtil<ForumTag>(ForumTag.class);
        util.exportExcel(response, list, "标签数据");
    }

    /**
     * 获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") Long tagId)
    {
        return success(forumTagService.selectForumTagByTagId(tagId));
    }

    /**
     * 新增标签
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:add')")
    @Log(title = "标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumTag forumTag)
    {
        return toAjax(forumTagService.insertForumTag(forumTag));
    }

    /**
     * 修改标签
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:edit')")
    @Log(title = "标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumTag forumTag)
    {
        return toAjax(forumTagService.updateForumTag(forumTag));
    }

    /**
     * 删除标签
     */
    @PreAuthorize("@ss.hasPermi('forum:tag:remove')")
    @Log(title = "标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds)
    {
        return toAjax(forumTagService.deleteForumTagByTagIds(tagIds));
    }
}