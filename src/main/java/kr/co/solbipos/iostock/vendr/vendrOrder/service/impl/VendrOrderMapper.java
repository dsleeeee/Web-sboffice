package kr.co.solbipos.iostock.vendr.vendrOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VendrOrderMapper {
    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 상세 조회 */
    DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 신규전표 조회 */
    String getNewSlipNo(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 등록 */
    int insertVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 수정 */
    int updateVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 HD 삭제 */
    int deleteVendrOrderHd(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 삭제시 상품이 있는지 여부 조회 */
    String getDtlProdExist(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주 진행상태 변경시 입고/반출 전표가 있는지 여부 조회 */
    String getVendrInstockExist(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 진행상태 변경 */
    int updateProcFg(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주상품 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderProdList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 진행구분 조회 */
    DefaultMap<String> getProcFgCheck(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderProdRegList(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 등록 */
    int insertVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 수정 */
    int updateVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL 삭제 */
    int deleteVendrOrderDtl(VendrOrderVO vendrOrderVO);

    /** 거래처 발주등록 - 발주정보 DTL의 집계정보 HD에 수정 */
    int updateVendrOrderDtlSumHd(VendrOrderVO vendrOrderVO);





    /** 거래처 발주등록 - 거래처 선택모듈 리스트 조회 */
    List<DefaultMap<String>> getVendrList(VendrOrderVO vendrOrderVO);



}
