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

}
