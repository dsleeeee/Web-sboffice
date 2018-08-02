package kr.co.solbipos.application.common.service;

/**
 * @Class Name : MenuUseHistVO.java
 * @Description : 메뉴 사용 기록 저장<br>table : TB_WB_MENU_USE_HIST
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
public class MenuUseHistVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    /** 순서 */
    private String seq;
    /** 사용자 아이디 */
    private String userId;
    /** 리소스 코드 */
    private String resrceCd;
    /** 사용 일자 */
    private String useDate;
    /** 사용 일시 */
    private String useDt;
    
    
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
     * @return the useDate
     */
    public String getUseDate() {
        return useDate;
    }
    /**
     * @param useDate the useDate to set
     */
    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }
    /**
     * @return the useDt
     */
    public String getUseDt() {
        return useDt;
    }
    /**
     * @param useDt the useDt to set
     */
    public void setUseDt(String useDt) {
        this.useDt = useDt;
    }
    
}
