package kr.co.solbipos.adi.sms.smsChargeHist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsChargeHistVO.java
 * @Description : 부가서비스 > SMS관리 > SMS충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SmsChargeHistVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 소속코드 */
    private String orgnCd;

    /** 소속명 */
    private String orgnNm;

    /** 소속코드 */
    private String srchOrgnCd;

    /** 소속명 */
    private String srchOrgnNm;

    /** 소속구분 */
    private String srchOrgnFg;

    /** 소속코드 */
    private String selectOrgnCd;

    /** 소속명 */
    private String selectOrgnNm;

    /** 성공여부 */
    private String successYn;

    /** 결제수단 */
    private String pgresource;

    /** 용도 */
    private String clsFg;

    /** 상태 */
    private String sysStatFg;

    /** SMS충전수량 */
    private String smsChargeQty;

    /** SMS기초수량 */
    private String smsBaseQty;

    /** 잔여수량 */
    private String smsQty;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getOrgnNm() { return orgnNm; }

    public void setOrgnNm(String orgnNm) { this.orgnNm = orgnNm; }

    public String getSrchOrgnCd() { return srchOrgnCd; }

    public void setSrchOrgnCd(String srchOrgnCd) { this.srchOrgnCd = srchOrgnCd; }

    public String getSrchOrgnNm() { return srchOrgnNm; }

    public void setSrchOrgnNm(String srchOrgnNm) { this.srchOrgnNm = srchOrgnNm; }

    public String getSrchOrgnFg() { return srchOrgnFg; }

    public void setSrchOrgnFg(String srchOrgnFg) { this.srchOrgnFg = srchOrgnFg; }

    public String getSelectOrgnCd() { return selectOrgnCd; }

    public void setSelectOrgnCd(String selectOrgnCd) { this.selectOrgnCd = selectOrgnCd; }

    public String getSelectOrgnNm() { return selectOrgnNm; }

    public void setSelectOrgnNm(String selectOrgnNm) { this.selectOrgnNm = selectOrgnNm; }

    public String getSuccessYn() { return successYn; }

    public void setSuccessYn(String successYn) { this.successYn = successYn; }

    public String getPgresource() { return pgresource; }

    public void setPgresource(String pgresource) { this.pgresource = pgresource; }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getSmsChargeQty() { return smsChargeQty; }

    public void setSmsChargeQty(String smsChargeQty) { this.smsChargeQty = smsChargeQty; }

    public String getSmsBaseQty() { return smsBaseQty; }

    public void setSmsBaseQty(String smsBaseQty) { this.smsBaseQty = smsBaseQty; }

    public String getSmsQty() { return smsQty; }

    public void setSmsQty(String smsQty) {  this.smsQty = smsQty; }
}