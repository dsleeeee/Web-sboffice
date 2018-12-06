package kr.co.solbipos.iostock.vendr.prodStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.prodStockInfo.service.ProdStockInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdStockInfoMapper {
    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getProdStockInfoList(ProdStockInfoVO prodStockInfoVO);

}
