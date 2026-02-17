package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumReply;
import com.ruoyi.forum.service.IForumReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 回复Controller
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@RestController
@RequestMapping("/forum/reply")
public class ForumReplyController extends BaseController
{
    @Autowired
    private IForumReplyService forumReplyService;

    /**
     * 查询回复列表
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumReply forumReply)
    {
        startPage();
        List<ForumReply> list = forumReplyService.selectForumReplyList(forumReply);
        return getDataTable(list);
    }

    /**
     * 导出回复列表
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:export')")
    @Log(title = "回复", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumReply forumReply)
    {
        List<ForumReply> list = forumReplyService.selectForumReplyList(forumReply);
        ExcelUtil<ForumReply> util = new ExcelUtil<ForumReply>(ForumReply.class);
        util.exportExcel(response, list, "回复数据");
    }

    /**
     * 获取回复详细信息
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:query')")
    @GetMapping(value = "/{replyId}")
    public AjaxResult getInfo(@PathVariable("replyId") Long replyId)
    {
        return success(forumReplyService.selectForumReplyByReplyId(replyId));
    }

    /**
     * 新增回复
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:add')")
    @Log(title = "回复", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumReply forumReply)
    {
        return toAjax(forumReplyService.insertForumReply(forumReply));
    }

    /**
     * 修改回复
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:edit')")
    @Log(title = "回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumReply forumReply)
    {
        return toAjax(forumReplyService.updateForumReply(forumReply));
    }

    /**
     * 删除回复
     */
    @PreAuthorize("@ss.hasPermi('forum:reply:remove')")
    @Log(title = "回复", businessType = BusinessType.DELETE)
    @DeleteMapping("/{replyIds}")
    public AjaxResult remove(@PathVariable Long[] replyIds)
    {
        return toAjax(forumReplyService.deleteForumReplyByReplyIds(replyIds));
    }
}