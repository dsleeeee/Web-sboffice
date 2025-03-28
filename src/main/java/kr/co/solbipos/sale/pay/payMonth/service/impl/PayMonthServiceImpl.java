package kr.co.solbipos.sale.pay.payMonth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.payMonth.service.PayMonthService;
import kr.co.solbipos.sale.pay.payMonth.service.PayMonthVO;
import kr.co.solbipos.sale.pay.payMonth.service.impl.PayMonthMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayMonthServiceImpl.java
 * @Description : 맘스터치 > 결제수단매출 > 월별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("payMonthService")
@Transactional
public class PayMonthServiceImpl implements PayMonthService {
    private final PayMonthMapper payMonthMapper;
    private final PopupMapper popupMapper;

    public PayMonthServiceImpl(PayMonthMapper payMonthMapper, PopupMapper popupMapper) {
        this.payMonthMapper = payMonthMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayMonthList(PayMonthVO payMonthVO, SessionInfoVO sessionInfoVO) {

        payMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payMonthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(payMonthVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payMonthVO.getStoreCds(), 3900));
            payMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payMonthVO.getStoreHqBrandCd() == "" || payMonthVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payMonthVO.getUserBrands().split(",");
                payMonthVO.setUserBrandList(userBrandList);
            }
        }

        // 결제수단 array 값 세팅
        payMonthVO.setArrPayCol(payMonthVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payMonthVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        payMonthVO.setPivotPayCol(pivotPayCol);

        return payMonthMapper.getPayMonthList(payMonthVO);
    }

}