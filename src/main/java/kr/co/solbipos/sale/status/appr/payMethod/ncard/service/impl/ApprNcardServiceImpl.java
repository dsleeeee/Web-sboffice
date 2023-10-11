package kr.co.solbipos.sale.status.appr.payMethod.ncard.service.impl;

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
import kr.co.solbipos.sale.status.appr.payMethod.ncard.service.ApprNcardService;
import kr.co.solbipos.sale.status.appr.payMethod.ncard.service.ApprNcardVO;

@Service("apprNcardService")
public class ApprNcardServiceImpl implements ApprNcardService {
    private final ApprNcardMapper apprNcardMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ApprNcardServiceImpl(ApprNcardMapper apprNcardMapper, PopupMapper popupMapper, MessageService messageService) {
        this.apprNcardMapper = apprNcardMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }



    /** 비매출카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcardList(ApprNcardVO apprNcardVO, SessionInfoVO sessionInfoVO) {
		apprNcardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprNcardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprNcardVO.setEmpNo(sessionInfoVO.getEmpNo());

		if (apprNcardVO.getPosNo() != null && !"".equals(apprNcardVO.getPosNo())) {
			String[] arrPosNo = apprNcardVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprNcardVO.setArrPosNo(arrPosNo);
//        				apprNcardVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprNcardVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprNcardVO.getStoreCd(), 3900));
                apprNcardVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprNcardMapper.getApprNcardList(apprNcardVO);
	}

	/** 비매출카드 승인현황 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcardExcelList(ApprNcardVO apprNcardVO, SessionInfoVO sessionInfoVO) {
		apprNcardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprNcardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprNcardVO.setEmpNo(sessionInfoVO.getEmpNo());

		if (apprNcardVO.getPosNo() != null && !"".equals(apprNcardVO.getPosNo())) {
			String[] arrPosNo = apprNcardVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprNcardVO.setArrPosNo(arrPosNo);
//        				apprNcardVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprNcardVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprNcardVO.getStoreCd(), 3900));
                apprNcardVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprNcardMapper.getApprNcardExcelList(apprNcardVO);
	}
}
