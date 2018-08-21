package kr.co.common.data.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Class Name : CustomDateTypeHandler.java
 * @Description : Mybatis Date Handler
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CustomDateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setTimestamp(i, new Timestamp((parameter).getTime()));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertStringToDate(rs.getString(columnName));
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertStringToDate(rs.getString(columnIndex));
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return convertStringToDate(cs.getString(columnIndex));
    }

    private Date convertStringToDate(String strValue) throws SQLException {

        DateFormat beforeFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date tempDate = null;
        Date result = null;
        String convertDate = "";
        if (strValue == null) {
            return null;
        }
        if ( strValue.length() > 8 ) {
            strValue = strValue.substring(0,8);
        }
        try {
            // 현재 yyyymmdd로된 날짜 형식으로 java.util.Date객체를 만든다.
            tempDate = beforeFormat.parse(strValue);
            convertDate = afterFormat.format(tempDate);
            result = Date.valueOf(convertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;

    }

}
