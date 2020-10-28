package kr.co.solbipos.base.prod.corner.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;

import java.util.List;

/**
 * @Class Name : CornerService.java
 * @Description : 기초관리 - 상품관리 - 코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.27  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 02.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CornerService {

    /** 상품별 코너변경 리스트 조회 */
    List<DefaultMap<String>> getProdCornerList(CornerVO cornerVO, SessionInfoVO sessionInfoVO);

    /** 상품별 코너 이동 */
    int changeProdCorner(CornerVO[] cornerVOs, SessionInfoVO sessionInfoVO);
}
