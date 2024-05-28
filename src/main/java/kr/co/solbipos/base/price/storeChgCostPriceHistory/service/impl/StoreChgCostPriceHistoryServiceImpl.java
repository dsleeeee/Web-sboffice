package kr.co.solbipos.base.price.storeChgCostPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.storeChgCostPriceHistory.service.StoreChgCostPriceHistoryService;
import kr.co.solbipos.base.price.storeChgCostPriceHistory.service.StoreChgCostPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : StoreChgCostPriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 매장원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.24  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeChgCostPriceHistoryService")
public class StoreChgCostPriceHistoryServiceImpl implements StoreChgCostPriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreChgCostPriceHistoryMapper storeChgCostPriceHistoryMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreChgCostPriceHistoryServiceImpl(StoreChgCostPriceHistoryMapper storeChgCostPriceHistoryMapper, PopupMapper popupMapper) {
        this.storeChgCostPriceHistoryMapper = storeChgCostPriceHistoryMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장원가변경History 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChgCostPriceHistoryList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storechgCostPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storechgCostPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        if(!StringUtil.getOrBlank(storechgCostPriceHistoryVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storechgCostPriceHistoryVO.getStoreCd(), 3900));
            storechgCostPriceHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storechgCostPriceHistoryVO.getProdHqBrandCd() == "" || storechgCostPriceHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storechgCostPriceHistoryVO.getUserProdBrands() != null && !"".equals(storechgCostPriceHistoryVO.getUserProdBrands())) {
                    String[] userBrandList = storechgCostPriceHistoryVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storechgCostPriceHistoryVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가변경History 조회
        if ("0".equals(storechgCostPriceHistoryVO.getCostUprcType())) {
            // 매장 상품 마스터 원가변경History 조회
            result = storeChgCostPriceHistoryMapper.getStoreCostPriceHistoryList(storechgCostPriceHistoryVO);
        } else {
            // 매장 수불 원가변경History 조회
            result = storeChgCostPriceHistoryMapper.getStoreIostockCostPriceHistoryList(storechgCostPriceHistoryVO);
        }

        return result;
    }

    /** 매장원가변경History 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChgCostPriceHistoryExcelList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        storechgCostPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storechgCostPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        if(!StringUtil.getOrBlank(storechgCostPriceHistoryVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storechgCostPriceHistoryVO.getStoreCd(), 3900));
            storechgCostPriceHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storechgCostPriceHistoryVO.getProdHqBrandCd() == "" || storechgCostPriceHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storechgCostPriceHistoryVO.getUserProdBrands() != null && !"".equals(storechgCostPriceHistoryVO.getUserProdBrands())) {
                    String[] userBrandList = storechgCostPriceHistoryVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storechgCostPriceHistoryVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        // 변경항목 선택에 따른 원가변경History 조회
        if ("0".equals(storechgCostPriceHistoryVO.getCostUprcType())) {
            // 매장 상품 마스터 원가변경History 조회
            result = storeChgCostPriceHistoryMapper.getStoreCostPriceHistoryExcelList(storechgCostPriceHistoryVO);
        } else {
            // 매장 수불 원가변경History 조회
            result = storeChgCostPriceHistoryMapper.getStoreIostockCostPriceHistoryExcelList(storechgCostPriceHistoryVO);
        }

        return result;
    }
}
