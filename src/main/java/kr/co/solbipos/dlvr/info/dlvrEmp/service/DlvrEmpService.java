package kr.co.solbipos.dlvr.info.dlvrEmp.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrEmpService.java
 * @Description : 배달관리 > 배달정보 > 배달사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.10.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface DlvrEmpService {

    /** 배달사원정보 목록 조회 */
    List<DefaultMap<String>> getDlvrEmpList(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO);

    /** 배달사원정보 상세 조회 */
    DefaultMap<String> getDlvrEmpDtl(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO);

    /** 배달사원 신규등록 */
    int insertDlvrEmp(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO);

    /** 배달사원 정보수정 */
    int updateDlvrEmp(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO);
}
