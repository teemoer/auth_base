package com.easy.auth.service;

import com.easy.auth.bean.SysSocketMsgTaskCache;
import com.easy.auth.utils.page.PageVo;
import com.easy.auth.utils.returns.Result;

import java.util.List;

public interface SysSocketMsgTaskCacheService {
    /**
     * 获得SysSocketMsgTaskCache数据集,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @return
     */
    List<SysSocketMsgTaskCache> find(SysSocketMsgTaskCache value);

    /**
     * 通过SysSocketMsgTaskCache的id获得SysSocketMsgTaskCache对象
     *
     * @param id
     * @return
     */
    SysSocketMsgTaskCache findOne(Object id);

    /**
     * 将SysSocketMsgTaskCache中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    Result saveNotNull(SysSocketMsgTaskCache value);

    /**
     * 通过SysSocketMsgTaskCache的id更新SysSocketMsgTaskCache中属性不为null的数据
     *
     * @param enti
     * @return
     */
    Result updateNotNullById(SysSocketMsgTaskCache enti);

    /**
     * 通过SysSocketMsgTaskCache的id删除SysSocketMsgTaskCache
     *
     * @param id
     * @return
     */
    Result deleteById(Object id);

    Result findByPageVo(PageVo<SysSocketMsgTaskCache> pageBaseVo);

    Result selectListByObj(SysSocketMsgTaskCache dsAttribute);

    List<String> findUserUniquenessIdByAreaCodeAndModelName(String higherAuthorities, String modelName);
}
