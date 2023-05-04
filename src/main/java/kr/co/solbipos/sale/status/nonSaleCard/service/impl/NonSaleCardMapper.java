package kr.co.solbipos.sale.status.nonSaleCard.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.nonSaleCard.service.NonSaleCardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : NonSaleCardMapper.java
 * @Description : 매출관리 > 승인현황 > 비매출카드상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface NonSaleCardMapper {

    /** 비매출카드상세 - 조회 */
    List<DefaultMap<Object>> getNonSaleCardList(NonSaleCardVO nonSaleCardVO);
}