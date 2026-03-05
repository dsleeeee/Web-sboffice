package kr.co.solbipos.sale.store.storeSoldOutHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : StoreSoldOutHistoryService.java
 * @Description : 맘스터치 > 매장관리 > 매장품절현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StoreSoldOutHistoryService {

    /** 매장품절현황 - 조회 */
    List<DefaultMap<Object>> getSearchSoldOutHistory(StoreSoldOutHistoryVO storeSoldOutHistoryVO, SessionInfoVO sessionInfoVO);

    /** 매장품절현황 - 엑셀조회 */
    List<DefaultMap<Object>> getStoreSoldOutHistoryExcelList(StoreSoldOutHistoryVO storeSoldOutHistoryVO, SessionInfoVO sessionInfoVO);
}
