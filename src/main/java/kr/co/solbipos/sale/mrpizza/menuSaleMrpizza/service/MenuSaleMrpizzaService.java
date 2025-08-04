package kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaVO;

import java.util.List;

/**
 * @Class Name : MenuSaleMrpizzaService.java
 * @Description : 미스터피자 > 마케팅조회 > 메뉴별판매
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface MenuSaleMrpizzaService {

    /** 메뉴별판매 리스트 조회 */
    List<DefaultMap<Object>> getMenuSaleMrpizzaList(MenuSaleMrpizzaVO menuSaleMrpizzaVO, SessionInfoVO sessionInfoVO);

}
