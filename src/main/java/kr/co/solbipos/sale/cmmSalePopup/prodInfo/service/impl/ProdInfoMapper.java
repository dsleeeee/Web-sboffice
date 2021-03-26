package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdInfoMapper {

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
//    List<DefaultMap<Object>> getProdSaleDtlList(ProdInfoVO prodInfoVO);

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getProdSaleDtlDayList(ProdInfoVO prodInfoVO);

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getProdSaleDtlMonthList(ProdInfoVO prodInfoVO);

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getProdSaleDtlDayProdClassList(ProdInfoVO prodInfoVO);

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getProdSaleDtlMonthProdClassList(ProdInfoVO prodInfoVO);
}
