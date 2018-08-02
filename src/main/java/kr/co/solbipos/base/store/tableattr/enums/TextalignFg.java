package kr.co.solbipos.base.store.tableattr.enums;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 테이블 속성 텍스트 Align enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR > TEXTALIGN_FG
 * 
 * @author bjcho
 *
 */
public enum TextalignFg implements CodeEnum {
    
    /** 왼쪽 */
    LEFT("1", "left"),
    /** 가운데 */
    CENTER("2", "center"),
    /** 오른쪽 */
    RIGHT("3", "right");
    
    private String code;
    private String desc;
  
    TextalignFg(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
   
    @MappedTypes(TextalignFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TextalignFg> {
        public TypeHandler() {
            super(TextalignFg.class);
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
     * @return
     */
    public static TextalignFg getEnum(String desc) {
        List<TextalignFg> list = Arrays.asList(TextalignFg.values());
        return list.stream().filter(m -> m.desc.equals(desc)).findAny().orElse(null);
    }

}
