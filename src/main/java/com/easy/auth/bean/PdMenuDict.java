package com.easy.auth.bean;

import com.easy.auth.enums.common.EnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * pd_menu_dict实体类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Data
@ApiModel("菜单类 json")
public class PdMenuDict {
    /**
     * 主键Id
     */
    @ApiModelProperty("主键id - 修改必须传递")
    private Integer id;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称 - 新增必传")
    private String name;
    /**
     * 菜单映射地址
     */
    @ApiModelProperty("菜单映射地址  - 新增必传")
    private String path;
    /**
     * 是否显示 1 是 2 否
     */
    @ApiModelProperty("是否显示  [是 - ENABLE] [否 - DISABLED]   - 新增必传")
    private EnableStatusEnum views;
    /**
     * 父级菜单Id 如果当前为顶级菜单  则 父id值为 null
     */
    @ApiModelProperty("父级菜单Id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date creatDate;
    /**
     * 是否是菜单 1 是 0 否
     */
    @ApiModelProperty("是否是菜单  [是 - ENABLE] [否 - DISABLED] - 新增必传")
    private EnableStatusEnum menuStatus;
    /**
     * 排序字段 值越大优先级越高
     */
    @ApiModelProperty("排序字段 值越大优先级越高  - 新增必传")
    private Integer sort;

    public PdMenuDict() {
    }

    public PdMenuDict(EnableStatusEnum menuStatus) {
        setMenuStatus(menuStatus);
    }
}
