package com.ruoyi.forum.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.forum.domain.ForumReplyImage;
import com.ruoyi.forum.service.IForumReplyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 回复图片Controller
 *
 * @author ruoyi
 * @date 2026-01-17
 */
@RestController
@RequestMapping("/forum/image")
public class ForumReplyImageController extends BaseController
{
    @Autowired
    private IForumReplyImageService forumReplyImageService;

    /**
     * 查询回复图片列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ForumReplyImage forumReplyImage)
    {
        startPage();
        List<ForumReplyImage> list = forumReplyImageService.selectForumReplyImageList(forumReplyImage);
        return getDataTable(list);
    }

    /**
     * 导出回复图片列表
     */

    @Log(title = "回复图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumReplyImage forumReplyImage)
    {
        List<ForumReplyImage> list = forumReplyImageService.selectForumReplyImageList(forumReplyImage);
        ExcelUtil<ForumReplyImage> util = new ExcelUtil<ForumReplyImage>(ForumReplyImage.class);
        util.exportExcel(response, list, "回复图片数据");
    }

    /**
     * 获取回复图片详细信息
     */

    @GetMapping(value = "/{imageId}")
    public AjaxResult getInfo(@PathVariable("imageId") Long imageId)
    {
        return success(forumReplyImageService.selectForumReplyImageByImageId(imageId));
    }

    /**
     * 新增回复图片
     */

    @Log(title = "回复图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ForumReplyImage forumReplyImage)
    {
        return toAjax(forumReplyImageService.insertForumReplyImage(forumReplyImage));
    }

    /**
     * 修改回复图片
     */

    @Log(title = "回复图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ForumReplyImage forumReplyImage)
    {
        return toAjax(forumReplyImageService.updateForumReplyImage(forumReplyImage));
    }

    /**
     * 删除回复图片
     */

    @Log(title = "回复图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{imageIds}")
    public AjaxResult remove(@PathVariable Long[] imageIds)
    {
        return toAjax(forumReplyImageService.deleteForumReplyImageByImageIds(imageIds));
    }
}