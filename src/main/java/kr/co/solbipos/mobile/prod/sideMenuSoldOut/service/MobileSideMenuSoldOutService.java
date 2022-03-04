package kr.co.solbipos.mobile.prod.sideMenuSoldOut.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileSideMenuSoldOutService.java
 * @Description : 상품관리 > 품절관리(선택상품)
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
public interface MobileSideMenuSoldOutService {
    // 선택그룹 조회
    List<DefaultMap<String>> getMobileSideMenuGrpList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO);

    // 선택분류 조회
    List<DefaultMap<String>> getMobileSideMenuClassList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO);

    // 선택상품 조회
    List<DefaultMap<String>> getMobileSideMenuProdList(MobileSideMenuSoldOutVO mobileSideMenuSoldOutVO, SessionInfoVO sessionInfoVO);

    // 품절여부 저장
    int getMobileSideMenuSoldOutSave(MobileSideMenuSoldOutVO[] mobileSideMenuSoldOutVOs, SessionInfoVO sessionInfoVO);
}
