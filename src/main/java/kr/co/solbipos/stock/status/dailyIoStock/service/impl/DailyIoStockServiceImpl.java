package kr.co.solbipos.stock.status.dailyIoStock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockService;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;

@Service("DailyIoStockkService")
public class DailyIoStockServiceImpl implements DailyIoStockService {

	private final DailyIoStockMapper dailyIoStockMapper;
    private final MessageService messageService;

    @Autowired
    public DailyIoStockServiceImpl(DailyIoStockMapper dailyIoStockMapper, MessageService messageService) {
        this.dailyIoStockMapper = dailyIoStockMapper;
        this.messageService = messageService;
    }


	@Override
	public List<DefaultMap<String>> getDailyIoStockList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO) {

		dailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dailyIoStockMapper.getDailyIoStockList(dailyIoStockVO);
    }
	

	@Override
	public List<DefaultMap<String>> getDailyIoStockExcelList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO) {

		dailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dailyIoStockMapper.getDailyIoStockExcelList(dailyIoStockVO);
    }

}
