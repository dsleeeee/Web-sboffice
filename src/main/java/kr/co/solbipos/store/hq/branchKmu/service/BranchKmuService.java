package kr.co.solbipos.store.hq.branchKmu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BranchKmuService.java
 * @Description : 국민대 > 매장관리 > 그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BranchKmuService {

    /** 조회 */
    List<DefaultMap<String>> getBranchKmuList(BranchKmuVO branchKmuVO, SessionInfoVO sessionInfoVO);

    /** 저장 */
    int saveBranchKmu(BranchKmuVO branchKmuVO, SessionInfoVO sessionInfoVO);

}
