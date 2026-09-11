package kr.co.solbipos.sale.benson.timeProdBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeProdBensonService.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeProdBensonService {

    /** 조회 */
    List<DefaultMap<Object>> getTimeProdBensonList(TimeProdBensonVO timeProdBensonVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTimeProdBensonExcelList(TimeProdBensonVO timeProdBensonVO, SessionInfoVO sessionInfoVO);

}
