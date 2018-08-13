package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : PwdChgHistVO.java
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PwdChgHistVO extends CmmVO {

    private static final long serialVersionUID = -1556192295591875098L;
    /** 사용자 아이디 */
    private String userId;
    /** 인덱스 */
    private Long idx;
    /** 이전 비밀번호 */
    private String priorPwd;
    /** 등록 IP */
    private String regIp;
    
    
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
     * @return the idx
     */
    public Long getIdx() {
        return idx;
    }
    /**
     * @param idx the idx to set
     */
    public void setIdx(Long idx) {
        this.idx = idx;
    }
    /**
     * @return the priorPwd
     */
    public String getPriorPwd() {
        return priorPwd;
    }
    /**
     * @param priorPwd the priorPwd to set
     */
    public void setPriorPwd(String priorPwd) {
        this.priorPwd = priorPwd;
    }
    /**
     * @return the regIp
     */
    public String getRegIp() {
        return regIp;
    }
    /**
     * @param regIp the regIp to set
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }
    
}
