package kr.co.solbipos.iostock.order.outstockReqDate.service;

import kr.co.common.data.structure.DefaultMap;

import java.util.List;

public interface OutstockReqDateService {
    /** 출고요청일관리 요일별 리스트 조회 */
    List<DefaultMap<String>> getDaysList(OutstockReqDateVO outstockReqDateVO);

}
