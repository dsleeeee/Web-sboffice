package kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.StoreDayTimeMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreDayTimeMrpizzaMapper.java
 * @Description : 미스터피자 > 매장분석 > 매장-일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreDayTimeMrpizzaMapper {

    /** 매장-일별시간대 리스트 조회 */
    List<DefaultMap<Object>> getStoreDayTimeMrpizzaList(StoreDayTimeMrpizzaVO storeDayTimeMrpizzaVO);
}
