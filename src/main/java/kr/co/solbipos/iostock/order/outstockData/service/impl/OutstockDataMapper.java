package kr.co.solbipos.iostock.order.outstockData.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OutstockDataMapper {
    /** 출고자료생성 리스트 조회 */
    List<DefaultMap<String>> getOutstockDataList(OutstockDataVO outstockDataVO);

    /** 출고자료생성 상세 리스트 조회 */
    List<DefaultMap<String>> getOutstockDataDtlList(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 출고자료생성 전표번호 조회 */
    String getMaxSlipNo(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 직배송거래처 및 배송기사 조회 */
    List<DefaultMap<String>> getStoreVendrDlvr(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 출고자료생성 TB_PO_HQ_STORE_DISTRIBUTE 수정 */
    int updateDstbDataCreate(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 출고자료생성 TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력 */
    int insertOutstockDtlDataCreate(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 출고자료생성 TB_PO_HQ_STORE_OUTSTOCK 자료입력 */
    int insertOutstockDataCreate(OutstockDataVO outstockDataVO);
    
    /** 반품자료생성 - TB_PO_HQ_STORE_OUTSTOCK_PROD 자료입력*/
    int insertRtnStoreOutStockProd(OutstockDataVO outstockDataVO);
}
