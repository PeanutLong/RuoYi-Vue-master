package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumCoinsTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumCoinsTransactionService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 金币流水Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/system/transaction")
public class ForumCoinsTransactionController extends BaseController
{
    @Autowired
    private IForumCoinsTransactionService forumCoinsTransactionService;

    /**
     * 查询金币流水列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumCoinsTransaction forumCoinsTransaction)
    {
        startPage();
        List<ForumCoinsTransaction> list = forumCoinsTransactionService.selectForumCoinsTransactionList(forumCoinsTransaction);
        return getDataTable(list);
    }

    /**
     * 导出金币流水列表
     */

    @Log(title = "金币流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumCoinsTransaction forumCoinsTransaction)
    {
        List<ForumCoinsTransaction> list = forumCoinsTransactionService.selectForumCoinsTransactionList(forumCoinsTransaction);
        ExcelUtil<ForumCoinsTransaction> util = new ExcelUtil<ForumCoinsTransaction>(ForumCoinsTransaction.class);
        util.exportExcel(response, list, "金币流水数据");
    }

    /**
     * 获取金币流水详细信息
     */

    @GetMapping(value = "/{transactionId}")
    public AjaxResult getInfo(@PathVariable("transactionId") Long transactionId)
    {
        return success(forumCoinsTransactionService.selectForumCoinsTransactionByTransactionId(transactionId));
    }

    /**
     * 新增金币流水
     */

    @Log(title = "金币流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumCoinsTransaction forumCoinsTransaction)
    {
        return toAjax(forumCoinsTransactionService.insertForumCoinsTransaction(forumCoinsTransaction));
    }

    /**
     * 修改金币流水
     */

    @Log(title = "金币流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumCoinsTransaction forumCoinsTransaction)
    {
        return toAjax(forumCoinsTransactionService.updateForumCoinsTransaction(forumCoinsTransaction));
    }

    /**
     * 删除金币流水
     */

    @Log(title = "金币流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{transactionIds}")
    public AjaxResult remove(@PathVariable Long[] transactionIds)
    {
        return toAjax(forumCoinsTransactionService.deleteForumCoinsTransactionByTransactionIds(transactionIds));
    }
}