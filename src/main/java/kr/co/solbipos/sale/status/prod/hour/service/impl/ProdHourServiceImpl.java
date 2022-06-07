package kr.co.solbipos.sale.status.prod.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourService;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdHourService")
public class ProdHourServiceImpl implements ProdHourService {
    private final ProdHourMapper prodHourMapper;
    private final MessageService messageService;
    private final DayMapper dayMapper;

    @Autowired
    public ProdHourServiceImpl(ProdHourMapper prodHourMapper, MessageService messageService, DayMapper dayMapper) {
    	this.prodHourMapper = prodHourMapper;
        this.messageService = messageService;
        this.dayMapper = dayMapper;
    }

    /** 시간대별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO) {

        prodHourVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	prodHourVO.setStoreCd(sessionInfoVO.getStoreCd());
        prodHourVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prodHourVO.getStoreCd()).equals("")) {
        	prodHourVO.setArrStoreCd(prodHourVO.getStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        // 매출 시간대 설정
        String isSaleHour = "";

        if(prodHourVO.getOptionFg().equals("time")) { // 시간대

            // 시간대 '전체' 선택 시
            if (prodHourVO.getSaleTime().equals("")) {
                for (int i = 0; i < 24; i++) {
                    // 10보다 작은건 01~09
                    isSaleHour = i < 10 ? "0" + String.valueOf(i) : String.valueOf(i);

                    sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_QTY) ELSE 0 END) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                    sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_AMT - tsdp.TOT_DC_AMT) ELSE 0 END) AS TOT_SALE_AMT_T" + isSaleHour + "\n";

                    sQuery2 += ", NVL(SUM(TOT_SALE_QTY_T" + isSaleHour + ") , 0) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                    sQuery2 += ", NVL(SUM(TOT_SALE_AMT_T" + isSaleHour + ") , 0) AS TOT_SALE_AMT_T" + isSaleHour + "\n";
                }
            } else {

                isSaleHour = prodHourVO.getSaleTime();

                sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_QTY) ELSE 0 END) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_AMT - tsdp.TOT_DC_AMT) ELSE 0 END) AS TOT_SALE_AMT_T" + isSaleHour + "\n";

                sQuery2 += ", NVL(SUM(TOT_SALE_QTY_T" + isSaleHour + ") , 0) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                sQuery2 += ", NVL(SUM(TOT_SALE_AMT_T" + isSaleHour + ") , 0) AS TOT_SALE_AMT_T" + isSaleHour + "\n";
            }
        } else if(prodHourVO.getOptionFg().equals("timeSlot")){
            DayVO dayVO = new DayVO();
            dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
            prodHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            List<DefaultMap<String>> timeSlotColList = dayMapper.getTimeSlotList(dayVO);
            for(int i = 0; i < timeSlotColList.size(); i++) {

                sQuery1 += ", SUM(CASE WHEN TIME_SLOT = '" + timeSlotColList.get(i).getStr("value").replace("~", "") + "' THEN TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
                sQuery1 += ", SUM(CASE WHEN TIME_SLOT = '" + timeSlotColList.get(i).getStr("value").replace("~", "") + "' THEN TOT_SALE_AMT - TOT_DC_AMT ELSE 0 END) AS TOT_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
            }
        }

        prodHourVO.setsQuery1(sQuery1);
        prodHourVO.setsQuery2(sQuery2);

        return prodHourMapper.getProdHourList(prodHourVO);
    }

    /** 시간대별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdHourExcelList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO) {

        prodHourVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodHourVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodHourVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (!StringUtil.getOrBlank(prodHourVO.getStoreCd()).equals("")) {
            prodHourVO.setArrStoreCd(prodHourVO.getStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        // 매출 시간대 설정
        String isSaleHour = "";

        if(prodHourVO.getOptionFg().equals("time")) { // 시간대

            // 시간대 '전체' 선택 시
            if (prodHourVO.getSaleTime().equals("")) {
                for (int i = 0; i < 24; i++) {
                    // 10보다 작은건 01~09
                    isSaleHour = i < 10 ? "0" + String.valueOf(i) : String.valueOf(i);

                    sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_QTY) ELSE 0 END) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                    sQuery1 += ", (CASE WHEN tsdp.SALE_HOUR = '" + isSaleHour + "' THEN SUM(tsdp.TOT_SALE_AMT - tsdp.TOT_DC_AMT) ELSE 0 END) AS TOT_SALE_AMT_T" + isSaleHour + "\n";

                    sQuery2 += ", NVL(SUM(TOT_SALE_QTY_T" + isSaleHour + ") , 0) AS TOT_SALE_QTY_T" + isSaleHour + "\n";
                    sQuery2 += ", NVL(SUM(TOT_SALE_AMT_T" + isSaleHour + ") , 0) AS TOT_SALE_AMT_T" + isSaleHour + "\n";
                }
            }
        } else if(prodHourVO.getOptionFg().equals("timeSlot")){
            DayVO dayVO = new DayVO();
            dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
            prodHourVO.setStoreCd(sessionInfoVO.getStoreCd());
            List<DefaultMap<String>> timeSlotColList = dayMapper.getTimeSlotList(dayVO);
            for(int i = 0; i < timeSlotColList.size(); i++) {

                sQuery1 += ", SUM(CASE WHEN TIME_SLOT = '" + timeSlotColList.get(i).getStr("value").replace("~", "") + "' THEN TOT_SALE_QTY ELSE 0 END) AS TOT_SALE_QTY_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
                sQuery1 += ", SUM(CASE WHEN TIME_SLOT = '" + timeSlotColList.get(i).getStr("value").replace("~", "") + "' THEN TOT_SALE_AMT - TOT_DC_AMT ELSE 0 END) AS TOT_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
            }
        }

        prodHourVO.setsQuery1(sQuery1);
        prodHourVO.setsQuery2(sQuery2);

        return prodHourMapper.getProdHourExcelList(prodHourVO);
    }
}
