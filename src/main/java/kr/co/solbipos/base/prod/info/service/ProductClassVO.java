package kr.co.solbipos.base.prod.info.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.hq.brand.service.HqClsVO;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * @Class Name : HqProductClassVO.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProductClassVO extends CmmVO {

    /** 소속구분 */
    private OrgnFg orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 상품 분류명 */
    private String prodClassNm;
    /** 상위상품 분류코드 */
    private String pProdClassCd;
    /** 분류레벨 */
    private int level;
    /** Child Items */
    private List<ProductClassVO> items;

    /**
     * @return the orgnFg
     */
    public OrgnFg getOrgnFg() { return orgnFg; }
    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) { this.orgnFg = orgnFg; }
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
     * @return the storeCd
     */
    public String getStoreCd() { return storeCd; }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }
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
     * @return the level
     */
    public int getLevel() {
        return level;
    }
    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * @return the items
     */
    public List<ProductClassVO> getItems() { return items; }
    /**
     * @param items the items to set
     */
    public void setItems(List<ProductClassVO> items) { this.items = items; }
}
