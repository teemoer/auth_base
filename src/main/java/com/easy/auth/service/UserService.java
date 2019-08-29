package com.easy.auth.service;

import com.easy.auth.bean.User;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;

import java.util.List;

public interface UserService {
    /**
     * 获得User数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @return
     */
    List<User> find(User value);

    /**
     * 通过User的id获得User对象
     *
     * @param id
     * @return
     */
    User findOne(Integer id);

    /**
     * 将User中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int saveNotNull(User value);

    /**
     * 通过User的id更新User中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNotNullById(User enti);

    /**
     * 通过User的id删除User
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);


    Result findByPageVo(PageVo<User> pageBaseVo);

    Result selectListByObj(User dsAttribute);

    User selectOneByUserName(String userName);

    List findUserModelListByUniquenessId(String uniquenessId);

    List<String> findFunctionListByUniquenessId(String uniquenessId);
}
