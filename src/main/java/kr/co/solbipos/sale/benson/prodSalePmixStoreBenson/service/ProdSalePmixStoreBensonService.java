package kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : ProdSalePmixStoreBensonService.java
 * @Description : 벤슨 > 간소화화면 > 상품매출(P.MIX 매장)
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
public interface ProdSalePmixStoreBensonService {

    /** 상품매출(P.MIX 매장) - 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출(P.MIX 매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonExcelList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품매출(P.MIX 매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreBensonExcelDivisionList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO);
}
