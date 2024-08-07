package kr.co.solbipos.sale.status.appr.payMethod.card.service.impl;

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
import kr.co.solbipos.sale.status.appr.payMethod.card.service.ApprCardService;
import kr.co.solbipos.sale.status.appr.payMethod.card.service.ApprCardVO;

@Service("apprCardService")
public class ApprCardServiceImpl implements ApprCardService {
    private final ApprCardMapper apprCardMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ApprCardServiceImpl(ApprCardMapper apprCardMapper, PopupMapper popupMapper, MessageService messageService) {
        this.apprCardMapper = apprCardMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }



    /** 신용카드 승인현황 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCardList(ApprCardVO apprCardVO, SessionInfoVO sessionInfoVO) {
		apprCardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprCardVO.setEmpNo(sessionInfoVO.getEmpNo());

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
            if(!StringUtil.getOrBlank(apprCardVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprCardVO.getStoreCd(), 3900));
                apprCardVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprCardMapper.getApprCardList(apprCardVO);
	}

	/** 신용카드 승인현황 신용카드 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprCardExcelList(ApprCardVO apprCardVO, SessionInfoVO sessionInfoVO) {
		apprCardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprCardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprCardVO.setEmpNo(sessionInfoVO.getEmpNo());

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
            if(!StringUtil.getOrBlank(apprCardVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprCardVO.getStoreCd(), 3900));
                apprCardVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}
		return apprCardMapper.getApprCardExcelList(apprCardVO);
	}

}
