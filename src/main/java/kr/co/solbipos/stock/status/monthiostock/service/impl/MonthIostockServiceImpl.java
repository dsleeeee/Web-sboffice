package kr.co.solbipos.stock.status.monthiostock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.monthiostock.service.MonthIostockService;
import kr.co.solbipos.stock.status.monthiostock.service.MonthIostockVO;


@Service("MonthIostockService")
public class MonthIostockServiceImpl implements MonthIostockService {
	 private final MonthIostockMapper monthIostockMapper;
	    private final MessageService messageService;

	    @Autowired
	    public MonthIostockServiceImpl(MonthIostockMapper monthIostockMapper, MessageService messageService) {
	        this.monthIostockMapper = monthIostockMapper;
	        this.messageService = messageService;
	    }

	/** 월수불현황 리스트 조회 */
	@Override
	public List<DefaultMap<String>> monthIostockList(MonthIostockVO monthIostockVO, SessionInfoVO sessionInfoVO) {
		monthIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		monthIostockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(monthIostockVO.getVendrCd()).equals("")) {
        	monthIostockVO.setArrVendrCd(monthIostockVO.getVendrCd().split(","));
        }
		List<DefaultMap<String>> list;
		if(monthIostockVO.getOrgnFg() == "H" && monthIostockVO.getOrgnFg() != null) { // 본사권한
			list = monthIostockMapper.hqMonthIostockList(monthIostockVO);
		}else { // 매장권한
			list = monthIostockMapper.storeMonthIostockList(monthIostockVO);
		}
		return list;
	}

}
