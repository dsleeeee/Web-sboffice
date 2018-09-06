package kr.co.solbipos.iostock.order.outstockReqDate.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface OutstockReqDateService {
    /** 출고요청일관리 요일별 리스트 조회 */
    List<DefaultMap<String>> getDaysList(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 요일별 출고가능일 저장 */
    int saveReqDateDays(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO);

    /** 출고요청일관리 특정일 리스트 조회 */
    List<DefaultMap<String>> getSpecificDateList(OutstockReqDateVO outstockReqDateVO);

    /** 출고요청일관리 특정일 출고가능여부 신규 등록 */
    int saveNewSpecificDate(OutstockReqDateVO outstockReqDateVO, SessionInfoVO sessionInfoVO);

    /** 출고요청일관리 특정일 출고가능여부 수정 */
    int saveSpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO);

    /** 출고요청일관리 특정일 출고가능여부 삭제 */
    int deleteSpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO);

    /** 출고요청일관리 특정일 출고가능여부 복사 */
    int copySpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO);

    /** 출고요청일관리 요일별 출고가능여부 복사 */
    int copyDays(OutstockReqDateVO outstockReqDateVO, SessionInfoVO sessionInfoVO);

}
