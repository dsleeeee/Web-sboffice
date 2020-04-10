package kr.co.solbipos.stock.status.dayiostock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.dayiostock.service.DayIostockService;
import kr.co.solbipos.stock.status.dayiostock.service.DayIostockVO;


@Service("DayIostockService")
public class DayIostockServiceImpl implements DayIostockService {
	 private final DayIostockMapper dayIostockMapper;
	    private final MessageService messageService;

	    @Autowired
	    public DayIostockServiceImpl(DayIostockMapper dayIostockMapper, MessageService messageService) {
	        this.dayIostockMapper = dayIostockMapper;
	        this.messageService = messageService;
	    }

	/** 일수불현황 리스트 조회 */
	@Override
	public List<DefaultMap<String>> dayIostockList(DayIostockVO dayIostockVO, SessionInfoVO sessionInfoVO) {
		dayIostockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dayIostockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		// 거래처 멀티 선택
        if(!StringUtil.getOrBlank(dayIostockVO.getVendrCd()).equals("")) {
        	dayIostockVO.setArrVendrCd(dayIostockVO.getVendrCd().split(","));
        }
		List<DefaultMap<String>> list;
		if(dayIostockVO.getOrgnFg() == "H" && dayIostockVO.getOrgnFg() != null) { // 본사권한
			list = dayIostockMapper.hqDayIostockList(dayIostockVO);
		}else { // 매장권한
			dayIostockVO.setStoreCd(sessionInfoVO.getStoreCd());
			list = dayIostockMapper.storeDayIostockList(dayIostockVO);
		}
		return list;
	}

}
