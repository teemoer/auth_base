package com.easy.auth.service.impl;

import com.easy.auth.bean.RoleMenuRel;
import com.easy.auth.dao.RoleMenuRelMapper;
import com.easy.auth.infrastructure.config.redis.utils.RedisUtil;
import com.easy.auth.service.RoleMenuRelService;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * RoleMenuRel的服务接口的实现类
 * 
 * @author 
 *
 */
@Service
public class RoleMenuRelServiceImpl implements RoleMenuRelService {
	private static Logger logger = LoggerFactory.getLogger(RoleMenuRelServiceImpl.class);
	@Autowired
	private RoleMenuRelMapper roleMenuRelMapper;

	@Deprecated
	@Override
	public List<RoleMenuRel> find(RoleMenuRel value) {
		List<RoleMenuRel> result = roleMenuRelMapper.selectRoleMenuRelByObj(value);
		return result;
	}
	@Override
	public RoleMenuRel findOne(Integer id) {
		RoleMenuRel result = roleMenuRelMapper.selectRoleMenuRelById(id);
		return result;
	}

	@Override
	public int saveNotNull(RoleMenuRel value) {
		RedisUtil.adminOperatingSysUserModule();
		int result = roleMenuRelMapper.insertNotNullRoleMenuRel(value);
		return result;
	}
	@Override
	public int updateNotNullById(RoleMenuRel value) {
		int result = roleMenuRelMapper.updateNotNullRoleMenuRelById(value);
		return result;
	}

	@Override
	public int deleteById(Integer id) {
		RedisUtil.adminOperatingSysUserModule();
		int result = roleMenuRelMapper.deleteRoleMenuRelById(id);
		return result;
	}

	@Override
	public Result findByPageVo(PageVo<RoleMenuRel> pageBaseVo){
		PageHelper.startPage(pageBaseVo.getPage(), pageBaseVo.getPageSize());
		List<RoleMenuRel> beanList =
		roleMenuRelMapper.selectRoleMenuRelByObj(pageBaseVo.getAddition());
		return Result.success(new PageInfo<RoleMenuRel>(beanList));
	}

	@Override
    public Result selectListByObj(RoleMenuRel beanValue){
		return Result.success(roleMenuRelMapper.selectRoleMenuRelByObj(beanValue));
	}


}