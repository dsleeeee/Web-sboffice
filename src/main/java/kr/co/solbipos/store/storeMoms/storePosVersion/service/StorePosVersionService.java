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
    List<DefaultMap<String>> getSelectVerList();

    /** 포스용도 조회 */
    List<DefaultMap<String>> getSelectSubPos();
}
