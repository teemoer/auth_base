package com.easy.auth.enums.base.typehandler;

import com.easy.auth.enums.common.EnableStatusEnum;
import com.easy.auth.enums.base.BaseEnum;
import com.easy.auth.enums.user.UserTableTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * mybatis枚举转换
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 * <p>
 * 所有定义在 bean 字段中的枚举类型 都需要在 此处的 MappedTypes 手动进行注册
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
@MappedTypes({
        EnableStatusEnum.class,
        UserTableTypeEnum.class,
})
public class UniversalEnumHandler<E extends BaseEnum> extends BaseTypeHandler<E> {
    private Class<E> type;

    private E[] enums;

    /**
     * 设置配置文件设置的转换类以及枚举类内容，比其他方法更便捷高效的实现
     *
     * @param type 配置文件中设置的转换类
     */
    public UniversalEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(
                    type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        // BaseTypeHandler已经做了parameter的null判断
        ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 根据数据库存储类型决定获取类型，数据库中存放String类型
        String i = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值，定位枚举子类
            return locateEnumStatus(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型， 数据库中存放String类型
        String i = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值
            return locateEnumStatus(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型， 数据库中存放String类型
        String i = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值
            return locateEnumStatus(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     *
     * @param value 数据库中存储的自定义value属性
     * @return value对应的枚举类
     */
    private E locateEnumStatus(String value) {
        for (E e : enums) {
            if (String.valueOf(e.getValue()).equals(value)) {
                return e;
            }
        }
        return null;
    }
}
