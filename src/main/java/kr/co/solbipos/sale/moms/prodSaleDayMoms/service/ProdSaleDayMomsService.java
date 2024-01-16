package kr.co.solbipos.sale.moms.prodSaleDayMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdSaleDayMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSaleDayMomsService {

    /** 상품매출일별 - 조회 */
    List<DefaultMap<Object>> getProdSaleDayMomsList(ProdSaleDayMomsVO prodSaleDayMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품매출일별 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getProdSaleDayMomsExcelList(ProdSaleDayMomsVO prodSaleDayMomsVO, SessionInfoVO sessionInfoVO);
}