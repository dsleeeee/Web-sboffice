package kr.co.solbipos.store.hq.brand.service;

import java.util.List;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : HqClsVO.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqClsVO extends CmmVO {

    private static final long serialVersionUID = 2271712626432433350L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 상품분류명 */
    private String prodClassNm;
    /** 상위상품분류코드 */
    private String pProdClassCd;
    /** Child Items */
    private List<HqClsVO> items;
    
    
    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }
    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }
    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }
    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }
    /**
     * @return the prodClassNm
     */
    public String getProdClassNm() {
        return prodClassNm;
    }
    /**
     * @param prodClassNm the prodClassNm to set
     */
    public void setProdClassNm(String prodClassNm) {
        this.prodClassNm = prodClassNm;
    }
    /**
     * @return the pProdClassCd
     */
    public String getpProdClassCd() {
        return pProdClassCd;
    }
    /**
     * @param pProdClassCd the pProdClassCd to set
     */
    public void setpProdClassCd(String pProdClassCd) {
        this.pProdClassCd = pProdClassCd;
    }
    /**
     * @return the items
     */
    public List<HqClsVO> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<HqClsVO> items) {
        this.items = items;
    }
    
}
