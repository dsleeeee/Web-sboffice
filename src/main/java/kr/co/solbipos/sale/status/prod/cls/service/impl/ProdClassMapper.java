package kr.co.solbipos.sale.status.prod.cls.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdClassMapper {
    /** 상품별 매출 - 분류별 리스트 조회 */
    List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO);

}
