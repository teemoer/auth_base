package com.easy.auth.dao;

import com.easy.auth.bean.SysApiWhiteList;
import com.util.Assist;

import java.util.List;

/**
 * 白名单url mapper
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface SysApiWhiteListMapper {

    /**
     * 获得SysApiWhiteList数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @param assist
     * @return
     */
    long getSysApiWhiteListRowCount(Assist assist);

    /**
     * 通过SysApiWhiteList的id获得SysApiWhiteList对象
     *
     * @param id
     * @return
     */
    SysApiWhiteList selectSysApiWhiteListById(Integer id);

    /**
     * 获取所有数据
     *
     * @return
     */
    SysApiWhiteList selectSysApiWhiteList();


    /**
     * 获得一个SysApiWhiteList对象,以参数SysApiWhiteList对象中不为空的属性作为条件进行查询
     *
     * @param obj
     * @return
     */
    List<SysApiWhiteList> selectSysApiWhiteListByObj(SysApiWhiteList obj);

    /**
     * 插入SysApiWhiteList中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int insertNotNullSysApiWhiteList(SysApiWhiteList value);

    /**
     * 批量插入SysApiWhiteList到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertSysApiWhiteListByBatch(List<SysApiWhiteList> value);

    /**
     * 通过SysApiWhiteList的id删除SysApiWhiteList
     *
     * @param id
     * @return
     */
    int deleteSysApiWhiteListById(Integer id);

    /**
     * 通过SysApiWhiteList的id更新SysApiWhiteList中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNotNullSysApiWhiteListById(SysApiWhiteList enti);


    List<String> getAllWhiteUrlList();


    int countByApiUrl(String apiUrl);

}