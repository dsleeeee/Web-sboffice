package kr.co.solbipos.base.price.salePrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceService;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
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
@Service("salePriceService")
public class SalePriceServiceImpl implements SalePriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SalePriceMapper salePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceServiceImpl(SalePriceMapper salePriceMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.salePriceMapper = salePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceMapper.getProdInfo(salePriceVO);
    }

    /** 상품별 매장 판매가 조회 */
    @Override
    public List<DefaultMap<String>> getProdSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(salePriceVO.getStoreCd()).equals("")) {
            salePriceVO.setArrStoreCd(salePriceVO.getStoreCd().split(","));
        }

        return salePriceMapper.getProdSalePriceList(salePriceVO);
    }

}
