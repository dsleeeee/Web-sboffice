package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnOutstockDataService {
    /** 반품자료생성 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockDataList(RtnOutstockDataVO rtnOutstockDataVO);

    /** 반품자료생성 - 반품자료생성 */
    int saveDataCreate(RtnOutstockDataVO[] rtnOutstockDataVOs, SessionInfoVO sessionInfoVO);

    /** 반품자료생성 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockDataDtlList(RtnOutstockDataVO rtnOutstockDataVO);
}
