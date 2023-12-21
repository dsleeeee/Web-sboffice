package kr.co.solbipos.sale.moms.prodSalePmixMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : ProdSalePmixMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출(P.MIX)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSalePmixMomsService {

    /** 상품매출(P.MIX) - 조회 */
    List<DefaultMap<Object>> getProdSalePmixMomsList(ProdSalePmixMomsVO prodSalePmixMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출(P.MIX) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSalePmixMomsExcelList(ProdSalePmixMomsVO prodSalePmixMomsVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(ProdSalePmixMomsVO prodSalePmixMomsVO);
}