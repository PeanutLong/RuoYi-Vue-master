package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumCheckIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumCheckInService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 签到Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/in")
public class ForumCheckInController extends BaseController
{
    @Autowired
    private IForumCheckInService forumCheckInService;

    /**
     * 查询签到列表
     */
    @PreAuthorize("@ss.hasPermi('forum:in:list')")
    @GetMapping("/list")
    public TableDataInfo list(ForumCheckIn forumCheckIn)
    {
        startPage();
        List<ForumCheckIn> list = forumCheckInService.selectForumCheckInList(forumCheckIn);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('forum:in:add')")
    @Log(title = "签到", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumCheckIn forumCheckIn)
    {
        // 1. 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        forumCheckIn.setUserId(userId);

        // 2. 设置签到时间 (如果数据库没有自动设置)
        forumCheckIn.setCheckDate(DateUtils.getNowDate());

        // 3. 调用Service处理业务逻辑（包含加金币）
        ForumCheckIn query = new ForumCheckIn();
        query.setUserId(userId);
        query.setCheckDate(DateUtils.parseDate(DateUtils.getDate())); // 需要确保日期格式匹配，或者只查当天
        List<ForumCheckIn> list = forumCheckInService.selectForumCheckInList(query);
        if (!list.isEmpty()) {
            return AjaxResult.error("您今天已经签到过了！");
        }
        try {
            return toAjax(forumCheckInService.insertForumCheckIn(forumCheckIn));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 导出签到列表
     */
    @PreAuthorize("@ss.hasPermi('forum:in:export')")
    @Log(title = "签到", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumCheckIn forumCheckIn)
    {
        List<ForumCheckIn> list = forumCheckInService.selectForumCheckInList(forumCheckIn);
        ExcelUtil<ForumCheckIn> util = new ExcelUtil<ForumCheckIn>(ForumCheckIn.class);
        util.exportExcel(response, list, "签到数据");
    }

    /**
     * 获取签到详细信息
     */
    @PreAuthorize("@ss.hasPermi('forum:in:query')")
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return success(forumCheckInService.selectForumCheckInByCheckId(checkId));
    }


    /**
     * 修改签到
     */
    @PreAuthorize("@ss.hasPermi('forum:in:edit')")
    @Log(title = "签到", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumCheckIn forumCheckIn)
    {
        return toAjax(forumCheckInService.updateForumCheckIn(forumCheckIn));
    }

    /**
     * 删除签到
     */
    @PreAuthorize("@ss.hasPermi('forum:in:remove')")
    @Log(title = "签到", businessType = BusinessType.DELETE)
    @DeleteMapping("/{checkIds}")
    public AjaxResult remove(@PathVariable Long[] checkIds)
    {
        return toAjax(forumCheckInService.deleteForumCheckInByCheckIds(checkIds));
    }
}