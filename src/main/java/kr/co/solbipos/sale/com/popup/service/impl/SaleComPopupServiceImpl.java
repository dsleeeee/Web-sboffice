package kr.co.solbipos.sale.com.popup.service.impl;

import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupService;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SaleComPopupService")
public class SaleComPopupServiceImpl implements SaleComPopupService {
    private final SaleComPopupMapper saleComPopupMapper;
    private final MessageService messageService;

    @Autowired
    public SaleComPopupServiceImpl(SaleComPopupMapper saleCompoPupMapper, MessageService messageService) {
    	this.saleComPopupMapper = saleCompoPupMapper;
        this.messageService = messageService;
    }


    /** 매출공통팝업 - 테이블별 매출현황 팝업(실매출 클릭) */
    @Override
    public List<DefaultMap<String>> getTablePopList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

    	saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (saleComPopupVO.getTblCd() != null && !"".equals(saleComPopupVO.getTblCd())) {
    		String[] arrTblCd = saleComPopupVO.getTblCd().split(",");
    		if (arrTblCd.length > 0) {
    			if (arrTblCd[0] != null && !"".equals(arrTblCd[0])) {
    				saleComPopupVO.setTblCd("");
    				saleComPopupVO.setArrStoreTbl(arrTblCd);
    			}
    		}
    	}


		return saleComPopupMapper.getTablePopList(saleComPopupVO);
    }

    /** 매출공통팝업 - 상품매출내역 팝업(수량 클릭) */
    @Override
    public List<DefaultMap<String>> getProdPopList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

    	saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	saleComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(saleComPopupVO.getStoreCd()).equals("")) {
        	saleComPopupVO.setArrStoreCd(saleComPopupVO.getStoreCd().split(","));
        }
        if(!StringUtil.getOrBlank(saleComPopupVO.getCornrCd()).equals("")) {
        	saleComPopupVO.setArrCornrCd(saleComPopupVO.getCornrCd().split(","));
        }

    	if(saleComPopupVO.getChkPop().equals("tablePop")) {
    		return saleComPopupMapper.getProdPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("empPop")) {
    		return saleComPopupMapper.getEmpPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("posPop")) {
    		return saleComPopupMapper.getPosPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("posMonthPop")) {
    		return saleComPopupMapper.getPosMonthPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("posHourPop")) {
    		return saleComPopupMapper.getPosHourPopList(saleComPopupVO);
    	}
        return null;
    }

    /** 매출공통팝업 - 승인현황(매장현황) 팝업(매장명 클릭) */
    @Override
    public List<DefaultMap<String>> getApprPopList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

    	saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	saleComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(saleComPopupVO.getCornrCd()).equals("")) {
        	saleComPopupVO.setArrStoreCornr(saleComPopupVO.getCornrCd().split(","));
        }
        if(!StringUtil.getOrBlank(saleComPopupVO.getPosNo()).equals("")) {
        	saleComPopupVO.setArrStorePos(saleComPopupVO.getPosNo().split(","));
        }

    	if(saleComPopupVO.getChkPop().equals("cardApprPop")) {				//카드
    		return saleComPopupMapper.getCardApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("cashApprPop")) {		//현금
    		return saleComPopupMapper.getCashApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("paycoApprPop")) {		//payco
    		return saleComPopupMapper.getPaycoApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("mpayApprPop")) {		//Mpay
    		return saleComPopupMapper.getMpayApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("mcouponApprPop")) {		//Mcoupon
    		return saleComPopupMapper.getMcouponApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("partnerApprPop")) {		//제휴카드
    		return saleComPopupMapper.getPartnerApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("ncardApprPop")) {		//비매출카드
    		return saleComPopupMapper.getNcardApprPopList(saleComPopupVO);
    	}else if(saleComPopupVO.getChkPop().equals("ncashApprPop")) {		//비매출현금
    		return saleComPopupMapper.getNcashApprPopList(saleComPopupVO);
    	}

        return null;
    }

    /** 매출공통팝업 - 상품선택(대분류) 팝업 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getClassProdList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

    	saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	saleComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		return saleComPopupMapper.getClassProdList(saleComPopupVO);
    }

    /** 매출공통팝업 - 상품선택(상품) 팝업 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

    	saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	saleComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		return saleComPopupMapper.getProdList(saleComPopupVO);
    }


    /** 매출공통팝업 - 상품선택(상품) 결제수단별탭 팝업 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPayFgList(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		saleComPopupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		saleComPopupVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(saleComPopupVO.getStoreCd()).equals("")) {
        	saleComPopupVO.setArrStoreCd(saleComPopupVO.getStoreCd().split(","));
        }

		return saleComPopupMapper.getPayFgList(saleComPopupVO);
	}

	/** 매출공통팝업 - 매장정보,매출종합내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop1(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		return saleComPopupMapper.selectBillSalePop1(saleComPopupVO);
	}

	/** 매출공통팝업 - 결제내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop3(SaleComPopupVO saleComPopupVO ,SessionInfoVO sessionInfoVO) {

        // 결제수단별 쿼리 변수
        String sQuery1 = "";

        List<DefaultMap<String>> payCd = saleComPopupMapper.getPayColList(saleComPopupVO);

        for(int i = 0; i < payCd.size(); i++) {
        	String j = payCd.get(i).get("payCd");
        	sQuery1 +=", CASE WHEN A.PAY_CD = "+ j + " THEN SUM(A.PAY_AMT) ELSE NULL END AS PAY" + j +  "\n";
        }

        saleComPopupVO.setsQuery1(sQuery1);

		return saleComPopupMapper.selectBillSalePop3(saleComPopupVO);
	}

	/** 매출공통팝업 - 회원정보 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop4(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		return saleComPopupMapper.selectBillSalePop4(saleComPopupVO);
	}

	/** 매출공통팝업 - 신용카드,현금 결제내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop5(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		//신용카드
		if(saleComPopupVO.getPayCd().equals("01")) {
			return saleComPopupMapper.selectBillSalePopCard(saleComPopupVO);
		//현금
		}else if(saleComPopupVO.getPayCd().equals("02")) {
			return saleComPopupMapper.selectBillSalePopCash(saleComPopupVO);
		}

		return null;
	}

	/** 매출공통팝업 - 상품내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop6(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		return saleComPopupMapper.selectBillSalePop6(saleComPopupVO);
	}

	/** 매출공통팝업 - 원거래매출정보 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillSalePop7(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		return saleComPopupMapper.selectBillSalePop7(saleComPopupVO);
	}

	/** 매출공통팝업 - 신용카드,현금 결제취소내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectBillRtnPop5(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		//신용카드
		if(saleComPopupVO.getPayCd().equals("01")) {
			return saleComPopupMapper.selectBillRtnPopCard(saleComPopupVO);
		//현금
		}else if(saleComPopupVO.getPayCd().equals("02")) {
			return saleComPopupMapper.selectBillRtnPopCash(saleComPopupVO);
		}

		return null;
	}

	/** 매출공통팝업 - 원 신용카드,현금 결제내역 - 영수증 팝업 조회 */
	@Override
	public List<DefaultMap<String>> selectbillRealPop(SaleComPopupVO saleComPopupVO, SessionInfoVO sessionInfoVO) {

		//신용카드
		if(saleComPopupVO.getPayCd().equals("01")) {
			return saleComPopupMapper.selectBillRealRtnPopCard(saleComPopupVO);
		//현금
		}else if(saleComPopupVO.getPayCd().equals("02")) {
			return saleComPopupMapper.selectBillRealRtnPopCash(saleComPopupVO);
		}

		return null;
	}
}
