package kr.co.solbipos.base.prod.pizzaEdge.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PizzaEdgeService.java
 * @Description : 미스터피자 > 상품관리 > 피자-엣지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PizzaEdgeService {

    /** 피자-엣지관리 - 피자 조회 */
    List<DefaultMap<Object>> getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

    /** 피자-엣지관리 - 등록상품 조회 */
    List<DefaultMap<Object>> getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

     /** 피자-엣지관리 - 미등록상품 조회 */
    List<DefaultMap<Object>> getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

    /** 피자-엣지관리 - 등록상품 삭제 */
    int getDeleteProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO);

    /** 피자-엣지관리 - 상품등록 */
    int getRegProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO);
}
