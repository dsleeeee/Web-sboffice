package kr.co.solbipos.base.prod.storeProdPrintYn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.storeProdPrintYn.service.StoreProdPrintYnService;
import kr.co.solbipos.base.prod.storeProdPrintYn.service.StoreProdPrintYnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreProdPrintYnServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 매장별출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeProdPrintYnService")
@Transactional
public class StoreProdPrintYnServiceImpl implements StoreProdPrintYnService {
    private final StoreProdPrintYnMapper storeProdPrintYnMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreProdPrintYnServiceImpl(StoreProdPrintYnMapper storeProdPrintYnMapper) { this.storeProdPrintYnMapper = storeProdPrintYnMapper; }


    /** 옵션관리 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreProdOptionPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, SessionInfoVO sessionInfoVO) {

        storeProdPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdPrintYnMapper.getStoreProdOptionPrintYnList(storeProdPrintYnVO);
    }

    /** 옵션관리 탭 - 저장 */
    @Override
    public int getStoreProdOptionPrintYnSave(StoreProdPrintYnVO[] storeProdPrintYnVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdPrintYnVO storeProdPrintYnVO : storeProdPrintYnVOs) {

            storeProdPrintYnVO.setModDt(currentDt);
            storeProdPrintYnVO.setModId(sessionInfoVO.getUserId());

            procCnt = storeProdPrintYnMapper.getStoreProdOptionPrintYnSaveUpdate(storeProdPrintYnVO);
        }

        return procCnt;
    }

    /** 사이드메뉴관리 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreSideMenuProdPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, SessionInfoVO sessionInfoVO) {

        storeProdPrintYnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdPrintYnMapper.getStoreSideMenuProdPrintYnList(storeProdPrintYnVO);
    }

    /** 사이드메뉴관리 탭 - 저장 */
    @Override
    public int getStoreSideMenuProdPrintYnSave(StoreProdPrintYnVO[] storeProdPrintYnVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdPrintYnVO storeProdPrintYnVO : storeProdPrintYnVOs) {

            storeProdPrintYnVO.setModDt(currentDt);
            storeProdPrintYnVO.setModId(sessionInfoVO.getUserId());

            procCnt = storeProdPrintYnMapper.getStoreSideMenuProdPrintYnSaveUpdate(storeProdPrintYnVO);
        }

        return procCnt;
    }
}