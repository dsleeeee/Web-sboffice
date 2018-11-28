package kr.co.solbipos.iostock.vendr.vendrInstock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VendrInstockMapper {
    /** 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrInstockList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrInstockOrderSlipList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 상세 조회(본사) */
    DefaultMap<String> getHqSlipInfo(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출 상세 조회(매장) */
//    DefaultMap<String> getStSlipInfo(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 신규전표 조회(본사) */
    String getHqNewSlipNo(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 신규전표 조회(매장) */
//        String getStNewSlipNo(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 HD 등록(본사) */
    int insertHqVendrInstockHd(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출 HD 등록(매장) */
//    int insertStVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 HD 수정(본사) */
    int updateHqVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 HD 삭제(본사) */
    int deleteHqVendrInstockHd(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출정보 HD 삭제(매장) */
//    int deleteStVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 삭제시 상품이 있는지 여부 조회(본사) */
    String getHqDtlProdExist(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경(본사) */
    int updateHqProcFg(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 입고관련 정보 초기화(본사) */
    int updateHqDefaultVendrOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 입고관련 정보 갱신(본사) */
    int updateHqVendrInstockToOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성(본사) */
    int insertHqVendrInstockToOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 집계정보 HD에 수정(본사) */
    int updateHqVendrOrderDtlSumHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 테이블의 진행구분 수정(본사) */
    int updateHqVendrOrderProcFg(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrInstockProdList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 진행구분 조회(본사) */
    DefaultMap<String> getHqProcFgCheck(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrInstockProdRegList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 등록(본사) */
    int insertHqVendrInstockDtl(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 등록(매장) */
    //    int insertStVendrInstockDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 수정(본사) */
    int updateHqVendrInstockDtl(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 수정(매장) */
    //    int updateStVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 삭제(본사) */
    int deleteHqVendrInstockDtl(VendrInstockVO vendrInstockVO);

//    /** 거래처 입고/반출등록 - 입고/반출정보 DTL 삭제(매장) */
    //    int deleteStVendrInstockDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 DTL의 집계정보 HD에 수정(본사) */
    int updateHqVendrInstockDtlSumHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회(본사) */
    List<DefaultMap<String>> getHqVendrInstockOrderInfoRegList(VendrInstockVO vendrInstockVO);

}
