package kr.co.solbipos.sale.anals.nonTaxSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.nonTaxSale.service.NonTaxSaleService;
import kr.co.solbipos.sale.anals.nonTaxSale.service.NonTaxSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : NonTaxSaleServiceImpl.java
 * @Description : 미스터피자 > 매출분석 > 비과세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("NonTaxSaleService")
@Transactional
public class NonTaxSaleServiceImpl implements NonTaxSaleService {

    private final NonTaxSaleMapper nonTaxSaleMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public NonTaxSaleServiceImpl(NonTaxSaleMapper nonTaxSaleMapper, PopupMapper popupMapper) {
        this.nonTaxSaleMapper = nonTaxSaleMapper;
        this.popupMapper = popupMapper;
    }

    /** 비과세매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getNonTaxSaleList(NonTaxSaleVO nonTaxSaleVO, SessionInfoVO sessionInfoVO) {

        nonTaxSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            nonTaxSaleVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(nonTaxSaleVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(nonTaxSaleVO.getStoreCds(), 3900));
            nonTaxSaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return nonTaxSaleMapper.getNonTaxSaleList(nonTaxSaleVO);
    }
}
