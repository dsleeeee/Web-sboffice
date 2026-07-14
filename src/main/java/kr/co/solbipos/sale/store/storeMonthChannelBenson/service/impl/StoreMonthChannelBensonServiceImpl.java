package kr.co.solbipos.sale.store.storeMonthChannelBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeMonthChannelBenson.service.StoreMonthChannelBensonService;
import kr.co.solbipos.sale.store.storeMonthChannelBenson.service.StoreMonthChannelBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreMonthChannelBensonServiceImpl.java
 * @Description : 벤슨 > 매장분석 > 매장-월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("storeMonthChannelBensonService")
@Transactional
public class StoreMonthChannelBensonServiceImpl implements StoreMonthChannelBensonService {

    private final StoreMonthChannelBensonMapper storeMonthChannelBensonMapper;
    private final PopupMapper popupMapper;

    public StoreMonthChannelBensonServiceImpl(StoreMonthChannelBensonMapper storeMonthChannelBensonMapper, PopupMapper popupMapper) {
        this.storeMonthChannelBensonMapper = storeMonthChannelBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 매장-월별매출현황(채널별) 리스트 조회
     * @param storeMonthChannelBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreMonthChannelBensonList(StoreMonthChannelBensonVO storeMonthChannelBensonVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMonthChannelBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMonthChannelBensonVO.getStoreCds(), 3900));
            storeMonthChannelBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelBensonVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelBensonVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelBensonVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelBensonVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthChannelBensonVO.getStoreHqBrandCd() == "" || storeMonthChannelBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthChannelBensonVO.getUserBrands().split(",");
                storeMonthChannelBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelBensonVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelBensonVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelBensonMapper.getStoreMonthChannelBensonList(storeMonthChannelBensonVO);
    }

    /**
     * 매장-일별매출현황(채널별) 엑셀 다운로드 조회
     * @param storeMonthChannelBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreMonthChannelBensonExcelList(StoreMonthChannelBensonVO storeMonthChannelBensonVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMonthChannelBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMonthChannelBensonVO.getStoreCds(), 3900));
            storeMonthChannelBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelBensonVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelBensonVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelBensonVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelBensonVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeMonthChannelBensonVO.getStoreHqBrandCd() == "" || storeMonthChannelBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeMonthChannelBensonVO.getUserBrands().split(",");
                storeMonthChannelBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelBensonVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelBensonVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelBensonMapper.getStoreMonthChannelBensonExcelList(storeMonthChannelBensonVO);
    }
}
