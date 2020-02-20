package kr.co.solbipos.sale.anals.versusPeriod.cls.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.versusPeriod.cls.service.VersusPeriodClassVO;

@Mapper
@Repository
public interface VersusPeriodClassMapper {
    /** 대비기간매출분석 - 분류상품별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodClassList(VersusPeriodClassVO versusPeriodClassVO);
    /** 대비기간매출분석 - 분류상품별 리스트 상세 조회 */
	List<DefaultMap<String>> getVersusPeriodClassDtlList(VersusPeriodClassVO versusPeriodClassVO);
	/** 대비기간매출분석 - 브랜드 코드 조회조건 */
	List<DefaultMap<String>> getBrandCdList(VersusPeriodClassVO versusPeriodClassVO);

}
