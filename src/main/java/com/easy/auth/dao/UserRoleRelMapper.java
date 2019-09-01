package com.easy.auth.dao;

import com.easy.auth.bean.UserRoleRel;
import com.easy.auth.utils.Assist;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * UserRoleRel的Dao接口
 * 
 * @author 
 *
 */
public interface UserRoleRelMapper {

	/**
	 * 获得UserRoleRel数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	long getUserRoleRelRowCount(Assist assist);
	
	/**
	 * 获得UserRoleRel数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	List<UserRoleRel> selectUserRoleRel(Assist assist);
	/**
	 * 通过UserRoleRel的id获得UserRoleRel对象
	 * 
	 * @param id
	 * @return
	 */
	UserRoleRel selectUserRoleRelById(Integer id);
	
	/**
	 * 获得一个UserRoleRel对象,以参数UserRoleRel对象中不为空的属性作为条件进行查询,返回符合条件的第一条
	 * 
	 * @param obj
	 * @return
	 */
	UserRoleRel selectUserRoleRelObjSingle(UserRoleRel obj);
	
	/**
	 * 获得一个UserRoleRel对象,以参数UserRoleRel对象中不为空的属性作为条件进行查询
	 * 
	 * @param obj
	 * @return
	 */
	List<UserRoleRel> selectUserRoleRelByObj(UserRoleRel obj);

	/**
	 * 插入UserRoleRel到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertUserRoleRel(UserRoleRel value);
	
	/**
	 * 插入UserRoleRel中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int insertNotNullUserRoleRel(UserRoleRel value);
	
	/**
	 * 批量插入UserRoleRel到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertUserRoleRelByBatch(List<UserRoleRel> value);
	/**
	 * 通过UserRoleRel的id删除UserRoleRel
	 * 
	 * @param id
	 * @return
	 */
	int deleteUserRoleRelById(Integer id);
	
	/**
	 * 通过辅助工具Assist的条件删除UserRoleRel
	 * 
	 * @param assist
	 * @return
	 */
	int deleteUserRoleRelByAssist(Assist assist);
	
	/**
	 * 通过UserRoleRel的id更新UserRoleRel中的数据,包括null值
	 * 
	 * @param enti
	 * @return
	 */
	int updateUserRoleRelById(UserRoleRel enti);
	
	/**
	 * 通过UserRoleRel的id更新UserRoleRel中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullUserRoleRelById(UserRoleRel enti);
	
	/**
	 * 通过辅助工具Assist的条件更新UserRoleRel中的数据,包括null值
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateUserRoleRel(@Param("enti") UserRoleRel value, @Param("assist") Assist assist);
	
	/**
	 * 通过辅助工具Assist的条件更新UserRoleRel中属性不为null的数据
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateNotNullUserRoleRel(@Param("enti") UserRoleRel value, @Param("assist") Assist assist);
}