package kr.co.solbipos.application.pos.exceptForward.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 기타출고 > 상태 enum type<br>
 * TB_NEOE_SPOS_EGR - FG_STATUS
 *
 * @author 김지은
 *
 */
public enum StatusFg implements CodeEnum {
    /** 정상(입력) */
    INSERT("0"),
    /** 삭제 */
    DELETE("1");

    String code;

    StatusFg(String code) {
        this.code = code;
    }

    @MappedTypes(StatusFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StatusFg> {
        public TypeHandler() {
            super(StatusFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
