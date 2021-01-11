package kr.co.solbipos.sale.status.prod.cls.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdClassMapper {
    /** 분류별상품탭 - 조회 */
    List<DefaultMap<String>> getProdClassList(ProdClassVO prodClassVO);
    
    /** 분류별상품탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdClassExcelList(ProdClassVO prodClassVO);
}