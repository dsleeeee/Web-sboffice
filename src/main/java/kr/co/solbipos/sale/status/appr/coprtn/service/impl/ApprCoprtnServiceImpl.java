package kr.co.solbipos.sale.status.appr.coprtn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.coprtn.service.ApprCoprtnService;
import kr.co.solbipos.sale.status.appr.coprtn.service.ApprCoprtnVO;

@Service("apprCoprtnService")
public class ApprCoprtnServiceImpl implements ApprCoprtnService {
    private final ApprCoprtnMapper apprCoprtnMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCoprtnServiceImpl(ApprCoprtnMapper apprCoprtnMapper, MessageService messageService) {
        this.apprCoprtnMapper = apprCoprtnMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCoprtnList(ApprCoprtnVO apprCoprtnVO, SessionInfoVO sessionInfoVO) {
		apprCoprtnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprCoprtnMapper.getApprCoprtnList(apprCoprtnVO);
	}
}
