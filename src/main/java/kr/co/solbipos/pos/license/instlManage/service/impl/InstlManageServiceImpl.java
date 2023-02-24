package kr.co.solbipos.pos.license.instlManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : InstlManageServiceImpl.java
 * @Description : 포스관리 > 라이선스관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("instlManageService")
@Transactional
public class InstlManageServiceImpl implements InstlManageService {
    private final InstlManageMapper instlManageMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public InstlManageServiceImpl(InstlManageMapper instlManageMapper, MessageService messageService) {
        this.instlManageMapper = instlManageMapper;
        this.messageService = messageService;
    }

    /** 업체현황탭 - 업체현황조회 */
    @Override
    public List<DefaultMap<Object>> getAgencyList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getAgencyList(instlManageVO);
    }

    /** 업체현황탭 - 업체현황 상세조회 */
    @Override
    public List<DefaultMap<Object>> getAgencyDtlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getAgencyDtlList(instlManageVO);
    }

    /** 업체현황탭 - 매장별매출 상세조회 */
    @Override
    public List<DefaultMap<Object>> getAgencyPurchsList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getAgencyPurchsList(instlManageVO);
    }

    /** 설치현황탭 - 설치현황조회 */
    @Override
    public List<DefaultMap<Object>> getInstlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getInstlList(instlManageVO);
    }

    /** 설치현황탭 - 설치현황 상세조회 */
    @Override
    public List<DefaultMap<Object>> getInstlDetailList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getInstlDetailList(instlManageVO);
    }

    /** 설치현황탭 - 설치현황 설치정보 상세조회 */
    @Override
    public List<DefaultMap<Object>> getInstlDtlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getInstlDtlList(instlManageVO);
    }

    /** 설치요청 목록 조회 */
    @Override
    public List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            instlManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return instlManageMapper.getInstlRequestList(instlManageVO);
    }

    /** 설치요청 목록 상세 */
    @Override
    public List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return instlManageMapper.getInstlRequestDtl(instlManageVO);
    }

    /** 설치요청 목록 삭제 */
    @Override
    public int delRequestDtl(InstlManageVO[] instlManageVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        for(InstlManageVO instlManageVO : instlManageVOs){
            result += instlManageMapper.delRequestDtl(instlManageVO);
        }
        return result;
    }

    /** 본사일때 대리점정보 */
    @Override
    public List<DefaultMap<String>> getAgencyInfo(SessionInfoVO sessionInfoVO) {
        return instlManageMapper.getAgencyInfo(sessionInfoVO);
    }

}