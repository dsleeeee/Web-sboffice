package kr.co.solbipos.mobile.prod.kioskKeyMap.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutVO;

import java.util.List;

/**
 * @Class Name : MobileProdSoldOutService.java
 * @Description : 상품관리 > 품절관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileKioskKeyMapService {
    // 매장 키맵 조회
    List<DefaultMap<String>> getMobileKioskKeyMapStoreList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO);
    // 포장 키맵 조회
    List<DefaultMap<String>> getMobileKioskKeyMapPackList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO);
    // 키맵 저장
    int getMobileKioskKeyMapGrpSave(MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, SessionInfoVO sessionInfoVO);
    // 키맵 상품 조회
    List<DefaultMap<String>> getMobileKioskKeyMapProdList(MobileKioskKeyMapVO mobileKioskKeyMapVO, SessionInfoVO sessionInfoVO);
    // 키맵 상품 저장
    int getMobileKioskKeyMapProdSave(MobileKioskKeyMapVO[] mobileKioskKeyMapVOs, SessionInfoVO sessionInfoVO);
}
