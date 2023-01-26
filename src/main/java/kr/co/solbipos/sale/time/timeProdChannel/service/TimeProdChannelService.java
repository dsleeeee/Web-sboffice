package kr.co.solbipos.sale.time.timeProdChannel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeProdChannelService.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별시간대매출(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.20   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeProdChannelService {

    /** 상품별시간대매출(채널별) 조회 */
    List<DefaultMap<String>> getTimeProdChannelList(TimeProdChannelVO timeProdChannelVO, SessionInfoVO sessionInfoVO);

    /** 상품별시간대매출(채널별) 엑셀다운로드 */
    List<DefaultMap<String>> getTimeProdChannelExcelList(TimeProdChannelVO timeProdChannelVO, SessionInfoVO sessionInfoVO);

}
