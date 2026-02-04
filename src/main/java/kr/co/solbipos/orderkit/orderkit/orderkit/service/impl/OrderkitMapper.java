package kr.co.solbipos.orderkit.orderkit.orderkit.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.orderkit.orderkit.orderkit.service.OrderkitVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name  : OrderkitMapper.java
 * @Description : 오더킷 > 오더킷 > 오더킷
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface OrderkitMapper {

    /** 매장정보 조회 */
    DefaultMap<Object> getStoreInfo(OrderkitVO orderkitVO);

    /** 개발/운영 Api URL 조회 */
    DefaultMap<Object> getApiUrl(OrderkitVO orderkitVO);
}
