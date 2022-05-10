package kr.co.solbipos.sale.status.table.dayPeriod.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.dayPeriod.service.TableDayPeriodService;
import kr.co.solbipos.sale.status.table.dayPeriod.service.TableDayPeriodVO;

@Service("tableDayPeriod")
public class TableDayPeriodServiceImple implements TableDayPeriodService {

	private final TableDayPeriodMapper tableDayPeriodMapper;
	private final MessageService messageService;

	public TableDayPeriodServiceImple(TableDayPeriodMapper tableDayPeriodMapper, MessageService messageService) {
		super();
		this.tableDayPeriodMapper = tableDayPeriodMapper;
		this.messageService = messageService;
	}

	/** 설정기간별 - 조회일자별 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayPeriodList(TableDayPeriodVO tableDayPeriodVO, SessionInfoVO sessionInfoVO) {
		tableDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		tableDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		tableDayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
		if(!StringUtil.getOrBlank(tableDayPeriodVO.getStoreCd()).equals("")) {
        	tableDayPeriodVO.setArrStoreCd(tableDayPeriodVO.getStoreCd().split(","));
        }

		return tableDayPeriodMapper.getTableDayPeriodList(tableDayPeriodVO);
	}

	/** 설정기간별 - 조회일자별 엑셀 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayPeriodExcelList(TableDayPeriodVO tableDayPeriodVO, SessionInfoVO sessionInfoVO) {
		tableDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		tableDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		tableDayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
		if(!StringUtil.getOrBlank(tableDayPeriodVO.getStoreCd()).equals("")) {
        	tableDayPeriodVO.setArrStoreCd(tableDayPeriodVO.getStoreCd().split(","));
        }

		return tableDayPeriodMapper.getTableDayPeriodExcelList(tableDayPeriodVO);
	}
}
