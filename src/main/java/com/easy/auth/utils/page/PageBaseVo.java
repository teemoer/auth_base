package com.easy.auth.utils.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */

/**
 * 前端分页查询 base 通用类
 *
 * @param <T>
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@ApiModel("分页参数")
public class PageBaseVo<T> {
    @ApiModelProperty(name = "第几页", example = "1", required = true)
    private Integer page = 1;

    @ApiModelProperty(name = "每一页的大小", example = "10", required = true)
    private Integer pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBaseVo:{" + "page:" + page + ", pageSize:" + pageSize + '}';
    }
}
