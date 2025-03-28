package kr.co.solbipos.sys.admin.funcKey.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : FuncKeyService.java
 * @Description : 시스템관리 > 관리자기능 > 기능키적용버전등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.09  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.09
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface FuncKeyService {

    /** 기능구분 그룹코드 조회(콤보박스용) */
    List<DefaultMap<String>> getNmcodeGrpCdList();

    /** 기능키적용버전 리스트 조회 */
    List<DefaultMap<String>> getFuncKeyList(FuncKeyVO funcKeyVO, SessionInfoVO sessionInfoVO);

    /** 기능키적용버전 저장*/
    int saveFuncKey(FuncKeyVO[] funcKeyVOs, SessionInfoVO sessionInfoVO);

    /** 기능키적용버전 삭제 */
    int deleteFuncKey(FuncKeyVO[] funcKeyVOs, SessionInfoVO sessionInfoVO);
}
