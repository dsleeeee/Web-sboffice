package kr.co.solbipos.sale.prod.monthSalesVolume.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.monthSalesVolume.service.MonthSalesVolumeVO;

import java.util.List;

/**
 * @Class Name : MonthSalesVolumeService.java
 * @Description : 맘스터치 > 승인관리2 > 월별 상품 판매량
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthSalesVolumeService {

    /** 일별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getMonthSalesVolumeList(MonthSalesVolumeVO monthSalesVolumeVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getMonthSalesVolumeExcelList(MonthSalesVolumeVO monthSalesVolumeVO, SessionInfoVO sessionInfoVO);

}