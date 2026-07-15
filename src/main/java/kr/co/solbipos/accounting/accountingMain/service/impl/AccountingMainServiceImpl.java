package kr.co.solbipos.accounting.accountingMain.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.accounting.accountingMain.service.AccountingMainService;
import kr.co.solbipos.accounting.accountingMain.service.AccountingMainVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AccountingMainServiceImpl.java
 * @Description : 벤슨 > 회계관리 > 회계관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("accountingMainService")
@Transactional
public class AccountingMainServiceImpl implements AccountingMainService {

    private final AccountingMainMapper accountingMainMapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    @Autowired
    public AccountingMainServiceImpl(AccountingMainMapper accountingMainMapper, PopupMapper popupMapper, MessageService messageService) {
        this.accountingMainMapper = accountingMainMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 일별전송 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getAcDayTransferList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO) {

        // 본사 세팅
        accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(accountingMainVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingMainVO.getStoreCd(), 3900));
            accountingMainVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingMainMapper.getAcDayTransferList(accountingMainVO);
    }

    /** 월별전송 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getAcMonthTransferList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO) {

        // 본사 세팅
        accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(accountingMainVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingMainVO.getStoreCd(), 3900));
            accountingMainVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingMainMapper.getAcMonthTransferList(accountingMainVO);
    }

    /** 매장별 항목관리 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getAcStoreOptionList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO) {

        // 본사 세팅
        accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(accountingMainVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(accountingMainVO.getStoreCd(), 3900));
            accountingMainVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return accountingMainMapper.getAcStoreOptionList(accountingMainVO);
    }

    /** 매장별 항목관리 탭 - 저장 */
    @Override
    public int saveAcStoreOption(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (AccountingMainVO accountingMainVO : accountingMainVOs) {

            accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            accountingMainVO.setRegDt(dt);
            accountingMainVO.setRegId(sessionInfoVO.getUserId());
            accountingMainVO.setModDt(dt);
            accountingMainVO.setModId(sessionInfoVO.getUserId());

            result += accountingMainMapper.saveAcStoreOption(accountingMainVO);
        }

        return result;
    }

    /** 매장별 항목관리 탭 - 삭제 */
    @Override
    public int delAcStoreOption(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (AccountingMainVO accountingMainVO : accountingMainVOs) {

            accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result += accountingMainMapper.delAcStoreOption(accountingMainVO);
        }

        return result;
    }

    /** 공통코드관리 탭 - 공통코드(좌측) 조회 */
    @Override
    public List<DefaultMap<String>> getAcComCodeList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO) {

        accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return accountingMainMapper.getAcComCodeList(accountingMainVO);
    }

    /** 공통코드관리 탭 - 상세코드(우측) 조회 */
    @Override
    public List<DefaultMap<String>> getAcComCodeDtlList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO) {

        accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return accountingMainMapper.getAcComCodeDtlList(accountingMainVO);
    }

    /** 공통코드관리 탭 - 공통코드(좌측) 저장 (I/U/D 일괄처리) */
    @Override
    public int saveAcComCode(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (AccountingMainVO accountingMainVO : accountingMainVOs) {

            accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            accountingMainVO.setRegDt(dt);
            accountingMainVO.setRegId(sessionInfoVO.getUserId());
            accountingMainVO.setModDt(dt);
            accountingMainVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if (accountingMainVO.getStatus() == GridDataFg.INSERT) {
                result += accountingMainMapper.saveAcComCodeInsert(accountingMainVO);
            // 수정
            } else if (accountingMainVO.getStatus() == GridDataFg.UPDATE) {
                result += accountingMainMapper.saveAcComCodeUpdate(accountingMainVO);
            // 삭제 (하위 상세코드 먼저 일괄삭제 후 공통코드 삭제)
            } else if (accountingMainVO.getStatus() == GridDataFg.DELETE) {
                accountingMainMapper.deleteAcComCodeDtlByNmcodeCd(accountingMainVO);
                result += accountingMainMapper.saveAcComCodeDelete(accountingMainVO);
            }
        }

        if (result == accountingMainVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }
    }

    /** 공통코드관리 탭 - 상세코드(우측) 저장 (I/U/D 일괄처리) */
    @Override
    public int saveAcComCodeDtl(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (AccountingMainVO accountingMainVO : accountingMainVOs) {

            accountingMainVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            accountingMainVO.setRegDt(dt);
            accountingMainVO.setRegId(sessionInfoVO.getUserId());
            accountingMainVO.setModDt(dt);
            accountingMainVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if (accountingMainVO.getStatus() == GridDataFg.INSERT) {
                result += accountingMainMapper.saveAcComCodeDtlInsert(accountingMainVO);
            // 수정
            } else if (accountingMainVO.getStatus() == GridDataFg.UPDATE) {
                result += accountingMainMapper.saveAcComCodeDtlUpdate(accountingMainVO);
            // 삭제
            } else if (accountingMainVO.getStatus() == GridDataFg.DELETE) {
                result += accountingMainMapper.saveAcComCodeDtlDelete(accountingMainVO);
            }
        }

        if (result == accountingMainVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }
    }

}
