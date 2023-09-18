package kr.co.solbipos.sale.month.monthPos2.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthPos2.service.MonthPos2Service;
import kr.co.solbipos.sale.month.monthPos2.service.MonthPos2VO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthPos2ServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황(포스별-영수건수)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.07.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthPos2Service")
@Transactional
public class MonthPos2ServiceImpl implements MonthPos2Service {
    private final MonthPos2Mapper monthPos2Mapper;
    private final PopupMapper popupMapper;

    public MonthPos2ServiceImpl(MonthPos2Mapper monthPos2Mapper, PopupMapper popupMapper) {
        this.monthPos2Mapper = monthPos2Mapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthPos2List(MonthPos2VO monthPos2VO, SessionInfoVO sessionInfoVO) {

        monthPos2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthPos2VO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthPos2VO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthPos2VO.getStoreCds(), 3900));
            monthPos2VO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = monthPos2VO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        monthPos2VO.setPivotPayCol(pivotPayCol);
        monthPos2VO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = monthPos2VO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                monthPos2VO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (monthPos2VO.getStoreHqBrandCd() == "" || monthPos2VO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthPos2VO.getUserBrands().split(",");
                monthPos2VO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = monthPos2VO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        monthPos2VO.setPivotMpayCol(pivotMpayCol);
        monthPos2VO.setArrMpayCol(mpayCol.split(","));

        return monthPos2Mapper.getMonthPos2List(monthPos2VO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthPos2ExcelList(MonthPos2VO monthPos2VO, SessionInfoVO sessionInfoVO) {

        monthPos2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthPos2VO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthPos2VO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthPos2VO.getStoreCds(), 3900));
            monthPos2VO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return monthPos2Mapper.getMonthPos2ExcelList(monthPos2VO);
    }
}