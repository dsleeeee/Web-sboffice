package kr.co.solbipos.excclc.excclc.depositDdc.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcService;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DepositDdcServiceImpl.java
 * @Description : 수불관리 > 수주관리 > 입금/공제관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.04.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("depositDdcService")
@Transactional
public class DepositDdcServiceImpl implements DepositDdcService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DepositDdcMapper depositDdcMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DepositDdcServiceImpl(DepositDdcMapper depositDdcMapper, MessageService messageService) {
        this.depositDdcMapper = depositDdcMapper;
        this.messageService = messageService;
    }

    /** 매장별집계 탭 - 매장별집계 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreTotalList(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO) {

        depositDdcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(depositDdcVO.getStoreCd()).equals("")) {
            depositDdcVO.setArrStoreCd(depositDdcVO.getStoreCd().split(","));
        }

        return depositDdcMapper.getStoreTotalList(depositDdcVO);
    }

    /** 매장별집계 탭 - 상세내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreTotalDtlList(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO) {

        depositDdcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return depositDdcMapper.getStoreTotalDtlList(depositDdcVO);
    }

    /** 매장별집계 탭 - 입금/기타공제 계정 콤보박스 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getMoneyFgCombo(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO) {

        return depositDdcMapper.getMoneyFgCombo(depositDdcVO);
    }

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 저장 */
    @Override
    public int saveDepositDdc(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        depositDdcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        depositDdcVO.setRegDt(dt);
        depositDdcVO.setRegId(sessionInfoVO.getUserId());
        depositDdcVO.setModDt(dt);
        depositDdcVO.setModId(sessionInfoVO.getUserId());

        if (depositDdcVO.getStatus() == GridDataFg.INSERT) {
            result = depositDdcMapper.insertDepositDdc(depositDdcVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }else if(depositDdcVO.getStatus() == GridDataFg.UPDATE) {
            result = depositDdcMapper.updateDepositDdc(depositDdcVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }else if(depositDdcVO.getStatus() == GridDataFg.DELETE) {
            result = depositDdcMapper.deleteDepositDdc(depositDdcVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 조회 */
    @Override
    public DefaultMap<String> getDepositDdc(DepositDdcVO depositDdcVO, SessionInfoVO sessionInfoVO) {

        depositDdcVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return depositDdcMapper.getDepositDdc(depositDdcVO);
    }



}
