package kr.co.solbipos.store.hq.branchMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BranchMomsService.java
 * @Description : 가맹점관리 > 본사정보 > 본사-지사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.23  권지현      최초생성
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2022.09.23
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface BranchMomsService {

    /** 조회 */
    List<DefaultMap<String>> getBranchMomsList(BranchMomsVO branchMomsVO, SessionInfoVO sessionInfoVO);

    /** 저장 */
    int saveBranchMoms(BranchMomsVO branchMomsVO, SessionInfoVO sessionInfoVO);

}
