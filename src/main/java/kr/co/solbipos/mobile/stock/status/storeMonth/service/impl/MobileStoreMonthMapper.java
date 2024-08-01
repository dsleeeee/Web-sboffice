package kr.co.solbipos.mobile.stock.status.storeMonth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.status.storeMonth.service.MobileStoreMonthVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobileStoreMonthMapper.java
 * @Description : (모바일)재고현황 > 매장월수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileStoreMonthMapper {

    /** 매장월수불 리스트 조회 */
    List<DefaultMap<String>> stockStoreMonthList(MobileStoreMonthVO mobileStoreMonthVO);
    List<DefaultMap<String>> stockStoreMonthExcelList(MobileStoreMonthVO mobileStoreMonthVO);
}
