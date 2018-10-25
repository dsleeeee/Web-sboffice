package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : ProdServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       생성자 주입, 상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodService")
public class ProdServiceImpl implements ProdService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProdMapper prodMapper;

    @Autowired
    public ProdServiceImpl(ProdMapper prodMapper) {
        this.prodMapper = prodMapper;
    }

    @Override
    public List<DefaultMap<String>> list(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodMapper.getProdList(prodVO);
    }

    @Override
    public DefaultMap<String> detail(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodMapper.getProdDetail(prodVO);
    }

    @Override
    public List<DefaultMap<String>> unitstProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodMapper.getUnitstProdList(prodVO);
    }

}
