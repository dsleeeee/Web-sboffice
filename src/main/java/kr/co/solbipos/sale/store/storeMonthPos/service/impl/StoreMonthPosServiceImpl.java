package kr.co.solbipos.sale.store.storeMonthPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeMonthPos.service.StoreMonthPosService;
import kr.co.solbipos.sale.store.storeMonthPos.service.StoreMonthPosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreMonthPosServiceImpl.java
 * @Description : 맘스터치 > 매장분석 > 매장-월별매출현황(포스별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.14  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("storeMonthPosService")
public class StoreMonthPosServiceImpl implements StoreMonthPosService {
    private final StoreMonthPosMapper storeMonthPosMapper;

    @Autowired
    public StoreMonthPosServiceImpl(StoreMonthPosMapper storeMonthPosMapper) {
        this.storeMonthPosMapper = storeMonthPosMapper;
    }

    /** 읠별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthList(StoreMonthPosVO storeMonthPosVO, SessionInfoVO sessionInfoVO) {

        storeMonthPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthPosVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        String[] storeCds = storeMonthPosVO.getStoreCds().split(",");
        storeMonthPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthPosVO.setPivotPayCol(pivotPayCol);
        storeMonthPosVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthPosVO.getStoreHqBrandCd() == "" || storeMonthPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthPosVO.getUserBrands().split(",");
                storeMonthPosVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthPosVO.setPivotMpayCol(pivotMpayCol);
        storeMonthPosVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthPosMapper.getMonthList(storeMonthPosVO);
    }

    /** 읠별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthExcelList(StoreMonthPosVO storeMonthPosVO, SessionInfoVO sessionInfoVO) {

        storeMonthPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthPosVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        String[] storeCds = storeMonthPosVO.getStoreCds().split(",");
        storeMonthPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthPosVO.setPivotPayCol(pivotPayCol);
        storeMonthPosVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthPosVO.getStoreHqBrandCd() == "" || storeMonthPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthPosVO.getUserBrands().split(",");
                storeMonthPosVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthPosVO.setPivotMpayCol(pivotMpayCol);
        storeMonthPosVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthPosMapper.getMonthExcelList(storeMonthPosVO);
    }
}