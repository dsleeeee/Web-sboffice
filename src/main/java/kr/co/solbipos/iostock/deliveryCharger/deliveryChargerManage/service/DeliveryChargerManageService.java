package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : deliveryChargerManageController.java
 * @Description : 수불관리 > 수주관리 > 배송기사관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.29  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.29
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DeliveryChargerManageService {
    /** 배송기사 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 상세 조회 */
    DefaultMap<String> getDlvrInfo(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 저장 */
    int saveDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO, SessionInfoVO sessionInfoVO);

    /** 배송기사 삭제 */
    int deleteDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당창고 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerStorageList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당창고 설정 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerStorageAllList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 저장 */
    int saveDeliveryChargerStorage(DeliveryChargerManageVO[] deliveryChargerManageVOs, SessionInfoVO sessionInfoVO);

    /** 배송기사 담당 창고 삭제 */
    int deleteDeliveryChargerStorage(DeliveryChargerManageVO[] deliveryChargerManageVOs, SessionInfoVO sessionInfoVO);

}
