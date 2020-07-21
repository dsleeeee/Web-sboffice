package kr.co.common.service.cmm.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.*;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CmmMenuMapper.java
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
@Mapper
@Repository
public interface CmmMenuMapper {

    /** 소속된 매장 코드만 조회 */
    List<String> getStoreCdList(String hqOfficeCd);

    /** 레이어 팝업 매장 조회 */
    List<StoreVO> getStoreInfo(StoreVO storeVO);

    /** 레이어 팝업 본사 조회 */
    List<HqVO> getHqInfo(HqVO hqVO);

    /** 레이어 팝업 업체 조회 */
    List<CmAgencyVO> getCmAgencyInfo(CmAgencyVO cmAgencyVO);
    
    /** 메뉴 사용 내역 저장 */
    int insertMenuUseHist(MenuUseHistVO menuUseHistVO);

    /** 메뉴목록 조회 : 사용자의 권한있는 메뉴 목록 */
    List<ResrceInfoBaseVO> getUserMenuList(SessionInfoVO sessionInfoVO);

    /** 즐겨찾기 메뉴 조회 */
    List<ResrceInfoBaseVO> getBkmkMenuList(SessionInfoVO sessionInfoVO);

    /** 고정 메뉴 조회 */
    List<ResrceInfoBaseVO> getFixedMenuList(SessionInfoVO sessionInfoVO);

    /** 권한 있는 메뉴 리스트 조회 */
    List<ResrceInfoVO> getAuthMenu(SessionInfoVO sessionInfoVO);

    /** 메뉴 조회 */
    List<DefaultMap<String>> getAllResrce(DefaultMap<String> map);

    /** 권한 있는 기능 조회 */
    List<DefaultMap<String>> getAuthedResrce(DefaultMap<String> map);

    /** 권한이 있는 즐겨찾기 조회 */
    List<DefaultMap<String>> getAuthedBkmk(DefaultMap<String> map);

    /** 유효 메뉴 여부 확인 */
    int menuResrceChk(String url);

}
