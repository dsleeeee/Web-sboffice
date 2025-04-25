package kr.co.solbipos.base.prod.pizzaEdge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.pizzaEdge.service.PizzaEdgeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PizzaEdgeMapper {

    List<DefaultMap<Object>> getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO);

    List<DefaultMap<Object>> getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO);

    List<DefaultMap<Object>> getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO);

    int getDeleteProd(PizzaEdgeVO pizzaEdgeVO);

    int getRegProd(PizzaEdgeVO pizzaEdgeVO);
}
