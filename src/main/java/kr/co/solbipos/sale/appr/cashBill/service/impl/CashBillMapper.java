package kr.co.solbipos.sale.appr.cashBill.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CashBillMapper.java
 * @Description : 맘스터치 > 승인관리2 > 현금영수증 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CashBillMapper {

    /** 현금영수증 승인 조회 */
    List<DefaultMap<Object>> getCashBillList(CashBillVO cashBillVO);

}