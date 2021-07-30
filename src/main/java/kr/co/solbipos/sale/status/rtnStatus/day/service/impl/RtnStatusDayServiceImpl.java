package kr.co.solbipos.sale.status.rtnStatus.day.service.impl;

import java.util.List;

import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayService;
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayVO;

/**
 * @Class Name : RecpOriginServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.06  김진        최초생성
 * @ 2021.01.04  김설아      주석
 *
 * @author 김진
 * @since 2020.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("rtnStatusDayService")
public class RtnStatusDayServiceImpl implements RtnStatusDayService {
    private final RtnStatusDayMapper rtnStatusDayMapper;
    private final MessageService messageService;

    @Autowired
    public RtnStatusDayServiceImpl(RtnStatusDayMapper rtnStatusDayMapper, MessageService messageService) {
        this.rtnStatusDayMapper = rtnStatusDayMapper;
        this.messageService = messageService;
    }


    /** 반품현황 - 일자별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnStatusDayList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
    	rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if (rtnStatusDayVO.getStoreCd() != null && !"".equals(rtnStatusDayVO.getStoreCd())) {
        	
        	String[] arrStoreCd = rtnStatusDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				rtnStatusDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
    	
        return rtnStatusDayMapper.getRtnStatusDayList(rtnStatusDayVO);
    }

    
    /** 반품현황 - 일자별 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getRtnStatusDayDtlList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return rtnStatusDayMapper.getRtnStatusDayDtlList(rtnStatusDayVO);
	}


	/** 반품현황 - 포스별 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getRtnStatusPosDtlList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return rtnStatusDayMapper.getRtnStatusPosDtlList(rtnStatusDayVO);
	}

	
	/** 반품현황 > 상품별 반품현황탭 - 조회 */
	@Override
	public List<DefaultMap<String>> getRtnStatusProdList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		rtnStatusDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		
		if (rtnStatusDayVO.getStoreCd() != null && !"".equals(rtnStatusDayVO.getStoreCd())) {
        	String[] arrStoreCd = rtnStatusDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				rtnStatusDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}

		return rtnStatusDayMapper.getRtnStatusProdList(rtnStatusDayVO);
	}

	/** 반품현황 > 상품별 반품현황탭 - 엑셀 조회 */
	@Override
	public List<DefaultMap<String>> getRtnStatusProdExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		rtnStatusDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (rtnStatusDayVO.getStoreCd() != null && !"".equals(rtnStatusDayVO.getStoreCd())) {
			String[] arrStoreCd = rtnStatusDayVO.getStoreCd().split(",");
			if (arrStoreCd.length > 0) {
				if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
					rtnStatusDayVO.setArrStoreCd(arrStoreCd);
				}
			}
		}
		return rtnStatusDayMapper.getRtnStatusProdExcelList(rtnStatusDayVO);
	}

	@Override
	public List<DefaultMap<String>> getRtnstatusDayExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	
    	if (rtnStatusDayVO.getStoreCd() != null && !"".equals(rtnStatusDayVO.getStoreCd())) {
        	
        	String[] arrStoreCd = rtnStatusDayVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				rtnStatusDayVO.setArrStoreCd(arrStoreCd);
    			}
    		}
    	}
    	
        return rtnStatusDayMapper.getRtnstatusDayExcelList(rtnStatusDayVO);
	}


	@Override
	public List<DefaultMap<String>> getRtnstatusDayDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return rtnStatusDayMapper.getRtnstatusDayDtlExcelList(rtnStatusDayVO);
	}


	@Override
	public List<DefaultMap<String>> getRtnStatusPosDtlExcelList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return rtnStatusDayMapper.getRtnStatusPosDtlExcelList(rtnStatusDayVO);
	}

	@Override
	public List<DefaultMap<String>> getPayColAddList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		return rtnStatusDayMapper.getPayColAddList(rtnStatusDayVO);
	}

	@Override
	public List<DefaultMap<String>> getDcColList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		return rtnStatusDayMapper.getDcColList(rtnStatusDayVO);
	}

	@Override
	public List<DefaultMap<String>> getGuestColList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
			rtnStatusDayVO.setStoreCd(sessionInfoVO.getStoreCd());
		}
		return rtnStatusDayMapper.getGuestColList(rtnStatusDayVO);
	}

	@Override
	public List<DefaultMap<String>> getRtnstatusBillList(RtnStatusDayVO rtnStatusDayVO, SessionInfoVO sessionInfoVO) {
		rtnStatusDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		if (rtnStatusDayVO.getStoreCd() != null && !"".equals(rtnStatusDayVO.getStoreCd())) {
			String[] arrStoreCd = rtnStatusDayVO.getStoreCd().split(",");
			if (arrStoreCd.length > 0) {
				if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
					rtnStatusDayVO.setArrStoreCd(arrStoreCd);
				}
			}
		}

		// 결제수단 array 값 세팅
		rtnStatusDayVO.setArrPayCol(rtnStatusDayVO.getPayCol().split(","));
		// 쿼리문 PIVOT IN 에 들어갈 문자열 생성
		String payCol= "";
		String pivotPayCol = "";
		String arrPayCol[] = rtnStatusDayVO.getPayCol().split(",");
		for(int i=0; i < arrPayCol.length; i++) {
			// 현금,현금영수증 제외
			if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
				pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
				payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
			}
		}
		rtnStatusDayVO.setPivotPayCol(pivotPayCol);
		rtnStatusDayVO.setArrPayCol(payCol.split(","));

		// 할인구분 array 값 세팅
		rtnStatusDayVO.setArrDcCol(rtnStatusDayVO.getDcCol().split(","));
		// 쿼리문 PIVOT IN 에 들어갈 문자열 생성
		String pivotDcCol = "";
		String arrDcCol[] = rtnStatusDayVO.getDcCol().split(",");
		for(int i=0; i < arrDcCol.length; i++) {
			pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
		}
		rtnStatusDayVO.setPivotDcCol(pivotDcCol);

		return rtnStatusDayMapper.getRtnstatusBillList(rtnStatusDayVO);
	}

}