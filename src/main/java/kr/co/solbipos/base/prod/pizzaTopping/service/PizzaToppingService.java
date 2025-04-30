package kr.co.solbipos.base.prod.pizzaTopping.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PizzaToppingController.java
 * @Description : 미스터피자 > 상품관리 > 피자-토핑관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PizzaToppingService {

    /** 피자-토핑관리 - 조회 */
    List<DefaultMap<Object>> getPizzaToppingList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO);

    /** 피자-토핑관리 - 등록 상품 조회 */
    List<DefaultMap<Object>> getPizzaToppingProdList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO);

    /** 피자-토핑관리 - 미등록 상품 조회 */
    List<DefaultMap<Object>> getPizzaToppingNoProdList(PizzaToppingVO pizzaToppingVO, SessionInfoVO sessionInfoVO);

    /** 피자-토핑관리 - 상품 저장 */
    int getPizzaToppingProdSave(PizzaToppingVO[] pizzaToppingVOs, SessionInfoVO sessionInfoVO);
}