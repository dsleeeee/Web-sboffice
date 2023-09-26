package kr.co.solbipos.sale.store.storeDayChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeDayChannel.service.StoreDayChannelService;
import kr.co.solbipos.sale.store.storeDayChannel.service.StoreDayChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeDayChannelService")
public class StoreDayChannelServiceImpl implements StoreDayChannelService {
    private final StoreDayChannelMapper storeDayChannelMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public StoreDayChannelServiceImpl(StoreDayChannelMapper storeDayChannelMapper, PopupMapper popupMapper) {
        this.storeDayChannelMapper = storeDayChannelMapper;
        this.popupMapper = popupMapper;
    }


    /** 일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayList(StoreDayChannelVO storeDayChannelVO, SessionInfoVO sessionInfoVO) {
        storeDayChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayChannelVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeDayChannelVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayChannelVO.getStoreCds(), 3900));
            storeDayChannelVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayChannelVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayChannelVO.setPivotPayCol(pivotPayCol);
        storeDayChannelVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayChannelVO.getStoreHqBrandCd() == "" || storeDayChannelVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayChannelVO.getUserBrands().split(",");
                storeDayChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayChannelVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayChannelVO.setPivotMpayCol(pivotMpayCol);
        storeDayChannelVO.setArrMpayCol(mpayCol.split(","));

        return storeDayChannelMapper.getDayList(storeDayChannelVO);
    }

    @Override
    public List<DefaultMap<String>> getDayExcelList(StoreDayChannelVO storeDayChannelVO, SessionInfoVO sessionInfoVO) {
        storeDayChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayChannelVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeDayChannelVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayChannelVO.getStoreCds(), 3900));
            storeDayChannelVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayChannelVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayChannelVO.setPivotPayCol(pivotPayCol);
        storeDayChannelVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayChannelVO.getStoreHqBrandCd() == "" || storeDayChannelVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayChannelVO.getUserBrands().split(",");
                storeDayChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayChannelVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayChannelVO.setPivotMpayCol(pivotMpayCol);
        storeDayChannelVO.setArrMpayCol(mpayCol.split(","));

        return storeDayChannelMapper.getDayExcelList(storeDayChannelVO);
    }

}