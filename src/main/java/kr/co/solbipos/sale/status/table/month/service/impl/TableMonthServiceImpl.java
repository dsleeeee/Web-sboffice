package kr.co.solbipos.sale.status.table.month.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.month.service.TableMonthService;
import kr.co.solbipos.sale.status.table.month.service.TableMonthVO;

@Service("tableMonthService")
public class TableMonthServiceImpl implements TableMonthService {

	private final TableMonthMapper tableMonthMapper;
	private final MessageService messageService;

	public TableMonthServiceImpl(TableMonthMapper tableDayMapper, MessageService messageService) {
		super();
		this.tableMonthMapper = tableDayMapper;
		this.messageService = messageService;
	}

	/** 테이블별 매출 - 월별 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableMonthList(TableMonthVO tableMonthVO, SessionInfoVO sessionInfoVO) {

		tableMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(tableMonthVO.getStoreCd()).equals("")) {
        	tableMonthVO.setArrStoreCd(tableMonthVO.getStoreCd().split(","));
        }

		return tableMonthMapper.getTableMonthList(tableMonthVO);
	}

	/** 테이블별 매출 - 월별 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableMonthExcelList(TableMonthVO tableMonthVO, SessionInfoVO sessionInfoVO) {

		tableMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(tableMonthVO.getStoreCd()).equals("")) {
        	tableMonthVO.setArrStoreCd(tableMonthVO.getStoreCd().split(","));
        }

		return tableMonthMapper.getTableMonthExcelList(tableMonthVO);
	}

	/** 테이블별 매출 - 월별 테이블 콤보박스 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStoreTableList(TableMonthVO tableMonthVO, SessionInfoVO sessionInfoVO) {

		if(!StringUtil.getOrBlank(tableMonthVO.getStoreCd()).equals("")) {
        	tableMonthVO.setArrStoreCd(tableMonthVO.getStoreCd().split(","));
        }

		return tableMonthMapper.getStoreTableList(tableMonthVO);
	}
}
