package com.easy.auth.dao;

import com.easy.auth.bean.SysUser;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.utils.Assist;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 系统管理员 mapper
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface SysUserMapper {

	/**
	 * 获得Sysuser数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	long getSysUserRowCount(Assist assist);
	
	/**
	 * 获得Sysuser数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	List<SysUser> selectSysUser(Assist assist);
	/**
	 * 通过Sysuser的id获得Sysuser对象
	 * 
	 * @param id
	 * @return
	 */
	SysUser selectSysuserById(Integer id);
	
	/**
	 * 获得一个Sysuser对象,以参数Sysuser对象中不为空的属性作为条件进行查询,返回符合条件的第一条
	 * 
	 * @param obj
	 * @return
	 */
	SysUser selectSysUserObjSingle(SysUser obj);
	
	/**
	 * 获得一个Sysuser对象,以参数Sysuser对象中不为空的属性作为条件进行查询
	 * 
	 * @param obj
	 * @return
	 */
	List<SysUser> selectSysUserByObj(SysUser obj);

	/**
	 * 插入Sysuser到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertSysUser(SysUser value);
	
	/**
	 * 插入Sysuser中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int insertNotNullSysUser(SysUser value);
	
	/**
	 * 批量插入Sysuser到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertSysUserByBatch(List<SysUser> value);
	/**
	 * 通过Sysuser的id删除Sysuser
	 * 
	 * @param id
	 * @return
	 */
	int deleteSysUserById(Integer id);
	
	/**
	 * 通过辅助工具Assist的条件删除Sysuser
	 * 
	 * @param assist
	 * @return
	 */
	int deleteSysUserByAssist(Assist assist);
	
	/**
	 * 通过Sysuser的id更新Sysuser中的数据,包括null值
	 * 
	 * @param enti
	 * @return
	 */
	int updateSysUserById(SysUser enti);
	
	/**
	 * 通过Sysuser的id更新Sysuser中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullSysUserById(SysUser enti);
	
	/**
	 * 通过辅助工具Assist的条件更新Sysuser中的数据,包括null值
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateSysUser(@Param("enti") SysUser value, @Param("assist") Assist assist);
	
	/**
	 * 通过辅助工具Assist的条件更新Sysuser中属性不为null的数据
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateNotNullSysUser(@Param("enti") SysUser value, @Param("assist") Assist assist);

	SysUser selectOneByUserName(@Param("userName")String userName);

    AdminLoginFormDbDto getSysUserByUserUniquenessId(@Param("userUniquenessId")String userUniquenessId);

    List<String> findUserModelListByUniquenessId(String uniquenessId);
}