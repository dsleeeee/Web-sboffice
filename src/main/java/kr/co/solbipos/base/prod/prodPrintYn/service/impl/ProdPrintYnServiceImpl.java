package kr.co.solbipos.base.prod.prodPrintYn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodPrintYn.service.ProdPrintYnService;
import kr.co.solbipos.base.prod.prodPrintYn.service.ProdPrintYnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdPrintYnServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodPrintYnService")
@Transactional
public class ProdPrintYnServiceImpl implements ProdPrintYnService {
    private final ProdPrintYnMapper prodPrintYnMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdPrintYnServiceImpl(ProdPrintYnMapper prodPrintYnMapper) { this.prodPrintYnMapper = prodPrintYnMapper; }

    /** 옵션관리 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdOptionPrintYnList(ProdPrintYnVO prodPrintYnVO, SessionInfoVO sessionInfoVO) {

        prodPrintYnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodPrintYnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodPrintYnMapper.getProdOptionPrintYnList(prodPrintYnVO);
    }

    /** 옵션관리 탭 - 저장 */
    @Override
    public int getProdOptionPrintYnSave(ProdPrintYnVO[] prodPrintYnVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ProdPrintYnVO prodPrintYnVO : prodPrintYnVOs) {

            prodPrintYnVO.setModDt(currentDt);
            prodPrintYnVO.setModId(sessionInfoVO.getUserId());

            prodPrintYnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                prodPrintYnVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            procCnt = prodPrintYnMapper.getProdOptionPrintYnSaveUpdate(prodPrintYnVO);
        }

        return procCnt;
    }

    /** 사이드메뉴관리 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSideMenuProdPrintYnList(ProdPrintYnVO prodPrintYnVO, SessionInfoVO sessionInfoVO) {

        prodPrintYnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodPrintYnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodPrintYnMapper.getSideMenuProdPrintYnList(prodPrintYnVO);
    }

    /** 사이드메뉴관리 탭 - 저장 */
    @Override
    public int getSideMenuProdPrintYnSave(ProdPrintYnVO[] prodPrintYnVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ProdPrintYnVO prodPrintYnVO : prodPrintYnVOs) {

            prodPrintYnVO.setModDt(currentDt);
            prodPrintYnVO.setModId(sessionInfoVO.getUserId());

            prodPrintYnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                prodPrintYnVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            procCnt = prodPrintYnMapper.getSideMenuProdPrintYnSaveUpdate(prodPrintYnVO);

            // 본사에서 접속시
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                procCnt = prodPrintYnMapper.getStoreSideMenuProdPrintYnSaveUpdate(prodPrintYnVO);
            }
        }

        return procCnt;
    }
}