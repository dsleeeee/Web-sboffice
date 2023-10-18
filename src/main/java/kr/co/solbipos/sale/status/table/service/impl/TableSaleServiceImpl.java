package kr.co.solbipos.sale.status.table.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.table.service.TableSaleService;
import kr.co.solbipos.sale.status.table.service.TableSaleVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.common.service.popup.impl.PopupMapper;

@Service("tableSaleService")
public class TableSaleServiceImpl implements TableSaleService {

    private final TableSaleMapper tableSaleMapper;
    private final MessageService messageService;
    private final PopupMapper popupMapper;

    public TableSaleServiceImpl(TableSaleMapper tableSaleMapper, MessageService messageService, PopupMapper popupMapper) {
        super();
        this.tableSaleMapper = tableSaleMapper;
        this.messageService = messageService;
        this.popupMapper = popupMapper;
    }

    /** 테이블별 매출 - 일자별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableDayList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getTableDayList(tableSaleVO);
    }

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableDayExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getTableDayExcelList(tableSaleVO);
    }

    /** 테이블별 매출 - 일자별 테이블 선택 조회조건 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreTableList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getStoreTableList(tableSaleVO);
    }

    /** 테이블별 매출 - 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableDayOfWeekList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getTableDayOfWeekList(tableSaleVO);
    }

    /** 테이블별 매출 - 월별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableMonthList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getTableMonthList(tableSaleVO);
    }

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableMonthExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }

        return tableSaleMapper.getTableMonthExcelList(tableSaleVO);
    }

    /** 설정기간별 - 조회일자별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableDayPeriodList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {
        tableSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tableSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getStoreCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return tableSaleMapper.getTableDayPeriodList(tableSaleVO);
    }

    /** 설정기간별 - 조회일자별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableDayPeriodExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {
        tableSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tableSaleVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getStoreCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return tableSaleMapper.getTableDayPeriodExcelList(tableSaleVO);
    }
}
