package kr.co.solbipos.sale.store.storeDayChannelBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreDayChannelBensonService.java
 * @Description : 벤슨 > 매장분석 > 매장-일별매출현황(채널별)
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
public interface StoreDayChannelBensonService {

    /** 매장-일별매출현황(채널별) 리스트 조회 */
    List<DefaultMap<String>> getStoreDayChannelBensonList(StoreDayChannelBensonVO storeDayChannelBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장-일별매출현황(채널별) 엑셀 다운로드 조회 */
    List<DefaultMap<String>> getStoreDayChannelBensonExcelList(StoreDayChannelBensonVO storeDayChannelBensonVO, SessionInfoVO sessionInfoVO);
}
