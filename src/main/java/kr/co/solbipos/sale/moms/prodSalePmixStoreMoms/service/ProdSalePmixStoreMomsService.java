package kr.co.solbipos.sale.moms.prodSalePmixStoreMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : ProdSalePmixStoreMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출(P.MIX 매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.21  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSalePmixStoreMomsService {

    /** 상품매출(P.MIX 매장) - 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreMomsList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출(P.MIX 매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreMomsExcelList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출(P.MIX 매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixStoreMomsExcelDivisionList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO);
}