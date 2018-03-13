package kr.co.solbipos.base.domain.prod.touchkey;

import java.util.List;
import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.base.enums.InFg;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 기초관리 - 상품관리 - 판매터치키
 * table : TB_MS_TOUCH_CLASS, TB_MS_TOUCH
 * 
 * @author 조병준
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class TouchClass extends Cmm {

    private static final long serialVersionUID = 1L;

    /** 매장코드 */
    private String storeCd;

    /** 터치키분류코드 */
    private String tukeyClassCd;

    /** 터치키분류명 */
    private String tukeyClassNm;

    /** 페이지번호 */
    private Long pageNo;

    /** X */
    @Builder.Default private Long x = 0L;

    /** Y */
    @Builder.Default private Long y = 0L;

    /** 폭 */
    @Builder.Default private Long width = 0L;

    /** 높이 */
    @Builder.Default private Long height = 0L;

    /** 입력구분 H:본사, S:매장 */
    private InFg inFg;

    /** 폰트크기 */
    @Builder.Default private Long fontSize = 10L;

    /** 폰트색 */
    @Builder.Default private String fontColor = "#000000";

    /** 채움색 */
    @Builder.Default private String fillColor = "#000000";

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;
    
    /** 테이블들 */
    private List<Touch> touchs;

}
