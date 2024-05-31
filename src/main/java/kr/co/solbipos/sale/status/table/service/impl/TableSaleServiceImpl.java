package kr.co.solbipos.sale.status.table.service.impl;

import java.util.ArrayList;
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
    public List<DefaultMap<Object>> getTableDayList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

//        /*if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
//            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
//        }*/

        int listScale = tableSaleVO.getListScale();
        int curr = tableSaleVO.getCurr();
        int startNum = (curr -1) * listScale +1;
        int endNum = curr * listScale;
        tableSaleVO.setStartNum(startNum);
        tableSaleVO.setEndNum(endNum);

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 날짜 추려내기
        List<DefaultMap<Object>> result2 =  tableSaleMapper.getSearchSaleDay(tableSaleVO);

        if(result2 != null) {
            tableSaleVO.setStartDate((String) result2.get(0).get("lVal"));
            tableSaleVO.setEndDate((String) result2.get(0).get("fVal"));
        }

        List<DefaultMap<Object>> result =  tableSaleMapper.getTableDayList(tableSaleVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();


        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storeTblCd").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_CNT", result.get(i).get("saleCnt"));
                map.put("TOT_GUEST_CNT", result.get(i).get("guestCnt"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 날짜,요일
                map.put("SALE_DATE", result.get(i).get("saleDate"));
                map.put("SALE_DAY", result.get(i).get("saleDay"));
                // 매장별 정보
                map.put(result.get(i).get("storeTblCd") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put(result.get(i).get("storeTblCd") + "_SALE_CNT", result.get(i).get("saleCnt"));
                map.put(result.get(i).get("storeTblCd") + "_GUEST_CNT", result.get(i).get("guestCnt"));
            }
        }

        return selectList;
    }

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTableDayExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }*/

        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        List<DefaultMap<Object>> result =  tableSaleMapper.getTableDayList(tableSaleVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();

        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storeTblCd").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_CNT", result.get(i).get("saleCnt"));
                map.put("TOT_GUEST_CNT", result.get(i).get("guestCnt"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 날짜,요일
                map.put("SALE_DATE", result.get(i).get("saleDate"));
                map.put("SALE_DAY", result.get(i).get("saleDay"));
                // 매장별 정보
                map.put(result.get(i).get("storeTblCd") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put(result.get(i).get("storeTblCd") + "_SALE_CNT", result.get(i).get("saleCnt"));
                map.put(result.get(i).get("storeTblCd") + "_GUEST_CNT", result.get(i).get("guestCnt"));
            }
        }

        return selectList;
    }

    /** 테이블별 매출 - 일자별 테이블 선택 조회조건 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreTableList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getStoreCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return tableSaleMapper.getStoreTableList(tableSaleVO);
    }

    /** 테이블별 매출 - 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTableDayOfWeekList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }*/
        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        List<DefaultMap<Object>> result =  tableSaleMapper.getTableDayOfWeekList(tableSaleVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();


        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storeTblCd").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_CNT", result.get(i).get("saleCnt"));
                map.put("TOT_GUEST_CNT", result.get(i).get("guestCnt"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 날짜,요일
                map.put("YOIL", result.get(i).get("yoil"));
                // 매장별 정보
                map.put(result.get(i).get("storeTblCd") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put(result.get(i).get("storeTblCd") + "_SALE_CNT", result.get(i).get("saleCnt"));
                map.put(result.get(i).get("storeTblCd") + "_GUEST_CNT", result.get(i).get("guestCnt"));
            }
        }

        return selectList;
    }

    /** 테이블별 매출 - 월별 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTableMonthList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }*/

        int listScale = tableSaleVO.getListScale();
        int curr = tableSaleVO.getCurr();
        int startNum = (curr -1) * listScale +1;
        int endNum = curr * listScale;
        tableSaleVO.setStartNum(startNum);
        tableSaleVO.setEndNum(endNum);

        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 날짜 추려내기
        List<DefaultMap<Object>> result2 =  tableSaleMapper.getSearchSaleMonth(tableSaleVO);

        if(result2 != null) {
            tableSaleVO.setStartDate((String) result2.get(0).get("lVal"));
            tableSaleVO.setEndDate((String) result2.get(0).get("fVal"));
        }

        List<DefaultMap<Object>> result =  tableSaleMapper.getTableMonthList(tableSaleVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();

        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storeTblCd").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_CNT", result.get(i).get("saleCnt"));
                map.put("TOT_GUEST_CNT", result.get(i).get("guestCnt"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 날짜,요일
                map.put("SALE_YM", result.get(i).get("saleYm"));
                // 매장별 정보
                map.put(result.get(i).get("storeTblCd") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put(result.get(i).get("storeTblCd") + "_SALE_CNT", result.get(i).get("saleCnt"));
                map.put(result.get(i).get("storeTblCd") + "_GUEST_CNT", result.get(i).get("guestCnt"));
            }
        }

        return selectList;
    }

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTableMonthExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {

        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /*if(!StringUtil.getOrBlank(tableSaleVO.getStoreCd()).equals("")) {
            tableSaleVO.setArrStoreCd(tableSaleVO.getStoreCd().split(","));
        }
*/
        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        List<DefaultMap<Object>> result =  tableSaleMapper.getTableMonthList(tableSaleVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();

        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storeTblCd").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_CNT", result.get(i).get("saleCnt"));
                map.put("TOT_GUEST_CNT", result.get(i).get("guestCnt"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 날짜,요일
                map.put("SALE_YM", result.get(i).get("saleYm"));
                // 매장별 정보
                map.put(result.get(i).get("storeTblCd") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put(result.get(i).get("storeTblCd") + "_SALE_CNT", result.get(i).get("saleCnt"));
                map.put(result.get(i).get("storeTblCd") + "_GUEST_CNT", result.get(i).get("guestCnt"));
            }
        }

        return selectList;
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

    /** 일자별 - 리스트 총 수량 조회 */
    @Override
    public List<DefaultMap<Object>> getDayListCnt(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {
        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return tableSaleMapper.getDayListCnt(tableSaleVO);
    }

    /** 월별 - 리스트 총 수량 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthListCnt(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO) {
        tableSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(tableSaleVO.getTableCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(tableSaleVO.getTableCd(), 3900));
            tableSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return tableSaleMapper.getMonthListCnt(tableSaleVO);
    }
}
