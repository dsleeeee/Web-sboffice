package kr.co.solbipos.base.price.hqSplyPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceVO;
import kr.co.solbipos.base.price.hqSplyPriceHistory.service.HqSplyPriceHistoryService;
import kr.co.solbipos.base.price.hqSplyPriceHistory.service.HqSplyPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : HqSplyPriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 본사공급가History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("hqSplyPriceHistoryService")
public class HqSplyPriceHistoryServiceImpl implements HqSplyPriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HqSplyPriceHistoryMapper hqSplyPriceHistoryMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public HqSplyPriceHistoryServiceImpl(HqSplyPriceHistoryMapper hqSplyPriceHistoryMapper) {
        this.hqSplyPriceHistoryMapper = hqSplyPriceHistoryMapper;
    }

    /** 본사 공급가 History 조회 */
    @Override
    public List<DefaultMap<String>> getHqSplyPriceHistoryList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSplyPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSplyPriceHistoryMapper.getHqSplyPriceHistoryList(hqSplyPriceHistoryVO);
    }

    /** 본사 공급가 History 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getHqSplyPriceHistoryExcelList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSplyPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        return hqSplyPriceHistoryMapper.getHqSplyPriceHistoryExcelList(hqSplyPriceHistoryVO);
    }
}
