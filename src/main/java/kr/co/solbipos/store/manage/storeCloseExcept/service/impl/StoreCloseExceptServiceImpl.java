package kr.co.solbipos.store.manage.storeCloseExcept.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptService;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreCloseExceptServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 폐점제외매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.04.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("StoreCloseExceptService")
public class StoreCloseExceptServiceImpl implements StoreCloseExceptService {

    private final StoreCloseExceptMapper storeCloseExceptMapper;

    /** Constructor Injection */
    @Autowired
    public StoreCloseExceptServiceImpl(StoreCloseExceptMapper storeCloseExceptMapper) {
        this.storeCloseExceptMapper = storeCloseExceptMapper;
    }

    /** 밴 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getVanComboList() {
        return storeCloseExceptMapper.getVanComboList();
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreCloseExceptVO storeCloseExceptVO, SessionInfoVO sessionInfoVO) {
        return storeCloseExceptMapper.getStoreList(storeCloseExceptVO);
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCloseExceptList(StoreCloseExceptVO storeCloseExceptVO, SessionInfoVO sessionInfoVO) {
        return storeCloseExceptMapper.getStoreCloseExceptList(storeCloseExceptVO);
    }

    /** 폐점제외매장 등록 */
    @Override
    public int saveStoreCloseExcept(StoreCloseExceptVO[] storeCloseExceptVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreCloseExceptVO storeCloseExceptVO : storeCloseExceptVOs) {
            storeCloseExceptVO.setRegDt(dt);
            storeCloseExceptVO.setRegId(sessionInfoVO.getUserId());
            storeCloseExceptVO.setModDt(dt);
            storeCloseExceptVO.setModId(sessionInfoVO.getUserId());
            result = storeCloseExceptMapper.saveStoreCloseExcept(storeCloseExceptVO);
        }

        return result;
    }

    /** 폐점제외매장 삭제 */
    @Override
    public int deleteStoreCloseExcept(StoreCloseExceptVO[] storeCloseExceptVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for (StoreCloseExceptVO storeCloseExceptVO : storeCloseExceptVOs) {
            result = storeCloseExceptMapper.deleteStoreCloseExcept(storeCloseExceptVO);
        }

        return result;
    }
}
