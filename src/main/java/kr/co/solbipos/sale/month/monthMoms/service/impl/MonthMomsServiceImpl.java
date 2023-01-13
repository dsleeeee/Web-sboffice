package kr.co.solbipos.sale.month.monthMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsService;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsVO;
import kr.co.solbipos.sale.month.monthMoms.service.impl.MonthMomsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthMomsService")
@Transactional
public class MonthMomsServiceImpl implements MonthMomsService {
    private final MonthMomsMapper monthMomsMapper;

    public MonthMomsServiceImpl(MonthMomsMapper monthMomsMapper) {
        this.monthMomsMapper = monthMomsMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthMomsList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO) {

        monthMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthMomsVO.getStoreCds().split(",");
        monthMomsVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = monthMomsVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        monthMomsVO.setPivotPayCol(pivotPayCol);
        monthMomsVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = monthMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                monthMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (monthMomsVO.getStoreHqBrandCd() == "" || monthMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthMomsVO.getUserBrands().split(",");
                monthMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = monthMomsVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        monthMomsVO.setPivotMpayCol(pivotMpayCol);
        monthMomsVO.setArrMpayCol(mpayCol.split(","));

        return monthMomsMapper.getMonthMomsList(monthMomsVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthMomsExcelList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO) {

        monthMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthMomsVO.getStoreCds().split(",");
        monthMomsVO.setStoreCdList(storeCds);

        return monthMomsMapper.getMonthMomsExcelList(monthMomsVO);
    }
}