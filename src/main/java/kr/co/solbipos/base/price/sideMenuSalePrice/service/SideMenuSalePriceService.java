package kr.co.solbipos.base.price.sideMenuSalePrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SideMenuSalePriceService.java
 * @Description : 기초관리 - 가격관리 - 매장별구성상품가격변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SideMenuSalePriceService {

    /** 선택메뉴 리스트 조회 */
    List<DefaultMap<String>> getSideMenuSalePriceList(SideMenuSalePriceVO sideMenuSalePriceVO, SessionInfoVO sessionInfoVO);

    /** 선택메뉴 가격 변경 */
    int saveSideMenuSalePrice(SideMenuSalePriceVO[] sideMenuSalePriceVOs, SessionInfoVO sessionInfoVO);
}
