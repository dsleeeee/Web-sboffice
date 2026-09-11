package kr.co.solbipos.sys.link.naverPlaceEasyLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : NaverPlaceEasyLinkService.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스 간편연동 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverPlaceEasyLinkService {

    /** 사용자현황 조회 */
    List<DefaultMap<Object>> getNaverPlaceUserList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO);

    /** 간편연동 업로드 검증 및 임시데이터 저장 */
    int saveNaverPlaceEasyLinkExcelCheck(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO);

    /** 간편연동 업로드 검증결과 조회 */
    List<DefaultMap<Object>> getNaverPlaceEasyLinkExcelList(NaverPlaceEasyLinkVO naverPlaceEasyLinkVO, SessionInfoVO sessionInfoVO);

    /** 간편연동 업로드 임시데이터 전체 삭제(세션 기준) */
    int deleteNaverPlaceEasyLinkExcelAll(SessionInfoVO sessionInfoVO);

    /** 간편연동 업로드 임시데이터 개별 삭제 */
    int deleteNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO);

    /** 간편연동 업로드 검증성공 데이터 저장 */
    int saveNaverPlaceEasyLinkExcel(NaverPlaceEasyLinkVO[] naverPlaceEasyLinkVOs, SessionInfoVO sessionInfoVO);
}
