package kr.co.solbipos.kds.anals.chart.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kds.anals.chart.service.KdsService;
import kr.co.solbipos.kds.anals.chart.service.KdsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("kdsDayService")
@Transactional
public class KdsServiceImpl implements KdsService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KdsMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KdsServiceImpl(KdsMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    @Override
    public List<DefaultMap<String>> getKdsDay(KdsVO kdsVO, SessionInfoVO sessionInfoVO) {
        return mapper.getKdsDay(kdsVO);
    }
}
