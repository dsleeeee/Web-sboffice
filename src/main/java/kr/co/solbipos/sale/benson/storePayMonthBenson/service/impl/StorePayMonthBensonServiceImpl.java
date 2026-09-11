package kr.co.solbipos.sale.benson.storePayMonthBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.storePayMonthBenson.service.StorePayMonthBensonService;
import kr.co.solbipos.sale.benson.storePayMonthBenson.service.StorePayMonthBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StorePayMonthBensonServiceImpl.java
 * @Description : 벤슨 > 결제수단매출 > 매장-월별결제수단매출
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
@Service("storePayMonthBensonService")
@Transactional
public class StorePayMonthBensonServiceImpl implements StorePayMonthBensonService {
    private final StorePayMonthBensonMapper storePayMonthBensonMapper;
    private final PopupMapper popupMapper;

    public StorePayMonthBensonServiceImpl(StorePayMonthBensonMapper storePayMonthBensonMapper, PopupMapper popupMapper) {
        this.storePayMonthBensonMapper = storePayMonthBensonMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayMonthBensonList(StorePayMonthBensonVO storePayMonthBensonVO, SessionInfoVO sessionInfoVO) {

        storePayMonthBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayMonthBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayMonthBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storePayMonthBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePayMonthBensonVO.getStoreCds(), 3900));
            storePayMonthBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayMonthBensonVO.getStoreHqBrandCd() == "" || storePayMonthBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayMonthBensonVO.getUserBrands().split(",");
                storePayMonthBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        storePayMonthBensonVO.setArrPayCol(storePayMonthBensonVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayMonthBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        storePayMonthBensonVO.setPivotPayCol(pivotPayCol);

        return storePayMonthBensonMapper.getStorePayMonthBensonList(storePayMonthBensonVO);
    }

    /** 엑셀조회 */
    @Override
    public List<DefaultMap<Object>> getStorePayMonthBensonExcelList(StorePayMonthBensonVO storePayMonthBensonVO, SessionInfoVO sessionInfoVO) {

        storePayMonthBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storePayMonthBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storePayMonthBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storePayMonthBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storePayMonthBensonVO.getStoreCds(), 3900));
            storePayMonthBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storePayMonthBensonVO.getStoreHqBrandCd() == "" || storePayMonthBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storePayMonthBensonVO.getUserBrands().split(",");
                storePayMonthBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        storePayMonthBensonVO.setArrPayCol(storePayMonthBensonVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storePayMonthBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        storePayMonthBensonVO.setPivotPayCol(pivotPayCol);

        return storePayMonthBensonMapper.getStorePayMonthBensonExcelList(storePayMonthBensonVO);
    }

}
