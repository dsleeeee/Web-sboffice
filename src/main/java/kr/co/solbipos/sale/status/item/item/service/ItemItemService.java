package kr.co.solbipos.sale.status.item.item.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;

import java.util.List;

/**
 * @Class Name : ItemItemService.java
 * @Description : 매출관리 > 매출현황 > 매출항목표시
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.26  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020.03.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface ItemItemService
{
    /** 매장 사원 목록 조회 */
    List<DefaultMap<String>> getItemList(ItemItemVO storeEmpVO, SessionInfoVO sessionInfoVO);

    /** 매장 사원정보 수정*/
    EmpResult saveItemInfo(ItemItemVO[] ItemItemVOs, SessionInfoVO sessionInfoVO);

}
