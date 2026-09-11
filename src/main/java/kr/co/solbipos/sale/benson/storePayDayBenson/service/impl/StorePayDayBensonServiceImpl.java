package kr.co.solbipos.sale.benson.storePayDayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.storePayDayBenson.service.StorePayDayBensonService;
import kr.co.solbipos.sale.benson.storePayDayBenson.service.StorePayDayBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StorePayDayBensonServiceImpl.java
 * @Description : 벤슨 > 결제수단매출 > 매장-일별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storePayDayBensonService")
@Transactional
public class StorePayDayBensonServiceImpl implements StorePayDayBensonService {
    private final StorePayDayBensonMapper storePayDayBensonMapper;
    private final PopupMapper popupMapper;

    public StorePayDayBensonServiceImpl(StorePayDayBensonMapper storePayDayBensonMapper, PopupMapper popupMapper) {
        this.storePayDayBensonMapper = storePayDayBensonMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayDayBensonList(StorePayDayBensonVO storePayDayBensonVO, SessionInfoVO sessionInfoVO) {
        storePayDayBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayDayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayDayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storePayDayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePayDayBensonVO.getStoreCds(), 3900));
            storePayDayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayDayBensonVO.getStoreHqBrandCd() == "" || storePayDayBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayDayBensonVO.getUserBrands().split(",");
                storePayDayBensonVO.setUserBrandList(userBrandList);
            }
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayDayBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        storePayDayBensonVO.setPivotPayCol(pivotPayCol);
        storePayDayBensonVO.setArrPayCol(payCol.split(","));

        return storePayDayBensonMapper.getStorePayDayBensonList(storePayDayBensonVO);
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayDayBensonExcelList(StorePayDayBensonVO storePayDayBensonVO, SessionInfoVO sessionInfoVO) {
        storePayDayBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayDayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayDayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storePayDayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePayDayBensonVO.getStoreCds(), 3900));
            storePayDayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayDayBensonVO.getStoreHqBrandCd() == "" || storePayDayBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayDayBensonVO.getUserBrands().split(",");
                storePayDayBensonVO.setUserBrandList(userBrandList);
            }
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayDayBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        storePayDayBensonVO.setPivotPayCol(pivotPayCol);
        storePayDayBensonVO.setArrPayCol(payCol.split(","));

        return storePayDayBensonMapper.getStorePayDayBensonExcelList(storePayDayBensonVO);
    }

}
