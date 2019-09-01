package com.easy.auth.dao;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.utils.Assist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * socket消息缓存记录 mapper
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface SysSocketMsgTaskCacheMapper {

    /**
     * 获得SysSocketMsgTaskCache数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @param assist
     * @return
     */
    long getSysSocketMsgTaskCacheRowCount(Assist assist);

    /**
     * 获得SysSocketMsgTaskCache数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @param assist
     * @return
     */
    List<SysSocketMsgTaskCache> selectSysSocketMsgTaskCache(Assist assist);

    /**
     * 通过SysSocketMsgTaskCache的id获得SysSocketMsgTaskCache对象
     *
     * @param id
     * @return
     */
    SysSocketMsgTaskCache selectSysSocketMsgTaskCacheById(Object id);

    /**
     * 获得一个SysSocketMsgTaskCache对象,以参数SysSocketMsgTaskCache对象中不为空的属性作为条件进行查询,返回符合条件的第一条
     *
     * @param obj
     * @return
     */
    SysSocketMsgTaskCache selectSysSocketMsgTaskCacheObjSingle(SysSocketMsgTaskCache obj);

    /**
     * 获得一个SysSocketMsgTaskCache对象,以参数SysSocketMsgTaskCache对象中不为空的属性作为条件进行查询
     *
     * @param obj
     * @return
     */
    List<SysSocketMsgTaskCache> selectSysSocketMsgTaskCacheByObj(SysSocketMsgTaskCache obj);

    /**
     * 插入SysSocketMsgTaskCache到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertSysSocketMsgTaskCache(SysSocketMsgTaskCache value);

    /**
     * 插入SysSocketMsgTaskCache中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int insertNotNullSysSocketMsgTaskCache(SysSocketMsgTaskCache value);

    /**
     * 批量插入SysSocketMsgTaskCache到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertSysSocketMsgTaskCacheByBatch(List<SysSocketMsgTaskCache> value);

    /**
     * 通过SysSocketMsgTaskCache的id删除SysSocketMsgTaskCache
     *
     * @param id
     * @return
     */
    int deleteSysSocketMsgTaskCacheById(Object id);

    /**
     * 通过辅助工具Assist的条件删除SysSocketMsgTaskCache
     *
     * @param assist
     * @return
     */
    int deleteSysSocketMsgTaskCacheByAssist(Assist assist);

    /**
     * 通过SysSocketMsgTaskCache的id更新SysSocketMsgTaskCache中的数据,包括null值
     *
     * @param enti
     * @return
     */
    int updateSysSocketMsgTaskCacheById(SysSocketMsgTaskCache enti);

    /**
     * 通过SysSocketMsgTaskCache的id更新SysSocketMsgTaskCache中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNotNullSysSocketMsgTaskCacheById(SysSocketMsgTaskCache enti);

    /**
     * 通过辅助工具Assist的条件更新SysSocketMsgTaskCache中的数据,包括null值
     *
     * @param value
     * @param assist
     * @return
     */
    int updateSysSocketMsgTaskCache(@Param("enti") SysSocketMsgTaskCache value, @Param("assist") Assist assist);

    /**
     * 通过辅助工具Assist的条件更新SysSocketMsgTaskCache中属性不为null的数据
     *
     * @param value
     * @param assist
     * @return
     */
    int updateNotNullSysSocketMsgTaskCache(@Param("enti") SysSocketMsgTaskCache value, @Param("assist") Assist assist);

    List<String> findSysUnitmanageUserUniquenessIdByAreaCodeAndModelName(@Param("higherAuthorities") String higherAuthorities, @Param("modelName") String modelName);

    List<String> findSysUserUniquenessIdByAreaCodeAndModelName(@Param("higherAuthorities") String higherAuthorities, @Param("modelName") String modelName);
}