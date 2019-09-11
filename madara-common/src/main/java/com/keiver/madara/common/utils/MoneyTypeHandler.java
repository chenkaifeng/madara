package com.keiver.madara.common.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


/**
 * mybatis处理表示Money的字段
 * @author Chenkf
 * @version $Id: MoneyTypeHandler.java, v 0.1 2018年6月12日 下午10:21:45 Chenkf Exp $
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {

    /**
     * 数据库中币种字段名
     */
    public static final String DATABASE_CURRENCY_COLUMN_NAME     = "CURRENCY_TYPE";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getCent());
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String currencyType = rs.getString(DATABASE_CURRENCY_COLUMN_NAME);
        Money money = new Money(0L, Currency.getInstance(currencyType));
        money.setCent(rs.getLong(columnName));
        return money;
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        String currencyType = rs.getString(DATABASE_CURRENCY_COLUMN_NAME);
        Money money = new Money(0L, Currency.getInstance(currencyType));
        money.setCent(rs.getLong(columnIndex));
        return money;
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        String currencyType = cs.getString(DATABASE_CURRENCY_COLUMN_NAME);
        Money money = new Money(0L, Currency.getInstance(currencyType));
        money.setCent(cs.getLong(columnIndex));
        return money;
    }

}
