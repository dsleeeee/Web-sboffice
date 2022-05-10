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
		apprPaycoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprPaycoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprPaycoVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprPaycoVO.getCornrCd() != null && !"".equals(apprPaycoVO.getCornrCd())) || (apprPaycoVO.getPosNo() != null && !"".equals(apprPaycoVO.getPosNo()))) {
    		if (apprPaycoVO.getCornrCd() != null && !"".equals(apprPaycoVO.getCornrCd())) {
    			String[] arrCornrCd = apprPaycoVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprPaycoVO.setArrCornrCd(arrCornrCd);
//        				apprPaycoVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprPaycoVO.getPosNo() != null && !"".equals(apprPaycoVO.getPosNo())) {
    			String[] arrPosNo = apprPaycoVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprPaycoVO.setArrPosNo(arrPosNo);
//        				apprPaycoVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprPaycoVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPaycoVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprPaycoMapper.getApprPaycoList(apprPaycoVO);
	}

	/** 승인현황 승인현황 페이코 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprPaycoExcelList(ApprPaycoVO apprPaycoVO, SessionInfoVO sessionInfoVO) {
		apprPaycoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprPaycoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprPaycoVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprPaycoVO.getCornrCd() != null && !"".equals(apprPaycoVO.getCornrCd())) || (apprPaycoVO.getPosNo() != null && !"".equals(apprPaycoVO.getPosNo()))) {
    		if (apprPaycoVO.getCornrCd() != null && !"".equals(apprPaycoVO.getCornrCd())) {
    			String[] arrCornrCd = apprPaycoVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprPaycoVO.setArrCornrCd(arrCornrCd);
//        				apprPaycoVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprPaycoVO.getPosNo() != null && !"".equals(apprPaycoVO.getPosNo())) {
    			String[] arrPosNo = apprPaycoVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprPaycoVO.setArrPosNo(arrPosNo);
//        				apprPaycoVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprPaycoVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprPaycoVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprPaycoMapper.getApprPaycoExcelList(apprPaycoVO);
	}
}
