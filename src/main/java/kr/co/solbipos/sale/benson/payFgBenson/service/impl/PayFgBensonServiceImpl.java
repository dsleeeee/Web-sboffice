package kr.co.solbipos.sale.benson.payFgBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.payFgBenson.service.PayFgBensonService;
import kr.co.solbipos.sale.benson.payFgBenson.service.PayFgBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayFgBensonServiceImpl.java
 * @Description : 벤슨 > 결제수단별 매출 > 결제수단별 일 매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("payFgBensonService")
@Transactional
public class PayFgBensonServiceImpl implements PayFgBensonService {
    private final PayFgBensonMapper payFgBensonMapper;
    private final PopupMapper popupMapper;

    public PayFgBensonServiceImpl(PayFgBensonMapper payFgBensonMapper, PopupMapper popupMapper) {
        this.payFgBensonMapper = payFgBensonMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayFgBensonList(PayFgBensonVO payFgBensonVO, SessionInfoVO sessionInfoVO) {

        payFgBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payFgBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payFgBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payFgBensonVO.getStoreCds(), 3900));
            payFgBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payFgBensonVO.getStoreHqBrandCd() == "" || payFgBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payFgBensonVO.getUserBrands().split(",");
                payFgBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payFgBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        payFgBensonVO.setPivotPayCol(pivotPayCol);
        payFgBensonVO.setArrPayCol(payCol.split(","));

        return payFgBensonMapper.getPayFgBensonList(payFgBensonVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPayFgBensonExcelList(PayFgBensonVO payFgBensonVO, SessionInfoVO sessionInfoVO) {

        payFgBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payFgBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payFgBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payFgBensonVO.getStoreCds(), 3900));
            payFgBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payFgBensonVO.getStoreHqBrandCd() == "" || payFgBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payFgBensonVO.getUserBrands().split(",");
                payFgBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payFgBensonVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        payFgBensonVO.setPivotPayCol(pivotPayCol);
        payFgBensonVO.setArrPayCol(payCol.split(","));

        return payFgBensonMapper.getPayFgBensonExcelList(payFgBensonVO);
    }
}
