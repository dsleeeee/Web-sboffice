package kr.co.solbipos.membr.anals.prepaid.service.impl;

import kr.co.common.data.structure.DefaultMap;
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
//
//    /** 후불 대상 회원 조회 */
//    List<DefaultMap<Object>> getDepositMemberList(CreditStoreVO creditStoreVO);
//
//    /** 외상 입금 */
//    int saveDeposit(CreditStoreVO creditStoreVO);
}
