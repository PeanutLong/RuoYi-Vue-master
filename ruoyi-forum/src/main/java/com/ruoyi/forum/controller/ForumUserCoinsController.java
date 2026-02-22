package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumUserCoins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumUserCoinsService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户金币Controller
 *
 * @author ruoyi
 * @date 2026-01-08
 */
@RestController
@RequestMapping("/forum/coins")
public class ForumUserCoinsController extends BaseController
{
    @Autowired
    private IForumUserCoinsService forumUserCoinsService;

    /**
     * 查询用户金币列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumUserCoins forumUserCoins)
    {
        startPage();
        List<ForumUserCoins> list = forumUserCoinsService.selectForumUserCoinsList(forumUserCoins);
        return getDataTable(list);
    }

    /**
     * 导出用户金币列表
     */

    @Log(title = "用户金币", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumUserCoins forumUserCoins)
    {
        List<ForumUserCoins> list = forumUserCoinsService.selectForumUserCoinsList(forumUserCoins);
        ExcelUtil<ForumUserCoins> util = new ExcelUtil<ForumUserCoins>(ForumUserCoins.class);
        util.exportExcel(response, list, "用户金币数据");
    }

    /**
     * 获取用户金币详细信息
     */

    @GetMapping(value = "/{coinsId}")
    public AjaxResult getInfo(@PathVariable("coinsId") Long coinsId)
    {
        return success(forumUserCoinsService.selectForumUserCoinsByCoinsId(coinsId));
    }

    /**
     * 新增用户金币
     */

    @Log(title = "用户金币", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumUserCoins forumUserCoins)
    {
        return toAjax(forumUserCoinsService.insertForumUserCoins(forumUserCoins));
    }

    /**
     * 修改用户金币
     */

    @Log(title = "用户金币", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumUserCoins forumUserCoins)
    {
        return toAjax(forumUserCoinsService.updateForumUserCoins(forumUserCoins));
    }

    /**
     * 删除用户金币
     */

    @Log(title = "用户金币", businessType = BusinessType.DELETE)
    @DeleteMapping("/{coinsIds}")
    public AjaxResult remove(@PathVariable Long[] coinsIds)
    {
        return toAjax(forumUserCoinsService.deleteForumUserCoinsByCoinsIds(coinsIds));
    }

    @GetMapping("/myCoins")
    public AjaxResult getMyCoins() {
        Long userId = SecurityUtils.getUserId();
        ForumUserCoins coins = forumUserCoinsService.selectCoinsByUserId(userId);
        if (coins == null) {
            // 如果用户还没有金币记录，可以返回一个初始化的对象
            return AjaxResult.success(new ForumUserCoins(userId, 0L, 0L, 0L));
        }
        return AjaxResult.success(coins);
    }
}