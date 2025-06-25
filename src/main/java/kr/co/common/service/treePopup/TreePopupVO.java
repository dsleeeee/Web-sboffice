package kr.co.common.service.treePopup;

import kr.co.solbipos.application.common.service.PageVO;

import java.util.List;
/**
 * @Class Name  : TreePopupVO.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class TreePopupVO extends PageVO {
    private static final long serialVersionUID = 4938208121431735495L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** */
    private String pageFg;

    /** 소속구분 */
    private String orgnFg;

    /** 상품분류코드 */
    private String prodClassCd;
    private String pProdClassCd;
    private String prodClassNm;

    private List<TreePopupVO> items;

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

    public String getPageFg() {
        return pageFg;
    }

    public void setPageFg(String pageFg) {
        this.pageFg = pageFg;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getpProdClassCd() {
        return pProdClassCd;
    }

    public void setpProdClassCd(String pProdClassCd) {
        this.pProdClassCd = pProdClassCd;
    }

    public String getProdClassNm() {
        return prodClassNm;
    }

    public void setProdClassNm(String prodClassNm) {
        this.prodClassNm = prodClassNm;
    }

    public List<TreePopupVO> getItems() {
        return items;
    }

    public void setItems(List<TreePopupVO> items) {
        this.items = items;
    }
}
