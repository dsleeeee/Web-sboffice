package kr.co.common.data.domain;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : AgencyVO.java
 * @Description : 대리점 조회 공통 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AgencyVO extends CmmVO {

    private static final long serialVersionUID = 1743145092912592093L;

    /** 대리점코드(검색용) */
    private String srchAgencyCd;
    /** 대리점명(검색용) */
    private String srchAgencyNm;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 업체코드 */
    private String agencyCd;

    /**
     * 본사코드만 가져올지, 본사코드 + 단독매장 코드 까지 가져올지 파악하기 위해
     * H : 본사코드만
     * A : 본사코드 + 단독매장
     */
    private String srchType;

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
    }

    public String getSrchAgencyNm() {
        return srchAgencyNm;
    }

    public void setSrchAgencyNm(String srchAgencyNm) {
        this.srchAgencyNm = srchAgencyNm;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getSrchType() {
        return srchType;
    }

    public void setSrchType(String srchType) {
        this.srchType = srchType;
    }
}
