package kr.co.solbipos.base.prod.pizzaEdge.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PizzaEdgeService {

    List<DefaultMap<Object>> getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<Object>> getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<Object>> getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO, SessionInfoVO sessionInfoVO);

    int getDeleteProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO);

    int getRegProd(PizzaEdgeVO[] pizzaEdgeVOs, SessionInfoVO sessionInfoVO);
}
