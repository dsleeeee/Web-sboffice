package kr.co.common.service.cmm;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.*;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 *
 *
 * @author 정용길
 */
public interface CmmMenuService {

    /**
     * 소속된 매장 코드만 조회
     *
     * @param hqOfficeCd
     * @return
     */
    List<String> selectStoreCdList(String hqOfficeCd);

    /**
      * 레이어 팝업 매장 조회
      *
      * @param storeVO
      * @return
      */
    List<StoreVO> selectStore(StoreVO storeVO);

    /**
     * 레이어 팝업 본사 조회
     * @param hqVO
     * @return
     */
    List<HqVO> selectHq(HqVO hqVO);

    /**
     * 레이어 팝업 업체 조회
     * @param caVO
     * @return
     */
    List<CmAgencyVO> selectCmAgency(CmAgencyVO caVO);

    /**
     *
     * 메뉴 디비 작업 관련
     *
     */

    /**
     * 리소스 정보로 메뉴 사용 내역 저장
     *
     * @param resrceInfoVO 리소스 정보
     * @param sessionInfoVO
     * @return
     */
    int insertMenuUseHist(ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO);

    /**
     * 즐겨찾기 메뉴 조회
     *
     * @param sessionInfoVO
     * @return
     */
    List<ResrceInfoBaseVO> selectBkmkMenu(SessionInfoVO sessionInfoVO);

    /**
     * 고정 메뉴 조회
     *
     * @param sessionInfoVO
     * @return
     */
    List<ResrceInfoBaseVO> selectFixingMenu(SessionInfoVO sessionInfoVO);

    /**
     *
     * 즐겨 찾기 메뉴 관리
     *
     */

    /**
     * 메뉴 사용 내역 저장
     *
     * @param menuUseHistVO
     * @return
     */
    int insertMenuUseHist(MenuUseHistVO menuUseHistVO);

    /**
     * 세션에 메뉴 히스토리 추가
     *
     * @param resrceInfoBaseVO
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO addHistMenu(ResrceInfoBaseVO resrceInfoBaseVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션에 사용 메뉴 추가 : intercepter 에서 호출
     *
     * @param resrceInfoVO
     * @param sessionInfoVO
     * @return
     */
    void addHistMenu(ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션 고정메뉴, 히스토리 메뉴 의 활성화 여부 하나만 체크
     *
     * @param resrceCd 활성화 체크할 리소스 키값
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO checkActivation(String resrceCd, SessionInfoVO sessionInfoVO);

    /**
     * 히스토리 메뉴 내역을 세션에서 삭제한다.
     *
     * @param resrceCd
     * @param sessionInfoVO
     */
    void deleteHistMenu(String resrceCd, SessionInfoVO sessionInfoVO);

    /**
     * 고정 메뉴를 세션에서 삭제한다. 디비에서 삭제 하는건 아님
     *
     * @param resrceCd
     * @param sessionInfoVO
     */
    void deleteFixMenu(String resrceCd, SessionInfoVO sessionInfoVO);

    /**
     *
     * 메인 메뉴 관련
     *
     * @param sessionInfo
     * @param menuType 메뉴 타입 (A : 전체메뉴, F : 즐겨찾기)
     */
    List<MenuVO> makeMenu(SessionInfoVO sessionInfo, String menuType);


    /**
     * 트리 데이터 생성
     * @param list
     * @param authedList
     * @return
     */
    List<MenuVO> makeTreeData(List<DefaultMap<String>> list, List<DefaultMap<String>> authedList);

}


