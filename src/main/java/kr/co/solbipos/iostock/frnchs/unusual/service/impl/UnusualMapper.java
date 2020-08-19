package kr.co.solbipos.iostock.frnchs.unusual.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualVO;

@Mapper
@Repository
public interface UnusualMapper {
    /** 본사 특이사항 입출고내역 - 특이사항 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getUnusualList(UnusualVO unusualVO);

    /** 본사 특이사항 입출고내역 - 특이사항 입출고내역 리스트 조회 */
	List<DefaultMap<String>> getUnusualExcelList(UnusualVO unusualVO);

}
