package com.easy.auth.dao;
import java.util.List;

import com.util.Assist;
import com.easy.auth.bean.RoleMenuRel;

import org.apache.ibatis.annotations.Param;
/**
 * RoleMenuRel的Dao接口
 * 
 * @author 
 *
 */
public interface RoleMenuRelMapper {

	/**
	 * 获得RoleMenuRel数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	long getRoleMenuRelRowCount(Assist assist);
	
	/**
	 * 获得RoleMenuRel数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @param assist
	 * @return
	 */
	List<RoleMenuRel> selectRoleMenuRel(Assist assist);
	/**
	 * 通过RoleMenuRel的id获得RoleMenuRel对象
	 * 
	 * @param id
	 * @return
	 */
	RoleMenuRel selectRoleMenuRelById(Integer id);
	
	/**
	 * 获得一个RoleMenuRel对象,以参数RoleMenuRel对象中不为空的属性作为条件进行查询,返回符合条件的第一条
	 * 
	 * @param obj
	 * @return
	 */
	RoleMenuRel selectRoleMenuRelObjSingle(RoleMenuRel obj);
	
	/**
	 * 获得一个RoleMenuRel对象,以参数RoleMenuRel对象中不为空的属性作为条件进行查询
	 * 
	 * @param obj
	 * @return
	 */
	List<RoleMenuRel> selectRoleMenuRelByObj(RoleMenuRel obj);

	/**
	 * 插入RoleMenuRel到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertRoleMenuRel(RoleMenuRel value);
	
	/**
	 * 插入RoleMenuRel中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int insertNotNullRoleMenuRel(RoleMenuRel value);
	
	/**
	 * 批量插入RoleMenuRel到数据库,包括null值
	 * 
	 * @param value
	 * @return
	 */
	int insertRoleMenuRelByBatch(List<RoleMenuRel> value);
	/**
	 * 通过RoleMenuRel的id删除RoleMenuRel
	 * 
	 * @param id
	 * @return
	 */
	int deleteRoleMenuRelById(Integer id);
	
	/**
	 * 通过辅助工具Assist的条件删除RoleMenuRel
	 * 
	 * @param assist
	 * @return
	 */
	int deleteRoleMenuRelByAssist(Assist assist);
	
	/**
	 * 通过RoleMenuRel的id更新RoleMenuRel中的数据,包括null值
	 * 
	 * @param enti
	 * @return
	 */
	int updateRoleMenuRelById(RoleMenuRel enti);
	
	/**
	 * 通过RoleMenuRel的id更新RoleMenuRel中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullRoleMenuRelById(RoleMenuRel enti);
	
	/**
	 * 通过辅助工具Assist的条件更新RoleMenuRel中的数据,包括null值
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateRoleMenuRel(@Param("enti") RoleMenuRel value, @Param("assist") Assist assist);
	
	/**
	 * 通过辅助工具Assist的条件更新RoleMenuRel中属性不为null的数据
	 * 
	 * @param value
	 * @param assist
	 * @return
	 */
	int updateNotNullRoleMenuRel(@Param("enti") RoleMenuRel value, @Param("assist") Assist assist);
}