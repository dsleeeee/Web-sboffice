package kr.co.solbipos.sale.dlvr.dlvrFg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrFgMapper {
    /** 배달구분 콤보박스 */
    String getDlvrFg(DlvrFgVO dlvrFgVO);

    /** 배달구분 콤보박스 데이터 */
    List<DefaultMap<String>> getDlvrFgData(DlvrFgVO dlvrFgVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getOrderFg(DlvrFgVO dlvrFgVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getProd(DlvrFgVO dlvrFgVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getProdDtl(DlvrFgVO dlvrFgVO);

    /** 상품-영수별매출상세 */
    List<DefaultMap<Object>> getSaleDtl(DlvrFgVO dlvrFgVO);

}
