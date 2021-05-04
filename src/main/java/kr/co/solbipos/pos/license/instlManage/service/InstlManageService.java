package kr.co.solbipos.pos.license.instlManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.install.service.InstallVO;

import java.util.List;

/**
 * @Class Name : InstlManageService.java
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
public interface InstlManageService {

    /** 업체현황탭 - 업체현황조회*/
    List<DefaultMap<Object>> getAgencyList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 업체현황탭 - 업체현황 상세조회*/
    List<DefaultMap<Object>> getAgencyDtlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 업체현황탭 - 매장별매출 상세조회*/
    List<DefaultMap<Object>> getAgencyPurchsList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치현황탭 - 설치현황조회*/
    List<DefaultMap<Object>> getInstlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치현황탭 - 설치현황 상세조회*/
    List<DefaultMap<Object>> getInstlDetailList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치현황탭 - 설치현황 설치정보 상세조회*/
    List<DefaultMap<Object>> getInstlDtlList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치요청 목록 상세 */
    List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO, SessionInfoVO sessionInfoVO);

    /** 설치요청 삭제 */
    int delRequestDtl(InstlManageVO[] instlManageVOs, SessionInfoVO sessionInfoVO);
}
