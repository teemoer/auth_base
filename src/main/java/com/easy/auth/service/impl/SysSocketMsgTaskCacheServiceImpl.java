package com.easy.auth.service.impl;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.dao.SysSocketMsgTaskCacheMapper;
import com.easy.auth.dao.SysUserMapper;
import com.easy.auth.service.SysSocketMsgTaskCacheService;
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
 * socket消息发送缓存记录 服务层
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
public class SysSocketMsgTaskCacheServiceImpl implements SysSocketMsgTaskCacheService {
    private static Logger logger = LoggerFactory.getLogger(SysSocketMsgTaskCacheServiceImpl.class);
    @Autowired
    private SysSocketMsgTaskCacheMapper sysSocketMsgTaskCacheMapper;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Deprecated
    @Override
    public List<SysSocketMsgTaskCache> find(SysSocketMsgTaskCache value) {
        List<SysSocketMsgTaskCache> result =
                sysSocketMsgTaskCacheMapper.selectSysSocketMsgTaskCacheByObj(value);
        return result;
    }

    @Override
    public SysSocketMsgTaskCache findOne(Object id) {
        SysSocketMsgTaskCache result = sysSocketMsgTaskCacheMapper.selectSysSocketMsgTaskCacheById(id);
        return result;
    }

    @Override
    public Result saveNotNull(SysSocketMsgTaskCache value) {
        int result = sysSocketMsgTaskCacheMapper.insertNotNullSysSocketMsgTaskCache(value);
        if (result == 0) {
            return Result.failure("操作失败!");
        } else {
            return Result.success();
        }
    }

    @Override
    public Result updateNotNullById(SysSocketMsgTaskCache value) {
        int result = sysSocketMsgTaskCacheMapper.updateNotNullSysSocketMsgTaskCacheById(value);
        if (result == 0) {
            return Result.failure("操作失败!");
        } else {
            return Result.success();
        }
    }

    @Override
    public Result deleteById(Object id) {
        int result = sysSocketMsgTaskCacheMapper.deleteSysSocketMsgTaskCacheById(id);
        if (result == 0) {
            return Result.failure("操作失败!");
        } else {
            return Result.success();
        }
    }

    @Override
    public Result findByPageVo(PageVo<SysSocketMsgTaskCache> pageBaseVo) {
        PageHelper.startPage(pageBaseVo.getPage(), pageBaseVo.getPageSize());
        List<SysSocketMsgTaskCache> beanList =
                sysSocketMsgTaskCacheMapper.selectSysSocketMsgTaskCacheByObj(pageBaseVo.getAddition());
        return Result.success(new PageInfo<>(beanList));
    }

    @Override
    public Result selectListByObj(SysSocketMsgTaskCache beanValue) {
        return Result.success(sysSocketMsgTaskCacheMapper.selectSysSocketMsgTaskCacheByObj(beanValue));
    }

    @Override
    public List<String> findUserUniquenessIdByAreaCodeAndModelName(
            String higherAuthorities, String modelName) {
        List<String> sysUnitmanageUserUniquenessIdList =
                sysSocketMsgTaskCacheMapper.findSysUnitmanageUserUniquenessIdByAreaCodeAndModelName(
                        higherAuthorities, modelName);

        List<String> sysUserUniquenessIdList =
                sysSocketMsgTaskCacheMapper.findSysUserUniquenessIdByAreaCodeAndModelName(
                        higherAuthorities, modelName);
        if (sysUserUniquenessIdList.size() > 0) {
            sysUnitmanageUserUniquenessIdList.addAll(sysUserUniquenessIdList);
        }
        return sysUnitmanageUserUniquenessIdList;
    }
}
