package kr.co.solbipos.sale.time.timeDay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.time.timeDay.service.TimeDayService;
import kr.co.solbipos.sale.time.timeDay.service.TimeDayVO;
import kr.co.solbipos.sale.time.timeDay.service.impl.TimeDayMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeDayServiceImpl.java
 * @Description : 맘스터치 > 시간대별매출 > 일자별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeDayService")
@Transactional
public class TimeDayServiceImpl implements TimeDayService {
    private final TimeDayMapper timeDayMapper;

    public TimeDayServiceImpl(TimeDayMapper timeDayMapper) {
        this.timeDayMapper = timeDayMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeDayList(TimeDayVO timeDayVO, SessionInfoVO sessionInfoVO) {

        timeDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeDayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeDayVO.getStoreCds().split(",");
        timeDayVO.setStoreCdList(storeCds);

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        for(int i = 0; i <= 23; i++) {
            sQuery1 += ", NVL(SALE_CNT" + i + ", 0) AS SALE_CNT"  + i +  "\n";
            sQuery1 += ", NVL(SALE_AMT" + i + ", 0) AS SALE_AMT"  + i +  "\n";
            sQuery1 += ", ROUND(RATIO_TO_REPORT(SALE_AMT" + i + ") OVER(), 4) * 100 AS RATE"  + i +  "\n";

            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT"  + i +  "\n";
            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN TOT_SALE_AMT ELSE 0 END) AS SALE_AMT"  + i +  "\n";
        }

        timeDayVO.setsQuery1(sQuery1);
        timeDayVO.setsQuery2(sQuery2);

        return timeDayMapper.getTimeDayList(timeDayVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeDayExcelList(TimeDayVO timeDayVO, SessionInfoVO sessionInfoVO) {

        timeDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeDayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeDayVO.getStoreCds().split(",");
        timeDayVO.setStoreCdList(storeCds);
        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        for(int i = 0; i <= 23; i++) {
            sQuery1 += ", NVL(SALE_CNT" + i + ", 0) AS SALE_CNT"  + i +  "\n";
            sQuery1 += ", NVL(SALE_AMT" + i + ", 0) AS SALE_AMT"  + i +  "\n";
            sQuery1 += ", ROUND(RATIO_TO_REPORT(SALE_AMT" + i + ") OVER(), 4) * 100 AS RATE"  + i +  "\n";

            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT"  + i +  "\n";
            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN TOT_SALE_AMT ELSE 0 END) AS SALE_AMT"  + i +  "\n";
        }

        timeDayVO.setsQuery1(sQuery1);
        timeDayVO.setsQuery2(sQuery2);
        
        return timeDayMapper.getTimeDayExcelList(timeDayVO);
    }
}