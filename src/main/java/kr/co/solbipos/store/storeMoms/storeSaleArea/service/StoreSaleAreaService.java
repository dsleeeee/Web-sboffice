package kr.co.solbipos.store.storeMoms.storeSaleArea.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreSaleAreaService.java
 * @Description : 맘스터치 > 매장관리 > 점포영업지역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.11.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface StoreSaleAreaService {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);

    /** 지사 조회(콤보박스용) */
    List<DefaultMap<String>> getBranchCombo(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);

    /** 매장 조회(콤보박스용) */
    List<DefaultMap<String>> getStoreCombo(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);

    /** 매장 영업지역 조회*/
    DefaultMap<String> getStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);

    /** 매장 영업지역 저장 */
    int saveStoreSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);

    /** 서울, 경기 매장 영업지역 조회 */
    List<DefaultMap<String>> getMetropolitanSaleArea(StoreSaleAreaVO storeSaleAreaVO, SessionInfoVO sessionInfoVO);
}
