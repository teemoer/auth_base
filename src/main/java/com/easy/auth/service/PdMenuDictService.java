package com.easy.auth.service;

import com.easy.auth.bean.PdMenuDict;
import com.easy.auth.enums.common.EnableStatusEnum;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;

import java.util.List;

public interface PdMenuDictService {
  /**
   * 获得PdMenuDict数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
   *
   * @return
   */
  List<PdMenuDict> find(PdMenuDict value);

  /**
   * 通过PdMenuDict的id获得PdMenuDict对象
   *
   * @param id
   * @return
   */
  PdMenuDict findOne(Integer id);

  Result findByPage(PageVo<PdMenuDict> beanPage);

    Result findAllMenu(EnableStatusEnum menuStatus);

  /**
   * 将PdMenuDict中属性值不为null的数据到数据库
   *
   * @param value
   * @return
   */
  Result saveNotNull(PdMenuDict value);

  /**
   * 通过PdMenuDict的id更新PdMenuDict中属性不为null的数据
   *
   * @param enti
   * @return
   */
  Result updateNotNullById(PdMenuDict enti);

  /**
   * 通过PdMenuDict的id删除PdMenuDict
   *
   * @param id
   * @return
   */
  Result deleteById(Integer id);

  Result selectMenuByLikeMenuNameOrPath(String menuNameOrPath);
}
