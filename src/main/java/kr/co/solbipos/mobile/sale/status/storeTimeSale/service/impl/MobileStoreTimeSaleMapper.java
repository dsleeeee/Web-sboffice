package kr.co.solbipos.mobile.sale.status.storeTimeSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.storeTimeSale.service.MobileStoreTimeSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileStoreTimeSaleMapper.java
 * @Description : (모바일) 매장매출 > 시간대별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileStoreTimeSaleMapper {

    /** 시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileStoreTimeSaleList(MobileStoreTimeSaleVO mobileStoreTimeSaleVO);
}