package kr.co.solbipos.sale.status.appr.payMethod.card.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.card.service.ApprCardService;
import kr.co.solbipos.sale.status.appr.payMethod.card.service.ApprCardVO;

@Service("apprCardService")
public class ApprCardServiceImpl implements ApprCardService {
    private final ApprCardMapper apprCardMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCardServiceImpl(ApprCardMapper apprCardMapper, MessageService messageService) {
        this.apprCardMapper = apprCardMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCardList(ApprCardVO apprCardVO, SessionInfoVO sessionInfoVO) {
		apprCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprCardMapper.getApprCardList(apprCardVO);
	}



	@Override
	public List<DefaultMap<String>> getCornerNmList(ApprCardVO apprCardVO, SessionInfoVO sessionInfoVO) {
		apprCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return apprCardMapper.getCornerNmList(apprCardVO);
	}
}
