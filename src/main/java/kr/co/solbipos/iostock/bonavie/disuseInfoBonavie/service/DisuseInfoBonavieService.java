package kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DisuseInfoBonavieService.java
 * @Description : 수불관리 > 보나비 > 폐기내역조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DisuseInfoBonavieService {

    /** 폐기내역조회 - 조회 */
    List<DefaultMap<Object>> getDisuseInfoBonavieList(DisuseInfoBonavieVO disuseInfoBonavieVO, SessionInfoVO sessionInfoVO);

    /** 폐기내역조회 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDisuseInfoBonavieExcelList(DisuseInfoBonavieVO disuseInfoBonavieVO, SessionInfoVO sessionInfoVO);
}