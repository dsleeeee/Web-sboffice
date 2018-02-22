package kr.co.solbipos.system;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import kr.co.solbipos.enums.CodeEnum;

/**
 * Mybatis Enum 처리
 * @see https://www.holaxprogramming.com/2015/11/12/spring-boot-mybatis-typehandler/
 * @author bjcho
 *
 * @param <E>
 */
public abstract class CodeEnumTypeHandler <E extends Enum <E>> implements TypeHandler <CodeEnum> {
  
  private Class <E> type;

  public CodeEnumTypeHandler(Class <E> type) {
      this.type = type;
  }

  @Override
  public void setParameter(PreparedStatement ps, int i, CodeEnum parameter, JdbcType jdbcType) throws SQLException {
      ps.setString(i, parameter.getCode());
  }

  @Override
  public CodeEnum getResult(ResultSet rs, String columnName) throws SQLException {
      String code = rs.getString(columnName);
      return getCodeEnum(code);
  }

  @Override
  public CodeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
      String code = rs.getString(columnIndex);
      return getCodeEnum(code);
  }

  @Override
  public CodeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
      String code = cs.getString(columnIndex);
      return getCodeEnum(code);
  }

  private CodeEnum getCodeEnum(String code) {
      try {
          CodeEnum[] enumConstants = (CodeEnum[]) type.getEnumConstants();
          for (CodeEnum codeNum: enumConstants) {
              if (codeNum.getCode().equals(code)) {
                  return codeNum;
              }
          }
          return null;
      } catch (Exception e) {
          throw new TypeException("Can't make enum object '" + type + "'", e);
      }
  }
}