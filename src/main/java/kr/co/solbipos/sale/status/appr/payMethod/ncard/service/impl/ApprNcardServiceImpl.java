package kr.co.solbipos.sale.status.appr.payMethod.ncard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.ncard.service.ApprNcardService;
import kr.co.solbipos.sale.status.appr.payMethod.ncard.service.ApprNcardVO;

@Service("apprNcardService")
public class ApprNcardServiceImpl implements ApprNcardService {
    private final ApprNcardMapper apprNcardMapper;
    private final MessageService messageService;

    @Autowired
    public ApprNcardServiceImpl(ApprNcardMapper apprNcardMapper, MessageService messageService) {
        this.apprNcardMapper = apprNcardMapper;
        this.messageService = messageService;
    }


    
    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcardList(ApprNcardVO apprNcardVO, SessionInfoVO sessionInfoVO) {
		apprNcardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		
		if (apprNcardVO.getPosNo() != null && !"".equals(apprNcardVO.getPosNo())) {
			String[] arrPosNo = apprNcardVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprNcardVO.setArrPosNo(arrPosNo);
//        				apprNcardVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprNcardVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprNcardVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return apprNcardMapper.getApprNcardList(apprNcardVO);
	}
}
