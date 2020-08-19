package kr.co.solbipos.base.store.empCorner.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : EmpCornerVO.java
 * @Description : 기초관리 > 매장관리 > 사원별코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EmpCornerVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 조회매장 */
    private String storeCd;

    /** 사원번호 */
    private String empNo;

    /** 코너코드 */
    private String cornrCd;

    /** 사용자ID */
    private String userId;

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getEmpNo() { return empNo; }

    public void setEmpNo(String empNo) { this.empNo = empNo; }

    public String getCornrCd() { return cornrCd; }

    public void setCornrCd(String cornrCd) { this.cornrCd = cornrCd; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}