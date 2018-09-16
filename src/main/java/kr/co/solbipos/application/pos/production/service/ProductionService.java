package kr.co.solbipos.application.pos.production.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.pos.exceptForward.service.ExcpForwardProductVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProductionService.java
 * @Description : POS 화면에서 생산량 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.16  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProductionService {

    /** 생산량 등록 대상 상품목록 조회 */
    List<DefaultMap<String>> getProductList(ProductionVO productionVO,
        SessionInfoVO sessionInfoVO);

}
