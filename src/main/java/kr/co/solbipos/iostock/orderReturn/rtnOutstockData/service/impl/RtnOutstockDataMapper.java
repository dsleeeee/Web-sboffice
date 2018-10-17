package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnOutstockDataMapper {
    /** 반품자료생성 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockDataList(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockDataDtlList(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 반품자료생성 전표번호 조회 */
    String getMaxSlipNo(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 직배송거래처 및 배송기사 조회 */
    List<DefaultMap<String>> getStoreVendrDlvr(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 반품자료생성 TB_PO_HQ_STORE_DISTRIBUTE 수정 */
    int updateDstbDataCreate(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 반품자료생성 TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력 */
    int insertOutstockDtlDataCreate(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 반품자료생성 TB_PO_HQ_STORE_OUTSTOCK 자료입력 */
    int insertRtnOutstockDataCreate(RtnOutstockDataVO rtnOutstockDataVO);
}
