package kr.co.solbipos.sale.day.dayTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayTime.service.DayTimeService;
import kr.co.solbipos.sale.day.dayTime.service.DayTimeVO;
import kr.co.solbipos.sale.day.dayTime.service.impl.DayTimeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayTimeServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayTimeService")
@Transactional
public class DayTimeServiceImpl implements DayTimeService {
    private final DayTimeMapper dayTimeMapper;

    public DayTimeServiceImpl(DayTimeMapper dayTimeMapper) {
        this.dayTimeMapper = dayTimeMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayTimeList(DayTimeVO dayTimeVO, SessionInfoVO sessionInfoVO) {

        dayTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayTimeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayTimeVO.getStoreCds().split(",");
        dayTimeVO.setStoreCdList(storeCds);

        if(dayTimeVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(dayTimeVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(dayTimeVO.getEndTime());

            String timeCol = "";

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                timeCol += Integer.toString(i);
                if(i != iSaleDateEnd){
                    timeCol += ",";
                }
            }

            String[] arrTimeCol = timeCol.split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    dayTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(dayTimeVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = dayTimeVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    dayTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
//            List<DefaultMap<String>> timeSlotColList = dayMapper.getTimeSlotList(dayVO);
//            for(int i = 0; i < timeSlotColList.size(); i++) {
//                sQuery1 += ", NVL(SUM(tssh.REAL_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS REAL_SALE_AMT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//                sQuery1 += ", NVL(SUM(tssh.SALE_CNT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS SALE_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//                sQuery1 += ", NVL(SUM(tssh.TOT_GUEST_CNT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS TOT_GUEST_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//
//                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
//            }
        }

        return dayTimeMapper.getDayTimeList(dayTimeVO);
    }

}