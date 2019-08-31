package com.easy.auth.service;
import java.util.List;

import com.easy.auth.service.impl.UserRoleRelServiceImpl;
import com.util.Assist;
import com.easy.auth.bean.UserRoleRel;
import com.easy.auth.utils.returns.Result;
import com.easy.auth.utils.page.PageVo;

/**
 * UserRoleRel的服务接口
 * 
 * @author 
 *
 */
public interface UserRoleRelService {
	/**
	 * 获得UserRoleRel数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * 
	 * @return
	 */
	List<UserRoleRel> find(UserRoleRel value);
	
	/**
	 * 通过UserRoleRel的id获得UserRoleRel对象
	 * 
	 * @param id
	 * @return
	 */
	UserRoleRel findOne(Integer id);
	
	/**
	 * 将UserRoleRel中属性值不为null的数据到数据库
	 * 
	 * @param value
	 * @return
	 */
	int saveNotNull(UserRoleRel value);
	
	/**
	 * 通过UserRoleRel的id更新UserRoleRel中属性不为null的数据
	 * 
	 * @param enti
	 * @return
	 */
	int updateNotNullById(UserRoleRel enti);
	
	/**
	 * 通过UserRoleRel的id删除UserRoleRel
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);


    Result findByPageVo(PageVo<UserRoleRel> pageBaseVo);

    Result selectListByObj(UserRoleRel dsAttribute);
}
