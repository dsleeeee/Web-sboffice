package kr.co.solbipos.sale.time.timeProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdService;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeProdServiceImpl.java
 * @Description : 맘스터치 > 시간대별매출 > 상품별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeProdService")
@Transactional
public class TimeProdServiceImpl implements TimeProdService {
    private final TimeProdMapper timeProdMapper;

    public TimeProdServiceImpl(TimeProdMapper timeProdMapper) {
        this.timeProdMapper = timeProdMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdList(TimeProdVO timeProdVO, SessionInfoVO sessionInfoVO) {

        timeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeProdVO.getStoreCds().split(",");
        timeProdVO.setStoreCdList(storeCds);

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

        timeProdVO.setsQuery1(sQuery1);
        timeProdVO.setsQuery2(sQuery2);

        return timeProdMapper.getTimeProdList(timeProdVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdExcelList(TimeProdVO timeProdVO, SessionInfoVO sessionInfoVO) {

        timeProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeProdVO.getStoreCds().split(",");
        timeProdVO.setStoreCdList(storeCds);

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

        timeProdVO.setsQuery1(sQuery1);
        timeProdVO.setsQuery2(sQuery2);

        return timeProdMapper.getTimeProdExcelList(timeProdVO);
    }
}