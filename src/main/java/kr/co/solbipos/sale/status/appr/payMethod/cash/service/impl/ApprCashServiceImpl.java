package kr.co.solbipos.sale.status.appr.payMethod.cash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.cash.service.ApprCashService;
import kr.co.solbipos.sale.status.appr.payMethod.cash.service.ApprCashVO;

@Service("apprCashService")
public class ApprCashServiceImpl implements ApprCashService {
    private final ApprCashMapper apprCashMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCashServiceImpl(ApprCashMapper apprCashMapper, MessageService messageService) {
        this.apprCashMapper = apprCashMapper;
        this.messageService = messageService;
    }



    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCashList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO) {
		apprCashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if ((apprCashVO.getCornrCd() != null && !"".equals(apprCashVO.getCornrCd())) || (apprCashVO.getPosNo() != null && !"".equals(apprCashVO.getPosNo()))) {
    		if (apprCashVO.getCornrCd() != null && !"".equals(apprCashVO.getCornrCd())) {
    			String[] arrCornrCd = apprCashVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprCashVO.setArrCornrCd(arrCornrCd);
//        				apprCashVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprCashVO.getPosNo() != null && !"".equals(apprCashVO.getPosNo())) {
    			String[] arrPosNo = apprCashVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprCashVO.setArrPosNo(arrPosNo);
//        				apprCashVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprCashVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprCashVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return apprCashMapper.getApprCashList(apprCashVO);
	}

	/** 신용카드 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCashExcelList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO) {
		apprCashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if ((apprCashVO.getCornrCd() != null && !"".equals(apprCashVO.getCornrCd())) || (apprCashVO.getPosNo() != null && !"".equals(apprCashVO.getPosNo()))) {
    		if (apprCashVO.getCornrCd() != null && !"".equals(apprCashVO.getCornrCd())) {
    			String[] arrCornrCd = apprCashVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprCashVO.setArrCornrCd(arrCornrCd);
//        				apprCashVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprCashVO.getPosNo() != null && !"".equals(apprCashVO.getPosNo())) {
    			String[] arrPosNo = apprCashVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprCashVO.setArrPosNo(arrPosNo);
//        				apprCashVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprCashVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprCashVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
		return apprCashMapper.getApprCashExcelList(apprCashVO);
	}

}
