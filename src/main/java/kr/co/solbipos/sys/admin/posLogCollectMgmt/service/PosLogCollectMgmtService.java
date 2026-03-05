package kr.co.solbipos.sys.admin.posLogCollectMgmt.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : PosLogCollectMgmtService.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface PosLogCollectMgmtService {

    /** POS로그수집관리 - 조회 */
    List<DefaultMap<Object>> getSearchPosLogList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO);

    /** POS로그수집등록 팝업 - 조회 */
    List<DefaultMap<Object>> getSearchStoreList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO);

    /** POS로그수집등록 팝업 - POS로그 저장 */
    int savePosLog(PosLogCollectMgmtVO[] posLogCollectMgmtVOs, SessionInfoVO sessionInfoVO);
}
