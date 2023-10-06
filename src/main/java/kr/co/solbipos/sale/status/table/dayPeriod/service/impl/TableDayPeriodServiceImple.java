package kr.co.solbipos.sale.status.table.dayPeriod.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
    private final PopupMapper popupMapper;
	private final MessageService messageService;

	public TableDayPeriodServiceImple(TableDayPeriodMapper tableDayPeriodMapper, PopupMapper popupMapper, MessageService messageService) {
		super();
		this.tableDayPeriodMapper = tableDayPeriodMapper;
		this.popupMapper = popupMapper;
		this.messageService = messageService;
	}

	/** 설정기간별 - 조회일자별 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getTableDayPeriodList(TableDayPeriodVO tableDayPeriodVO, SessionInfoVO sessionInfoVO) {
		tableDayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		tableDayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		tableDayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(tableDayPeriodVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableDayPeriodVO.getStoreCd(), 3900));
            tableDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
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
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableDayPeriodVO.getStoreCd(), 3900));
            tableDayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

		return tableDayPeriodMapper.getTableDayPeriodExcelList(tableDayPeriodVO);
	}
}
