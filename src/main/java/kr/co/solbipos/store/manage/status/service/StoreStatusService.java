package kr.co.solbipos.store.manage.status.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreStatusService.java
 * @Description : 기초관리 > 매장정보관리 > 매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreStatusService {

    /** 매장탭 - 매장정보조회*/
    List<DefaultMap<Object>> getStatusStoreList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** 매장탭 - 코너 상세조회*/
    List<DefaultMap<Object>> getStatusStoreCornerList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** 관리업체탭 - 관리업체 조회*/
    List<DefaultMap<Object>> getStatusAgencyList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** 관리업체탭 - 관리업체 상세조회*/
    List<DefaultMap<Object>> getStatusAgencyDetailList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** VAN사탭 - VAN사 조회*/
    List<DefaultMap<Object>> getStatusVanList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** VAN사탭 - VAN사 상세조회*/
    List<DefaultMap<Object>> getStatusVanDetailList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);

    /** POS설치현황탭 - POS설치현황 조회*/
    List<DefaultMap<Object>> getStatusPosInstallList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO);
}
