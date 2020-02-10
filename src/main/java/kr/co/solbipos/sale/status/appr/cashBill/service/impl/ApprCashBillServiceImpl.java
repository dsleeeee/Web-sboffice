package kr.co.solbipos.sale.status.appr.cashBill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.cashBill.service.ApprCashBillService;
import kr.co.solbipos.sale.status.appr.cashBill.service.ApprCashBillVO;

@Service("apprCashBillService")
public class ApprCashBillServiceImpl implements ApprCashBillService {
    private final ApprCashBillMapper apprCashBillMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCashBillServiceImpl(ApprCashBillMapper apprCashBillMapper, MessageService messageService) {
        this.apprCashBillMapper = apprCashBillMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCashBillList(ApprCashBillVO apprCashBillVO, SessionInfoVO sessionInfoVO) {
		apprCashBillVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprCashBillMapper.getApprCashBillList(apprCashBillVO);
	}
}
