package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumReplyReference;
import com.ruoyi.forum.service.IForumReplyReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 回复引用Controller
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@RestController
@RequestMapping("/forum/reference")
public class ForumReplyReferenceController extends BaseController
{
    @Autowired
    private IForumReplyReferenceService forumReplyReferenceService;

    /**
     * 查询回复引用列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumReplyReference forumReplyReference)
    {
        startPage();
        List<ForumReplyReference> list = forumReplyReferenceService.selectForumReplyReferenceList(forumReplyReference);
        return getDataTable(list);
    }

    /**
     * 导出回复引用列表
     */

    @Log(title = "回复引用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumReplyReference forumReplyReference)
    {
        List<ForumReplyReference> list = forumReplyReferenceService.selectForumReplyReferenceList(forumReplyReference);
        ExcelUtil<ForumReplyReference> util = new ExcelUtil<ForumReplyReference>(ForumReplyReference.class);
        util.exportExcel(response, list, "回复引用数据");
    }

    /**
     * 获取回复引用详细信息
     */

    @GetMapping(value = "/{referenceId}")
    public AjaxResult getInfo(@PathVariable("referenceId") Long referenceId)
    {
        return success(forumReplyReferenceService.selectForumReplyReferenceByReferenceId(referenceId));
    }

    /**
     * 新增回复引用
     */

    @Log(title = "回复引用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumReplyReference forumReplyReference)
    {
        return toAjax(forumReplyReferenceService.insertForumReplyReference(forumReplyReference));
    }

    /**
     * 修改回复引用
     */

    @Log(title = "回复引用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumReplyReference forumReplyReference)
    {
        return toAjax(forumReplyReferenceService.updateForumReplyReference(forumReplyReference));
    }

    /**
     * 删除回复引用
     */

    @Log(title = "回复引用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{referenceIds}")
    public AjaxResult remove(@PathVariable Long[] referenceIds)
    {
        return toAjax(forumReplyReferenceService.deleteForumReplyReferenceByReferenceIds(referenceIds));
    }
}