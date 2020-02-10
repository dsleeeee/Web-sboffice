package kr.co.solbipos.sale.status.rtnStatus.day.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayVO;

@Mapper
@Repository
public interface RtnStatusDayMapper {
    /** 반품현황 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getRtnStatusDayList(RtnStatusDayVO rtnStatusDayVO);

    /** 반품현황 - 일자별 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusDayDtlList(RtnStatusDayVO rtnStatusDayVO);

	/** 반품현황 - 포스별 상세 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusPosDtlList(RtnStatusDayVO rtnStatusDayVO);

	/** 반품현황 - 상품별 반품현황 리스트 조회 */
	List<DefaultMap<String>> getRtnStatusProdList(RtnStatusDayVO rtnStatusDayVO);

}
