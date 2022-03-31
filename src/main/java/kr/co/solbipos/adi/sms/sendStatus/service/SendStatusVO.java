package kr.co.solbipos.adi.sms.sendStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SendStatusVO.java
 * @Description : 부가서비스 > SMS관리 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SendStatusVO extends PageVO {

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

    /** 사용자ID */
    private String userId;

    /** 송신자 소속코드 */
    private String ssOrgnCd;

    /** 송신자 소속명 */
    private String ssOrgnNm;

    /** 수신자 소속코드 */
    private String rrOrgnCd;

    /** 수신자 소속명 */
    private String rrOrgnNm;

    /** 메시지고유ID */
    private String msgId;

    /** 구분 */
    private String gubun;

    /** 전송이력시퀀스 */
    private String smsSendSeq;

    /** 메세지타입 */
    private String msgType;

    /** 송신자 전화번호 */
    private String ssPhoneNumber;

    /** 예약여부 */
    private String reserveYn;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getSsOrgnCd() { return ssOrgnCd; }

    public void setSsOrgnCd(String ssOrgnCd) { this.ssOrgnCd = ssOrgnCd; }

    public String getSsOrgnNm() { return ssOrgnNm; }

    public void setSsOrgnNm(String ssOrgnNm) { this.ssOrgnNm = ssOrgnNm; }

    public String getRrOrgnCd() { return rrOrgnCd; }

    public void setRrOrgnCd(String rrOrgnCd) { this.rrOrgnCd = rrOrgnCd; }

    public String getRrOrgnNm() { return rrOrgnNm; }

    public void setRrOrgnNm(String rrOrgnNm) { this.rrOrgnNm = rrOrgnNm; }

    public String getMsgI() { return msgId; }

    public void setMsgId(String msgId) { this.msgId = msgId; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getSmsSendSeq() {
        return smsSendSeq;
    }

    public void setSmsSendSeq(String smsSendSeq) {
        this.smsSendSeq = smsSendSeq;
    }

    public String getMsgType() { return msgType; }

    public void setMsgType(String msgType) { this.msgType = msgType; }

    public String getSsPhoneNumber() { return ssPhoneNumber; }

    public void setSsPhoneNumber(String ssPhoneNumber) { this.ssPhoneNumber = ssPhoneNumber; }

    public String getReserveYn() { return reserveYn; }

    public void setReserveYn(String reserveYn) { this.reserveYn = reserveYn; }

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
}