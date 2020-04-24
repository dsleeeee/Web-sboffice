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

		if ((apprCardVO.getCornrCd() != null && !"".equals(apprCardVO.getCornrCd())) || (apprCardVO.getPosNo() != null && !"".equals(apprCardVO.getPosNo()))) {
    		if (apprCardVO.getCornrCd() != null && !"".equals(apprCardVO.getCornrCd())) {
    			String[] arrCornrCd = apprCardVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprCardVO.setArrCornrCd(arrCornrCd);
//        				apprCardVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprCardVO.getPosNo() != null && !"".equals(apprCardVO.getPosNo())) {
    			String[] arrPosNo = apprCardVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprCardVO.setArrPosNo(arrPosNo);
//        				apprCardVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprCardVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprCardVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return apprCardMapper.getApprCardList(apprCardVO);
	}

	/** 신용카드 승인현황 신용카드 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCardExcelList(ApprCardVO apprCardVO, SessionInfoVO sessionInfoVO) {
		apprCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if ((apprCardVO.getCornrCd() != null && !"".equals(apprCardVO.getCornrCd())) || (apprCardVO.getPosNo() != null && !"".equals(apprCardVO.getPosNo()))) {
    		if (apprCardVO.getCornrCd() != null && !"".equals(apprCardVO.getCornrCd())) {
    			String[] arrCornrCd = apprCardVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprCardVO.setArrCornrCd(arrCornrCd);
//        				apprCardVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprCardVO.getPosNo() != null && !"".equals(apprCardVO.getPosNo())) {
    			String[] arrPosNo = apprCardVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprCardVO.setArrPosNo(arrPosNo);
//        				apprCardVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprCardVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprCardVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return apprCardMapper.getApprCardExcelList(apprCardVO);
	}

}
