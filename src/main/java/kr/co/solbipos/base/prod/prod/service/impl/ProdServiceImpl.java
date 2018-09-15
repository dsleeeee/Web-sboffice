package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : ProdServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수      최초생성
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodService")
public class ProdServiceImpl implements ProdService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProdMapper mapper;

    @Autowired
    private MessageService messageService;

    @Override
    public List<DefaultMap<String>> list(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 매장인 경우
        if( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            prodVO.setStoreCd(sessionInfoVO.getOrgnCd());
            return mapper.getProdList(prodVO);
        }
        // 본사인 경우
        else if( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            return mapper.getHqProdList(prodVO);
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }
    }

    @Override
    public DefaultMap<String> detail(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 매장인 경우
        if( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            prodVO.setStoreCd(sessionInfoVO.getOrgnCd());
            return mapper.getProdDetail(prodVO);
        }
        // 본사인 경우
        else if( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            return mapper.getHqProdDetail(prodVO);
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }
    }

    @Override
    public List<DefaultMap<String>> unitstProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 매장인 경우
        if( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            prodVO.setStoreCd(sessionInfoVO.getOrgnCd());
            return mapper.getUnitstProdList(prodVO);
        }
        // 본사인 경우
        else if( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            return mapper.getHqUnitstProdList(prodVO);
        }
        else {
            return new ArrayList<>();
        }
    }

}
