package kr.co.solbipos.iostock.order.outstockData.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface OutstockDataService {
    /** 출고자료생성 리스트 조회 */
    List<DefaultMap<String>> getOutstockDataList(OutstockDataVO outstockDataVO);

    /** 출고자료생성 - 출고자료생성 */
    int saveDataCreate(OutstockDataVO[] outstockDataVOs, SessionInfoVO sessionInfoVO);

    /** 출고자료생성 상세 리스트 조회 */
    List<DefaultMap<String>> getOutstockDataDtlList(OutstockDataVO outstockDataVO);
}
