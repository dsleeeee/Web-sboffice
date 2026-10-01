package kr.co.solbipos.sys.stats.userWebHist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : UserWebHistService.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 * @ 2026.09.22  김유승      조회정보(조회건수) 팝업 조회 추가
 * @ 2026.09.23  김유승      ③ 메뉴(URL)별 건수 조회 추가
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface UserWebHistService {

    /** 사용자웹사용이력 조회 */
    List<DefaultMap<Object>> getUserWebHistList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** 사용자 아이디 일시정지 */
    int getPauseUserId(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** 사용자 아이디 일시정지 해제 */
    int getResumeUserId(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] 조회건수 USER_ID 종합 조회 */
    List<DefaultMap<Object>> getUserWebChkCntList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] 조회건수 USER_ID + IP별 상세 조회 */
    List<DefaultMap<Object>> getUserWebChkCntIpList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] ③ 메뉴(URL)별 건수 조회 */
    List<DefaultMap<Object>> getUserWebChkCntMenuList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] ② IP별 상세 0건 시 원천 데이터 진단 */
    DefaultMap<Object> getUserWebChkCntIpDiag(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] 일시정지 + 이력 등록 (1: 반영, 0: 미반영-이미 정지 등) */
    int getPauseUserIdWithHist(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] 일시정지 이력 조회 */
    List<DefaultMap<Object>> getUserPauseHistList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] 일시정지 해제 + 이력 등록 (1: 반영, 0: 미반영-정지상태 아님, -2: USER_ID 없음) */
    int getResumeUserIdWithHist(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);

    /** [조회정보 팝업] ⑤ 웹사용자정보변경이력 조회 */
    List<DefaultMap<Object>> getUserInfoLogList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO);
}