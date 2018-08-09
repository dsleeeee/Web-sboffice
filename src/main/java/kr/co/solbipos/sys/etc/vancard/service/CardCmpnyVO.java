package kr.co.solbipos.sys.etc.vancard.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : CardCmpnyVO.java
 * @Description : 시스템관리 > VAN/CARD사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CardCmpnyVO extends CmmVO {

    private static final long serialVersionUID = 741645320989300833L;
    /** 카드사코드 */
    private String cardcoCd;
    /** 카드사명 */
    private String cardcoNm;
    /** 사업자번호 */
    private String bizNo;
    /** 전화번호 */
    private String telNo;
    /** 팩스번호 */
    private String faxNo;
    /** 홈페이지 */
    private String hmpgAddr;
    
    
    /**
     * @return the cardcoCd
     */
    public String getCardcoCd() {
        return cardcoCd;
    }
    /**
     * @param cardcoCd the cardcoCd to set
     */
    public void setCardcoCd(String cardcoCd) {
        this.cardcoCd = cardcoCd;
    }
    /**
     * @return the cardcoNm
     */
    public String getCardcoNm() {
        return cardcoNm;
    }
    /**
     * @param cardcoNm the cardcoNm to set
     */
    public void setCardcoNm(String cardcoNm) {
        this.cardcoNm = cardcoNm;
    }
    /**
     * @return the bizNo
     */
    public String getBizNo() {
        return bizNo;
    }
    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }
    /**
     * @return the hmpgAddr
     */
    public String getHmpgAddr() {
        return hmpgAddr;
    }
    /**
     * @param hmpgAddr the hmpgAddr to set
     */
    public void setHmpgAddr(String hmpgAddr) {
        this.hmpgAddr = hmpgAddr;
    }
    
}
