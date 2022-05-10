package kr.co.solbipos.sale.status.appr.acquire.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireService;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireVO;

@Service("apprAcquireService")
public class ApprAcquireServiceImpl implements ApprAcquireService {
    private final ApprAcquireMapper apprAcquireMapper;
    private final MessageService messageService;

    @Autowired
    public ApprAcquireServiceImpl(ApprAcquireMapper apprAcquireMapper, MessageService messageService) {
        this.apprAcquireMapper = apprAcquireMapper;
        this.messageService = messageService;
    }



    /** 승인현황 카드매입사별 탭 - 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 모바일쿠폰 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireMcouponList(ApprAcquireVO apprAcquireVO,	SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireMcouponList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 모바일페이 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireMpayList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireMpayList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 비매출카드 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireNcardList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireNcardList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireExcelList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 모바일쿠폰 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireMcouponExcelList(ApprAcquireVO apprAcquireVO,	SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireMcouponExcelList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 모바일페이 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireMpayExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ((apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) || (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo()))) {
    		if (apprAcquireVO.getCornrCd() != null && !"".equals(apprAcquireVO.getCornrCd())) {
    			String[] arrCornrCd = apprAcquireVO.getCornrCd().split(",");
    			if (arrCornrCd.length > 0) {
        			if (arrCornrCd[0] != null && !"".equals(arrCornrCd[0])) {
        				apprAcquireVO.setArrCornrCd(arrCornrCd);
//        				apprAcquireVO.setArrStoreCornr(arrCornrCd);
        			}
        		}
    		}
    		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
    			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
    			if (arrPosNo.length > 0) {
        			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
        				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
        			}
        		}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireMpayExcelList(apprAcquireVO);
	}


	/** 승인현황 카드매입사별 탭 - 비매출카드 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getApprAcquireNcardExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO) {
		apprAcquireVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		apprAcquireVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		apprAcquireVO.setEmpNo(sessionInfoVO.getEmpNo());

		if (apprAcquireVO.getPosNo() != null && !"".equals(apprAcquireVO.getPosNo())) {
			String[] arrPosNo = apprAcquireVO.getPosNo().split(",");
			if (arrPosNo.length > 0) {
    			if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
    				apprAcquireVO.setArrPosNo(arrPosNo);
//        				apprAcquireVO.setArrStorePos(arrPosNo);
    			}
    		}
    	} else {
    		String[] arrStoreCd = apprAcquireVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				apprAcquireVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return apprAcquireMapper.getApprAcquireNcardExcelList(apprAcquireVO);
	}
}
