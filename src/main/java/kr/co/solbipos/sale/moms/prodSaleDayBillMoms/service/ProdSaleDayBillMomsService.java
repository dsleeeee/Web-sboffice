package kr.co.solbipos.sale.moms.prodSaleDayBillMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : ProdSaleDayBillMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별(영수)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSaleDayBillMomsService {

    /** 상품매출일별(영수) - 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillMomsList(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출일별(영수) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillMomsExcelList(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출일별(영수) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayBillMomsExcelDivisionList(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO);
}