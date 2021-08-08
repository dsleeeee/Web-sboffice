package kr.co.solbipos.stock.status.periodiostock.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockService;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

@Service("PeriodIostockService")
public class PeriodIostockServiceImpl implements PeriodIostockService {
	private final PeriodIostockMapper periodIostockMapper;
	
	public PeriodIostockServiceImpl(PeriodIostockMapper periodIostockMapper) {
		this.periodIostockMapper = periodIostockMapper;
	}

	/** 기간수불현황 - 기간수불현황 리스트 조회 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<DefaultMap<String>> getPeriodIostockList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO) {
		
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(periodIostockVO.getVendrCd()).equals("")) {
        	periodIostockVO.setArrVendrCd(periodIostockVO.getVendrCd().split(","));
        }
        
		periodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		periodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return periodIostockMapper.getPeriodIostockList(periodIostockVO);
		} else {
			return periodIostockMapper.getPeriodIostockStoreList(periodIostockVO);
		}
	}

	/** 기간수불현황 - 기간수불현황 상세 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getPeriodiostockProdDtlList(PeriodIostockVO periodIostockVO,
			SessionInfoVO sessionInfoVO) {
		
		periodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		periodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return periodIostockMapper.getPeriodiostockProdDtlList(periodIostockVO);
		} else {
			return periodIostockMapper.getPeriodiostockProdStoreDtlList(periodIostockVO);
		}
	}

	/** 기간수불현황 - 기간수불현황 엑셀 전체다운로드 조회 */
	@Override
	public List<DefaultMap<String>> getPeriodIostockExcelList(PeriodIostockVO periodIostockVO,
			SessionInfoVO sessionInfoVO) {
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(periodIostockVO.getVendrCd()).equals("")) {
        	periodIostockVO.setArrVendrCd(periodIostockVO.getVendrCd().split(","));
        }
        
		periodIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		periodIostockVO.setStoreCd(sessionInfoVO.getStoreCd());

		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return periodIostockMapper.getPeriodIostockExcelList(periodIostockVO);
		} else {
			return periodIostockMapper.getPeriodIostockStoreExcelList(periodIostockVO);
		}
	}
}
