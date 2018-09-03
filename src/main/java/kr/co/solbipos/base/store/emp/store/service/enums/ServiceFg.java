package kr.co.solbipos.base.store.emp.store.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

public enum ServiceFg implements CodeEnum {
    /** 재직 */
    WORK("1"),
    /** 퇴사 */
    RESIGNATION("2"),
    /** 휴직 */
    TAKE_TIME_OFF("3"),
    /** 기타 */
    ETC("4");

    String code;

    ServiceFg(String code) {
        this.code = code;
    }

    @MappedTypes(ServiceFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ServiceFg> {
        public TypeHandler() {
            super(ServiceFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
