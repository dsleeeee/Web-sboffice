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



    /** 제휴카드 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprPartnerList(ApprPartnerVO apprPartnerVO, SessionInfoVO sessionInfoVO) {
		apprPartnerVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprPartnerVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprPartnerVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprPartnerVO.getCornrCd() != null && !"".equals(apprPartnerVO.getCornrCd())) || (apprPartnerVO.getPosNo() != null && !"".equals(apprPartnerVO.getPosNo()))) {
    		if (apprPartnerVO.getCornrCd() != null && !"".equals(apprPartnerVO.getCornrCd())) {
    			String[] arrCornrCd = apprPartnerVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprPartnerVO.setArrCornrCd(arrCornrCd);
//        				apprPartnerVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprPartnerVO.getPosNo() != null && !"".equals(apprPartnerVO.getPosNo())) {
    			String[] arrPosNo = apprPartnerVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprPartnerVO.setArrPosNo(arrPosNo);
//        				apprPartnerVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprPartnerVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPartnerVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprPartnerMapper.getApprPartnerList(apprPartnerVO);
	}

	/** 제휴카드 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprPartnerExcelList(ApprPartnerVO apprPartnerVO, SessionInfoVO sessionInfoVO) {
		apprPartnerVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprPartnerVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprPartnerVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprPartnerVO.getCornrCd() != null && !"".equals(apprPartnerVO.getCornrCd())) || (apprPartnerVO.getPosNo() != null && !"".equals(apprPartnerVO.getPosNo()))) {
    		if (apprPartnerVO.getCornrCd() != null && !"".equals(apprPartnerVO.getCornrCd())) {
    			String[] arrCornrCd = apprPartnerVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprPartnerVO.setArrCornrCd(arrCornrCd);
//        				apprPartnerVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprPartnerVO.getPosNo() != null && !"".equals(apprPartnerVO.getPosNo())) {
    			String[] arrPosNo = apprPartnerVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprPartnerVO.setArrPosNo(arrPosNo);
//        				apprPartnerVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprPartnerVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPartnerVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprPartnerMapper.getApprPartnerExcelList(apprPartnerVO);
	}

}
