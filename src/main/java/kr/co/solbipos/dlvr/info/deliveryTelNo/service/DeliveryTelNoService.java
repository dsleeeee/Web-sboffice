package kr.co.solbipos.dlvr.info.deliveryTelNo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DeliveryTelNoService {

    /** 배달지정보관리 조회 */
    List<DefaultMap<Object>> getDeliveryTelNoList(DeliveryTelNoVO deliveryTelNoVO, SessionInfoVO sessionInfoVO);

    /** 배달지정보관리 수정 */
    int updateDeliveryTelNo(DeliveryTelNoVO[] deliveryTelNoVOs, SessionInfoVO sessionInfoVO);

    /** 배달지정보관리 전체 삭제 */
    int deleteAllDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO, SessionInfoVO sessionInfoVO);

}
