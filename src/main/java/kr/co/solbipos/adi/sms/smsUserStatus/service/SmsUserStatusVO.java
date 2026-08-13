package kr.co.solbipos.adi.sms.smsUserStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsUserStatusVO.java
 * @Description : 부가서비스 > SMS관리 > SMS사용현황(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class SmsUserStatusVO extends PageVO {

    private static final long serialVersionUID = 5363926858165081293L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    /*private String orgnFg;*/

    /** 소속코드 */
    private String orgnCd;

    /** 소속명 */
    private String orgnNm;

    /** 사용자 ID */
    private String userId;

    /** 사용자명 */
    private String userNm;

    /** 발신번호 */
    private String telNo;

    /** sms사용자등록 구분 */
    private String smsUseRegFg;

    /*public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
*/

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getSmsUseRegFg() {
        return smsUseRegFg;
    }

    public void setSmsUseRegFg(String smsUseRegFg) {
        this.smsUseRegFg = smsUseRegFg;
    }
}
