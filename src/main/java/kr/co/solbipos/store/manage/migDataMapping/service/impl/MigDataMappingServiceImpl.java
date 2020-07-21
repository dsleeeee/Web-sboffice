package kr.co.solbipos.store.manage.migDataMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingService;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : MigDataMappingServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > OKPOS-KCP 데이터 이관
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("migDataMappingService")
@Transactional
public class MigDataMappingServiceImpl implements MigDataMappingService {
    private final MigDataMappingMapper migDataMappingMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MigDataMappingServiceImpl(MigDataMappingMapper migDataMappingMapper) { this.migDataMappingMapper = migDataMappingMapper; }

    /** OKPOS-KCP 데이터 이관 조회 */
    @Override
    public List<DefaultMap<Object>> getMigDataMappingList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {

        return migDataMappingMapper.getMigDataMappingList(migDataMappingVO);
    }

    /** OKPOS-KCP 사용자정보 조회 */
    @Override
    public DefaultMap<String> getOkposUserInfoList(MigDataMappingVO migDataMappingVO,  SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        resultMap = migDataMappingMapper.getOkposUserInfoList(migDataMappingVO);

        return resultMap;
    }

    /** OKPOS-KCP 매장 조회 */
    @Override
    public List<DefaultMap<Object>> getMigDataMappingInfoList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {

        return migDataMappingMapper.getMigDataMappingInfoList(migDataMappingVO);
    }

    /** OKPOS-KCP 데이터 이관 저장 */
    @Override
    public int getMigDataMappingInfoSave(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();

        migDataMappingVO.setRegDt(currentDt);
        migDataMappingVO.setRegId(sessionInfoVO.getUserId());
        migDataMappingVO.setModDt(currentDt);
        migDataMappingVO.setModId(sessionInfoVO.getUserId());
        migDataMappingVO.setDate(currentDate);

        procCnt = migDataMappingMapper.getMigDataMappingInfoSaveInsert(migDataMappingVO);

        return procCnt;
    }
}