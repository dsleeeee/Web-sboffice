package kr.co.solbipos.sale.pay.payDtlBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.payDtlBenson.service.PayDtlBensonService;
import kr.co.solbipos.sale.pay.payDtlBenson.service.PayDtlBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayDtlBensonServiceImpl.java
 * @Description : 벤슨 > 결제수단매출 > 결제수단상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("payDtlBensonService")
@Transactional
public class PayDtlBensonServiceImpl implements PayDtlBensonService {

    private final PayDtlBensonMapper payDtlBensonMapper;
    private final PopupMapper popupMapper;

    public PayDtlBensonServiceImpl(PayDtlBensonMapper payDtlBensonMapper, PopupMapper popupMapper) {
        this.payDtlBensonMapper = payDtlBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 결제수단상세 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getPayDtlBensonList(PayDtlBensonVO payDtlBensonVO, SessionInfoVO sessionInfoVO) {

        payDtlBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payDtlBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().toString());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            payDtlBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 Array 값 세팅
        if (!StringUtil.getOrBlank(payDtlBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payDtlBensonVO.getStoreCds(), 3900));
            payDtlBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if ((payDtlBensonVO.getStoreHqBrandCd() == null || payDtlBensonVO.getStoreHqBrandCd().equals(""))
                    && !StringUtil.getOrBlank(payDtlBensonVO.getUserBrands()).equals("")) {
                // 사용자별 브랜드 array 값 세팅
                payDtlBensonVO.setUserBrandList(payDtlBensonVO.getUserBrands().split(","));
            }
        }

        return payDtlBensonMapper.getPayDtlBensonList(payDtlBensonVO);
    }

    /** 결제수단상세 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getPayDtlBensonExcelDivisionList(PayDtlBensonVO payDtlBensonVO, SessionInfoVO sessionInfoVO) {

        payDtlBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        payDtlBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().toString());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            payDtlBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        /** 결제수단상세 리스트 조회 */
        if (!StringUtil.getOrBlank(payDtlBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(payDtlBensonVO.getStoreCds(), 3900));
            payDtlBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if ((payDtlBensonVO.getStoreHqBrandCd() == null || payDtlBensonVO.getStoreHqBrandCd().equals(""))
                    && !StringUtil.getOrBlank(payDtlBensonVO.getUserBrands()).equals("")) {
                // 사용자별 브랜드 array 값 세팅
                payDtlBensonVO.setUserBrandList(payDtlBensonVO.getUserBrands().split(","));
            }
        }

        return payDtlBensonMapper.getPayDtlBensonExcelDivisionList(payDtlBensonVO);
    }
}
