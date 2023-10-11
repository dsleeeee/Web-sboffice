package kr.co.solbipos.sale.status.appr.payMethod.cash.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCashServiceImpl(ApprCashMapper apprCashMapper, PopupMapper popupMapper, MessageService messageService) {
        this.apprCashMapper = apprCashMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }



    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCashList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO) {
		apprCashVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprCashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprCashVO.setEmpNo(sessionInfoVO.getEmpNo());

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
            if(!StringUtil.getOrBlank(apprCashVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprCashVO.getStoreCd(), 3900));
                apprCashVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprCashMapper.getApprCashList(apprCashVO);
	}

	/** 신용카드 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCashExcelList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO) {
		apprCashVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprCashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprCashVO.setEmpNo(sessionInfoVO.getEmpNo());

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
            if(!StringUtil.getOrBlank(apprCashVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprCashVO.getStoreCd(), 3900));
                apprCashVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprCashMapper.getApprCashExcelList(apprCashVO);
	}

}
