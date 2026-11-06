package kr.co.solbipos.sys.admin2.saleBillDataChk.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : SaleBillDataChkService.java
 * @Description : 시스템관리 > 관리자기능2 > 매출자료검증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.11.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleBillDataChkService {

    /** 매출자료검증 - 영수증 1건 정합성 체크 */
    SaleBillDataChkVO getSaleBillDataChk(SaleBillDataChkVO saleBillDataChkVO, SessionInfoVO sessionInfoVO);
}
