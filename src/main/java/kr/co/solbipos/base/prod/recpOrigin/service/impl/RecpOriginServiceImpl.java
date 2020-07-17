package kr.co.solbipos.base.prod.recpOrigin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginService;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : RecpOriginServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("recpOriginService")
@Transactional
public class RecpOriginServiceImpl implements RecpOriginService {
    private final RecpOriginMapper recpOriginMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public RecpOriginServiceImpl(RecpOriginMapper recpOriginMapper) {
        this.recpOriginMapper = recpOriginMapper;
    }

    /** 원산지관리 조회 */
    @Override
    public List<DefaultMap<Object>> getRecpOriginList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO) {

        recpOriginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        recpOriginVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            recpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return recpOriginMapper.getRecpOriginList(recpOriginVO);
    }

    /** 원산지관리 저장 */
    @Override
    public int getRecpOriginSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(RecpOriginVO recpOriginVO : recpOriginVOs) {

            recpOriginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            recpOriginVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                recpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            recpOriginVO.setModDt(currentDt);
            recpOriginVO.setModId(sessionInfoVO.getUserId());

            if (recpOriginVO.getStatus() == GridDataFg.INSERT) {
                recpOriginVO.setRegDt(currentDt);
                recpOriginVO.setRegId(sessionInfoVO.getUserId());

                // 재료코드 (자동 채번)
                String RecipesCd = recpOriginMapper.getRecpOriginRecipesCd(recpOriginVO);
                recpOriginVO.setRecipesCd(RecipesCd);

                procCnt = recpOriginMapper.getRecpOriginSaveInsert(recpOriginVO);

            } else if(recpOriginVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = recpOriginMapper.getRecpOriginSaveUpdate(recpOriginVO);

            } else if (recpOriginVO.getStatus() == GridDataFg.DELETE) {
                procCnt = recpOriginMapper.getRecpOriginSaveDelete(recpOriginVO);

                // 원산지관리 저장 delete 시, 재료-상품 저장 delete
                procCnt = recpOriginMapper.getRecpProdSaveDeleteAll(recpOriginVO);
            }
        }

        return procCnt;
    }

    /** 재료-상품 조회 */
    @Override
    public List<DefaultMap<Object>> getRecpOriginDetailList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO) {

        recpOriginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        recpOriginVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            recpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return recpOriginMapper.getRecpOriginDetailList(recpOriginVO);
    }

    /** 재료-상품 등록 팝업 - 상품조회 */
    @Override
    public List<DefaultMap<Object>> getRecpProdList(RecpOriginVO recpOriginVO, SessionInfoVO sessionInfoVO) {

        recpOriginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        recpOriginVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            recpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return recpOriginMapper.getRecpProdList(recpOriginVO);
    }

    /** 재료-상품 저장 */
    @Override
    public int getRecpOriginDetailSave(RecpOriginVO[] recpOriginVOs, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(RecpOriginVO recpOriginVO : recpOriginVOs) {
            recpOriginVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            recpOriginVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                recpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            recpOriginVO.setModDt(currentDt);
            recpOriginVO.setModId(sessionInfoVO.getUserId());

            if (recpOriginVO.getStatus() == GridDataFg.INSERT) {
                recpOriginVO.setRegDt(currentDt);
                recpOriginVO.setRegId(sessionInfoVO.getUserId());

                procCnt = recpOriginMapper.getRecpProdSaveInsert(recpOriginVO);

            } else if (recpOriginVO.getStatus() == GridDataFg.DELETE) {
                procCnt = recpOriginMapper.getRecpProdSaveDelete(recpOriginVO);
            }
        }

        return procCnt;
    }
}