package kr.co.solbipos.store.manage.status.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.status.service.StoreStatusService;
import kr.co.solbipos.store.manage.status.service.StoreStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreStatusServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeStatusService")
@Transactional
public class StoreStatusServiceImpl implements StoreStatusService {
    private final StoreStatusMapper storeStatusMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreStatusServiceImpl(StoreStatusMapper storeStatusMapper) {
        this.storeStatusMapper = storeStatusMapper;
    }

    /** 매장탭 - 매장정보조회 */
    @Override
    public List<DefaultMap<Object>> getStatusStoreList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusStoreList(storeStatusVO);
    }

    /** 매장탭 - 코너 상세조회 */
    @Override
    public List<DefaultMap<Object>> getStatusStoreCornerList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusStoreCornerList(storeStatusVO);
    }

    /** 관리업체탭 - 관리업체 조회 */
    @Override
    public List<DefaultMap<Object>> getStatusAgencyList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusAgencyList(storeStatusVO);
    }

    /** 관리업체탭 - 관리업체 상세조회 */
    @Override
    public List<DefaultMap<Object>> getStatusAgencyDetailList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusAgencyDetailList(storeStatusVO);
    }

    /** VAN사탭 - VAN사 조회 */
    @Override
    public List<DefaultMap<Object>> getStatusVanList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusVanList(storeStatusVO);
    }

    /** VAN사탭 - VAN사 상세조회 */
    @Override
    public List<DefaultMap<Object>> getStatusVanDetailList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusVanDetailList(storeStatusVO);
    }

    /** POS설치현황탭 - POS설치현황 조회 */
    @Override
    public List<DefaultMap<Object>> getStatusPosInstallList(StoreStatusVO storeStatusVO, SessionInfoVO sessionInfoVO) {

        return storeStatusMapper.getStatusPosInstallList(storeStatusVO);
    }
}
