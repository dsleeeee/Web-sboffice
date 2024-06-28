package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : AlimtalkSendStatusVO.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AlimtalkSendStatusVO extends PageVO {

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
    private String alkSendSeq;

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

    /** API에서 쓰는 고정키값 */
    private String apiUrl;

    /** API에서 쓰는 고정키값 */
    private String appKey;

    /** API에서 쓰는 Header키 값 */
    private String secretKey;

    /** NHN매장계정 그룹아이디 */
    private String groupSenderKey;

    /** NHN매장계정 그룹아이디명 */
    private String groupSenderKeyNm;

    /** 문자 전송 성공시 메세지ID */
    private String requestId;

    /** 결과 */
    private String sendStatus;

    /** 예약일시 */
    private String sendDate;

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

    public String getAlkSendSeq() {
        return alkSendSeq;
    }

    public void setAlkSendSeq(String alkSendSeq) {
        this.alkSendSeq = alkSendSeq;
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

    public String getApiUrl() { return apiUrl; }

    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getAppKey() { return appKey; }

    public void setAppKey(String appKey) { this.appKey = appKey; }

    public String getSecretKey() { return secretKey; }

    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getGroupSenderKey() { return groupSenderKey; }

    public void setGroupSenderKey(String groupSenderKey) { this.groupSenderKey = groupSenderKey; }

    public String getGroupSenderKeyNm() { return groupSenderKeyNm; }

    public void setGroupSenderKeyNm(String groupSenderKeyNm) { this.groupSenderKeyNm = groupSenderKeyNm; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}