package kr.co.solbipos.base.promotion.storeSelfPromotion.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import kr.co.solbipos.base.promotion.storeSelfPromotion.service.StoreSelfPromotionService;
import kr.co.solbipos.base.promotion.storeSelfPromotion.service.StoreSelfPromotionVO;
import kr.co.solbipos.sale.dlvr.orderChannel.service.OrderChannelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : StoreSelfPromotionServiceImpl.java
 * @Description : 기초관리 - 프로모션관리 - 매장자체프로모션현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.07  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .09. 07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("StoreSelfPromotionService")
public class StoreSelfPromotionServiceImpl implements StoreSelfPromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreSelfPromotionMapper storeSelfPromotionMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreSelfPromotionServiceImpl(StoreSelfPromotionMapper storeSelfPromotionMapper, MessageService messageService) {
        this.storeSelfPromotionMapper = storeSelfPromotionMapper;
        this.messageService = messageService;
    }

    /** 매장자체프로모션현황 - 매장자체 프로모션 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSelfPromotionList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO) {
        storeSelfPromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장코드
        if(!StringUtil.getOrBlank(storeSelfPromotionVO.getSrchStoreCd()).equals("")) {
            storeSelfPromotionVO.setArrStoreCd(storeSelfPromotionVO.getSrchStoreCd().split(","));
        }

        return storeSelfPromotionMapper.getStoreSelfPromotionList(storeSelfPromotionVO);
    }

    /** 매장자체프로모션현황 - 매장자체 프로모션 상세 조회 */
    @Override
    public DefaultMap<String> getStoreSelfPromotionDtl(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO){

        return storeSelfPromotionMapper.getStoreSelfPromotionDtl(storeSelfPromotionVO);
    }

    /** 매장자체프로모션현황 - 적용상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSelfPromotionProdList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO){

        return storeSelfPromotionMapper.getStoreSelfPromotionProdList(storeSelfPromotionVO);
    }

    /** 매장자체프로모션현황 - 혜택상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSelfPromotionPresentList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO){

        return storeSelfPromotionMapper.getStoreSelfPromotionPresentList(storeSelfPromotionVO);
    }

}
