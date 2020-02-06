package kr.co.solbipos.sale.status.appr.payMethod.payco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.payco.service.ApprPaycoService;
import kr.co.solbipos.sale.status.appr.payMethod.payco.service.ApprPaycoVO;

@Service("apprPaycoService")
public class ApprPaycoServiceImpl implements ApprPaycoService {
    private final ApprPaycoMapper apprPaycoMapper;
    private final MessageService messageService;

    @Autowired
    public ApprPaycoServiceImpl(ApprPaycoMapper apprPaycoMapper, MessageService messageService) {
        this.apprPaycoMapper = apprPaycoMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprPaycoList(ApprPaycoVO apprPaycoVO, SessionInfoVO sessionInfoVO) {
		apprPaycoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprPaycoMapper.getApprPaycoList(apprPaycoVO);
	}
}
