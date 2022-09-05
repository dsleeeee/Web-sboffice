package kr.co.solbipos.mobile.prod.kioskKeyMap.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileKioskKeyMapVO.java
 * @Description : 상품관리 > 키오스크 키맵
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.22  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileKioskKeyMapVO extends PageVO {

    private static final long serialVersionUID = 2507060678407090632L;

    /** 브랜드명 */
    private String hqBrandNm;
    /** 매장코드 */
    private String storeCd;
    /** 포스번호 */
    private String posNo;
    /** 순서 */
    private String indexNo;
    /** 카테고리 그룹 */
    private String tuClsType;
    /** 카테고리 코드 */
    private String tuClsCd;
    /** 터치키 코드 */
    private String tuKeyCd;
    /** 상품 코드 */
    private String prodCd;

    private String envstCd;
    private String tuMClsCd;

    public String getHqBrandNm() {
        return hqBrandNm;
    }

    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
    }
    
    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getTuClsType() {
        return tuClsType;
    }

    public void setTuClsType(String tuClsType) {
        this.tuClsType = tuClsType;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getTuKeyCd() {
        return tuKeyCd;
    }

    public void setTuKeyCd(String tuKeyCd) {
        this.tuKeyCd = tuKeyCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getTuMClsCd() {
        return tuMClsCd;
    }

    public void setTuMClsCd(String tuMClsCd) {
        this.tuMClsCd = tuMClsCd;
    }
}