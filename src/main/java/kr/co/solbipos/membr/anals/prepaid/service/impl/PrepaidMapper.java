package kr.co.solbipos.membr.anals.prepaid.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Class Name : PrepaidMapper.java
 * @Description : 회원관리 > 회원분석 > 선불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.01  김지은      최초생성
 * @ 2019.08.28  이다솜      선불입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.10.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface PrepaidMapper {

    /** 선불 회원 충전, 사용 내역 */
    List<DefaultMap<Object>> getPrepaidMemberList(PrepaidStoreVO prepaidStoreVO);

    /** 선불 회원 충전, 사용 내역(엑셀) */
    List<DefaultMap<Object>> getPrepaidMemberListExcel(PrepaidStoreVO prepaidStoreVO);

    /** 후불 대상 회원 조회 */
    List<DefaultMap<Object>> getChargeMemberList(PrepaidStoreVO prepaidStoreVO);

    /** 선불충전 */
    int saveChargeAmt(PrepaidStoreVO prepaidStoreVO);

    /** 선불충전 집계 */
    int savePaidBalancePrePaid(PrepaidStoreVO prepaidStoreVO);
}
