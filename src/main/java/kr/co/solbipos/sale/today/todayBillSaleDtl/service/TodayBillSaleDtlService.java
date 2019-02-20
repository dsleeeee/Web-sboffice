package kr.co.solbipos.sale.today.todayBillSaleDtl.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface TodayBillSaleDtlService {
    /** 영수증별매출상세현황 - 영수증별매출상세 리스트 조회 */
    List<DefaultMap<String>> getTodayBillSaleDtlList(TodayBillSaleDtlVO todayBillSaleDtlVO, SessionInfoVO sessionInfoVO);

}
