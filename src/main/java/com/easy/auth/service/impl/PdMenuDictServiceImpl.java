package com.easy.auth.service.impl;

import com.easy.auth.dao.PdMenuDictMapper;
import com.easy.auth.bean.PdMenuDict;
import com.easy.auth.service.PdMenuDictService;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * PdMenuDict的服务接口的实现类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@Service
public class PdMenuDictServiceImpl implements PdMenuDictService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired private PdMenuDictMapper pdMenuDictMapper;

  @Override
  @Deprecated
  public List<PdMenuDict> find(PdMenuDict value) {
    // TODO这里可以做通过Assist做添加查询
    List<PdMenuDict> result = pdMenuDictMapper.selectPdMenuDict(null);
    return result;
  }

  /**
   * 查询全部菜单权限
   *
   * @param beanPage
   * @return
   */
  @Override
  public Result findByPage(PageVo<PdMenuDict> beanPage) {
    PageHelper.startPage(beanPage.getPage(), beanPage.getPageSize());
    List<PdMenuDict> pdMenuDicts = pdMenuDictMapper.selectPdMenuDictByObj(beanPage.getAddition());
    return Result.success(new PageInfo<>(pdMenuDicts));
  }

  @Override
  public Result findAllMenu(Integer isMenu) {
      PdMenuDict pdMenuDict = new PdMenuDict();
      if (isMenu != null && isMenu != 2) {
          pdMenuDict.setIsMenu(isMenu);
      }
      return Result.success(pdMenuDictMapper.selectPdMenuDictByObj(pdMenuDict));
  }

  @Override
  public PdMenuDict findOne(Integer id) {

    PdMenuDict result = pdMenuDictMapper.selectPdMenuDictById(id);
    return result;
  }

  /**
   * 添加权限菜单
   *
   * @param value
   * @return
   */
  @Override
  public Result saveNotNull(PdMenuDict value) {
    if (value.getParentId() != 0) {
      PdMenuDict temp = pdMenuDictMapper.selectPdMenuDictById(value.getParentId());
      if (temp != null && temp.getLevel() <= 2) {
        value.setLevel(temp.getLevel() + 1);
      } else {
        return Result.failure("后台数据错误");
      }
    } else {
      value.setLevel(1);
    }
    value.setCreatTime(new Date(System.currentTimeMillis()));
    pdMenuDictMapper.insertNotNullPdMenuDict(value);
    return Result.success();
  }

  @Override
  public Result updateNotNullById(PdMenuDict value) {
      if (StringUtils.isNotBlank(value.getPath())) {
      PdMenuDict pdMenuDict = pdMenuDictMapper.selectByPath(value);
          if (pdMenuDict != null && !pdMenuDict.getId().equals(value.getId())) {
        return Result.failure("菜单已经存在!");
      }
    }
    pdMenuDictMapper.updateNotNullPdMenuDictById(value);
    return Result.success();
  }

  @Override
  public Result deleteById(Integer id) {
    List<PdMenuDict> list = pdMenuDictMapper.selectByParentId(id);
    if (list != null && list.size() > 0) {
      return Result.failure("该菜单包含子菜单，不可删除!");
    } else {
      pdMenuDictMapper.deletePdMenuDictById(id);
      return Result.success();
    }
  }



  @Override
  public Result selectMenuByLikeMenuNameOrPath(String menuNameOrPath) {
    return Result.success(pdMenuDictMapper.selectMenuByLikeMenuNameOrPath(menuNameOrPath));
  }


}
