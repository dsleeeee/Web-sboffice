package kr.co.solbipos.store.manage.closeStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreService;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CloseStoreServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 폐점예정매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.22  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("CloseStoreService")
public class CloseStoreServiceImpl implements CloseStoreService {

    private final CloseStoreMapper closeStoreMapper;
    private final AuthMapper authMapper;

    /** Constructor Injection */
    @Autowired
    public CloseStoreServiceImpl(CloseStoreMapper closeStoreMapper, AuthMapper authMapper) {
        this.closeStoreMapper = closeStoreMapper;
        this.authMapper = authMapper;
    }

    /** 폐점예정매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCloseStoreList(CloseStoreVO closeStoreVO, SessionInfoVO sessionInfoVO) {
        return closeStoreMapper.getCloseStoreList(closeStoreVO);
    }

    @Override
    public int saveCloseStore(CloseStoreVO[] closeStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (CloseStoreVO closeStoreVO : closeStoreVOs) {
            closeStoreVO.setModDt(dt);
            closeStoreVO.setModId(sessionInfoVO.getUserId());
            result = closeStoreMapper.saveCloseStore(closeStoreVO);
        }

        return result;
    }
}
