package kr.co.solbipos.store.enums;

import org.apache.ibatis.type.MappedTypes;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 환경설정 타겟
 * TB_HQ_ENVST
 * @author 김지은
 *
 */
public enum TargtFg implements CodeEnum {
    
    /** 공통(변경가능) */
    COMM("C"),
    /** 본사전용 */
    HQ("H"),
    /** 매장전용 */
    STORE("S"),
    /** 본사기준_매장까지 적용 */
    BOTH("X"),
    /** No */
    NO("N");

    private String code;
    
    TargtFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(HqType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TargtFg> {
        public TypeHandler() {
            super(TargtFg.class);
        }
    }
    
    @Override
    public String getCode() {
        return code;
    }

}
