package kr.co.solbipos.sale.store.storeDayChannel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreDayChannelService {

    /** 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(StoreDayChannelVO storeDayChannelVO, SessionInfoVO sessionInfoVO);
}
