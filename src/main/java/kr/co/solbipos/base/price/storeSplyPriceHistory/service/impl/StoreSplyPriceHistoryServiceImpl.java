package kr.co.solbipos.base.price.storeSplyPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.HqSalePriceHistoryVO;
import kr.co.solbipos.base.price.storeSplyPriceHistory.service.StoreSplyPriceHistoryService;
import kr.co.solbipos.base.price.storeSplyPriceHistory.service.StoreSplyPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreSplyPriceHistoryServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 매장공급가History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.24  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeSplyPriceHistoryService")
public class StoreSplyPriceHistoryServiceImpl implements StoreSplyPriceHistoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreSplyPriceHistoryMapper storeSplyPriceHistoryMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreSplyPriceHistoryServiceImpl(StoreSplyPriceHistoryMapper storeSplyPriceHistoryMapper, PopupMapper popupMapper) {
        this.storeSplyPriceHistoryMapper = storeSplyPriceHistoryMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장 공급가 History 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSplyPriceHistoryList(StoreSplyPriceHistoryVO storeSplyPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        storeSplyPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeSplyPriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSplyPriceHistoryVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSplyPriceHistoryVO.getStoreCd(), 3900));
            storeSplyPriceHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSplyPriceHistoryVO.getProdHqBrandCd() == "" || storeSplyPriceHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceHistoryVO.getUserProdBrands() != null && !"".equals(storeSplyPriceHistoryVO.getUserProdBrands())) {
                    String[] userBrandList = storeSplyPriceHistoryVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceHistoryVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceHistoryMapper.getStoreSplyPriceHistoryList(storeSplyPriceHistoryVO);
    }

    /** 매장 공급가 History 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSplyPriceHistoryExcelList(StoreSplyPriceHistoryVO storeSplyPriceHistoryVO, SessionInfoVO sessionInfoVO) {

        storeSplyPriceHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeSplyPriceHistoryVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeSplyPriceHistoryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSplyPriceHistoryVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSplyPriceHistoryVO.getStoreCd(), 3900));
            storeSplyPriceHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSplyPriceHistoryVO.getProdHqBrandCd() == "" || storeSplyPriceHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSplyPriceHistoryVO.getUserProdBrands() != null && !"".equals(storeSplyPriceHistoryVO.getUserProdBrands())) {
                    String[] userBrandList = storeSplyPriceHistoryVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSplyPriceHistoryVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSplyPriceHistoryMapper.getStoreSplyPriceHistoryExcelList(storeSplyPriceHistoryVO);
    }
}
