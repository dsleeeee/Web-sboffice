package kr.co.solbipos.sale.today.todayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.today.todayBenson.service.TodayBensonService;
import kr.co.solbipos.sale.today.todayBenson.service.TodayBensonVO;
import kr.co.solbipos.sale.today.todayBenson.service.impl.TodayBensonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TodayBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 당일매출현황(영수증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("todayBensonService")
@Transactional
public class TodayBensonServiceImpl implements TodayBensonService {
    private final TodayBensonMapper todayBensonMapper;
    private final PopupMapper popupMapper;

    public TodayBensonServiceImpl(TodayBensonMapper todayBensonMapper, PopupMapper popupMapper) {
        this.todayBensonMapper = todayBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 당일매출현황(영수증) 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getTodayBensonList(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO) {

        todayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            todayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(todayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(todayBensonVO.getStoreCds(), 3900));
            todayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        /** PAY_CD = 02 현금,현금영수증 분리 */
        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = todayBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        todayBensonVO.setPivotPayCol(pivotPayCol);
        todayBensonVO.setArrPayCol(payCol.split(","));


        // 할인구분 array 값 세팅
        todayBensonVO.setArrDcCol(todayBensonVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = todayBensonVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        todayBensonVO.setPivotDcCol(pivotDcCol);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (todayBensonVO.getStoreHqBrandCd() == "" || todayBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = todayBensonVO.getUserBrands().split(",");
                todayBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = todayBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        todayBensonVO.setPivotMpayCol(pivotMpayCol);
        todayBensonVO.setArrMpayCol(mpayCol.split(","));

        return todayBensonMapper.getTodayBensonList(todayBensonVO);
    }

    /** 당일매출현황(영수증) 엑셀 다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTodayBensonExcelList(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO) {

        todayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            todayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(todayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(todayBensonVO.getStoreCds(), 3900));
            todayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }


        /** PAY_CD = 02 현금,현금영수증 분리 */
        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = todayBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        todayBensonVO.setPivotPayCol(pivotPayCol);
        todayBensonVO.setArrPayCol(payCol.split(","));


        // 할인구분 array 값 세팅
        todayBensonVO.setArrDcCol(todayBensonVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = todayBensonVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        todayBensonVO.setPivotDcCol(pivotDcCol);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (todayBensonVO.getStoreHqBrandCd() == "" || todayBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = todayBensonVO.getUserBrands().split(",");
                todayBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = todayBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        todayBensonVO.setPivotMpayCol(pivotMpayCol);
        todayBensonVO.setArrMpayCol(mpayCol.split(","));

        return todayBensonMapper.getTodayBensonExcelList(todayBensonVO);
    }

    /** 영수증조회팝업 - 영수증 출력데이터 조회 */
    @Override
    public DefaultMap<String> getBillPrintData(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO) {
        todayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return todayBensonMapper.getBillPrintData(todayBensonVO);
    }

}
