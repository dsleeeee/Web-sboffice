package kr.co.solbipos.base.prod.prodBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeService;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdBatchChangeServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 상품정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 20201.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodBatchChangeService")
@Transactional
public class ProdBatchChangeServiceImpl implements ProdBatchChangeService {
    private final ProdBatchChangeMapper prodBatchChangeMapper;
    private final ProdMapper prodMapper; // 상품등록

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdBatchChangeServiceImpl(ProdBatchChangeMapper prodBatchChangeMapper, ProdMapper prodMapper) {
        this.prodBatchChangeMapper = prodBatchChangeMapper;
        this.prodMapper = prodMapper;
    }

    /** 상품정보일괄변경 조회 */
    @Override
    public List<DefaultMap<Object>> getProdBatchChangeList(ProdBatchChangeVO prodBatchChangeVO, SessionInfoVO sessionInfoVO) {

        prodBatchChangeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodBatchChangeVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodBatchChangeMapper.getProdBatchChangeList(prodBatchChangeVO);
    }

    /** 상품정보일괄변경 저장 */
    @Override
    public int getProdBatchChangeSave(ProdBatchChangeVO[] prodBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ProdBatchChangeVO prodBatchChangeVO : prodBatchChangeVOs) {

            prodBatchChangeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodBatchChangeVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodBatchChangeVO.setModDt(currentDt);
            prodBatchChangeVO.setModId(sessionInfoVO.getUserId());

             if(prodBatchChangeVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = prodBatchChangeMapper.getProdBatchChangeSaveUpdate(prodBatchChangeVO);

                 // ProdVO
//                 ProdVO prodVO = new ProdVO();
//                 prodVO.setHqOfficeCd(prodBatchChangeVO.getHqOfficeCd());
//                 prodVO.setProdCd(prodBatchChangeVO.getProdCd());
//                 prodVO.setRegId(sessionInfoVO.getUserId());

                 // 본사인 경우 매장에 수정정보 내려줌
                 if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                     // 상품정보 매장에 UPDATE
//                     String procResult = prodMapper.updateHqProdToStoreProd(prodVO);
                     // 상품정보 매장에 UPDATE
                     procCnt = prodBatchChangeMapper.getProdBatchChangeSaveStoreUpdate(prodBatchChangeVO);
                 }
            }
        }

        return procCnt;
    }

}