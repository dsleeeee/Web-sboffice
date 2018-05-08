package kr.co.solbipos.base.enums;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 테이블 속성 텍스트 VAlign enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR > TEXTVALIGN_FG
 * 
 * @author bjcho
 *
 */
public enum TextvalignFg implements CodeEnum {
    
    /** 상단 */
    TOP("1", "top"),
    /** 중간 */
    MIDDLE("2", "middle"),
    /** 하단 */
    BOTTOM("3", "bottom");
    
    private String code;
    private String desc;
  
    TextvalignFg(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
   
    @MappedTypes(TextvalignFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TextvalignFg> {
        public TypeHandler() {
            super(TextvalignFg.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

    /**
     * 설명 텍스트로 ENUM 조회
     * @param desc
     * @return ENUM
     */
    public static TextvalignFg getEnum(String desc) {
        List<TextvalignFg> list = Arrays.asList(TextvalignFg.values());
        return list.stream().filter(m -> m.desc.equals(desc)).findAny().orElse(null);
    }

}
