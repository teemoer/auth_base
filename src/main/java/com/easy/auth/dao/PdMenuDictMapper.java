package com.easy.auth.dao;

import com.easy.auth.bean.PdMenuDict;
import com.easy.auth.utils.Assist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单信息 mapper
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public interface PdMenuDictMapper {


    List<PdMenuDict> findAllMenuByUserUniquenessId(String uniquenessId);

    /**
     * PdMenuDict的Dao接口
     *
     * @author
     */


    /**
     * 获得PdMenuDict数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @param assist
     * @return
     */
    long getPdMenuDictRowCount(Assist assist);

    /**
     * 获得PdMenuDict数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     *
     * @param assist
     * @return
     */
    List<PdMenuDict> selectPdMenuDict(Assist assist);

    /**
     * 通过PdMenuDict的id获得PdMenuDict对象
     *
     * @param id
     * @return
     */
    PdMenuDict selectPdMenuDictById(Integer id);

    PdMenuDict selectPdMenuDictByPath(String path);

    PdMenuDict selectByPath(PdMenuDict obj);

    /**
     * 获得一个PdMenuDict对象,以参数PdMenuDict对象中不为空的属性作为条件进行查询,返回符合条件的第一条
     *
     * @param obj
     * @return
     */
    PdMenuDict selectPdMenuDictObjSingle(PdMenuDict obj);

    /**
     * 获得一个PdMenuDict对象,以参数PdMenuDict对象中不为空的属性作为条件进行查询
     *
     * @param obj
     * @return
     */
    List<PdMenuDict> selectPdMenuDictByObj(PdMenuDict obj);

    List<PdMenuDict> selectByParentId(Integer parentId);

    /**
     * 插入PdMenuDict中属性值不为null的数据到数据库
     *
     * @param value
     * @return
     */
    int insertNotNullPdMenuDict(PdMenuDict value);

    /**
     * 批量插入PdMenuDict到数据库,包括null值
     *
     * @param value
     * @return
     */
    int insertPdMenuDictByBatch(List<PdMenuDict> value);

    /**
     * 通过PdMenuDict的id删除PdMenuDict
     *
     * @param id
     * @return
     */
    int deletePdMenuDictById(Integer id);

    /**
     * 通过辅助工具Assist的条件删除PdMenuDict
     *
     * @param assist
     * @return
     */
    int deletePdMenuDictByAssist(Assist assist);

    /**
     * 通过PdMenuDict的id更新PdMenuDict中的数据,包括null值
     *
     * @param enti
     * @return
     */
    int updatePdMenuDictById(PdMenuDict enti);

    /**
     * 通过PdMenuDict的id更新PdMenuDict中属性不为null的数据
     *
     * @param enti
     * @return
     */
    int updateNotNullPdMenuDictById(PdMenuDict enti);

    /**
     * 通过辅助工具Assist的条件更新PdMenuDict中的数据,包括null值
     *
     * @param value
     * @param assist
     * @return
     */
    int updatePdMenuDict(@Param("enti") PdMenuDict value, @Param("assist") Assist assist);

    /**
     * 通过辅助工具Assist的条件更新PdMenuDict中属性不为null的数据
     *
     * @param value
     * @param assist
     * @return
     */
    int updateNotNullPdMenuDict(@Param("enti") PdMenuDict value, @Param("assist") Assist assist);

    List<PdMenuDict> selectMenuByLikeMenuNameOrPath(@Param("menuNameOrPath") String menuNameOrPath);


    /**
     * 根据usid获取全部权限api菜单
     *
     * @param usid
     * @return
     */
    List<PdMenuDict> findAllMenuByUserUsid(@Param("usid") long usid);

    /**
     * 根据unid获取全部权限api菜单
     *
     * @param unid
     * @return
     */
    List<PdMenuDict> findAllMenuByUserUnId(@Param("unid") long unid);

}
