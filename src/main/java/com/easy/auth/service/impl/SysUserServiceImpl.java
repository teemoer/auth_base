package com.easy.auth.service.impl;

import com.easy.auth.bean.SysUser;
import com.easy.auth.common.AdminLoginFormDbDto;
import com.easy.auth.dao.SysUserMapper;
import com.easy.auth.dao.UserMapper;
import com.easy.auth.service.SysUserService;
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
 * 系统用户服务层
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
public class SysUserServiceImpl implements SysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private UserMapper userMapper;

    @Deprecated
    @Override
    public List<SysUser> find(SysUser value) {
        List<SysUser> result = sysUserMapper.selectSysUserByObj(value);
        return result;
    }

    @Override
    public SysUser findOne(Integer id) {
        SysUser result = sysUserMapper.selectSysuserById(id);
        return result;
    }

    @Override
    public int saveNotNull(SysUser value) {
        int result = sysUserMapper.insertNotNullSysUser(value);
        return result;
    }

    @Override
    public int updateNotNullById(SysUser value) {
        int result = sysUserMapper.updateNotNullSysUserById(value);
        return result;
    }

    @Override
    public int deleteById(Integer id) {
        int result = sysUserMapper.deleteSysUserById(id);
        return result;
    }

    @Override
    public Result findByPageVo(PageVo<SysUser> pageBaseVo) {
        PageHelper.startPage(pageBaseVo.getPage(), pageBaseVo.getPageSize());
        List<SysUser> beanList =
                sysUserMapper.selectSysUserByObj(pageBaseVo.getAddition());
        return Result.success(new PageInfo<SysUser>(beanList));
    }

    @Override
    public Result selectListByObj(SysUser beanValue) {
        return Result.success(sysUserMapper.selectSysUserByObj(beanValue));
    }

    @Override
    public SysUser selectOneByUserName(String userName) {
        return sysUserMapper.selectOneByUserName(userName);
    }

    @Override
    public List findUserModelListByUniquenessId(String uniquenessId) {
        return sysUserMapper.findUserModelListByUniquenessId(uniquenessId);
    }

    @Override
    public List<String> findFunctionListByUniquenessId(String uniquenessId) {
        return null;
    }

    @Override
    public AdminLoginFormDbDto getSysUserOrUserByUserUniquenessId(String userUniquenessId) {
        AdminLoginFormDbDto adminLoginFormDbDto = sysUserMapper.getSysUserByUserUniquenessId(userUniquenessId);
        if (adminLoginFormDbDto == null) {
            adminLoginFormDbDto = userMapper.getUserByUserUniquenessId(userUniquenessId);
        }
        return adminLoginFormDbDto;
    }


}