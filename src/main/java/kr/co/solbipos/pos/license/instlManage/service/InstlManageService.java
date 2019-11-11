package kr.co.solbipos.pos.license.instlManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.install.service.InstallVO;

import java.util.List;

public interface InstlManageService {
    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치요청 목록 상세 */
    List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);
}
