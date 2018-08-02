package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : OtpAuthVO.java
 * @Description : 어플리케이션 > 세션 > 사용자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class OtpAuthVO extends CmmVO {

    private static final long serialVersionUID = 8082293790154692741L;
    /** 순서 */
    private String seq;
    /** 사용자 아이디 */
    private String userId;
    /** 인증 구분(904) */
    private String authFg;
    /** 인증 번호 */
    private String authNo;
    /** 수신 휴대폰 번호 */
    private String recvMpNo;
    /** 요청 IP */
    private String reqIp;
    /** 요청 일자 */
    private String reqDate;
    /** 요청 일시 */
    private String reqDt;
    /** 요청 일시 + 분 */
    private int otpLimit;
    
    
    /**
     * @return the seq
     */
    public String getSeq() {
        return seq;
    }
    /**
     * @param seq the seq to set
     */
    public void setSeq(String seq) {
        this.seq = seq;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the authFg
     */
    public String getAuthFg() {
        return authFg;
    }
    /**
     * @param authFg the authFg to set
     */
    public void setAuthFg(String authFg) {
        this.authFg = authFg;
    }
    /**
     * @return the authNo
     */
    public String getAuthNo() {
        return authNo;
    }
    /**
     * @param authNo the authNo to set
     */
    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }
    /**
     * @return the recvMpNo
     */
    public String getRecvMpNo() {
        return recvMpNo;
    }
    /**
     * @param recvMpNo the recvMpNo to set
     */
    public void setRecvMpNo(String recvMpNo) {
        this.recvMpNo = recvMpNo;
    }
    /**
     * @return the reqIp
     */
    public String getReqIp() {
        return reqIp;
    }
    /**
     * @param reqIp the reqIp to set
     */
    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }
    /**
     * @return the reqDate
     */
    public String getReqDate() {
        return reqDate;
    }
    /**
     * @param reqDate the reqDate to set
     */
    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }
    /**
     * @return the reqDt
     */
    public String getReqDt() {
        return reqDt;
    }
    /**
     * @param reqDt the reqDt to set
     */
    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }
    /**
     * @return the otpLimit
     */
    public int getOtpLimit() {
        return otpLimit;
    }
    /**
     * @param otpLimit the otpLimit to set
     */
    public void setOtpLimit(int otpLimit) {
        this.otpLimit = otpLimit;
    }
    
}
