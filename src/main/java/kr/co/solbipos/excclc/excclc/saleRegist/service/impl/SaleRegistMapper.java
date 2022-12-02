package kr.co.solbipos.excclc.excclc.saleRegist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcVO;
import kr.co.solbipos.excclc.excclc.saleRegist.service.SaleRegistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleRegistMapper.java
 * @Description : 정산관리 > 정산관리 > 매출수기등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.24  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface SaleRegistMapper {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistList(SaleRegistVO saleRegistVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistVO saleRegistVO);

    /** 영수번호 채번 */
    String getBillNo(SaleRegistVO saleRegistVO);

    /** DTL 저장 */
    int getSaleDtl(SaleRegistVO saleRegistVO);
    int getSaleDtlPay(SaleRegistVO saleRegistVO);
    int getSaleDtlDc(SaleRegistVO saleRegistVO);

    /** HDR 저장 */
    int getSaleHdr(SaleRegistVO saleRegistVO);
    int getSaleHdrPay(SaleRegistVO saleRegistVO);
    int getSaleHdrDc(SaleRegistVO saleRegistVO);

    /** PAY 저장 */
    int getSalePayCash(SaleRegistVO saleRegistVO);
    int getSalePayCard(SaleRegistVO saleRegistVO);
    int getSalePay(SaleRegistVO saleRegistVO);
    int getSalePaySeq(SaleRegistVO saleRegistVO);


    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistVO saleRegistVO);
    List<DefaultMap<String>> getCashAmt(SaleRegistVO saleRegistVO);
    String getSaleFg(SaleRegistVO saleRegistVO);

    /** HDR 삭제 */
    int delSaleHdr(SaleRegistVO saleRegistVO);
}
