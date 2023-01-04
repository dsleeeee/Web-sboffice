package kr.co.solbipos.sale.pay.storePayMonth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.storePayMonth.service.StorePayMonthService;
import kr.co.solbipos.sale.pay.storePayMonth.service.StorePayMonthVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StorePayMonthServiceImpl.java
 * @Description : 맘스터치 > 결제수단매출 > 매장-월별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storePayMonthService")
@Transactional
public class StorePayMonthServiceImpl implements StorePayMonthService {
    private final StorePayMonthMapper storePayMonthMapper;

    public StorePayMonthServiceImpl(StorePayMonthMapper storePayMonthMapper) {
        this.storePayMonthMapper = storePayMonthMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayMonthList(StorePayMonthVO storePayMonthVO, SessionInfoVO sessionInfoVO) {

        storePayMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayMonthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storePayMonthVO.getStoreCds().split(",");
        storePayMonthVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayMonthVO.getStoreHqBrandCd() == "" || storePayMonthVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayMonthVO.getUserBrands().split(",");
                storePayMonthVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        storePayMonthVO.setArrPayCol(storePayMonthVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayMonthVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        storePayMonthVO.setPivotPayCol(pivotPayCol);

        return storePayMonthMapper.getStorePayMonthList(storePayMonthVO);
    }

    /** 엑셀조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayMonthExcelList(StorePayMonthVO storePayMonthVO, SessionInfoVO sessionInfoVO) {

        storePayMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayMonthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storePayMonthVO.getStoreCds().split(",");
        storePayMonthVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayMonthVO.getStoreHqBrandCd() == "" || storePayMonthVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayMonthVO.getUserBrands().split(",");
                storePayMonthVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        storePayMonthVO.setArrPayCol(storePayMonthVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayMonthVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        storePayMonthVO.setPivotPayCol(pivotPayCol);

        return storePayMonthMapper.getStorePayMonthExcelList(storePayMonthVO);
    }

}