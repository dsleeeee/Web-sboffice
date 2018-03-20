package kr.co.solbipos.base.enums;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 테이블 속성 셀 스타일 enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR > FONT_NM, FONT_SIZE 등
 * 
 * @author bjcho
 *
 */
public enum Style implements CodeEnum {
    
    /** 텍스트 정렬 구분 */
    TEXTALIGN_FG("align"),
    TEXTVALIGN_FG("verticalAlign"),
    /** 폰트명 */
    FONT_NM("fontFamily"),
    /** 폰트크기 */
    FONT_SIZE("fontSize"),
    /** 폰트스타일구분 */
    FONT_STYLE_FG("fontStyle"),
    /** 폰트색 */
    FONT_COLOR("fontColor"),
    /** 채움색 */
    FILL_COLOR("fillColor"),


    /** 상품코드-custom */
    PROD_CD("prodCd"),
    /** 테이블좌석수-custom */
    TBL_SEAT_CNT("tblSeatCnt"),
    /** 테이블좌석수-custom */
    TBL_TYPE_FG("tblTypeFg"),
    /** 테이블그룹구분-custom */
    TBL_GRP_FG("tblGrpFg"),
    /** 백그라운드컬러-custom */
    BG_COLOR("bgColor"),
    /** 백그라운드이미지-custom */
    BG_IMG("bgImg")
    ;
    
    
    private String code;
  
    Style(String code) {
        this.code = code;
    }
    @MappedTypes(Style.class)
    public static class TypeHandler extends CodeEnumTypeHandler<Style> {
        public TypeHandler() {
        super(Style.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
    public static Style getEnum(String code) {
        List<Style> list = Arrays.asList(Style.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }

}
