package kr.co.solbipos.sale.pay.storePayDay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.storePayDay.service.StorePayDayService;
import kr.co.solbipos.sale.pay.storePayDay.service.StorePayDayVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StorePayDayServiceImpl.java
 * @Description : 맘스터치 > 결제수단매출 > 매장-일별결제수단매출
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
@Service("storePayDayService")
@Transactional
public class StorePayDayServiceImpl implements StorePayDayService {
    private final StorePayDayMapper storePayDayMapper;

    public StorePayDayServiceImpl(StorePayDayMapper storePayDayMapper) {
        this.storePayDayMapper = storePayDayMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayDayList(StorePayDayVO storePayDayVO, SessionInfoVO sessionInfoVO) {
        storePayDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayDayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storePayDayVO.getStoreCds().split(",");
        storePayDayVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayDayVO.getStoreHqBrandCd() == "" || storePayDayVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayDayVO.getUserBrands().split(",");
                storePayDayVO.setUserBrandList(userBrandList);
            }
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayDayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        storePayDayVO.setPivotPayCol(pivotPayCol);
        storePayDayVO.setArrPayCol(payCol.split(","));

        return storePayDayMapper.getStorePayDayList(storePayDayVO);
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayDayExcelList(StorePayDayVO storePayDayVO, SessionInfoVO sessionInfoVO) {
        storePayDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayDayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storePayDayVO.getStoreCds().split(",");
        storePayDayVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayDayVO.getStoreHqBrandCd() == "" || storePayDayVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayDayVO.getUserBrands().split(",");
                storePayDayVO.setUserBrandList(userBrandList);
            }
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayDayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        storePayDayVO.setPivotPayCol(pivotPayCol);
        storePayDayVO.setArrPayCol(payCol.split(","));

        return storePayDayMapper.getStorePayDayExcelList(storePayDayVO);
    }

}