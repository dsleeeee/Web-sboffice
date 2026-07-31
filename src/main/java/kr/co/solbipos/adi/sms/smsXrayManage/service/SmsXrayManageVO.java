package kr.co.solbipos.adi.sms.smsXrayManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsXrayManageVO.java
 * @Description : 부가서비스 > SMS관리 > SMS Xray관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SmsXrayManageVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 소속코드 */
    private String orgnCd;

    /** 소속명(본사/매장) */
    private String orgnNm;

    /** 요청ID */
    private String reqId;

    /** 요청자명 */
    private String reqNm;

    /** 승인여부 (0:요청, 1:승인, 2:반려) */
    private String apprFg;

    /** 사용자ID */
    private String userId;

    /** 발신번호 */
    private String telNo;

    /** 전송URL */
    private String sendUrl;

    /** 사용기간 */
    private String usePeriod;

    /** 사용시작일자 */
    private String useStartDate;

    /** 승인자 ID */
    private String apprId;

    /** 사유 */
    private String apprReason;

    /** URL 체크 결과 구분 (B:블랙리스트, W:화이트리스트, G:그레이(미등록)) - 탐지/차단결과 로그 조회조건 */
    private String urlType;

    /** 체크 URL 검색어 - 탐지/차단결과 로그 조회조건 */
    private String chkUrl;

    /** 메시지 상태 (allow/block) - 탐지/차단결과 로그 조회조건 */
    private String msgStatus;

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    public String getOrgnNm() {
        return orgnNm;
    }

    public void setOrgnNm(String orgnNm) {
        this.orgnNm = orgnNm;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqNm() {
        return reqNm;
    }

    public void setReqNm(String reqNm) {
        this.reqNm = reqNm;
    }

    public String getApprFg() {
        return apprFg;
    }

    public void setApprFg(String apprFg) {
        this.apprFg = apprFg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getUsePeriod() {
        return usePeriod;
    }

    public void setUsePeriod(String usePeriod) {
        this.usePeriod = usePeriod;
    }

    public String getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(String useStartDate) {
        this.useStartDate = useStartDate;
    }

    public String getApprId() {
        return apprId;
    }

    public void setApprId(String apprId) {
        this.apprId = apprId;
    }

    public String getApprReason() {
        return apprReason;
    }

    public void setApprReason(String apprReason) {
        this.apprReason = apprReason;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getChkUrl() {
        return chkUrl;
    }

    public void setChkUrl(String chkUrl) {
        this.chkUrl = chkUrl;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }
}
