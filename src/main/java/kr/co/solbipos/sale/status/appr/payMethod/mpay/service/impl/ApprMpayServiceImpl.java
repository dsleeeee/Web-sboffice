package kr.co.solbipos.sale.status.appr.payMethod.mpay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.mpay.service.ApprMpayService;
import kr.co.solbipos.sale.status.appr.payMethod.mpay.service.ApprMpayVO;

@Service("apprMpayService")
public class ApprMpayServiceImpl implements ApprMpayService {
    private final ApprMpayMapper apprMpayMapper;
    private final MessageService messageService;

    @Autowired
    public ApprMpayServiceImpl(ApprMpayMapper apprMpayMapper, MessageService messageService) {
        this.apprMpayMapper = apprMpayMapper;
        this.messageService = messageService;
    }


    
    /** Mpay 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMpayList(ApprMpayVO apprMpayVO, SessionInfoVO sessionInfoVO) {
		apprMpayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprMpayMapper.getApprMpayList(apprMpayVO);
	}
}
