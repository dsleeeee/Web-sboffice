package kr.co.solbipos.pos.license.instlManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("instlManageService")
@Transactional
public class InstlManageServiceImpl implements InstlManageService {

    private final InstlManageMapper instlManageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public InstlManageServiceImpl(InstlManageMapper instlManageMapper, MessageService messageService) {
        this.instlManageMapper = instlManageMapper;
        this.messageService = messageService;
    }

    /** 설치요청 목록 조회 */
    @Override
    public List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        instlManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        instlManageVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY && !sessionInfoVO.getpAgencyCd().equals("00000")) {
            instlManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return instlManageMapper.getInstlRequestList(instlManageVO);
    }

    /** 설치요청 목록 상세 */
    @Override
    public List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO) {
        return instlManageMapper.getInstlRequestDtl(instlManageVO);
    }
}
