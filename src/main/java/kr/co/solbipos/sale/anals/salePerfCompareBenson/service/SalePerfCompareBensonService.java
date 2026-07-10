package kr.co.solbipos.sale.anals.salePerfCompareBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : SalePerfCompareBensonService.java
 * @Description : 벤슨 > 매출분석 > 대비기간 매출실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePerfCompareBensonService {

    /** 대비기간 매출실적 - 전체점포탭 조회 */
    List<DefaultMap<Object>> getSalePerfCompareBensonList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO);

    /** 대비기간 매출실적 - 전체점포탭 채널별 조회 */
    List<DefaultMap<Object>> getSalePerfCompareBensonDtlList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO);

    /** 대비기간 매출실적 - 선택점포탭 조회 */
    List<DefaultMap<Object>> getSalePerfCompareBensonStoreList(SalePerfCompareBensonVO salePerfCompareBensonVO, SessionInfoVO sessionInfoVO);
}
