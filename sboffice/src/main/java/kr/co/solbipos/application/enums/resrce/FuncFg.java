package kr.co.solbipos.application.enums.resrce;

import kr.co.solbipos.enums.EnumValue;

/**
 * 리소스 기능 구분 enum type<br>
 * TB_WB_RESRCE_INFO > ResrceFg
 * 
 * @author 정용길
 *
 */
public enum FuncFg implements EnumValue<String> {
    
    /** 기능 : 조회 */
    SELECT("F001")
    /** 기능 : 등록 */
    , INSERT("F002")
    /** 기능 : 수정 */
    , UPDATE("F003")
    /** 기능 : 삭제 */
    , DELETE("F004");
    
    private final String value;
    
    FuncFg(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
