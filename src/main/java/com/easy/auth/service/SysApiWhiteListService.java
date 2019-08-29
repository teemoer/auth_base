package com.easy.auth.service;


import com.easy.auth.controller.sys.dto.SysApiWhiteListCo;
import com.easy.auth.bean.SysApiWhiteList;
import com.easy.auth.utils.returns.Result;

import java.util.Map;

public interface SysApiWhiteListService {


    Result findByPage(SysApiWhiteListCo co);

    Result saveOrUpdate(SysApiWhiteList sysApiWhiteList);

    Result deleteById(Integer sysApiWhiteListId);

    Map<String,String> getAllWhiteUrlList();

}
