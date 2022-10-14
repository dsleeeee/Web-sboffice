package kr.co.solbipos.sale.store.storeTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeTime.service.StoreTimeService;
import kr.co.solbipos.sale.store.storeTime.service.StoreTimeVO;
import kr.co.solbipos.sale.store.storeTime.service.impl.StoreTimeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreTimeServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 점포-시간대별 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeTimeService")
@Transactional
public class StoreTimeServiceImpl implements StoreTimeService {
    private final StoreTimeMapper storeTimeMapper;

    public StoreTimeServiceImpl(StoreTimeMapper storeTimeMapper) {
        this.storeTimeMapper = storeTimeMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreTimeList(StoreTimeVO storeTimeVO, SessionInfoVO sessionInfoVO) {

        storeTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeTimeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storeTimeVO.getStoreCds().split(",");
        storeTimeVO.setStoreCdList(storeCds);

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

        storeTimeVO.setsQuery1(sQuery1);
        storeTimeVO.setsQuery2(sQuery2);

        
        return storeTimeMapper.getStoreTimeList(storeTimeVO);
    }
    
    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreTimeExcelList(StoreTimeVO storeTimeVO, SessionInfoVO sessionInfoVO) {

        storeTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeTimeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storeTimeVO.getStoreCds().split(",");
        storeTimeVO.setStoreCdList(storeCds);
        
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

        storeTimeVO.setsQuery1(sQuery1);
        storeTimeVO.setsQuery2(sQuery2);

        
        return storeTimeMapper.getStoreTimeExcelList(storeTimeVO);
    }

}