package kr.co.solbipos.sale.status.weight.weight.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : WeightService.java
 * @Description : 매출관리 > 매출현황2 > 중량별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.08  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface WeightService {
    /** 중량별 리스트 조회 */
    List<DefaultMap<String>> getWeightList(WeightVO weightVO, SessionInfoVO sessionInfoVO);

    /** 일자별 리스트 조회 */
    List<DefaultMap<String>> getWeightDayList(WeightVO weightVO, SessionInfoVO sessionInfoVO);

    /** 상품별 리스트 조회 */
    List<DefaultMap<String>> getWeightProdList(WeightVO weightVO, SessionInfoVO sessionInfoVO);

    /** 상품별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getWeightProdExcelList(WeightVO weightVO, SessionInfoVO sessionInfoVO);

}
