package kr.co.solbipos.sys.bill.kind.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : KindVO.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class KindVO extends CmmVO {

    private static final long serialVersionUID = -8264590468114276658L;
    /** 출력물분류코드 */
    private String prtClassCd;
    /** 출력물분류명 */
    private String prtClassNm;
    /** 일반사용여부 */
    private Boolean generalYn;
    /** 외식사용여부 */
    private Boolean foodYn;
    /** 출력물코드 */
    private String prtCd;
    /** 표기순번 */
    private String dispSeq;
    /** 출력물명 */
    private String prtNm;
    /** 예제사용여부 */
    private Boolean samplYn;
    /** 예제 */
    private String content;

    /** 출력물분류코드 array */
    private String arrPrtClassCd[];

    /**
     * @return the prtClassCd
     */
    public String getPrtClassCd() {
        return prtClassCd;
    }
    /**
     * @param prtClassCd the prtClassCd to set
     */
    public void setPrtClassCd(String prtClassCd) {
        this.prtClassCd = prtClassCd;
    }
    /**
     * @return the prtClassNm
     */
    public String getPrtClassNm() {
        return prtClassNm;
    }
    /**
     * @param prtClassNm the prtClassNm to set
     */
    public void setPrtClassNm(String prtClassNm) {
        this.prtClassNm = prtClassNm;
    }
    /**
     * @return the generalYn
     */
    public Boolean getGeneralYn() {
        return generalYn;
    }
    /**
     * @param generalYn the generalYn to set
     */
    public void setGeneralYn(Boolean generalYn) {
        this.generalYn = generalYn;
    }
    /**
     * @return the foodYn
     */
    public Boolean getFoodYn() {
        return foodYn;
    }
    /**
     * @param foodYn the food to set
     */
    public void setFoodYn(Boolean foodYn) {
        this.foodYn = foodYn;
    }
    /**
     * @return the prtCd
     */
    public String getPrtCd() {
        return prtCd;
    }
    /**
     * @param prtCd the prtCd to set
     */
    public void setPrtCd(String prtCd) {
        this.prtCd = prtCd;
    }
    /**
     * @return the dispSeq
     */
    public String getDispSeq() {
        return dispSeq;
    }
    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }
    /**
     * @return the prtNm
     */
    public String getPrtNm() {
        return prtNm;
    }
    /**
     * @param prtNm the prtNm to set
     */
    public void setPrtNm(String prtNm) {
        this.prtNm = prtNm;
    }

    /**
     * @return the samplYn
     */
    public Boolean getSamplYn() {
        return samplYn;
    }

    /**
     * @param samplYn the samplYn to set
     */
    public void setSamplYn(Boolean samplYn) {
        this.samplYn = samplYn;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String[] getArrPrtClassCd() {
        return arrPrtClassCd;
    }

    public void setArrPrtClassCd(String[] arrPrtClassCd) { this.arrPrtClassCd = arrPrtClassCd; }
}
