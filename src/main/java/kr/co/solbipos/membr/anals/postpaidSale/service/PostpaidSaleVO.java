package kr.co.solbipos.membr.anals.postpaidSale.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PostpaidSaleVO.java
 * @Description : 국민대 > 매출처관리 > 외상매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PostpaidSaleVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 회원번호 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    /** 매출구분 */
    private String saleFg;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) { this.membrNo = membrNo; }

    public String getMembrNm() {
        return membrNm;
    }

    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    public String getSaleFg() {
        return saleFg;
    }

    public void setSaleFg(String saleFg) {
        this.saleFg = saleFg;
    }
}