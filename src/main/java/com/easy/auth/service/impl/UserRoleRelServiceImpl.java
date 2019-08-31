package com.easy.auth.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;

import com.easy.auth.service.UserRoleRelService;
import com.easy.auth.dao.UserRoleRelMapper;
import com.util.Assist;
import com.easy.auth.bean.UserRoleRel;
/**
 * UserRoleRel的服务接口的实现类
 * 
 * @author 
 *
 */
@Service
public class UserRoleRelServiceImpl implements UserRoleRelService {
	private static Logger logger = LoggerFactory.getLogger(UserRoleRelServiceImpl.class);
	@Autowired
	private UserRoleRelMapper userRoleRelMapper;

	@Deprecated
	@Override
	public List<UserRoleRel> find(UserRoleRel value) {
		List<UserRoleRel> result = userRoleRelMapper.selectUserRoleRelByObj(value);
		return result;
	}
	@Override
	public UserRoleRel findOne(Integer id) {
		UserRoleRel result = userRoleRelMapper.selectUserRoleRelById(id);
		return result;
	}

	@Override
	public int saveNotNull(UserRoleRel value) {
		int result = userRoleRelMapper.insertNotNullUserRoleRel(value);
		return result;
	}
	@Override
	public int updateNotNullById(UserRoleRel value) {
		int result = userRoleRelMapper.updateNotNullUserRoleRelById(value);
		return result;
	}

	@Override
	public int deleteById(Integer id) {
		int result = userRoleRelMapper.deleteUserRoleRelById(id);
		return result;
	}

	@Override
	public Result findByPageVo(PageVo<UserRoleRel> pageBaseVo){
		PageHelper.startPage(pageBaseVo.getPage(), pageBaseVo.getPageSize());
		List<UserRoleRel> beanList =
		userRoleRelMapper.selectUserRoleRelByObj(pageBaseVo.getAddition());
		return Result.success(new PageInfo<UserRoleRel>(beanList));
	}

	@Override
    public Result selectListByObj(UserRoleRel beanValue){
		return Result.success(userRoleRelMapper.selectUserRoleRelByObj(beanValue));
	}


}