package kr.co.solbipos.sale.anals.abc.abc.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.abc.abc.service.AbcVO;

@Mapper
@Repository
public interface AbcMapper {
	
	/**상픔ABC분석 - 상픔ABC분석 리스트 조회   */
    List<DefaultMap<String>> getAbcList(AbcVO abcVO);

}
