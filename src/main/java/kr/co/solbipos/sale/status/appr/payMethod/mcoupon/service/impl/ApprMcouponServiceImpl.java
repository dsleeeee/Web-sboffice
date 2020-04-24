package kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.ApprMcouponService;
import kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.ApprMcouponVO;

@Service("apprMcouponService")
public class ApprMcouponServiceImpl implements ApprMcouponService {
    private final ApprMcouponMapper apprMcouponMapper;
    private final MessageService messageService;

    @Autowired
    public ApprMcouponServiceImpl(ApprMcouponMapper apprMcouponMapper, MessageService messageService) {
        this.apprMcouponMapper = apprMcouponMapper;
        this.messageService = messageService;
    }



    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMcouponList(ApprMcouponVO apprMcouponVO, SessionInfoVO sessionInfoVO) {
		apprMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if ((apprMcouponVO.getCornrCd() != null && !"".equals(apprMcouponVO.getCornrCd())) || (apprMcouponVO.getPosNo() != null && !"".equals(apprMcouponVO.getPosNo()))) {
    		if (apprMcouponVO.getCornrCd() != null && !"".equals(apprMcouponVO.getCornrCd())) {
    			String[] arrCornrCd = apprMcouponVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprMcouponVO.setArrCornrCd(arrCornrCd);
//        				apprMcouponVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprMcouponVO.getPosNo() != null && !"".equals(apprMcouponVO.getPosNo())) {
    			String[] arrPosNo = apprMcouponVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprMcouponVO.setArrPosNo(arrPosNo);
//        				apprMcouponVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprMcouponVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprMcouponVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprMcouponMapper.getApprMcouponList(apprMcouponVO);
	}

	/** 모바일쿠폰 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMcouponExcelList(ApprMcouponVO apprMcouponVO, SessionInfoVO sessionInfoVO) {
		apprMcouponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if ((apprMcouponVO.getCornrCd() != null && !"".equals(apprMcouponVO.getCornrCd())) || (apprMcouponVO.getPosNo() != null && !"".equals(apprMcouponVO.getPosNo()))) {
    		if (apprMcouponVO.getCornrCd() != null && !"".equals(apprMcouponVO.getCornrCd())) {
    			String[] arrCornrCd = apprMcouponVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprMcouponVO.setArrCornrCd(arrCornrCd);
//        				apprMcouponVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprMcouponVO.getPosNo() != null && !"".equals(apprMcouponVO.getPosNo())) {
    			String[] arrPosNo = apprMcouponVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprMcouponVO.setArrPosNo(arrPosNo);
//        				apprMcouponVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprMcouponVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprMcouponVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprMcouponMapper.getApprMcouponExcelList(apprMcouponVO);
	}
}
