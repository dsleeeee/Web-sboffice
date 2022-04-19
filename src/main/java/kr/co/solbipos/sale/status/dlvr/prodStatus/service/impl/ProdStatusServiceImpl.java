package kr.co.solbipos.sale.status.dlvr.prodStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.dlvr.prodStatus.service.ProdStatusService;
import kr.co.solbipos.sale.status.dlvr.prodStatus.service.ProdStatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : ProdStatusServiceImpl.java
 * @Description : 매출관리 - 매출현황 - 배달상품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.11  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodStatusService")
public class ProdStatusServiceImpl implements ProdStatusService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final ProdStatusMapper prodStatusMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ProdStatusServiceImpl(ProdStatusMapper prodStatusMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.prodStatusMapper = prodStatusMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 가격예약(본사판매가) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdStatusList(ProdStatusVO prodStatusVO, SessionInfoVO sessionInfoVO) {
        prodStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodStatusVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodStatusMapper.getProdStatusList(prodStatusVO);
    }
}
