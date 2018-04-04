package kr.co.solbipos.adi.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * {@link kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage}<br>
 * {@code inFg} enum type<br>
 * 근태 입력 구분
 * 
 * @author 정용길
 *
 */
public enum DclzInFg implements CodeEnum {

    /** 전체 */
    ALL("ALL")
    /** 웹 입력 */
    ,WEB("010")
    /** 포스 입력 */
    ,POS("020")
    /**  */
    ,SVR("030");

    private String code;

    DclzInFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(DclzInFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<DclzInFg> {
        public TypeHandler() {
            super(DclzInFg.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
