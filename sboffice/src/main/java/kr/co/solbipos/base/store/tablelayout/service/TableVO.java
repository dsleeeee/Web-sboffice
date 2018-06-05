package kr.co.solbipos.base.store.tablelayout.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.store.tableattr.enums.TblTypeFg;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 기초관리 - 매장관리 - 테이블구성
 * table : TB_MS_TABLE
 * 
 * @author 조병준
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class TableVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 매장코드 */
    private String storeCd;

    /** 테이블코드 */
    private String tblCd;

    /** 테이블명 */
    private String tblNm;

    /** 테이블그룹코드 */
    private String tblGrpCd;

    /** 테이블좌석수 */
    @Builder.Default private Long tblSeatCnt = 0L;;

    /** X */
    @Builder.Default private Long x = 0L;

    /** Y */
    @Builder.Default private Long y = 0L;

    /** 폭 */
    @Builder.Default private Long width = 0L;

    /** 높이 */
    @Builder.Default private Long height = 0L;

    /** 테이블유형구분 */
    private TblTypeFg tblTypeFg;

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
