package kr.co.solbipos.store.manage.virtualLoginAgency.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : VirtualLoginAgencyVO.java
 * @Description : 기초관리 > 총판/대리점 가상로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.31  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.31
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VirtualLoginAgencyVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 대리점코드 */
    private String agencyCd;

    /** 대리점명 */
    private String agencyNm;

    /** 사용자아이디 */
    private String userId;

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}