package kr.co.solbipos.store.hq.brandTerminal.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreTerminalVO.java
 * @Description : 기초관리 > 본사정보관리 > 브랜드별 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BrandTerminalVO extends CmmVO {

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

    /** 본사명 */
    private String hqOfficeNm;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 브랜드명 */
    private String hqBrandNm;

    /** 사용여부 */
    private String useYn;

    /** [벤더구분] */
    private String vendorFg;
    /** [벤더구분명] */
    private String vendorFgNm;
    /** [벤더코드] */
    private String vendorCd;
    /** [벤더명] */
    private String vendorNm;
    /** [벤더 터미널번호] */
    private String vendorTermnlNo;
    /** [벤더시리얼번호] */
    private String vendorSerNo;

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

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getHqBrandNm() {
        return hqBrandNm;
    }

    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getVendorFg() {
        return vendorFg;
    }

    public void setVendorFg(String vendorFg) {
        this.vendorFg = vendorFg;
    }

    public String getVendorFgNm() {
        return vendorFgNm;
    }

    public void setVendorFgNm(String vendorFgNm) {
        this.vendorFgNm = vendorFgNm;
    }

    public String getVendorCd() {
        return vendorCd;
    }

    public void setVendorCd(String vendorCd) {
        this.vendorCd = vendorCd;
    }

    public String getVendorNm() {
        return vendorNm;
    }

    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    public String getVendorTermnlNo() {
        return vendorTermnlNo;
    }

    public void setVendorTermnlNo(String vendorTermnlNo) {
        this.vendorTermnlNo = vendorTermnlNo;
    }

    public String getVendorSerNo() {
        return vendorSerNo;
    }

    public void setVendorSerNo(String vendorSerNo) {
        this.vendorSerNo = vendorSerNo;
    }
}
