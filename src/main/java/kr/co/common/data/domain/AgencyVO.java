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

    /** 대리점코드 */
    private String agencyCd;
    /** 대리점명 */
    private String agencyNm;

    /**
     * @return the agencyCd
     */

    public String getAgencyCd() {
        return agencyCd;
    }

    /**
     * @param agencyCd the agencyCd to set
     */
    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    /**
     * @return the agencyNm
     */

    public String getAgencyNm() {
        return agencyNm;
    }

    /**
     * @param agencyNm the agencyNm to set
     */
    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }
}
