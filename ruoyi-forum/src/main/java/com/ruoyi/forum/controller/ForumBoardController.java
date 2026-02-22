package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumBoardService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 论坛板块Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/board")
public class ForumBoardController extends BaseController
{
    @Autowired
    private IForumBoardService forumBoardService;
    /**
     * 查询论坛板块列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumBoard forumBoard)
    {
        startPage();
        List<ForumBoard> list = forumBoardService.selectForumBoardList(forumBoard);
        return getDataTable(list);
    }

    /**
     * 导出论坛板块列表
     */

    @Log(title = "论坛板块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumBoard forumBoard)
    {
        List<ForumBoard> list = forumBoardService.selectForumBoardList(forumBoard);
        ExcelUtil<ForumBoard> util = new ExcelUtil<ForumBoard>(ForumBoard.class);
        util.exportExcel(response, list, "论坛板块数据");
    }

    /**
     * 获取论坛板块详细信息
     */

    @GetMapping(value = "/{boardId}")
    public AjaxResult getInfo(@PathVariable("boardId") Long boardId)
    {
        return success(forumBoardService.selectForumBoardByBoardId(boardId));
    }

    /**
     * 新增论坛板块
     */
    @PreAuthorize("@ss.hasPermi('forum:board:add')")
    @Log(title = "论坛板块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumBoard forumBoard)
    {
        return toAjax(forumBoardService.insertForumBoard(forumBoard));
    }

    /**
     * 修改论坛板块
     */
    @PreAuthorize("@ss.hasPermi('forum:board:edit')")
    @Log(title = "论坛板块", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumBoard forumBoard)
    {
        return toAjax(forumBoardService.updateForumBoard(forumBoard));
    }

    /**
     * 删除论坛板块
     */
    @PreAuthorize("@ss.hasPermi('forum:board:remove')")
    @Log(title = "论坛板块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{boardIds}")
    public AjaxResult remove(@PathVariable Long[] boardIds)
    {
        return toAjax(forumBoardService.deleteForumBoardByBoardIds(boardIds));
    }
}