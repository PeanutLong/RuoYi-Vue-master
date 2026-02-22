package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumBounty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.forum.service.IForumBountyService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 悬赏Controller
 *
 * @author ruoyi
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/forum/bounty")
public class ForumBountyController extends BaseController
{
    @Autowired
    private IForumBountyService forumBountyService;

    /**
     * 查询悬赏列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumBounty forumBounty)
    {
        startPage();
        List<ForumBounty> list = forumBountyService.selectForumBountyList(forumBounty);
        return getDataTable(list);
    }

    /**
     * 导出悬赏列表
     */

    @Log(title = "悬赏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumBounty forumBounty)
    {
        List<ForumBounty> list = forumBountyService.selectForumBountyList(forumBounty);
        ExcelUtil<ForumBounty> util = new ExcelUtil<ForumBounty>(ForumBounty.class);
        util.exportExcel(response, list, "悬赏数据");
    }

    /**
     * 获取悬赏详细信息
     */

    @GetMapping(value = "/{bountyId}")
    public AjaxResult getInfo(@PathVariable("bountyId") Long bountyId)
    {
        return success(forumBountyService.selectForumBountyByBountyId(bountyId));
    }

    /**
     * 新增悬赏
     */

    @Log(title = "悬赏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumBounty forumBounty)
    {
        return toAjax(forumBountyService.insertForumBounty(forumBounty));
    }

    /**
     * 修改悬赏
     */

    @Log(title = "悬赏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumBounty forumBounty)
    {
        return toAjax(forumBountyService.updateForumBounty(forumBounty));
    }

    /**
     * 删除悬赏
     */

    @Log(title = "悬赏", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bountyIds}")
    public AjaxResult remove(@PathVariable Long[] bountyIds)
    {
        return toAjax(forumBountyService.deleteForumBountyByBountyIds(bountyIds));
    }

    @Log(title = "悬赏采纳", businessType = BusinessType.UPDATE)
    @PostMapping("/accept")
    public AjaxResult acceptAnswer(@RequestBody Map<String, Long> params)
    {
        Long postId = params.get("postId");
        Long replyId = params.get("replyId");
        Long replyUserId = params.get("replyUserId");

        if (postId == null || replyId == null || replyUserId == null) {
            return AjaxResult.error("参数不完整");
        }

        return toAjax(forumBountyService.acceptAnswer(postId, replyId, replyUserId));
    }
}