package kr.co.solbipos.pos.license.runStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.runStore.service.RunStoreService;
import kr.co.solbipos.pos.license.runStore.service.RunStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : RunStoreServiceImpl.java
 * @Description : 포스관리 > 라이선스 관리 > 런닝매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.11  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("RunStoreService")
@Transactional
public class RunStoreServiceImpl implements RunStoreService {

    private final RunStoreMapper runStoreMapper;

    public RunStoreServiceImpl(RunStoreMapper runStoreMapper) {
        this.runStoreMapper = runStoreMapper;
    }

    /** 런닝매출현황 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getRunStoreList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO) {
        return runStoreMapper.getRunStoreList(runStoreVO);
    }

    /** 런닝COPY수 - 런닝/신규/폐점 매장 수 조회 */
    @Override
    public DefaultMap<String> getRunCopyCnt(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO) {

        return runStoreMapper.getRunCopyCnt(runStoreVO);
    }

    /** 런닝COPY수 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getRunCopyList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO) {
        return runStoreMapper.getRunCopyList(runStoreVO);
    }

    /** 런닝COPY수 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getRunTrnsitnList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO) {
        return runStoreMapper.getRunTrnsitnList(runStoreVO);
    }
}
