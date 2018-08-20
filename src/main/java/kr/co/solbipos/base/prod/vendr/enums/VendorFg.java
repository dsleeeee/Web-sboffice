package kr.co.solbipos.base.prod.vendr.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 거래처구분 enum type 013<br>
 * 거래처 조회에서 사용
 * 
 * @author hmnoh
 *
 */
public enum VendorFg implements CodeEnum {
    
    /** 전체 */
    ALL("all"),
    /** 매입거래처 */
    A("1"),
    /** 매출거래처 */
    B("2");
    
    private String code;
  
    VendorFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(VendorFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<VendorFg> {
        public TypeHandler() {
            super(VendorFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
