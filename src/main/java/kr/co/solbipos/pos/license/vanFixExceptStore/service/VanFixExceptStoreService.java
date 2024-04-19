package kr.co.solbipos.pos.license.vanFixExceptStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VanFixExceptStoreService.java
 * @Description : 포스관리 > 라이선스 관리 > VAN사 변경허용 매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.09  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VanFixExceptStoreService {

    /** 밴사코드 조회 */
    List<DefaultMap<String>> getVanComboList();

    /** VAN사 변경허용매장 조회 */
    List<DefaultMap<String>> getVanFixExceptStore(VanFixExceptStoreVO vanFixExceptStoreVO, SessionInfoVO sessionInfoVO);

    /** 매장 조회 */ 
    List<DefaultMap<String>> getStoreList(VanFixExceptStoreVO vanFixExceptStoreVO, SessionInfoVO sessionInfoVO);

    /** 변경허용매장 등록 */
    int saveFixExceptStore(VanFixExceptStoreVO[] vanFixExceptStoreVOs, SessionInfoVO sessionInfoVO);

    /** 변경허용매장 삭제 */
    int deleteFixExceptStore(VanFixExceptStoreVO[] vanFixExceptStoreVOs, SessionInfoVO sessionInfoVO);
}
