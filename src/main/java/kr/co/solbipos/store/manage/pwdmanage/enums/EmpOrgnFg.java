package kr.co.solbipos.store.manage.pwdmanage.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

/**
 * 사원 소속 구분
 *
 * @author bjcho
 */
public enum EmpOrgnFg implements CodeEnum  {

    /** 전체 */
    ALL(""),
    /** 본사 */
    HQ("H"),
    /** 매장 */
    STORE("S");

    private String code;

    EmpOrgnFg(String code) {
        this.code = code;
    }

    @MappedTypes(EmpOrgnFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<EmpOrgnFg> {
        public TypeHandler() {
        super(EmpOrgnFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
