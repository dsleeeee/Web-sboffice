package kr.co.solbipos.pos.license.instlAgency.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpVO;

import java.util.List;

public interface InstlAgencyService {
    /** 설치업체관리 조회 */
    List<DefaultMap<String>> getInstlAgency(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);

    /** 설치업체관리 상세정보 조회 */
    DefaultMap<String> getInstlAgencyDtl(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);

    /** 설치업체관리 저장 */
    int saveAgency(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);

    /** 설치업체 사원목록 조회 */
    List<DefaultMap<String>> getAgencyEmp(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);

    /** 설치업체 사원상세 조회 */
    DefaultMap<String> getAgencyEmpDtl(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);

    /** 설치업체 사원저장 */
    EmpResult saveAgencyEmp(InstlAgencyVO instlAgencyVO, SessionInfoVO sessionInfoVO);
}
