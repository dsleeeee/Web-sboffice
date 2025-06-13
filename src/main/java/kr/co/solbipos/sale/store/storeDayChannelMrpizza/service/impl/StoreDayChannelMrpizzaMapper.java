package kr.co.solbipos.sale.store.storeDayChannelMrpizza.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeDayChannelMrpizza.service.StoreDayChannelMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreDayChannelMrpizzaMapper.java
 * @Description : 미스터피자 > 매장분석 > 매장-일별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface StoreDayChannelMrpizzaMapper {

    /** 매장-일별매출현황(채널별) 리스트 조회 */
    List<DefaultMap<String>> getStoreDayChannelMrpizzaList(StoreDayChannelMrpizzaVO storeDayChannelMrpizzaVO);

    /** 매장-일별매출현황(채널별) 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreDayChannelMrpizzaExcelList(StoreDayChannelMrpizzaVO storeDayChannelMrpizzaVO);
}
