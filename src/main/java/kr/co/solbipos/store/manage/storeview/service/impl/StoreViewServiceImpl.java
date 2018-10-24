package kr.co.solbipos.store.manage.storeview.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.storeview.service.StoreViewService;
import kr.co.solbipos.store.manage.storeview.service.StoreViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Class Name : StoreViewServiceImpl.java
* @Description : 가맹점 관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.07  김영근      최초생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.07
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("storeViewService")
public class StoreViewServiceImpl implements StoreViewService {

    private final StoreViewMapper mapper;

    /** Constructor Injection */
    @Autowired
    public StoreViewServiceImpl(StoreViewMapper mapper) {
        this.mapper = mapper;
    }

    /** 매장정보 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreViewList(StoreViewVO storeViewVO, SessionInfoVO sessionInfoVO) {

        storeViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeViewVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getStoreViewList(storeViewVO);
    }
}
