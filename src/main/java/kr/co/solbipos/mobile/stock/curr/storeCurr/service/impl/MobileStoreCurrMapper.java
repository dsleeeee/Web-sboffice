package kr.co.solbipos.mobile.stock.curr.storeCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.curr.storeCurr.service.MobileStoreCurrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobileStoreCurrMapper.java
 * @Description : (모바일)재고현황 > 매장현재고
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
public interface MobileStoreCurrMapper {

    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStoreCurrList(MobileStoreCurrVO mobileStoreCurrVO);
}
