package kr.co.solbipos.pos.license.oper.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : OperVO.java
 * @Description : 포스관리 > 라이선스관리 > 운영현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class OperVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 전체기간체크 */
    private boolean chkDt;

    /** 업체코드 */
    private String agencyCd;

    /** 업체명 */
    private String agencyNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 업체구분 */
    private String agencyFg;

    /** 소속코드 */
    private String orgnCd;

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }
}
