package kr.co.solbipos.sale.status.appr.payMethod.ncash.service.impl;

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
import kr.co.solbipos.sale.status.appr.payMethod.ncash.service.ApprNcashService;
import kr.co.solbipos.sale.status.appr.payMethod.ncash.service.ApprNcashVO;

@Service("apprNcashService")
public class ApprNcashServiceImpl implements ApprNcashService {
    private final ApprNcashMapper apprNcashMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public ApprNcashServiceImpl(ApprNcashMapper apprNcashMapper, PopupMapper popupMapper, MessageService messageService) {
        this.apprNcashMapper = apprNcashMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }



    /** 비매출현금 승인현황 탭 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcashList(ApprNcashVO apprNcashVO, SessionInfoVO sessionInfoVO) {
		apprNcashVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprNcashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprNcashVO.setEmpNo(sessionInfoVO.getEmpNo());

		if (apprNcashVO.getPosNo() != null && !"".equals(apprNcashVO.getPosNo())) {
			String[] arrPosNo = apprNcashVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprNcashVO.setArrPosNo(arrPosNo);
//        				apprNcashVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprNcashVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprNcashVO.getStoreCd(), 3900));
                apprNcashVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}

		return apprNcashMapper.getApprNcashList(apprNcashVO);
	}

	/** 비매출현금 승인현황 탭 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprNcashExcelList(ApprNcashVO apprNcashVO, SessionInfoVO sessionInfoVO) {
		apprNcashVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if (apprNcashVO.getPosNo() != null && !"".equals(apprNcashVO.getPosNo())) {
			String[] arrPosNo = apprNcashVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprNcashVO.setArrPosNo(arrPosNo);
//        				apprNcashVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
            if(!StringUtil.getOrBlank(apprNcashVO.getStoreCd()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(apprNcashVO.getStoreCd(), 3900));
                apprNcashVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
    	}

		return apprNcashMapper.getApprNcashExcelList(apprNcashVO);
	}
}
