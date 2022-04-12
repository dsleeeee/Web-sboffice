package kr.co.solbipos.sale.status.dlvr.prodStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.dlvr.prodStatus.service.ProdStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdStatusMapper {

    /** 배달상품현황 리스트 조회 */
    List<DefaultMap<String>> getProdStatusList(ProdStatusVO prodStatusVO);
}
