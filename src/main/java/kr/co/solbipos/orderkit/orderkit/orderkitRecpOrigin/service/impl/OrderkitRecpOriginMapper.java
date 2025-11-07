package kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.OrderkitRecpOriginVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : OrderKitRecpOriginMapper.java
 * @Description : 오더킷 > 오더킷 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.11.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface OrderkitRecpOriginMapper {

    /** 원산지 정보 조회 */
   List<DefaultMap<Object>> getOrderkitRecpOrigin(OrderkitRecpOriginVO orderkitRecpOriginVO);
}
