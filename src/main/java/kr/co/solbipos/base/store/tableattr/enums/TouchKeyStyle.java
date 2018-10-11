package kr.co.solbipos.base.store.tableattr.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

import java.util.Arrays;
import java.util.List;

/**
 * 테이블 속성 셀 스타일 enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR > FONT_NM, FONT_SIZE 등
 * 
 * @author bjcho
 *
 */
public enum TouchKeyStyle implements CodeEnum {
    
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
    /** 버튼곡선 */
    ROUNDED("rounded"),
    /** 테두리컬러 */
    STROKE_COLOR("strokeColor"),
    /** 리사이즈여부 */
    RESIZABLE("resizable"),
    /** 줄바꿈 */
    WHITE_SPACE("whiteSpace"),
    /** 수직정렬 */
    VERTICAL_ALIGN("verticalAlign"),
    /** 좌우정렬 */
    ALIGN("align"),

    /** CLASS_CD-custom */
    CLASS_CD("classCd"),
    /** TUKEY_CD-custom */
    TUKEY_CD("tukeyCd"),
    /** TUKEY_CD-custom */
    TUKEY_FG("tukeyFg"),
    /** TUKEY_CD-custom */
    STYLE_CD("styleCd"),
    /** 상품코드-custom */
    PROD_CD("prodCd"),
    /** 금액태그-custom */
    SALE_UPRC("saleUprc"),
    /** 테이블좌석수-custom */
    TBL_SEAT_CNT("tblSeatCnt"),
    /** 테이블좌석수-custom */
    TBL_TYPE_FG("tblTypeFg"),
    /** 테이블그룹구분-custom */
    TBL_GRP_FG("tblGrpFg"),
    /** 백그라운드컬러-custom */
    BG_COLOR("fillColor"),
    /** 백그라운드이미지-custom */
    BG_IMG("image"),
    /** 백그라운드이미지-custom */
    IMG_WIDTH("imageWidth"),
    /** 백그라운드이미지-custom */
    IMG_HEIGHT("imageHeight");

    
    private String code;
  
    TouchKeyStyle(String code) {
        this.code = code;
    }
    @MappedTypes(TouchKeyStyle.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TouchKeyStyle> {
        public TypeHandler() {
        super(TouchKeyStyle.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
    
    public static TouchKeyStyle getEnum(String code) {
        List<TouchKeyStyle> list = Arrays.asList(TouchKeyStyle.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }

}
