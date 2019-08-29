package com.easy.auth.utils.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前端查询分页使用到的 参数接收类 采用泛型 接受 附加数据 使用方法 请参考 com.minyuan.ds.controller.DsAttributeController.findByPage
 *
 * @param <T> 附加条件属性类 如 可以是 DsAttribute 前端 传递 数据方式应该为: { "page":1, "pageSize":20, "addition":{ } }
 *     page默认值为 1 ,pageSize默认值为 20
 * @author 连晋
 */
@ApiModel("分页泛型类")
public class PageVo<T> extends PageBaseVo {

  /**
   * 附加字段 泛型 提高代码复用性
   *
   * <p>传递的 T 泛型 如果为 bean 请手动为 bean 生成 toString方法 方便 后续使用 javaDoc直接生成 markdown 接口文档
   */
  @ApiModelProperty(value = "附加搜索数据类", required = false)
  private T addition;

  public PageVo() {}

  public T getAddition() {
    return addition;
  }

  public void setAddition(T addition) {
    this.addition = addition;
  }

  @Override
  public String toString() {
    return "PageVo:{" + "addition:" + addition + "," + super.toString() + '}';
  }
}
