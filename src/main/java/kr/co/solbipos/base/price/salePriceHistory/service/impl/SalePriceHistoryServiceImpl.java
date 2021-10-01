package kr.co.solbipos.base.price.salePriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.impl.HqSalePriceHistoryMapper;
import kr.co.solbipos.base.price.salePriceHistory.service.SalePriceHistoryService;
import kr.co.solbipos.base.price.salePriceHistory.service.SalePriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : SalePriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.28  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("SalePriceHistoryService")
public class SalePriceHistoryServiceImpl implements SalePriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SalePriceHistoryMapper salePriceHistoryMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceHistoryServiceImpl(CmmEnvUtil cmmEnvUtil, MessageService messageService, SalePriceHistoryMapper salePriceHistoryMapper) {
        this.salePriceHistoryMapper = salePriceHistoryMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 판매가변경이력 - 조회 */
    @Override
    public List<DefaultMap<String>> getSalePriceHistoryList(SalePriceHistoryVO salePriceHistoryVO, SessionInfoVO sessionInfoVO) {

        salePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        return salePriceHistoryMapper.getSalePriceHistoryList(salePriceHistoryVO);
    }

    /** 판매가변경이력 - 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getSalePriceHistoryExcelList(SalePriceHistoryVO salePriceHistoryVO, SessionInfoVO sessionInfoVO) {
        salePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        return salePriceHistoryMapper.getSalePriceHistoryExcelList(salePriceHistoryVO);
    }
}