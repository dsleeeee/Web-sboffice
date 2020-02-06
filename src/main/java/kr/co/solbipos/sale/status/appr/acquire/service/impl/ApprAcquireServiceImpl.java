package kr.co.solbipos.sale.status.appr.acquire.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireService;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireVO;

@Service("apprAcquireService")
public class ApprAcquireServiceImpl implements ApprAcquireService {
    private final ApprAcquireMapper apprAcquireMapper;
    private final MessageService messageService;

    @Autowired
    public ApprAcquireServiceImpl(ApprAcquireMapper apprAcquireMapper, MessageService messageService) {
        this.apprAcquireMapper = apprAcquireMapper;
        this.messageService = messageService;
    }


    
    /** 승인현황 카드매입사별 탭 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprAcquireMapper.getApprAcquireList(apprAcquireVO);
	}
}
