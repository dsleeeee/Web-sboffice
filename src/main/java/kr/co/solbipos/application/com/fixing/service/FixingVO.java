package kr.co.solbipos.application.com.fixing.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
* @Class Name : FixingVO.java
* @Description : 어플리케이션 > 공통 > 고정메뉴
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  노현수      최초생성
*
* @author 솔비포스 차세대개발실 노현수
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class FixingVO extends CmmVO {

    private static final long serialVersionUID = -4799786653132758521L;
    /** 사용자 아이디 */
    private String userId;
    /** 리소스 코드 */
    private String resrceCd;
    /** 리소스코드 (Array) */
    private String[] resrceCds;
    
    
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
     * @return the resrceCd
     */
    public String getResrceCd() {
        return resrceCd;
    }
    /**
     * @param resrceCd the resrceCd to set
     */
    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }
    /**
     * @return the resrceCds
     */
    public String[] getResrceCds() {
        return resrceCds;
    }
    /**
     * @param resrceCds the resrceCds to set
     */
    public void setResrceCds(String[] resrceCds) {
        this.resrceCds = resrceCds;
    }
    
}
