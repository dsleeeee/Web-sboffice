package kr.co.solbipos.sale.cmmSalePopup.billInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BillInfoMapper {
    /** 매출공통팝업 - 영수증상세 종합내역 조회 */
    DefaultMap<String> getBillInfo(BillInfoVO billInfoVO);

    /** 매출공통팝업 - 영수증상세 결제내역 조회 */
    DefaultMap<String> getBillPayInfo(BillInfoVO billInfoVO);

    /** 매출공통팝업 - 영수증상세 방문인원 조회 */
    DefaultMap<String> getBillGuestInfo(BillInfoVO billInfoVO);

    /** 매출공통팝업 - 영수증상세 상품 리스트 조회 */
    List<DefaultMap<String>> getBillProdList(BillInfoVO billInfoVO);

}
