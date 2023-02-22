package kr.co.solbipos.sys.admin.momsBatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.sys.admin.momsBatch.service.MomsBatchService;
import kr.co.solbipos.sys.admin.momsBatch.service.MomsBatchVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MomsBatchServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > 맘스터치일괄처리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2023.02.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("MomsBatchServiceImpl")
@Transactional
public class MomsBatchServiceImpl implements MomsBatchService {

    private final MomsBatchMapper momsBatchMapper;

    public MomsBatchServiceImpl(MomsBatchMapper momsBatchMapper) {
            this.momsBatchMapper = momsBatchMapper;
    }

    /** 일괄처리 */
    @Override
    public String batchProc(MomsBatchVO momsBatchVO, SessionInfoVO sessionInfoVO) {

        momsBatchVO.setUserId(sessionInfoVO.getUserId());

        momsBatchMapper.batchProc(momsBatchVO);

        return momsBatchVO.getResult();
    }

    /** 매장코드 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreList(MomsBatchVO momsBatchVO, SessionInfoVO sessionInfoVO) {

        return momsBatchMapper.selectStoreList(momsBatchVO);
    }
}
