package kr.co.solbipos.base.domain.store.tableattr;

import kr.co.solbipos.application.domain.cmm.CmmVO;
import kr.co.solbipos.base.enums.AttrCd;
import kr.co.solbipos.base.enums.TblTypeFg;
import kr.co.solbipos.base.enums.TextalignFg;
import kr.co.solbipos.base.enums.TextvalignFg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 기초관리 - 매장관리 - 테이블속성
 * table : TB_CM_TABLE_ATTR, TB_MS_TABLE_ATTR
 * 
 * @author 조병준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TableAttrVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 매장코드 */
    private String storeCd;

    /** 테이블유형구분 */
    private TblTypeFg tblTypeFg;

    /** 속성코드 */
    private AttrCd attrCd;

    /** 속성명 */
    private String attrNm;

    /** X */
    @Builder.Default private Long x = 0L;

    /** Y */
    @Builder.Default private Long y = 0L;

    /** 폭 */
    @Builder.Default private Long width = 0L;

    /** 높이 */
    @Builder.Default private Long height = 0L;

    /** 텍스트수평정렬구분 */
    @Builder.Default private TextalignFg textalignFg = TextalignFg.CENTER;
    
    /** 텍스트수직정렬구분 */
    @Builder.Default private TextvalignFg textvalignFg = TextvalignFg.MIDDLE;

    /** 이미지명 */
    private String imgNm;

    /** 폰트명 */
    @Builder.Default private String fontNm = "NotoR";

    /** 폰트크기 */
    @Builder.Default private Long fontSize = 10L;

    /** 폰트스타일구분 */
    @Builder.Default private String fontStyleFg = "0";

    /** 폰트색 */
    @Builder.Default private String fontColor = "#000000";

    /** 사용여부 */
    private String useYn;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

}
