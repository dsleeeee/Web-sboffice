package kr.co.solbipos.dlvr.anals.dlvrReceipt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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

    @Override
    public List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO) {
        dlvrReceiptVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getDlvrReceiptList(dlvrReceiptVO);
    }

    @Override
    public List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO) {
//        dlvrReceiptVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getDlvrReceiptDetailList(dlvrReceiptVO);
    }
}
