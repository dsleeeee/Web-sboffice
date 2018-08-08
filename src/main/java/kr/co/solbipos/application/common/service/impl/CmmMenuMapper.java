package kr.co.solbipos.application.common.service.impl;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.HqVO;
import kr.co.solbipos.application.common.service.MenuUseHistVO;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 정용길
 *
 */
@Mapper
@Repository
public interface CmmMenuMapper {

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
      * 메뉴 사용 내역 저장
      *
      * @param menuUseHistVO
      * @return
      */
    int insertMenuUseHist(MenuUseHistVO menuUseHistVO);

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
     * 메뉴 조회
     *
     * @return List
     */
    List<DefaultMap<String>> selectAllResrce(DefaultMap<String> map);

    /**
     * 권한 있는 기능 조회
     *
     * @return List
     */
    List<DefaultMap<String>> selectAuthedResrce(DefaultMap<String> map);

    /**
     * 권한이 있는 즐겨찾기 조회
     * @param map
     * @return
     */
    List<DefaultMap<String>> selectAuthedBkmk(DefaultMap<String> map);



}
