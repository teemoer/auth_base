package com.easy.auth.service.impl;

import com.easy.auth.bean.User;
import com.easy.auth.dao.UserMapper;
import com.easy.auth.service.UserService;
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
 * 普通用户服务层
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
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Deprecated
    @Override
    public List<User> find(User value) {
        List<User> result = userMapper.selectUserByObj(value);
        return result;
    }

    @Override
    public User findOne(Integer id) {
        User result = userMapper.selectUserById(id);
        return result;
    }

    @Override
    public int saveNotNull(User value) {
        int result = userMapper.insertNotNullUser(value);
        return result;
    }

    @Override
    public int updateNotNullById(User value) {
        int result = userMapper.updateNotNullUserById(value);
        return result;
    }

    @Override
    public int deleteById(Integer id) {
        int result = userMapper.deleteUserById(id);
        return result;
    }

    @Override
    public Result findByPageVo(PageVo<User> pageBaseVo) {
        PageHelper.startPage(pageBaseVo.getPage(), pageBaseVo.getPageSize());
        List<User> beanList =
                userMapper.selectUserByObj(pageBaseVo.getAddition());
        return Result.success(new PageInfo<User>(beanList));
    }

    @Override
    public Result selectListByObj(User beanValue) {
        return Result.success(userMapper.selectUserByObj(beanValue));
    }

    @Override
    public User selectOneByUserName(String userName) {
        return userMapper.selectOneByUserName(userName);
    }

    @Override
    public List findUserModelListByUniquenessId(String uniquenessId) {
        return userMapper.findUserModelListByUniquenessId(uniquenessId);
    }

    @Override
    public List<String> findFunctionListByUniquenessId(String uniquenessId) {
        return null;
    }


}