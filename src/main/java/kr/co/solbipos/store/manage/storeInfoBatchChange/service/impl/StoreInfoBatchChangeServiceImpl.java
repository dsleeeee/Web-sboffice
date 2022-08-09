package kr.co.solbipos.store.manage.storeInfoBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.storeInfoBatchChange.service.StoreInfoBatchChangeService;
import kr.co.solbipos.store.manage.storeInfoBatchChange.service.StoreInfoBatchChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : StoreInfoBatchChangeServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeInfoBatchChangeService")
@Transactional
public class StoreInfoBatchChangeServiceImpl implements StoreInfoBatchChangeService {
    private final StoreInfoBatchChangeMapper storeInfoBatchChangeMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreInfoBatchChangeServiceImpl(StoreInfoBatchChangeMapper storeInfoBatchChangeMapper) { this.storeInfoBatchChangeMapper = storeInfoBatchChangeMapper; }

    /** 매장정보일괄변경 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreInfoBatchChangeList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO) {

        return storeInfoBatchChangeMapper.getStoreInfoBatchChangeList(storeInfoBatchChangeVO);
    }

    /** 대리점코드 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getAgencyCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO) {

        return storeInfoBatchChangeMapper.getAgencyCdComboList(storeInfoBatchChangeVO);
    }

    /** 관리벤사 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getVanCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO) {

        return storeInfoBatchChangeMapper.getVanCdComboList(storeInfoBatchChangeVO);
    }

    /** 매장정보일괄변경 - 저장 */
    @Override
    public int getStoreInfoBatchChangeSave(StoreInfoBatchChangeVO[] storeInfoBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreInfoBatchChangeVO storeInfoBatchChangeVO : storeInfoBatchChangeVOs) {

            storeInfoBatchChangeVO.setModDt(currentDt);
            storeInfoBatchChangeVO.setModId(sessionInfoVO.getUserId());

            if(storeInfoBatchChangeVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = storeInfoBatchChangeMapper.getStoreInfoBatchChangeSaveUpdate(storeInfoBatchChangeVO);
            }
        }

        return procCnt;
    }
}