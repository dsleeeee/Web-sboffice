package kr.co.solbipos.pos.confg.verrecv.service;

import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Class Name : VerRecvVO.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 수신현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class VerRecvVO extends PageVO {
    
    /** 버전일련번호 */
    private String verSerNo;
    
    /** 버전명 */
    private String verSerNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;
    
    /** 매장코드 */
    private String storeCd;
    
    /** 매장명 */
    private String storeNm;
    
    /** 최종버전 */
    private String lastVer;
    
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
