package kr.co.solbipos.sale.day.dayPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayPos.service.DayPosService;
import kr.co.solbipos.sale.day.dayPos.service.DayPosVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayPosServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(포스별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayPosService")
@Transactional
public class DayPosServiceImpl implements DayPosService {
    private final DayPosMapper dayPosMapper;

    public DayPosServiceImpl(DayPosMapper dayPosMapper) {
        this.dayPosMapper = dayPosMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayPosList(DayPosVO dayPosVO, SessionInfoVO sessionInfoVO) {

        dayPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPosVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayPosVO.getStoreCds().split(",");
        dayPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayPosVO.setPivotPayCol(pivotPayCol);
        dayPosVO.setArrPayCol(payCol.split(","));

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = dayPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        dayPosVO.setPivotMpayCol(pivotMpayCol);
        dayPosVO.setArrMpayCol(mpayCol.split(","));

        String[] arrDlvrInFgCol = dayPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                dayPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (dayPosVO.getStoreHqBrandCd() == "" || dayPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayPosVO.getUserBrands().split(",");
                dayPosVO.setUserBrandList(userBrandList);
            }
        }

        return dayPosMapper.getDayPosList(dayPosVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayPosExcelList(DayPosVO dayPosVO, SessionInfoVO sessionInfoVO) {

        dayPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPosVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayPosVO.getStoreCds().split(",");
        dayPosVO.setStoreCdList(storeCds);

        return dayPosMapper.getDayPosExcelList(dayPosVO);
    }
}