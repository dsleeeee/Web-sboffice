package kr.co.solbipos.sale.store.storeDayPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeDayPos.service.StoreDayPosService;
import kr.co.solbipos.sale.store.storeDayPos.service.StoreDayPosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeDayPosService")
public class StoreDayPosServiceImpl implements StoreDayPosService {
    private final StoreDayPosMapper storeDayPosMapper;

    @Autowired
    public StoreDayPosServiceImpl(StoreDayPosMapper storeDayPosMapper) {
        this.storeDayPosMapper = storeDayPosMapper;
    }


    /** 일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayList(StoreDayPosVO storeDayPosVO, SessionInfoVO sessionInfoVO) {
        storeDayPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayPosVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = storeDayPosVO.getStoreCds().split(",");
        storeDayPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayPosVO.setPivotPayCol(pivotPayCol);
        storeDayPosVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayPosVO.getStoreHqBrandCd() == "" || storeDayPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayPosVO.getUserBrands().split(",");
                storeDayPosVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayPosVO.setPivotMpayCol(pivotMpayCol);
        storeDayPosVO.setArrMpayCol(mpayCol.split(","));

        return storeDayPosMapper.getDayList(storeDayPosVO);
    }

    @Override
    public List<DefaultMap<String>> getDayExcelList(StoreDayPosVO storeDayPosVO, SessionInfoVO sessionInfoVO) {
        storeDayPosVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayPosVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayPosVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = storeDayPosVO.getStoreCds().split(",");
        storeDayPosVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayPosVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayPosVO.setPivotPayCol(pivotPayCol);
        storeDayPosVO.setArrPayCol(payCol.split(","));

        String[] arrDlvrInFgCol = storeDayPosVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                storeDayPosVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayPosVO.getStoreHqBrandCd() == "" || storeDayPosVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayPosVO.getUserBrands().split(",");
                storeDayPosVO.setUserBrandList(userBrandList);
            }
        }

        // 모바일페이상세 array 값 세팅
        String mpayCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = storeDayPosVO.getMpayCol().split(",");
        for(int i=0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        storeDayPosVO.setPivotMpayCol(pivotMpayCol);
        storeDayPosVO.setArrMpayCol(mpayCol.split(","));

        return storeDayPosMapper.getDayExcelList(storeDayPosVO);
    }

}