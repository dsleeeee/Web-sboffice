package kr.co.solbipos.excclc.excclc.saleRegistKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.saleRegistKmu.service.SaleRegistKmuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleRegistKmuMapper.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(일반)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleRegistKmuMapper {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistKmuList(SaleRegistKmuVO saleRegistKmuVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistKmuVO saleRegistKmuVO);

    /** 영수번호 채번 */
    String getBillNo(SaleRegistKmuVO saleRegistKmuVO);

    /** DTL 저장 */
    int getSaleDtl(SaleRegistKmuVO saleRegistKmuVO);
    int getSaleDtlPay(SaleRegistKmuVO saleRegistKmuVO);
    int getSaleDtlDc(SaleRegistKmuVO saleRegistKmuVO);

    /** HDR 저장 */
    int getSaleHdr(SaleRegistKmuVO saleRegistKmuVO);
    int getSaleHdrPay(SaleRegistKmuVO saleRegistKmuVO);
    int getSaleHdrDc(SaleRegistKmuVO saleRegistKmuVO);
    int getSaleHdrMembr(SaleRegistKmuVO saleRegistKmuVO);

    /** PAY 저장 */
    int getSalePayCash(SaleRegistKmuVO saleRegistKmuVO);
    int getSalePayCard(SaleRegistKmuVO saleRegistKmuVO);
    int getSalePayPostpaid(SaleRegistKmuVO saleRegistKmuVO);
    int getSalePay(SaleRegistKmuVO saleRegistKmuVO);
    int getSalePaySeq(SaleRegistKmuVO saleRegistKmuVO);

    /** MEMBER_POSTPAID 저장 */
    int getMemberPostpaid(SaleRegistKmuVO saleRegistKmuVO);

    /** MEMBER_PAID_BALANCE 저장 */
    int getMemberPaidBalance(SaleRegistKmuVO saleRegistKmuVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistKmuVO saleRegistKmuVO);
    List<DefaultMap<String>> getCashAmt(SaleRegistKmuVO saleRegistKmuVO);
    String getSaleFg(SaleRegistKmuVO saleRegistKmuVO);

    /** HDR 삭제 */
    int delSaleHdr(SaleRegistKmuVO saleRegistKmuVO);
    int delSaleHdrMembr(SaleRegistKmuVO saleRegistKmuVO);
    int delMemberPostpaid(SaleRegistKmuVO saleRegistKmuVO);
    int delMemberPaidBalance(SaleRegistKmuVO saleRegistKmuVO);

    /** 회원정보 조회 */
    List<DefaultMap<Object>> getMemberList(SaleRegistKmuVO saleRegistKmuVO);

    /** 회원선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistKmuMemberList(SaleRegistKmuVO saleRegistKmuVO);

    /** 총매출액 조회 */
    String getSaleHdrTotSaleAmt(SaleRegistKmuVO saleRegistKmuVO);
}