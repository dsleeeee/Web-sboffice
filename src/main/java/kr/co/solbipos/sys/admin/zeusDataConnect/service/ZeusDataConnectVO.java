package kr.co.solbipos.sys.admin.zeusDataConnect.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ZeusDataConnectVO.java
 * @Description : 시스템관리 > 관리자기능 > 제우스데이터연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.03.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.03.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ZeusDataConnectVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 프로시져 실행 결과 */
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}