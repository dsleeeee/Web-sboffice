package kr.co.solbipos.store.manage.migDataMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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
 * @author 솔비포스 개발본부 WEB개발팀 김설아
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

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        migDataMappingVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        migDataMappingVO.setUserId(sessionInfoVO.getUserId());

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

//        System.out.println("test1111" + migDataMappingVO.getSolbiStoreCd());
        procCnt = migDataMappingMapper.getMigDataMappingInfoSaveInsert(migDataMappingVO);

        return procCnt;
    }

    /** SOLBI 매장코드 조회 */
    @Override
    public DefaultMap<String> getMigDataMappingSolbiStoreCdList(MigDataMappingVO migDataMappingVO,  SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        resultMap = migDataMappingMapper.getMigDataMappingSolbiStoreCdList(migDataMappingVO);

        return resultMap;
    }

    /** 매출재이관 */
    @Override
    public int getMigDataMappingSaleAgainSave(MigDataMappingVO[] migDataMappingVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(MigDataMappingVO migDataMappingVO : migDataMappingVOs) {
//            System.out.println("test1111" + migDataMappingVO.getOkposStoreCd());
            procCnt = migDataMappingMapper.getMigDataMappingSaleAgainSave(migDataMappingVO);
        }

        return procCnt;
    }

    /** NXPOS1 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getNxMigDataMappingList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){
            migDataMappingVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        migDataMappingVO.setUserId(sessionInfoVO.getUserId());

        return migDataMappingMapper.getNxMigDataMappingList(migDataMappingVO);
    }

    /** NXPOS1 탭 - 매장 조회 */
    @Override
    public List<DefaultMap<Object>> getNxMigDataMappingInfoList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){
            migDataMappingVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return migDataMappingMapper.getNxMigDataMappingInfoList(migDataMappingVO);
    }

    /** NXPOS1 탭 - 이관 등록 */
    @Override
    public int getNxMigDataMappingInfoSave(MigDataMappingVO[] migDataMappingVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDay = currentDateString();

        for(MigDataMappingVO migDataMappingVO : migDataMappingVOs) {

            migDataMappingVO.setRegDt(currentDt);
            migDataMappingVO.setRegId(sessionInfoVO.getUserId());
            migDataMappingVO.setModDt(currentDt);
            migDataMappingVO.setModId(sessionInfoVO.getUserId());
            migDataMappingVO.settSaleDate(currentDay);
            migDataMappingVO.setConvRegDate(currentDay);

            storeCnt = migDataMappingMapper.getNxMigDataMappingInfoSave(migDataMappingVO);
        }

        return storeCnt;
    }

    /** 제우스 탭 - 매장 조회 */
    @Override
    public List<DefaultMap<Object>> getZeusDataMappingList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {
        return migDataMappingMapper.getZeusDataMappingList(migDataMappingVO);
    }

    /** 제우스 탭 - 연동 매장 삭제 */
    @Override
    public int getDeleteStoreMapping(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;

        storeCnt = migDataMappingMapper.getDeleteZeusStoreMapping(migDataMappingVO);
        storeCnt = migDataMappingMapper.getDeleteLynkStoreMapping(migDataMappingVO);

        return storeCnt;
    }

    /** 제우스 탭 - 매장연동신청팝업 매장 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchZeusStoreList(MigDataMappingVO migDataMappingVO, SessionInfoVO sessionInfoVO) {
        return migDataMappingMapper.getSearchZeusStoreList(migDataMappingVO);
    }

    /** 제우스 탭 - 매장연동신청팝업 매장 등록 */
    @Override
    public int getStoreMappingReg(MigDataMappingVO[] migDataMappingVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDay = currentDateString();

        for(MigDataMappingVO migDataMappingVO : migDataMappingVOs) {

            migDataMappingVO.setRegDt(currentDt);
            migDataMappingVO.setRegId(sessionInfoVO.getUserId());
            migDataMappingVO.setModDt(currentDt);
            migDataMappingVO.setModId(sessionInfoVO.getUserId());

            migDataMappingMapper.getStoreEnvstReg(migDataMappingVO);

            storeCnt = migDataMappingMapper.getZeusStoreMappingReg(migDataMappingVO);
            storeCnt = migDataMappingMapper.getLynkStoreMappingReg(migDataMappingVO);
        }

        return storeCnt;
    }

}