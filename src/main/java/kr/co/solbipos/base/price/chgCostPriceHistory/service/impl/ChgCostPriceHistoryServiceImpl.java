package kr.co.solbipos.base.price.chgCostPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryService;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : ChgCostPriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("chgCostPriceHistoryService")
public class ChgCostPriceHistoryServiceImpl implements ChgCostPriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ChgCostPriceHistoryMapper chgCostPriceHistoryMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ChgCostPriceHistoryServiceImpl(ChgCostPriceHistoryMapper chgCostPriceHistoryMapper) {
        this.chgCostPriceHistoryMapper = chgCostPriceHistoryMapper;
    }

    /** 원가변경History 조회 */
    @Override
    public List<DefaultMap<String>> getChgCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceHistoryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        chgCostPriceHistoryVO.setUserId(sessionInfoVO.getUserId());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 원가변경History 조회
            if ("0".equals(chgCostPriceHistoryVO.getCostUprcType())) {
                //  본사 상품 마스터 원가변경History 조회
                result = chgCostPriceHistoryMapper.getHqCostPriceHistoryList(chgCostPriceHistoryVO);
            } else {
                // 본사 수불 원가변경History 조회
                result = chgCostPriceHistoryMapper.getHqIostockCostPriceHistoryList(chgCostPriceHistoryVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 원가변경History 조회
            if ("0".equals(chgCostPriceHistoryVO.getCostUprcType())) {
                // 매장 상품 마스터 원가변경History 조회
                result = chgCostPriceHistoryMapper.getStoreCostPriceHistoryList(chgCostPriceHistoryVO);
            } else {
                // 매장 수불 원가변경History 조회
                result = chgCostPriceHistoryMapper.getStoreIostockCostPriceHistoryList(chgCostPriceHistoryVO);
            }
        }

        return result;
    }

    /** 원가변경History 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getChgCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceHistoryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        chgCostPriceHistoryVO.setUserId(sessionInfoVO.getUserId());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 원가변경History 엑셀다운로드 조회
            if ("0".equals(chgCostPriceHistoryVO.getCostUprcType())) {
                //  본사 상품 마스터 원가변경History 엑셀다운로드 조회
                result = chgCostPriceHistoryMapper.getHqCostPriceHistoryExcelList(chgCostPriceHistoryVO);
            } else {
                // 본사 수불 원가변경History 엑셀다운로드 조회
                result = chgCostPriceHistoryMapper.getHqIostockCostPriceHistoryExcelList(chgCostPriceHistoryVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 원가변경History 엑셀다운로드 조회
            if ("0".equals(chgCostPriceHistoryVO.getCostUprcType())) {
                // 매장 상품 마스터 원가변경History 엑셀다운로드 조회
                result = chgCostPriceHistoryMapper.getStoreCostPriceHistoryExcelList(chgCostPriceHistoryVO);
            } else {
                // 매장 수불 원가변경History 엑셀다운로드 조회
                result = chgCostPriceHistoryMapper.getStoreIostockCostPriceHistoryExcelList(chgCostPriceHistoryVO);
            }
        }

        return result;
    }
}
