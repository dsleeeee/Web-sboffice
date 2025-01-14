package kr.co.solbipos.sale.status.dayProdSaleStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayProdSaleStoreService.java
 * @Description : 매출관리 > 매출현황2 > 일별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.01.09  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2025.01.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayProdSaleStoreService {

    /** 일별상품매출현황(매장별) - 조회 */
    List<DefaultMap<String>> getDayProdSaleStoreList(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황(매장별) - 엑셀다운로드 조회 */
    List<DefaultMap<String>> getDayProdSaleStoreExcelList(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황(매장별) - 상세 팝업 리스트 조회 */
    List<DefaultMap<String>> getDayProdSaleStoreDtl(DayProdSaleStoreVO dayProdSaleStoreVO, SessionInfoVO sessionInfoVO);

}
