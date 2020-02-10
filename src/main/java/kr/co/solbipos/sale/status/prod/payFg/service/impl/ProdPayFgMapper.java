package kr.co.solbipos.sale.status.prod.payFg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdPayFgMapper {
    /** 상품별 매출 - 결제수단별 리스트 조회 */
    List<DefaultMap<String>> getProdPayFgList(ProdPayFgVO prodPayFgVO);
    
    /** 상품별 매출 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(ProdPayFgVO prodPayFgVO);
    
}
