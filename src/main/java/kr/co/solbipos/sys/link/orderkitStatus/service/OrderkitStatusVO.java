package kr.co.solbipos.sys.link.orderkitStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : OrderkitStatusVO.java
 * @Description : 시스템관리 > 연동 > 오더킷현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.21  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class OrderkitStatusVO extends PageVO {

    private static final long serialVersionUID = 3840367174921222737L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 연동구분(OMS, QR, NAVER) */
    private String agencyFg;
    /** 연동활성화여부 */
    private String agencyUseYn;
    /** 최종응답 */
    private String lastResponse;
    /** 최종응답시간 */
    private String lastResponseDt;
    /** 최종결과코드 */
    private String lastStatusCode;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;
    /** 메뉴 리소스 코드 */
    private String resrceCd;
    /** 메뉴 리소스 명 */
    private String resrceNm;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }

    public String getAgencyUseYn() {
        return agencyUseYn;
    }

    public void setAgencyUseYn(String agencyUseYn) {
        this.agencyUseYn = agencyUseYn;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getLastResponseDt() {
        return lastResponseDt;
    }

    public void setLastResponseDt(String lastResponseDt) {
        this.lastResponseDt = lastResponseDt;
    }

    public String getLastStatusCode() {
        return lastStatusCode;
    }

    public void setLastStatusCode(String lastStatusCode) {
        this.lastStatusCode = lastStatusCode;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public String getResrceNm() {
        return resrceNm;
    }

    public void setResrceNm(String resrceNm) {
        this.resrceNm = resrceNm;
    }
}
