package kr.co.solbipos.base.price.hqSplyPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.hqSplyPriceHistory.service.HqSplyPriceHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : HqSplyPriceHistoryMapper.java
 * @Description : 기초관리 - 가격관리 - 본사공급가History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface HqSplyPriceHistoryMapper {

    /** 본사 공급가 History 조회 */
    List<DefaultMap<String>> getHqSplyPriceHistoryList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO);

    /** 본사 공급가 History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceHistoryExcelList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO);
}
