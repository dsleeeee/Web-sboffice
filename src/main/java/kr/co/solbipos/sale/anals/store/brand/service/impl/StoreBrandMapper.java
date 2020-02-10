package kr.co.solbipos.sale.anals.store.brand.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.store.brand.service.StoreBrandVO;

@Mapper
@Repository
public interface StoreBrandMapper {
	
	/** 브랜드별 매출 - 브랜드별 매출 리스트 조회  */
    List<DefaultMap<String>> getStoreBrandList(StoreBrandVO storeBrandVO);
    
    /** 브랜드별 매출 - 조회조건 정렬구분 리스트 조회 */
    List<DefaultMap<String>> getSortFgComboList(StoreBrandVO storeBrandVO);
}
