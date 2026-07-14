package kr.co.solbipos.sale.store.storeDayChannelBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeDayChannelBenson.service.StoreDayChannelBensonService;
import kr.co.solbipos.sale.store.storeDayChannelBenson.service.StoreDayChannelBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreDayChannelBensonServiceImpl.java
 * @Description : 벤슨 > 매장분석 > 매장-일별매출현황(채널별)
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
@Service("storeDayChannelBensonService")
@Transactional
public class StoreDayChannelBensonServiceImpl implements StoreDayChannelBensonService {

    private final StoreDayChannelBensonMapper storeDayChannelBensonMapper;
    private final PopupMapper popupMapper;

    public StoreDayChannelBensonServiceImpl(StoreDayChannelBensonMapper storeDayChannelBensonMapper, PopupMapper popupMapper) {
        this.storeDayChannelBensonMapper = storeDayChannelBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 매장-일별매출현황(채널별) 리스트 조회
     * @param storeDayChannelBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreDayChannelBensonList(StoreDayChannelBensonVO storeDayChannelBensonVO, SessionInfoVO sessionInfoVO) {

        storeDayChannelBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayChannelBensonVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayChannelBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeDayChannelBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayChannelBensonVO.getStoreCds(), 3900));
            storeDayChannelBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayChannelBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayChannelBensonVO.setPivotPayCol(pivotPayCol);
        storeDayChannelBensonVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayChannelBensonVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayChannelBensonVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayChannelBensonVO.getStoreHqBrandCd() == "" || storeDayChannelBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayChannelBensonVO.getUserBrands().split(",");
                storeDayChannelBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayChannelBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayChannelBensonVO.setPivotMpayCol(pivotMpayCol);
        storeDayChannelBensonVO.setArrMpayCol(mpayCol.split(","));

        return storeDayChannelBensonMapper.getStoreDayChannelBensonList(storeDayChannelBensonVO);
    }

    /**
     * 매장-일별매출현황(채널별) 엑셀 다운로드 조회
     * @param storeDayChannelBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreDayChannelBensonExcelList(StoreDayChannelBensonVO storeDayChannelBensonVO, SessionInfoVO sessionInfoVO) {

        storeDayChannelBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayChannelBensonVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayChannelBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeDayChannelBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayChannelBensonVO.getStoreCds(), 3900));
            storeDayChannelBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayChannelBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayChannelBensonVO.setPivotPayCol(pivotPayCol);
        storeDayChannelBensonVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayChannelBensonVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayChannelBensonVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayChannelBensonVO.getStoreHqBrandCd() == "" || storeDayChannelBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayChannelBensonVO.getUserBrands().split(",");
                storeDayChannelBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayChannelBensonVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayChannelBensonVO.setPivotMpayCol(pivotMpayCol);
        storeDayChannelBensonVO.setArrMpayCol(mpayCol.split(","));

        return storeDayChannelBensonMapper.getStoreDayChannelBensonExcelList(storeDayChannelBensonVO);
    }

}
