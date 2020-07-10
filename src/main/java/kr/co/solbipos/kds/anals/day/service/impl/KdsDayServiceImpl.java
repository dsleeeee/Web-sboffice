package kr.co.solbipos.kds.anals.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.impl.DlvrRegistMapper;
import kr.co.solbipos.kds.anals.day.service.KdsDayService;
import kr.co.solbipos.kds.anals.day.service.KdsDayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("kdsDayService")
@Transactional
public class KdsDayServiceImpl implements KdsDayService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KdsDayMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KdsDayServiceImpl(KdsDayMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    @Override
    public List<DefaultMap<String>> getKdsDay(KdsDayVO kdsDayVO, SessionInfoVO sessionInfoVO) {
        return mapper.getKdsDay(kdsDayVO);
    }
}
