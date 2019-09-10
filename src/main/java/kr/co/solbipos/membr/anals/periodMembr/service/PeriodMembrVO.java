package kr.co.solbipos.membr.anals.periodMembr.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PeriodMembrVO.java
 * @Description : 회원관리 > 회원분석 > 기간회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PeriodMembrVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 조회매장 */
    private String storeCd;

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
}
