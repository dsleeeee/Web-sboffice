package kr.co.solbipos.pos.domain.confg.vermanage;

import kr.co.solbipos.application.domain.cmm.PageVO;
import kr.co.solbipos.pos.enums.VerRecvFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 포스관리 > POS 설정관리 > POS 버전 관리
 * 
 * @author 김지은
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplcStoreVO extends PageVO {

    /** 버전일련번호 */
    private String verSerNo;
    
    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 본사명 */
    private String hqOfficeNm;
    
    /** 매장코드 */
    private String storeCd;
    
    /** 매장명 */
    private String storeNm;
    
    /** 버전수신구분 */
    private VerRecvFg verRecvFg;
    
    /** 버전수신일시 */
    private String verRecvDt;
    
    /** 용도 */
    private String clsFg;
    
    /** 상태 */
    private String sysStatFg;
    
    /** van 코드 */
    private String vanCd;
    
    /** POS번호 */
    private String posNo;
    
    /** 포스명  */
    private String posNm;

    /** 포스IP  */
    private String posIp;
    
    /** 최종버전 */
    private String lastVer;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아이디 */
    private String modId;
    
}
