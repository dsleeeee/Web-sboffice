package kr.co.solbipos.sale.status.table.dayOfWeek.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.dayOfWeek.service.TableDayOfWeekService;
import kr.co.solbipos.sale.status.table.dayOfWeek.service.TableDayOfWeekVO;

@Service("tableDayOfWeekService")
public class TableDayOfWeekServiceImpl implements TableDayOfWeekService {

	private final TableDayOfWeekMapper tableDayOfWeekMapper;
	private final MessageService messageService;

	public TableDayOfWeekServiceImpl(TableDayOfWeekMapper tableDayMapper, MessageService messageService) {
		super();
		this.tableDayOfWeekMapper = tableDayMapper;
		this.messageService = messageService;
	}

	/** 테이블별 매출 - 요일별 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayOfWeekList(TableDayOfWeekVO tableDayOfWeekVO, SessionInfoVO sessionInfoVO) {

		tableDayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(tableDayOfWeekVO.getStoreCd()).equals("")) {
        	tableDayOfWeekVO.setArrStoreCd(tableDayOfWeekVO.getStoreCd().split(","));
        }

		return tableDayOfWeekMapper.getTableDayOfWeekList(tableDayOfWeekVO);
	}

	/** 테이블별 매출 - 요일별 테이블 콤보박스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStoreTableList(TableDayOfWeekVO tableDayOfWeekVO, SessionInfoVO sessionInfoVO) {

		if(!StringUtil.getOrBlank(tableDayOfWeekVO.getStoreCd()).equals("")) {
        	tableDayOfWeekVO.setArrStoreCd(tableDayOfWeekVO.getStoreCd().split(","));
        }

		return tableDayOfWeekMapper.getStoreTableList(tableDayOfWeekVO);
	}

}
