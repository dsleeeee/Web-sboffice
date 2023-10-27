package kr.co.solbipos.sale.appr.cashBill.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillService;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : CashBillServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 현금영수증 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cashBillService")
@Transactional
public class CashBillServiceImpl implements CashBillService {
    private final CashBillMapper cashBillMapper;
    private final PopupMapper popupMapper;

    public CashBillServiceImpl(CashBillMapper cashBillMapper, PopupMapper popupMapper) {
        this.cashBillMapper = cashBillMapper;
        this.popupMapper = popupMapper;
    }

    /** 신용카드입금관리 - 매장 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getCashBillList(CashBillVO cashBillVO, SessionInfoVO sessionInfoVO) {

        cashBillVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            cashBillVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(cashBillVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(cashBillVO.getStoreCds(), 3900));
            cashBillVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return cashBillMapper.getCashBillList(cashBillVO);
    }

    /** 신용카드입금관리 - 매장 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getCashBillExcelList(CashBillVO cashBillVO, SessionInfoVO sessionInfoVO) {

        cashBillVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            cashBillVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(cashBillVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(cashBillVO.getStoreCds(), 3900));
            cashBillVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return cashBillMapper.getCashBillExcelList(cashBillVO);
    }

}