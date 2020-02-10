package kr.co.solbipos.sale.status.appr.payMethod.partner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.partner.service.ApprPartnerService;
import kr.co.solbipos.sale.status.appr.payMethod.partner.service.ApprPartnerVO;

@Service("apprPartnerService")
public class ApprPartnerServiceImpl implements ApprPartnerService {
    private final ApprPartnerMapper apprPartnerMapper;
    private final MessageService messageService;

    @Autowired
    public ApprPartnerServiceImpl(ApprPartnerMapper apprPartnerMapper, MessageService messageService) {
        this.apprPartnerMapper = apprPartnerMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprPartnerList(ApprPartnerVO apprPartnerVO, SessionInfoVO sessionInfoVO) {
		apprPartnerVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprPartnerMapper.getApprPartnerList(apprPartnerVO);
	}

}
