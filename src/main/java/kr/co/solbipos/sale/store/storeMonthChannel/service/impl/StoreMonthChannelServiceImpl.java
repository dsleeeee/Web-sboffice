package kr.co.solbipos.sale.store.storeMonthChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeMonthChannel.service.StoreMonthChannelService;
import kr.co.solbipos.sale.store.storeMonthChannel.service.StoreMonthChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreMonthChannelServiceImpl.java
 * @Description : 맘스터치 > 매장분석 > 매장-월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.21  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2022.11.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("storeMonthChannelService")
public class StoreMonthChannelServiceImpl implements StoreMonthChannelService {
    private final StoreMonthChannelMapper storeMonthChannelMapper;

    @Autowired
    public StoreMonthChannelServiceImpl(StoreMonthChannelMapper storeMonthChannelMapper) {
        this.storeMonthChannelMapper = storeMonthChannelMapper;
    }

    /** 읠별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthList(StoreMonthChannelVO storeMonthChannelVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        String[] storeCds = storeMonthChannelVO.getStoreCds().split(",");
        storeMonthChannelVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthChannelVO.getStoreHqBrandCd() == "" || storeMonthChannelVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthChannelVO.getUserBrands().split(",");
                storeMonthChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelMapper.getMonthList(storeMonthChannelVO);
    }

    /** 읠별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMonthExcelList(StoreMonthChannelVO storeMonthChannelVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        String[] storeCds = storeMonthChannelVO.getStoreCds().split(",");
        storeMonthChannelVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthChannelVO.getStoreHqBrandCd() == "" || storeMonthChannelVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthChannelVO.getUserBrands().split(",");
                storeMonthChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelMapper.getMonthExcelList(storeMonthChannelVO);
    }
}