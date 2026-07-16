package kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdSaleMonthStoreBensonService.java
 * @Description : 벤슨 > 간소화화면 > 상품매출월별(매장)
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
public interface ProdSaleMonthStoreBensonService {

    /** 상품매출월별(매장) - 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleMonthStoreBensonExcelDivisionList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크 */
    int getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 isnert */
    String getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크 */
    String getDivisionExcelDownloadCntChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update */
    int getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, SessionInfoVO sessionInfoVO);
}
