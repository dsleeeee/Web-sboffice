package kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.SaleInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleInfoMapper {

    /** 매장정보,매출종합내역,결제내역,회원정보 조회 */
    DefaultMap<String> getSaleDtlList(SaleInfoVO saleInfoVO);

    /** 신용카드 결재내역 조회 */
    List<DefaultMap<String>> getSaleCardDtlList(SaleInfoVO saleInfoVO);

    /** 현금영수증 결재내역 조회 */
    List<DefaultMap<String>> getSaleCashDtlList(SaleInfoVO saleInfoVO);

    /** 상품내역 조회 */
    List<DefaultMap<String>> getSaleProdDtlList(SaleInfoVO saleInfoVO);
}