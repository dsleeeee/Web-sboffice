package kr.co.solbipos.sale.day.dayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsService;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsVO;
import kr.co.solbipos.sale.day.dayMoms.service.impl.DayMomsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayMomsService")
@Transactional
public class DayMomsServiceImpl implements DayMomsService {
    private final DayMomsMapper dayMomsMapper;

    public DayMomsServiceImpl(DayMomsMapper dayMomsMapper) {
        this.dayMomsMapper = dayMomsMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayMomsList(DayMomsVO dayMomsVO, SessionInfoVO sessionInfoVO) {

        dayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayMomsVO.getStoreCds().split(",");
        dayMomsVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayMomsVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayMomsVO.setPivotPayCol(pivotPayCol);
        dayMomsVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = dayMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                dayMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (dayMomsVO.getStoreHqBrandCd() == "" || dayMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayMomsVO.getUserBrands().split(",");
                dayMomsVO.setUserBrandList(userBrandList);
            }
        }

        return dayMomsMapper.getDayMomsList(dayMomsVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayMomsExcelList(DayMomsVO dayMomsVO, SessionInfoVO sessionInfoVO) {

        dayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayMomsVO.getStoreCds().split(",");
        dayMomsVO.setStoreCdList(storeCds);

        return dayMomsMapper.getDayMomsExcelList(dayMomsVO);
    }
}