package kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeMonthChannel.service.StoreMonthChannelVO;
import kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service.StoreMonthChannelMrpizzaService;
import kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service.StoreMonthChannelMrpizzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreMonthChannelMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 매장분석 > 매장-월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/

@Service("storeMonthChannelMrpizzaService")
public class StoreMonthChannelMrpizzaServiceImpl implements StoreMonthChannelMrpizzaService {

    private final StoreMonthChannelMrpizzaMapper storeMonthChannelMrpizzaMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public StoreMonthChannelMrpizzaServiceImpl(StoreMonthChannelMrpizzaMapper storeMonthChannelMrpizzaMapper, PopupMapper popupMapper) {
        this.storeMonthChannelMrpizzaMapper = storeMonthChannelMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 매장-월별매출현황(채널별) 리스트 조회
     * @param storeMonthChannelMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreMonthChannelMrpizzaList(StoreMonthChannelMrpizzaVO storeMonthChannelMrpizzaVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelMrpizzaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelMrpizzaVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMonthChannelMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMonthChannelMrpizzaVO.getStoreCds(), 3900));
            storeMonthChannelMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelMrpizzaVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelMrpizzaVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelMrpizzaVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelMrpizzaVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelMrpizzaVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelMrpizzaVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelMrpizzaVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelMrpizzaVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelMrpizzaMapper.getStoreMonthChannelMrpizzaList(storeMonthChannelMrpizzaVO);
    }

    /**
     * 매장-월별매출현황(채널별) 엑셀 리스트 조회
     * @param storeMonthChannelMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getStoreMonthChannelMrpizzaExcelList(StoreMonthChannelMrpizzaVO storeMonthChannelMrpizzaVO, SessionInfoVO sessionInfoVO) {

        storeMonthChannelMrpizzaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthChannelMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMonthChannelMrpizzaVO.setEmpNo(sessionInfoVO.getEmpNo());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMonthChannelMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMonthChannelMrpizzaVO.getStoreCds(), 3900));
            storeMonthChannelMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthChannelMrpizzaVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthChannelMrpizzaVO.setPivotPayCol(pivotPayCol);
        storeMonthChannelMrpizzaVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeMonthChannelMrpizzaVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeMonthChannelMrpizzaVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeMonthChannelMrpizzaVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeMonthChannelMrpizzaVO.setPivotMpayCol(pivotMpayCol);
        storeMonthChannelMrpizzaVO.setArrMpayCol(mpayCol.split(","));

        return storeMonthChannelMrpizzaMapper.getStoreMonthChannelMrpizzaExcelList(storeMonthChannelMrpizzaVO);
    }
}
