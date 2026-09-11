package kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : StoreSoldOutHistoryBensonService.java
 * @Description : 벤슨 > 매장분석 > 매장품절현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreSoldOutHistoryBensonService {

    /** 매장품절현황 - 조회 */
    List<DefaultMap<Object>> getSearchSoldOutHistory(StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장품절현황 - 엑셀조회 */
    List<DefaultMap<Object>> getStoreSoldOutHistoryBensonExcelList(StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO, SessionInfoVO sessionInfoVO);
}
