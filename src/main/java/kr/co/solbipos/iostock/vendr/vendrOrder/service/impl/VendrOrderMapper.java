package kr.co.solbipos.iostock.vendr.vendrOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VendrOrderMapper {
    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrOrderList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회(매장) */
//    List<DefaultMap<String>> getStVendrOrderList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 상세 조회(본사) */
    DefaultMap<String> getHqSlipInfo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 상세 조회(매장) */
//    DefaultMap<String> getStSlipInfo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 신규전표 조회(본사) */
    String getHqNewSlipNo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 신규전표 조회(매장) */
//    String getStNewSlipNo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 등록(본사) */
    int insertHqVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 등록(매장) */
//    int insertStVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 수정(본사) */
    int updateHqVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 수정(매장) */
//    int updateStVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 삭제(본사) */
    int deleteHqVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 삭제(매장) */
//    int deleteStVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrOrderProdList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 진행구분 조회(본사) */
    DefaultMap<String> getHqProcFgCheck(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주상품 등록 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrOrderProdRegList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 등록(본사) */
    int insertHqVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 등록(매장) */
    //    int insertStVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 수정(본사) */
    int updateHqVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 수정(매장) */
    //    int updateStVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 삭제(본사) */
    int deleteHqVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 삭제(매장) */
    //    int deleteStVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주 삭제시 상품이 있는지 여부 조회(본사) */
    String getHqDtlProdExist(VendrOrderVO vendrOrderVO);






    /** 거래처 발주등록 - 거래처 선택모듈 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrList(VendrOrderVO vendrOrderVO);



}
