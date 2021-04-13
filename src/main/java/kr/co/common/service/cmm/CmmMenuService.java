package kr.co.common.service.cmm;

import kr.co.solbipos.application.common.service.*;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CmmMenuService.java
 * @Description : 공통메뉴관련
 * @Modification Information
 * @
 * @ 수정일       수정자      수정내용
 * @ ----------  ---------  -------------------------------
 * @ 2018.10.23  노현수      주석추가, 로직정리...
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CmmMenuService {

    /** 본사에 속한 가맹점 코드 목록을 조회 */
    List<String> getStoreCdList(String hqOfficeCd);

    /** 레이어 팝업 매장 조회 */
    List<StoreVO> getStoreInfo(StoreVO storeVO);

    /** 레이어 팝업 본사 조회 */
    List<HqVO> getHqInfo(HqVO hqVO);

    /** 레이어 팝업 업체 조회 */
    List<CmAgencyVO> getCmAgencyInfo(CmAgencyVO cmAgencyVO);

    /** 리소스 정보로 메뉴 사용 내역 저장 */
    int insertMenuUseHist(ResrceInfoBaseVO resrceInfoBaseVO, SessionInfoVO sessionInfoVO);

    /** 메뉴목록 조회 : 사용자의 권한있는 메뉴 목록 */
    List<ResrceInfoBaseVO> getUserMenuList(SessionInfoVO sessionInfoVO);

    /** 즐겨찾기 메뉴 조회 */
    List<ResrceInfoBaseVO> getBkmkMenuList(SessionInfoVO sessionInfoVO);

    /** 고정 메뉴 조회 */
    List<ResrceInfoBaseVO> getFixedMenuList(SessionInfoVO sessionInfoVO);

    /** 메뉴 사용 내역 저장 */
    int insertMenuUseHist(MenuUseHistVO menuUseHistVO);

    /** 세션에 메뉴 히스토리 추가 */
    SessionInfoVO addHistMenu(ResrceInfoBaseVO resrceInfoBaseVO, SessionInfoVO sessionInfoVO);

    /** 세션에 사용 메뉴 추가 : interceptor 에서 호출 */
    void addHistMenu(ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO);

    /** 세션 고정메뉴, 히스토리 메뉴 의 활성화 여부 하나만 체크 */
    SessionInfoVO checkActivation(String resrceCd, SessionInfoVO sessionInfoVO);

    /** 히스토리 메뉴 내역을 세션에서 삭제 */
    void deleteHistMenu(String resrceCd, SessionInfoVO sessionInfoVO);

    /** 고정 메뉴를 세션에서 삭제. 디비에서 삭제 하는건 아님 */
    void deleteFixMenu(String resrceCd, SessionInfoVO sessionInfoVO);

    /** 유효 메뉴 여부 확인 */
    int menuResrceChk(ResrceInfoVO resrceInfoVO);

}


