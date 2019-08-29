package com.easy.auth.service.impl;

import com.easy.auth.bean.SysApiWhiteList;
import com.easy.auth.common.handler.RoleValid;
import com.easy.auth.controller.sys.dto.SysApiWhiteListCo;
import com.easy.auth.dao.SysApiWhiteListMapper;
import com.easy.auth.service.SysApiWhiteListService;
import com.easy.auth.utils.returns.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统白名单url服务层
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
public class SysApiWhiteListServiceImpl implements SysApiWhiteListService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysApiWhiteListMapper sysApiWhiteListMapper;

    @Override
    public Result findByPage(SysApiWhiteListCo co) {
        int page = co.getPage() < 1 ? 1 : co.getPage();
        int pageSize = co.getPageSize() < 1 ? 10 : co.getPageSize();
        PageHelper.startPage(page, pageSize);
        List<SysApiWhiteList> sysApiWhiteList =
                sysApiWhiteListMapper.selectSysApiWhiteListByObj(co.getSysApiWhiteList());
        PageInfo<SysApiWhiteList> pageInfo = new PageInfo<>(sysApiWhiteList);
        return Result.success(pageInfo);
    }

    @Override
    public Result saveOrUpdate(SysApiWhiteList sysApiWhiteList) {
        if (sysApiWhiteList.getCreateTime() == null && sysApiWhiteList.getId() == null) {
            sysApiWhiteList.setCreateTime(new Date());
        }
        try {

            if (sysApiWhiteList.getId() != null) {
                SysApiWhiteList oldData =
                        sysApiWhiteListMapper.selectSysApiWhiteListById(sysApiWhiteList.getId());
                sysApiWhiteListMapper.updateNotNullSysApiWhiteListById(sysApiWhiteList);
                if (!oldData.getApiUrl().equals(sysApiWhiteList.getApiUrl())) {
                    Map<String, String> allWhiteUrlListUrlMap = RoleValid.getAllWhiteUrlListUrlMap();
                    allWhiteUrlListUrlMap.remove(oldData.getApiUrl());
                    allWhiteUrlListUrlMap.put(sysApiWhiteList.getApiUrl(), sysApiWhiteList.getApiUrl());
                    RoleValid.setAllWhiteUrlListUrlMap(allWhiteUrlListUrlMap);
                }
            } else {
                int count = sysApiWhiteListMapper.countByApiUrl(sysApiWhiteList.getApiUrl());
                if (count > 0) {
                    return Result.failure("新增失败,该ApiUrl已存在！");
                }
                Map<String, String> allWhiteUrlListUrlMap = RoleValid.getAllWhiteUrlListUrlMap();
                sysApiWhiteListMapper.insertNotNullSysApiWhiteList(sysApiWhiteList);
                allWhiteUrlListUrlMap.put(sysApiWhiteList.getApiUrl(), sysApiWhiteList.getApiUrl());
                RoleValid.setAllWhiteUrlListUrlMap(allWhiteUrlListUrlMap);
            }
            return Result.success();
        } catch (Exception e) {
            logger.error("新增或修改白名单报错:{}", e);
        }

        return Result.failure("操作失败！");
    }

    @Override
    public Result deleteById(Integer sysApiWhiteListId) {
        SysApiWhiteList oldData = sysApiWhiteListMapper.selectSysApiWhiteListById(sysApiWhiteListId);
        if (oldData != null) {
            String apiUrl = oldData.getApiUrl();
            Integer i = sysApiWhiteListMapper.deleteSysApiWhiteListById(sysApiWhiteListId);
            Map<String, String> allWhiteUrlListUrlMap = RoleValid.getAllWhiteUrlListUrlMap();
            allWhiteUrlListUrlMap.remove(apiUrl);
            RoleValid.setAllWhiteUrlListUrlMap(allWhiteUrlListUrlMap);
            if (i > 0) {
                return Result.success();
            } else {
                return Result.failure("操作失败！");
            }
        }
        return Result.failure("操作失败！");
    }

    @Override
    public Map<String, String> getAllWhiteUrlList() {
        List<String> allWhiteUrlList = sysApiWhiteListMapper.getAllWhiteUrlList();
        Map<String, String> map =
                allWhiteUrlList.stream().collect(Collectors.toMap(String::toString, Function.identity()));
        return map;
    }
}
