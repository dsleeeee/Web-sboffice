package kr.co.solbipos.sale.benson.prodSaleDayBillBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : ProdSaleDayBillBensonService.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별(영수)
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
public interface ProdSaleDayBillBensonService {

    /** 상품매출일별(영수) - 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillBensonList(ProdSaleDayBillBensonVO prodSaleDayBillBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출일별(영수) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillBensonExcelList(ProdSaleDayBillBensonVO prodSaleDayBillBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출일별(영수) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillBensonExcelDivisionList(ProdSaleDayBillBensonVO prodSaleDayBillBensonVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(ProdSaleDayBillBensonVO prodSaleDayBillBensonVO);
}
