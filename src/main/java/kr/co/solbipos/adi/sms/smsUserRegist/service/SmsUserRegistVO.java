package kr.co.solbipos.adi.sms.smsUserRegist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsUserRegistVO.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SmsUserRegistVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 사용자아이디 */
    private String userId;

    /** DI(중복가입확인정보) */
    private String di;

    /** 휴대폰번호 */
    private String telNo;

    /** 동의1여부(본인확인 개인정보 수집이용동의) */
    private String agree1Yn;

    /** 동의2여부(SMS 발신 서비스 이용약관) */
    private String agree2Yn;

    /** 등록일시 */
    private String regDt;

    /** 등록자 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정자 */
    private String modId;

    /** 사용여부 */
    private String useYn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAgree1Yn() {
        return agree1Yn;
    }

    public void setAgree1Yn(String agree1Yn) {
        this.agree1Yn = agree1Yn;
    }

    public String getAgree2Yn() {
        return agree2Yn;
    }

    public void setAgree2Yn(String agree2Yn) {
        this.agree2Yn = agree2Yn;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
