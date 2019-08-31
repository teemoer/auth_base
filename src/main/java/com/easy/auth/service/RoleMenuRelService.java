package com.easy.auth.service;
import java.util.List;

import com.util.Assist;
import com.easy.auth.bean.RoleMenuRel;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.page.PageVo;

/**
 * RoleMenuRel的服务接口
 * 
 * @author 
 *
 */
public interface RoleMenuRelService {
	/**
	 * 获得RoleMenuRel数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @return
	 */
	List<RoleMenuRel> find(RoleMenuRel value);
	
	/**
	 * 通过RoleMenuRel的id获得RoleMenuRel对象
	 * 
	 * @param id
	 * @return
	 */
	RoleMenuRel findOne(Integer id);
	
	/**
	 * 将RoleMenuRel中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int saveNotNull(RoleMenuRel value);
	
	/**
	 * 通过RoleMenuRel的id更新RoleMenuRel中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullById(RoleMenuRel enti);
	
	/**
	 * 通过RoleMenuRel的id删除RoleMenuRel
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);


    Result findByPageVo(PageVo<RoleMenuRel> pageBaseVo);

    Result selectListByObj(RoleMenuRel dsAttribute);
}
