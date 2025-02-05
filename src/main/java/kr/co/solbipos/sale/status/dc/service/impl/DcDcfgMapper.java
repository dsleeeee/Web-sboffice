package kr.co.solbipos.sale.status.dc.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.dc.service.DcDcfgVO;

@Mapper
@Repository
public interface DcDcfgMapper {
    /** 할인구분별매출 탭 - 리스트 조회 */
    List<DefaultMap<String>> getDcDcfgList(DcDcfgVO dcDcfgVO);

    /** 할인구분별매출 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getDcDcfgExcelList(DcDcfgVO dcDcfgVO);

    /** 할인구분별매출 탭 - 리스트 상세 조회 */
    List<DefaultMap<String>> getDcDcfgDtlList(DcDcfgVO dcDcfgVO);
}
