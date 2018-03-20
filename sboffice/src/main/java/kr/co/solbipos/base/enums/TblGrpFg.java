package kr.co.solbipos.base.enums;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 테이블 그룹 구분 enum type<br>
 * TB_MS_TABLE_GROUP > TBL_GRP_FG
 * 
 * @author bjcho
 *
 */
public enum TblGrpFg implements CodeEnum {
    
    /** 일반 */
    NORMAL("1", "1"),
    /** 포장 */
    TOGO("2", "2"),
    /** 배달 */
    DELIVERY("3", "3");
    
    private String code;
    private String desc;
  
    TblGrpFg(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
   
    @MappedTypes(TblGrpFg.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TblGrpFg> {
        public TypeHandler() {
            super(TblGrpFg.class);
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
    public static TblGrpFg getEnum(String desc) {
        List<TblGrpFg> list = Arrays.asList(TblGrpFg.values());
        return list.stream().filter(m -> m.desc.equals(desc)).findAny().orElse(null);
    }

}
