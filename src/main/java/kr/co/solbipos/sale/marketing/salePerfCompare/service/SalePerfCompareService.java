package kr.co.solbipos.sale.marketing.salePerfCompare.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : SalePerfCompareService.java
 * @Description : 미스터피자 > 마케팅조회 > 매출실적비교
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.08  김유승      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePerfCompareService {
    List<DefaultMap<Object>> getSalePerfCompareList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<Object>> getSalePerfCompareDtlList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<Object>> getSalePerfCompareStoreList(SalePerfCompareVO salePerfCompareVO, SessionInfoVO sessionInfoVO);
}
