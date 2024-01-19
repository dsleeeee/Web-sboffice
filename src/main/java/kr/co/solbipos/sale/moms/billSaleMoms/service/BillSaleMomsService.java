package kr.co.solbipos.sale.moms.billSaleMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BillSaleMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 영수건별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BillSaleMomsService {

    /** 영수건별매출 - 조회 */
    List<DefaultMap<Object>> getBillSaleMomsList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO);

    /** 영수건별매출 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getBillSaleMomsExcelList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO);

    /** 영수건별매출 - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getBillSaleMomsExcelDivisionList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO);
}