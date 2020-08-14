package kr.co.solbipos.base.prod.menuRank.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

import java.util.List;

/**
 * @Class Name : MenuRankService.java
 * @Description : 기초관리 - 상품관리 - 메뉴 순위 표시 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.06  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface MenuRankService {

    /**  메뉴 순위 표시 관리 조회 */
    List<DefaultMap<String>> getRankInfo(MenuRankVO menuRankVO, SessionInfoVO sessionInfoVO);

    /**  메뉴 순위 표시 사용/미사용 매장 조회 */
    List<DefaultMap<String>> getRegStore(MenuRankVO menuRankVO, SessionInfoVO sessionInfoVO);

    /** 메뉴 순위 표시 미사용 처리 */
    int deleteStore(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO);

    /** 메뉴 순위 표시 사용 처리 */
    int insertStore(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO);

    /** 메뉴 순위 표시 관리 저장 */
    int saveRankUse(MenuRankVO[] menuRanks, SessionInfoVO sessionInfoVO);

}
