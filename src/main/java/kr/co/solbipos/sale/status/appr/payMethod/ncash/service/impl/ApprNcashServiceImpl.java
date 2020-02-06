package kr.co.solbipos.sale.status.appr.payMethod.ncash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.ncash.service.ApprNcashService;
import kr.co.solbipos.sale.status.appr.payMethod.ncash.service.ApprNcashVO;

@Service("apprNcashService")
public class ApprNcashServiceImpl implements ApprNcashService {
    private final ApprNcashMapper apprNcashMapper;
    private final MessageService messageService;

    @Autowired
    public ApprNcashServiceImpl(ApprNcashMapper apprNcashMapper, MessageService messageService) {
        this.apprNcashMapper = apprNcashMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcashList(ApprNcashVO apprNcashVO, SessionInfoVO sessionInfoVO) {
		apprNcashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprNcashMapper.getApprNcashList(apprNcashVO);
	}
}
