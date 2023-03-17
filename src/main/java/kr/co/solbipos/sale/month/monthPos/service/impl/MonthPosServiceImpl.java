package kr.co.solbipos.sale.month.monthPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthPos.service.MonthPosService;
import kr.co.solbipos.sale.month.monthPos.service.MonthPosVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthPosServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthPosService")
@Transactional
public class MonthPosServiceImpl implements MonthPosService {
    private final MonthPosMapper monthPosMapper;

    public MonthPosServiceImpl(MonthPosMapper monthPosMapper) {
        this.monthPosMapper = monthPosMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthPosList(MonthPosVO monthPosVO, SessionInfoVO sessionInfoVO) {

        monthPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthPosVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthPosVO.getStoreCds().split(",");
        monthPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = monthPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        monthPosVO.setPivotPayCol(pivotPayCol);
        monthPosVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = monthPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                monthPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (monthPosVO.getStoreHqBrandCd() == "" || monthPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthPosVO.getUserBrands().split(",");
                monthPosVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = monthPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        monthPosVO.setPivotMpayCol(pivotMpayCol);
        monthPosVO.setArrMpayCol(mpayCol.split(","));

        return monthPosMapper.getMonthPosList(monthPosVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthPosExcelList(MonthPosVO monthPosVO, SessionInfoVO sessionInfoVO) {

        monthPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthPosVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthPosVO.getStoreCds().split(",");
        monthPosVO.setStoreCdList(storeCds);

        return monthPosMapper.getMonthPosExcelList(monthPosVO);
    }
}