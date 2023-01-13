package kr.co.solbipos.base.price.storeSalePrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceService;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : SalePriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeSalePriceService")
public class StoreSalePriceServiceImpl implements StoreSalePriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreSalePriceMapper storeSalePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public StoreSalePriceServiceImpl(CmmEnvUtil cmmEnvUtil, MessageService messageService, StoreSalePriceMapper storeSalePriceMapper) {
        this.storeSalePriceMapper = storeSalePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<String>> getSalePriceList(StoreSalePriceVO storeSalePriceVO, SessionInfoVO sessionInfoVO) {

        storeSalePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(storeSalePriceVO.getStoreCd()).equals("")) {
            storeSalePriceVO.setArrStoreCd(storeSalePriceVO.getStoreCd().split(","));
        }
        storeSalePriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSalePriceVO.getProdHqBrandCd() == "" || storeSalePriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSalePriceVO.getUserProdBrands() != null && !"".equals(storeSalePriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeSalePriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSalePriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSalePriceMapper.getSalePriceList(storeSalePriceVO);
    }

    /** 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getSalePriceExcelList(StoreSalePriceVO storeSalePriceVO, SessionInfoVO sessionInfoVO) {

        storeSalePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(storeSalePriceVO.getStoreCd()).equals("")) {
            storeSalePriceVO.setArrStoreCd(storeSalePriceVO.getStoreCd().split(","));
        }
        storeSalePriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (storeSalePriceVO.getProdHqBrandCd() == "" || storeSalePriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (storeSalePriceVO.getUserProdBrands() != null && !"".equals(storeSalePriceVO.getUserProdBrands())) {
                    String[] userBrandList = storeSalePriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        storeSalePriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return storeSalePriceMapper.getSalePriceExcelList(storeSalePriceVO);
    }
}