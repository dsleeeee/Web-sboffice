package kr.co.solbipos.sale.benson.timeProdChannelBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeProdChannelBensonService.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출(채널별)
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
public interface TimeProdChannelBensonService {

    /** 상품별시간대매출(채널별) 조회 */
    List<DefaultMap<String>> getTimeProdChannelBensonList(TimeProdChannelBensonVO timeProdChannelBensonVO, SessionInfoVO sessionInfoVO);

    /** 상품별시간대매출(채널별) 엑셀다운로드 */
    List<DefaultMap<String>> getTimeProdChannelBensonExcelList(TimeProdChannelBensonVO timeProdChannelBensonVO, SessionInfoVO sessionInfoVO);

}
