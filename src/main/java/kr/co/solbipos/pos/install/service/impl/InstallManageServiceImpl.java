package kr.co.solbipos.pos.install.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.install.enums.InstallFg;
import kr.co.solbipos.pos.install.service.InstallManageService;
import kr.co.solbipos.pos.install.service.InstallVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : InstallManageServiceImpl.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2019.01.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("installManageService")
public class InstallManageServiceImpl implements InstallManageService {

    private final InstallManageMapper installManageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public InstallManageServiceImpl(InstallManageMapper installManageMapper, MessageService messageService) {
        this.installManageMapper = installManageMapper;
        this.messageService = messageService;
    }

    /** 설치요청 목록 조회 */
    @Override
    public List<DefaultMap<String>> getInstallRequestList(InstallVO installVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        installVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        installVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY && !sessionInfoVO.getpAgencyCd().equals("00000")) {
            installVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return installManageMapper.getInstallRequestList(installVO);
    }

    /** 포스 목록 */
    @Override
    public List<DefaultMap<String>> getPosList(InstallVO installVO) {
        return installManageMapper.getPosList(installVO);
    }

    /** 설치요청 등록 */
    @Override
    public int saveInstallRequest(InstallVO[] installVOs, SessionInfoVO sessionInfoVO ) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(InstallVO installVO : installVOs){

            installVO.setInstFg(InstallFg.REQ); // 요청
            installVO.setInstReqDt(currentDt);
            installVO.setInstReqId(sessionInfoVO.getUserId());
            installVO.setRegDt(currentDt);
            installVO.setRegId(sessionInfoVO.getUserId());
            installVO.setModDt(currentDt);
            installVO.setModId(sessionInfoVO.getUserId());


            // 설치요청 등록
            result = installManageMapper.saveInstallRequest(installVO);

            // 포스 H/W인증키 초기화
            result = installManageMapper.initPosHwKey(installVO);

        }

        return result;
    }
}
