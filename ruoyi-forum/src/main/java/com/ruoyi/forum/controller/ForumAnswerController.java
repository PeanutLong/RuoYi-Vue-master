package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumAnswerService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 回答Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/answer")
public class ForumAnswerController extends BaseController
{
    @Autowired
    private IForumAnswerService forumAnswerService;

    /**
     * 查询回答列表
     */
    @PreAuthorize("@ss.hasPermi('forum:answer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumAnswer forumAnswer)
    {
        startPage();
        List<ForumAnswer> list = forumAnswerService.selectForumAnswerList(forumAnswer);
        return getDataTable(list);
    }

    /**
     * 导出回答列表
     */
    @PreAuthorize("@ss.hasPermi('forum:answer:export')")
    @Log(title = "回答", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumAnswer forumAnswer)
    {
        List<ForumAnswer> list = forumAnswerService.selectForumAnswerList(forumAnswer);
        ExcelUtil<ForumAnswer> util = new ExcelUtil<ForumAnswer>(ForumAnswer.class);
        util.exportExcel(response, list, "回答数据");
    }

    /**
     * 获取回答详细信息
     */
    @PreAuthorize("@ss.hasPermi('forum:answer:query')")
    @GetMapping(value = "/{answerId}")
    public AjaxResult getInfo(@PathVariable("answerId") Long answerId)
    {
        return success(forumAnswerService.selectForumAnswerByAnswerId(answerId));
    }

    /**
     * 新增回答
     */
    @PreAuthorize("@ss.hasPermi('forum:answer:add')")
    @Log(title = "回答", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumAnswer forumAnswer)
    {
        return toAjax(forumAnswerService.insertForumAnswer(forumAnswer));
    }

    /**
     * 修改回答
     */
    @PreAuthorize("@ss.hasPermi('system:answer:edit')")
    @Log(title = "回答", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumAnswer forumAnswer)
    {
        return toAjax(forumAnswerService.updateForumAnswer(forumAnswer));
    }

    /**
     * 删除回答
     */
    @PreAuthorize("@ss.hasPermi('system:answer:remove')")
    @Log(title = "回答", businessType = BusinessType.DELETE)
    @DeleteMapping("/{answerIds}")
    public AjaxResult remove(@PathVariable Long[] answerIds)
    {
        return toAjax(forumAnswerService.deleteForumAnswerByAnswerIds(answerIds));
    }
}