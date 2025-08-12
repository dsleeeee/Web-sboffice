package kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.BillSaleMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BillSaleMrpizzaMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 영수별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface BillSaleMrpizzaMapper {

    /** 영수별매출 리스트 조회 */
    List<DefaultMap<Object>> getBillSaleMrpizzaList(BillSaleMrpizzaVO billSaleMrpizzaVO);
}
