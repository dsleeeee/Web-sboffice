package kr.co.solbipos.membr.info.regist.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 기념일 조회 enum type<br>
 *
 * @author ygjeong
 *
 */
public enum AnvType implements CodeEnum {

    /** 생일 */
    BIRTHDAY("1"),
    /** 결혼기념일 */
    WEDDING("2");

    private String code;

    AnvType(String code) {
        this.code = code;
    }
    
    @MappedTypes(AnvType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<AnvType> {
        public TypeHandler() {
            super(AnvType.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
