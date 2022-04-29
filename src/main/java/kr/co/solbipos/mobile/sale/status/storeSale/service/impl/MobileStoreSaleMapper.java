package kr.co.solbipos.mobile.sale.status.storeSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.status.storeSale.service.MobileStoreSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileStoreSaleMapper.java
 * @Description : (모바일) 매장매출 > 매장종합
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
public interface MobileStoreSaleMapper {

    /** 매장종합 - 조회 */
    List<DefaultMap<Object>> getMobileStoreSaleList(MobileStoreSaleVO mobileStoreSaleVO);

    /** 상세 - 조회 */
    List<DefaultMap<Object>> getMobileStoreSaleDtlList(MobileStoreSaleVO mobileStoreSaleVO);
}