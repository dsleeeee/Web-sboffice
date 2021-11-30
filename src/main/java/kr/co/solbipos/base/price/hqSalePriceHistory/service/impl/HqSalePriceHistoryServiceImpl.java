package kr.co.solbipos.base.price.hqSalePriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.HqSalePriceHistoryService;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.HqSalePriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : HqSalePriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(본사)
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
@Service("HqSalePriceHistoryService")
public class HqSalePriceHistoryServiceImpl implements HqSalePriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HqSalePriceHistoryMapper hqSalePriceHistoryMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public HqSalePriceHistoryServiceImpl(CmmEnvUtil cmmEnvUtil, MessageService messageService, HqSalePriceHistoryMapper hqSalePriceHistoryMapper) {
        this.hqSalePriceHistoryMapper = hqSalePriceHistoryMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 매장판매가변경이력 - 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSalePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(hqSalePriceHistoryVO.getStoreCd()).equals("")) {
            hqSalePriceHistoryVO.setArrStoreCd(hqSalePriceHistoryVO.getStoreCd().split(","));
        }
        hqSalePriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSalePriceHistoryMapper.getStoreSalePriceHistoryList(hqSalePriceHistoryVO);
    }

    /** 매장판매가변경이력 - 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getStoreSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSalePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(hqSalePriceHistoryVO.getStoreCd()).equals("")) {
            hqSalePriceHistoryVO.setArrStoreCd(hqSalePriceHistoryVO.getStoreCd().split(","));
        }
        hqSalePriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSalePriceHistoryMapper.getStoreSalePriceHistoryExcelList(hqSalePriceHistoryVO);
    }

    /** 본사판매가변경이력 - 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSalePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSalePriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSalePriceHistoryMapper.getHqSalePriceHistoryList(hqSalePriceHistoryVO);
    }

    /** 본사판매가변경이력 - 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSalePriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSalePriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSalePriceHistoryMapper.getHqSalePriceHistoryExcelList(hqSalePriceHistoryVO);
    }
}