package com.easy.auth.service;
import java.util.List;

import com.easy.auth.bean.SysUser;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;

public interface SysUserService {
	/**
	 * 获得Sysuser数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @return
	 */
	List<SysUser> find(SysUser value);
	
	/**
	 * 通过Sysuser的id获得Sysuser对象
	 * 
	 * @param id
	 * @return
	 */
	SysUser findOne(Integer id);
	
	/**
	 * 将Sysuser中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int saveNotNull(SysUser value);
	
	/**
	 * 通过Sysuser的id更新Sysuser中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullById(SysUser enti);
	
	/**
	 * 通过Sysuser的id删除Sysuser
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);


    Result findByPageVo(PageVo<SysUser> pageBaseVo);

    Result selectListByObj(SysUser dsAttribute);

	SysUser selectOneByUserName(String userName);

    List findUserModelListByUniquenessId(String uniquenessId);

	List<String> findFunctionListByUniquenessId(String uniquenessId);

    AdminLoginFormDbDto getSysUserOrUserByUserUniquenessId(String userUniquenessId);
}
