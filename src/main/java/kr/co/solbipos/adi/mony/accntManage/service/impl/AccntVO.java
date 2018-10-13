package kr.co.solbipos.adi.mony.accntManage.service.impl;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

/**
 * @Class Name : AccntVO.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AccntVO extends CmmVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 소속구분 */
    private OrgnFg orgnFg;
    /** 계정코드 */
    private String accntCd;
    /** 계정명 */
    private String accntNm;
    /** 계정구분 (1:입금, 2:출금) */
    private String accntFg;
    /** 사용여부 */
    private String useYn;

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

    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the orgnFg
     */

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the accntCd
     */

    public String getAccntCd() {
        return accntCd;
    }

    /**
     * @param accntCd the accntCd to set
     */
    public void setAccntCd(String accntCd) {
        this.accntCd = accntCd;
    }

    /**
     * @return the accntNm
     */

    public String getAccntNm() {
        return accntNm;
    }

    /**
     * @param accntNm the accntNm to set
     */
    public void setAccntNm(String accntNm) {
        this.accntNm = accntNm;
    }

    /**
     * @return the accntFg
     */

    public String getAccntFg() {
        return accntFg;
    }

    /**
     * @param accntFg the accntFg to set
     */
    public void setAccntFg(String accntFg) {
        this.accntFg = accntFg;
    }

    /**
     * @return the useYn
     */

    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
