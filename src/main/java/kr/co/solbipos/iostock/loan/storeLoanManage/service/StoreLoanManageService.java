package kr.co.solbipos.iostock.loan.storeLoanManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreLoanManageService.java
 * @Description : 수불관리 > 수주관리 > 매장여신관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreLoanManageService {
    /** 매장여신 목록 조회 */
    List<DefaultMap<String>> getStoreLoanManageList(StoreLoanManageVO storeLoanManageVO);

    /** 매장여신 저장 */
    int saveLoanManageList(StoreLoanManageVO[] storeLoanManageVOs, SessionInfoVO sessionInfoVO);

    /** 매장여신 삭제 */
    int delLoanManageList(StoreLoanManageVO[] storeLoanManageVOs, SessionInfoVO sessionInfoVO);

    /** 매장여신 상세 현황 조회 */
    List<DefaultMap<String>> getStoreLoanManageDtlList(StoreLoanManageVO storeLoanManageVO);

}
