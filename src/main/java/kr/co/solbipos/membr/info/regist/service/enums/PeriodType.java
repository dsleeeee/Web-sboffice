package kr.co.solbipos.membr.info.regist.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 조회기간 타입 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum PeriodType implements CodeEnum {

    /** 기간 미사용 */
    ALL("all"),
    /** 가입일 */
    REG_DATE("reg"),
    /** 최종 방문일 */
    LAST_SALE_DATE("last");

    private String code;

    PeriodType(String code) {
        this.code = code;
    }
    
    @MappedTypes(PeriodType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PeriodType> {
        public TypeHandler() {
            super(PeriodType.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}