package kr.co.common.data.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Class Name : CustomBooleanTypeHandler.java
 * @Description : Mybatis Boolean Handler
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.07  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CustomBooleanTypeHandler extends BaseTypeHandler<Boolean> {

    private static final String YES = "Y";
    private static final String NO = "N";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter,
        JdbcType jdbcType) throws SQLException {
        String paramStr = (parameter != null && parameter == true) ? YES : NO;
        ps.setString(i, paramStr);
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertStringToBooelan(rs.getString(columnName));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertStringToBooelan(rs.getString(columnIndex));
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return convertStringToBooelan(cs.getString(columnIndex));
    }

    private Boolean convertStringToBooelan(String strValue) throws SQLException {
        if (strValue == null) {
            return false;
        }
        if (YES.equalsIgnoreCase(strValue)) {
            return new Boolean(true);
        } else if (NO.equalsIgnoreCase(strValue)) {
            return new Boolean(false);
        } else {
            throw new SQLException("Unexpected value " + strValue
                + " found where " + YES + " or " + NO + " was expected.");
        }
    }

}
