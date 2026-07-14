package kr.co.solbipos.sale.store.storeMonthChannelBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreMonthChannelBensonService.java
 * @Description : 벤슨 > 매장분석 > 매장-월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StoreMonthChannelBensonService {

    /** 매장-월별매출현황(채널별) 리스트 조회 */
    List<DefaultMap<String>> getStoreMonthChannelBensonList(StoreMonthChannelBensonVO storeMonthChannelBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장-월별매출현황(채널별) 엑셀 리스트 조회 */
    List<DefaultMap<String>> getStoreMonthChannelBensonExcelList(StoreMonthChannelBensonVO storeMonthChannelBensonVO, SessionInfoVO sessionInfoVO);

}
