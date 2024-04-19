package kr.co.solbipos.pos.license.runStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : RunStoreService.java
 * @Description : 포스관리 > 라이선스 관리 > 런닝매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.11  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface RunStoreService {

    /** 런닝매출현황 탭 - 조회 */
    List<DefaultMap<Object>> getRunStoreList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO);

    /** 런닝COPY수 탭- 런닝/신규/폐점 매장 수 조회 */
    DefaultMap<String> getRunCopyCnt(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO);

    /** 런닝COPY수 탭 - 조회 */
    List<DefaultMap<Object>> getRunCopyList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO);

    /** 런닝매장추이 탭 - 조회 */
    List<DefaultMap<Object>> getRunTrnsitnList(RunStoreVO runStoreVO, SessionInfoVO sessionInfoVO);
}
