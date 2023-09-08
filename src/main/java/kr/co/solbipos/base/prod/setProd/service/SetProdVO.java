package kr.co.solbipos.base.prod.setProd.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SetProdVO.java
 * @Description : 기초관리 > 상품관리 > 세트메뉴구성(BBQ전용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.05  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.09.05
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SetProdVO extends CmmVO {

    private static final long serialVersionUID = 1793262388267633994L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 사이드상품여부 */
    private String sideProdYn;
    /** 사이드그룹코드 */
    private String sdselGrpCd;
    /** 선택그룹사용정보 */
    private String sdselTypeFg;
    /** 사용여부 */
    private String useYn;
    /** 구분 */
    private String gubun;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getSideProdYn() {
        return sideProdYn;
    }

    public void setSideProdYn(String sideProdYn) {
        this.sideProdYn = sideProdYn;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselTypeFg() {
        return sdselTypeFg;
    }

    public void setSdselTypeFg(String sdselTypeFg) {
        this.sdselTypeFg = sdselTypeFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }
}