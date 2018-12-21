package kr.co.solbipos.application.pos.exceptForward.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;

import java.util.List;

/**
 * @Class Name : ExceptForwardService.java
 * @Description : POS 화면에서 예외출고 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.14  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ExceptForwardService {

    /** 예외출고용 상품분류 조회 */
    List<ProductClassVO> getProdClassTree(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 예외출고 대상 상품목록 조회 */
    List<DefaultMap<String>> getExcpForwardProduct(ExcpForwardProductVO excpForwardProductVO, SessionInfoVO sessionInfoVO);

    /** 예외출고 수량 저장 */
    int saveExcpForwardProduct(ExcpForwardProductVO productVO, SessionInfoVO sessionInfoVO);

}
