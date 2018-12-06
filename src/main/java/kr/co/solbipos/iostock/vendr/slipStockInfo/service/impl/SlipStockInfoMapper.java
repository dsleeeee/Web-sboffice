package kr.co.solbipos.iostock.vendr.slipStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.slipStockInfo.service.SlipStockInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SlipStockInfoMapper {
    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getSlipStockInfoList(SlipStockInfoVO slipStockInfoVO);

    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getSlipStockInfoDtlList(SlipStockInfoVO slipStockInfoVO);

}
