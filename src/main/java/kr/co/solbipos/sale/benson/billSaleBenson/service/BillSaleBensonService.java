package kr.co.solbipos.sale.benson.billSaleBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BillSaleBensonService.java
 * @Description : 벤슨 > 간소화화면 > 영수건별매출
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
public interface BillSaleBensonService {

    /** 영수건별매출 - 조회 */
    List<DefaultMap<Object>> getBillSaleBensonList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO);

    /** 영수건별매출 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getBillSaleBensonExcelList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO);

    /** 영수건별매출 - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getBillSaleBensonExcelDivisionList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO);
}
