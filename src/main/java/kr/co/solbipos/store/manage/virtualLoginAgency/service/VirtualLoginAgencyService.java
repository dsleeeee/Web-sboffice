package kr.co.solbipos.store.manage.virtualLoginAgency.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VirtualLoginAgencyService.java
 * @Description : 기초관리 > 총판/대리점 가상로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.31  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.31
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VirtualLoginAgencyService {

    /** 총판/대리점 가상로그인 조회 */
    List<DefaultMap<Object>> getVirtualLoginAgencyrList(VirtualLoginAgencyVO virtualLoginAgencyVO, SessionInfoVO sessionInfoVO);
}