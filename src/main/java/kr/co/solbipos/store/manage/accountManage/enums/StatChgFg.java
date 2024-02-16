package kr.co.solbipos.store.manage.accountManage.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

public enum StatChgFg implements CodeEnum {

    /** 계정복구 */
    RECOVERY("recovery"),
    /** 계정삭제 */
    DELETE("delete"),
    /** 계정사용허용 */
    ALLOWED_USE("allowedUse"),
    /** 계정사용중지 */
    STOP_USING("stopUsing"),
    /** 계정휴면 */
    DORMANCY("dormancy");

    private String code;

    StatChgFg(String code) {
            this.code = code;
        }

    @MappedTypes(StatChgFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<StatChgFg> {
        public TypeHandler() {
            super(StatChgFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
