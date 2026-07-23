package kr.co.solbipos.accounting.accountingDlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.accounting.accountingDlvr.service.AccountingDlvrService;
import kr.co.solbipos.accounting.accountingDlvr.service.AccountingDlvrVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : AccountingDlvrServiceImpl.java
 * @Description : 벤슨 > 회계관리 > 배달비 현황
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
@Service("accountingDlvrService")
@Transactional
public class AccountingDlvrServiceImpl implements AccountingDlvrService {

    private final AccountingDlvrMapper accountingDlvrMapper;
    private final PopupMapper popupMapper;

    public AccountingDlvrServiceImpl(AccountingDlvrMapper accountingDlvrMapper, PopupMapper popupMapper) {
        this.accountingDlvrMapper = accountingDlvrMapper;
        this.popupMapper = popupMapper;
    }

    /** 배달비현황 - 일별 탭 조회 */
    @Override
    public List<DefaultMap<Object>> getAccountingDlvrDayList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO) {

        accountingDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            accountingDlvrVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(accountingDlvrVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingDlvrVO.getStoreCds(), 3900));
            accountingDlvrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingDlvrMapper.getAccountingDlvrDayList(accountingDlvrVO);
    }

    /** 배달비현황 - 일별 탭 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getAccountingDlvrDayExcelList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO) {

        accountingDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            accountingDlvrVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(accountingDlvrVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingDlvrVO.getStoreCds(), 3900));
            accountingDlvrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingDlvrMapper.getAccountingDlvrDayExcelList(accountingDlvrVO);
    }

    /** 배달비현황 - 월별 탭 조회 */
    @Override
    public List<DefaultMap<Object>> getAccountingDlvrMonthList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO) {

        accountingDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            accountingDlvrVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(accountingDlvrVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingDlvrVO.getStoreCds(), 3900));
            accountingDlvrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingDlvrMapper.getAccountingDlvrMonthList(accountingDlvrVO);
    }

    /** 배달비현황 - 월별 탭 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getAccountingDlvrMonthExcelList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO) {

        accountingDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            accountingDlvrVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(accountingDlvrVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingDlvrVO.getStoreCds(), 3900));
            accountingDlvrVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingDlvrMapper.getAccountingDlvrMonthExcelList(accountingDlvrVO);
    }
}
