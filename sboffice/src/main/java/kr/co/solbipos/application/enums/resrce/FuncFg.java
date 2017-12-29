package kr.co.solbipos.application.enums.resrce;

import kr.co.solbipos.enums.EnumValue;

/**
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author 정용길
 *
 */
public enum FuncFg implements EnumValue<String> {
    
    SELECT("F001")      /** 기능 : 조회 */
    , INSERT("F002")    /** 기능 : 등록 */
    , UPDATE("F003")    /** 기능 : 수정 */
    , DELETE("F004");   /** 기능 : 삭제 */
    
    private final String value;
    
    FuncFg(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
