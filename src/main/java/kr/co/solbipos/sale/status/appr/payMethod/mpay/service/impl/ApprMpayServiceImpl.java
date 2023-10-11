package kr.co.solbipos.sale.status.appr.payMethod.mpay.service.impl;

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
import kr.co.solbipos.sale.status.appr.payMethod.mpay.service.ApprMpayService;
import kr.co.solbipos.sale.status.appr.payMethod.mpay.service.ApprMpayVO;

@Service("apprMpayService")
public class ApprMpayServiceImpl implements ApprMpayService {
    private final ApprMpayMapper apprMpayMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ApprMpayServiceImpl(ApprMpayMapper apprMpayMapper, PopupMapper popupMapper, MessageService messageService) {
        this.apprMpayMapper = apprMpayMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }



    /** Mpay 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMpayList(ApprMpayVO apprMpayVO, SessionInfoVO sessionInfoVO) {
		apprMpayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprMpayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprMpayVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprMpayVO.getCornrCd() != null && !"".equals(apprMpayVO.getCornrCd())) || (apprMpayVO.getPosNo() != null && !"".equals(apprMpayVO.getPosNo()))) {
    		if (apprMpayVO.getCornrCd() != null && !"".equals(apprMpayVO.getCornrCd())) {
    			String[] arrCornrCd = apprMpayVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprMpayVO.setArrCornrCd(arrCornrCd);
//        				apprMpayVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprMpayVO.getPosNo() != null && !"".equals(apprMpayVO.getPosNo())) {
    			String[] arrPosNo = apprMpayVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprMpayVO.setArrPosNo(arrPosNo);
//        				apprMpayVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprMpayVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprMpayVO.getStoreCd(), 3900));
                apprMpayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}

		return apprMpayMapper.getApprMpayList(apprMpayVO);
	}

	/** Mpay 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprMpayExcelList(ApprMpayVO apprMpayVO, SessionInfoVO sessionInfoVO) {
		apprMpayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprMpayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprMpayVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprMpayVO.getCornrCd() != null && !"".equals(apprMpayVO.getCornrCd())) || (apprMpayVO.getPosNo() != null && !"".equals(apprMpayVO.getPosNo()))) {
    		if (apprMpayVO.getCornrCd() != null && !"".equals(apprMpayVO.getCornrCd())) {
    			String[] arrCornrCd = apprMpayVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprMpayVO.setArrCornrCd(arrCornrCd);
//        				apprMpayVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprMpayVO.getPosNo() != null && !"".equals(apprMpayVO.getPosNo())) {
    			String[] arrPosNo = apprMpayVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprMpayVO.setArrPosNo(arrPosNo);
//        				apprMpayVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprMpayVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprMpayVO.getStoreCd(), 3900));
                apprMpayVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}

		return apprMpayMapper.getApprMpayExcelList(apprMpayVO);
	}
}
