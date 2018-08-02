package kr.co.solbipos.sys.etc.vancard.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : VanCardVO.java
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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VanCardVO extends CmmVO {
    
    private static final long serialVersionUID = -8172035887190498958L;
    /** 밴사코드 */
    private String vanCd;
    /** 밴사카드사코드 */
    private String vanCardcoCd;
    /** 밴사카드사명 */
    private String vanCardcoNm;
    /** 카드사코드 */
    private String cardcoCd;
    
    
    /**
     * @return the vanCd
     */
    public String getVanCd() {
        return vanCd;
    }
    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }
    /**
     * @return the vanCardcoCd
     */
    public String getVanCardcoCd() {
        return vanCardcoCd;
    }
    /**
     * @param vanCardcoCd the vanCardcoCd to set
     */
    public void setVanCardcoCd(String vanCardcoCd) {
        this.vanCardcoCd = vanCardcoCd;
    }
    /**
     * @return the vanCardcoNm
     */
    public String getVanCardcoNm() {
        return vanCardcoNm;
    }
    /**
     * @param vanCardcoNm the vanCardcoNm to set
     */
    public void setVanCardcoNm(String vanCardcoNm) {
        this.vanCardcoNm = vanCardcoNm;
    }
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
    
}
