package kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service.SaleRegistChargeKmuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleRegistChargeKmuMapper.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(수수료)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleRegistChargeKmuMapper {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistChargeKmuList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 영수번호 채번 */
    String getBillNo(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** DTL 저장 */
    int getSaleDtl(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleDtlPay(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleDtlDc(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleDtlSlipKmu(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** HDR 저장 */
    int getSaleHdr(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleHdrPay(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleHdrDc(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSaleHdrMembr(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** PAY 저장 */
    int getSalePayCash(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSalePayCard(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSalePayPostpaid(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSalePay(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int getSalePaySeq(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** MEMBER_POSTPAID 저장 */
    int getMemberPostpaid(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** MEMBER_PAID_BALANCE 저장 */
    int getMemberPaidBalance(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    List<DefaultMap<String>> getCashAmt(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    String getSaleFg(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** HDR 삭제 */
    int delSaleHdr(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int delSaleHdrMembr(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int delMemberPostpaid(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int delMemberPaidBalance(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
    int delSaleDtlSlipKmu(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 회원정보 조회 */
    List<DefaultMap<Object>> getMemberList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 회원선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistChargeKmuMemberList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);

    /** 매출처선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistChargeKmuCustomerList(SaleRegistChargeKmuVO saleRegistChargeKmuVO);
}