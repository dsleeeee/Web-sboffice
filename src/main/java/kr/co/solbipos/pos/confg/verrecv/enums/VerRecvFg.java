package kr.co.solbipos.pos.confg.verrecv.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 포스버전 수신구분
 * TB_CM_POS_VERSN_STORE > VER_RECV_FG
 * 
 * @author 김지은
 *
 */
public enum VerRecvFg implements CodeEnum {
    
    /** 버전등록 */
    REG("1"),
    /** 수신완료 */
    RECV("2"),
    /** 수신오류  */
    ERR("3");
    
    private String code;
  
    VerRecvFg(String code) {
        this.code = code;
    }
    
    @MappedTypes(VerRecvFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<VerRecvFg> {
        public TypeHandler() {
            super(VerRecvFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
}
