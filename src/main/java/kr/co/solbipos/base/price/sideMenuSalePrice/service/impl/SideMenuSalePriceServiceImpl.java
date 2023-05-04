package kr.co.solbipos.base.price.sideMenuSalePrice.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import kr.co.solbipos.base.price.salePrice.service.impl.SalePriceMapper;
import kr.co.solbipos.base.price.sideMenuSalePrice.service.SideMenuSalePriceService;
import kr.co.solbipos.base.price.sideMenuSalePrice.service.SideMenuSalePriceVO;
import kr.co.solbipos.base.price.sideMenuSalePrice.service.impl.SideMenuSalePriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SideMenuSalePriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 매장별구성상품가격변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideMenuSalePriceService")
public class SideMenuSalePriceServiceImpl implements SideMenuSalePriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SideMenuSalePriceMapper sideMenuSalePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final SalePriceMapper salePriceMapper; // 본사판매가관리

    /** Constructor Injection */
    @Autowired
    public SideMenuSalePriceServiceImpl(SideMenuSalePriceMapper sideMenuSalePriceMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, SalePriceMapper salePriceMapper) {
        this.sideMenuSalePriceMapper = sideMenuSalePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
        this.salePriceMapper = salePriceMapper;
    }

    /** 선택메뉴 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSideMenuSalePriceList(SideMenuSalePriceVO sideMenuSalePriceVO, SessionInfoVO sessionInfoVO) {

        sideMenuSalePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
        if (sideMenuSalePriceVO.getProdHqBrandCd() == "" || sideMenuSalePriceVO.getProdHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            if (sideMenuSalePriceVO.getUserProdBrands() != null && !"".equals(sideMenuSalePriceVO.getUserProdBrands())) {
                String[] userBrandList = sideMenuSalePriceVO.getUserProdBrands().split(",");
                if (userBrandList.length > 0) {
                    sideMenuSalePriceVO.setUserProdBrandList(userBrandList);
                }
            }
        }
        
        return sideMenuSalePriceMapper.getSideMenuSalePriceList(sideMenuSalePriceVO);
    }

    /** 선택메뉴 가격 변경 */
    @Override
    public int saveSideMenuSalePrice(SideMenuSalePriceVO[] sideMenuSalePriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SideMenuSalePriceVO sideMenuSalePriceVO : sideMenuSalePriceVOs) {

            sideMenuSalePriceVO.setRegDt(currentDt);
            sideMenuSalePriceVO.setRegId(sessionInfoVO.getUserId());
            sideMenuSalePriceVO.setModDt(currentDt);
            sideMenuSalePriceVO.setModId(sessionInfoVO.getUserId());

            result = sideMenuSalePriceMapper.saveSideMenuSalePrice(sideMenuSalePriceVO);
        }
        return result;
    }

}