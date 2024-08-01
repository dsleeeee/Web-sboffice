package kr.co.solbipos.mobile.stock.status.dailyIoStock.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : MobileDailyIoStockVO.java
 * @Description : (모바일)재고현황 > 일자별수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileDailyIoStockVO extends PageVO {

    private static final long serialVersionUID = -8897037068098223217L;

    /** 본사/가맹정 구분 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 컬럼 헤더 구분 */
    private String ioOccrFg;
    /** 전표변호 */
    private String slipFg;
    /** 일자 */
    private String saleDate;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }
    public String getStorageCd() {
        return storageCd;
    }
    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
    public String getStoreCd() {
        return storeCd;
    }
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    public String getOrgnFg() {
        return orgnFg;
    }
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
    public String getIoOccrFg() {
        return ioOccrFg;
    }
    public void setIoOccrFg(String ioOccrFg) {
        this.ioOccrFg = ioOccrFg;
    }
    public String getSlipFg() {
        return slipFg;
    }
    public void setSlipFg(String slipFg) {
        this.slipFg = slipFg;
    }
    public String getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
