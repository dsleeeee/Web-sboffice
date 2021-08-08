package kr.co.solbipos.dlvr.anals.dlvrReceipt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptService;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import kr.co.solbipos.dlvr.info.regist.service.impl.DlvrRegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DlvrReceiptServiceImpl.java
 * @Description : 배달관리 > 배달분석 > 배달지별 내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dlvrReceiptService")
@Transactional
public class DlvrReceiptServiceImpl implements DlvrReceiptService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrReceiptMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrReceiptServiceImpl(DlvrReceiptMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }


    /** 배달지별 -조회*/
    @Override
    public List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            dlvrReceiptVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            dlvrReceiptVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getDlvrReceiptList(dlvrReceiptVO);
    }

    /** 배달지별 - 상세조회*/
    @Override
    public List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            dlvrReceiptVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            dlvrReceiptVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getDlvrReceiptDetailList(dlvrReceiptVO);
    }
}
