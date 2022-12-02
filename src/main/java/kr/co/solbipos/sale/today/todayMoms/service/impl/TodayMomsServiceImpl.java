package kr.co.solbipos.sale.today.todayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsService;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsVO;
import kr.co.solbipos.sale.today.todayMoms.service.impl.TodayMomsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TodayMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 당일 매출 현황
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
@Service("todayMomsService")
@Transactional
public class TodayMomsServiceImpl implements TodayMomsService {
    private final TodayMomsMapper todayMomsMapper;

    public TodayMomsServiceImpl(TodayMomsMapper todayMomsMapper) {
        this.todayMomsMapper = todayMomsMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTodayMomsList(TodayMomsVO todayMomsVO, SessionInfoVO sessionInfoVO) {

        todayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            todayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = todayMomsVO.getStoreCds().split(",");
        todayMomsVO.setStoreCdList(storeCds);


        /** PAY_CD = 02 현금,현금영수증 분리 */
        // 결제수단 array 값 세팅
//        todayDtlVO.setArrPayCol(todayDtlVO.getPayCol().split(","));
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = todayMomsVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        todayMomsVO.setPivotPayCol(pivotPayCol);
        todayMomsVO.setArrPayCol(payCol.split(","));


        // 할인구분 array 값 세팅
        todayMomsVO.setArrDcCol(todayMomsVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = todayMomsVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        todayMomsVO.setPivotDcCol(pivotDcCol);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (todayMomsVO.getStoreHqBrandCd() == "" || todayMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = todayMomsVO.getUserBrands().split(",");
                todayMomsVO.setUserBrandList(userBrandList);
            }
        }

        return todayMomsMapper.getTodayMomsList(todayMomsVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTodayMomsExcelList(TodayMomsVO todayMomsVO, SessionInfoVO sessionInfoVO) {

        todayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            todayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = todayMomsVO.getStoreCds().split(",");
        todayMomsVO.setStoreCdList(storeCds);


        return todayMomsMapper.getTodayMomsExcelList(todayMomsVO);
    }

}