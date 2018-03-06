package kr.co.solbipos.base.enums;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.enums.CodeEnum;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 테이블 속성 구분 enum type<br>
 * TB_MS_TABLE_ATTR, TB_CM_TABLE_ATTR > ATTR_CD
 * 
 * @author bjcho
 *
 */
public enum AttrCd implements CodeEnum {
    
    /** 테이블명 */
    TABLE_NAME("01"),
    /** 주문금액 */
    ORDER_AMOUNT("02"),
    /** 손님수 */
    GUEST_COUNT("03"),
    /** 담당자 */
    CHARGE("04"),
    /** 경과시간 */
    ELAPSED_TIME("05"),
    /** 단체명 */
    ORG("06"),
    /** 메뉴리스트 */
    MENU("07"),
    /** 테이블상태-사용 */
    TABLE_USE("08"),
    /** 테이블상태-분할 */
    TABLE_SPLIT("09"),
    /** 테이블상태-예약 */
    TABLE_RESV("10"),
    /** 배달상태 */
    DLVR_STATUS("11"),
    /** 배달주소 */
    DLVR_ADDRESS("12"),
    /** 배달연락처 */
    DLVR_CONTRACT("13"),
    /** 배달원명 */
    DLVR_NM("14"),
    /** 배달원명 */
    RETURN_NO("15");
    
    private String code;
    private AttrCd[] values;
  
    AttrCd(String code, AttrCd... values) {
        this.code = code;
        this.values = values;
    }
  
    public AttrCd[] getValues() {
        return values;
    }
   
    @MappedTypes(AttrCd.class)
    public static class TypeHandler extends CodeEnumTypeHandler<AttrCd> {
        public TypeHandler() {
        super(AttrCd.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    public static AttrCd getEnum(String code) {
        List<AttrCd> list = Arrays.asList(AttrCd.values());
        return list.stream().filter(m -> m.code.equals(code)).findAny().orElse(null);
    }
}
