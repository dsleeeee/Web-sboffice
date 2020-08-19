package kr.co.solbipos.sale.status.table.day.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.day.service.TableDayService;
import kr.co.solbipos.sale.status.table.day.service.TableDayVO;

@Service("tableDayService")
public class TableDayServiceImpl implements TableDayService {

	private final TableDayMapper tableDayMapper;
	private final MessageService messageService;

	public TableDayServiceImpl(TableDayMapper tableDayMapper, MessageService messageService) {
		super();
		this.tableDayMapper = tableDayMapper;
		this.messageService = messageService;
	}

	/** 테이블별 매출 - 일자별 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayList(TableDayVO tableDayVO, SessionInfoVO sessionInfoVO) {

		tableDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(tableDayVO.getStoreCd()).equals("")) {
        	tableDayVO.setArrStoreCd(tableDayVO.getStoreCd().split(","));
        }

		return tableDayMapper.getTableDayList(tableDayVO);
	}

	/** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayExcelList(TableDayVO tableDayVO, SessionInfoVO sessionInfoVO) {

		tableDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(tableDayVO.getStoreCd()).equals("")) {
        	tableDayVO.setArrStoreCd(tableDayVO.getStoreCd().split(","));
        }

		return tableDayMapper.getTableDayExcelList(tableDayVO);
	}


	/** 테이블별 매출 - 일자별 테이블 선택 조회조건 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStoreTableList(TableDayVO tableDayVO, SessionInfoVO sessionInfoVO) {

		if(!StringUtil.getOrBlank(tableDayVO.getStoreCd()).equals("")) {
        	tableDayVO.setArrStoreCd(tableDayVO.getStoreCd().split(","));
        }

		return tableDayMapper.getStoreTableList(tableDayVO);
	}
}
