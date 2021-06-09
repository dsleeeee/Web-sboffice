package kr.co.solbipos.base.store.dlvrAddr.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;

/**
 * @Class Name : DlvrAddrVO.java
 * @Description : 기초관리 > 매장관리 > 배달권역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DlvrAddrVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 조회매장 */
    private String storeCd;

    private String addrNm;

    private String myAreaCd;

    private String sidoNm;

    private String sigunguNm;

    private String lawDongNm;

    private String lawDongCd;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getAddrNm() {
        return addrNm;
    }

    public void setAddrNm(String addrNm) {
        this.addrNm = addrNm;
    }

    public String getMyAreaCd() {
        return myAreaCd;
    }

    public void setMyAreaCd(String myAreaCd) {
        this.myAreaCd = myAreaCd;
    }

    public String getSidoNm() {
        return sidoNm;
    }

    public void setSidoNm(String sidoNm) {
        this.sidoNm = sidoNm;
    }

    public String getSigunguNm() {
        return sigunguNm;
    }

    public void setSigunguNm(String sigunguNm) {
        this.sigunguNm = sigunguNm;
    }

    public String getLawDongNm() {
        return lawDongNm;
    }

    public void setLawDongNm(String lawDongNm) {
        this.lawDongNm = lawDongNm;
    }

    public String getLawDongCd() {
        return lawDongCd;
    }

    public void setLawDongCd(String lawDongCd) {
        this.lawDongCd = lawDongCd;
    }
}