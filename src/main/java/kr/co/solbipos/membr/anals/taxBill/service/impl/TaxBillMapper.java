package kr.co.solbipos.membr.anals.taxBill.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Class Name : TaxBillMapper.java
 * @Description : 회원관리 > 회원분석 > 세금계산서 발행 목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.13  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.13
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TaxBillMapper {

    /** 세금계산서 발행 요청 목록 */
    List<DefaultMap<Object>> getTaxBillRequestList(TaxBillVO taxBillVO);
}
