package kr.co.solbipos.membr.anals.postpaid.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Class Name : PostpaidMapper.java
 * @Description : 회원관리 > 회원분석 > 후불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 * @ 2019.08.28  이다솜      외상입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PostpaidMapper {

    /** 후불 회원 외상, 입금 내역 */
    List<DefaultMap<Object>> getPostpaidMemberList(PostpaidStoreVO postpaidStoreVO);

    /** 후불 대상 회원 조회 */
    List<DefaultMap<Object>> getDepositMemberList(PostpaidStoreVO postpaidStoreVO);

    /** 외상 입금 StoreCd */
    String getDepositStoreCd(PostpaidStoreVO postpaidStoreVO);

    /** 외상 입금 */
    int saveDeposit(PostpaidStoreVO postpaidStoreVO);

    /** 외상입금 집계 */
    int savePaidBalancePostPaid(PostpaidStoreVO postpaidStoreVO);

    /** 세금계산서 요청목록 조회 */
    List<DefaultMap<Object>> getTaxBillList(TaxBillVO taxBillVO);

    /** 세금계산서 발행 입금 */
    int saveTaxBillComplete(TaxBillVO taxBillVO);
}
