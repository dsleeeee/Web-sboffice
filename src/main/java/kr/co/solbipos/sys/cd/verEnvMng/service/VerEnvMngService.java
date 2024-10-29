package kr.co.solbipos.sys.cd.verEnvMng.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VerEnvMngService.java
 * @Description : 시스템관리 > 코드관리 > 버전별환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.10.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VerEnvMngService {

    /** 버전 리스트 조회 */
    List<DefaultMap<String>> getVerList(VerEnvMngVO verEnvMngVO);

    /** 버전 등록 */
    int saveVer(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO);

    /** 대표명칭 리스트 조회 */
    List<DefaultMap<String>> getEnvstList(VerEnvMngVO verEnvMngVO);

    /** 대표명칭 사용여부 저장 */
    int saveEnvst(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO);

    /** 세부명칭 리스트 조회 */
    List<DefaultMap<String>> getEnvstDtlList(VerEnvMngVO verEnvMngVO);

    /** 세부명칭 초기값여부 저장 */
    int saveEnvstDtl(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO);

    /** 기능구분 리스트 조회 */
    List<DefaultMap<String>> getFuncFgList(VerEnvMngVO verEnvMngVO);

    /** 기능 리스트 조회 */
    List<DefaultMap<String>> getFuncList(VerEnvMngVO verEnvMngVO);

    /** 기능 사용여부 저장 */
    int saveFunc(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO);
}
