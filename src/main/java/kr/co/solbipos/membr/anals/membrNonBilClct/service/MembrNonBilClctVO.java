package kr.co.solbipos.membr.anals.membrNonBilClct.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MembrNonBilClctVO.java
 * @Description : 회원관리 > 회원분석 > 회원 미수금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MembrNonBilClctVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 회원번호 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) {  this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    public String getMembrNm() {
        return membrNm;
    }

    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }
}