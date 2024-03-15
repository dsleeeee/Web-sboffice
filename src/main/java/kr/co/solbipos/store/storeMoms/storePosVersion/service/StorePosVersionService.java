package kr.co.solbipos.store.storeMoms.storePosVersion.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StorePosVersionService.java
 * @Description : 맘스터치 > 매장관리 > 매장포스버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.30  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface StorePosVersionService {

    /** 조회 */
    List<DefaultMap<String>> getStorePosVersionList(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfoVO);

    /** 포스버전 조회 */
    List<DefaultMap<String>> getSelectVerList(SessionInfoVO sessionInfoVO);

    /** 포스용도 조회 */
    List<DefaultMap<String>> getSelectSubPos();

    /** 패치정보 상세 팝업 */
    List<DefaultMap<String>> getPatchDtlList(StorePosVersionVO storePosVersionVO);

    /** 버전 적용 매장 등록 */
    int registStore(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfo);

    /** 포스패치로그 조회 */
    List<DefaultMap<String>> getPosPatchLogList(StorePosVersionVO storePosVersionVO, SessionInfoVO sessionInfoVO);
}
