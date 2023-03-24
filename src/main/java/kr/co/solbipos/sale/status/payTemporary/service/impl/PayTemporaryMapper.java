package kr.co.solbipos.sale.status.payTemporary.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PayTemporaryMapper {

    /** 리스트 조회 */
    List<DefaultMap<String>> getPayTemporaryList(PayTemporaryVO payTemporaryVO);
    List<DefaultMap<String>> getPayTemporaryExcelList(PayTemporaryVO payTemporaryVO);
    List<DefaultMap<String>> getPayTemporaryDtlList(PayTemporaryVO payTemporaryVO);

}
