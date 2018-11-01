package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeliveryChargerManageMapper {
    /** 배송기사 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 상세 조회 */
    DefaultMap<String> getDlvrInfo(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 신규 배송기사코드 조회 */
    String getNewDlvrCd(SessionInfoVO sessionInfoVO);

    /** 배송기사 등록 */
    int insertDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 수정 */
    int updateDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 삭제 */
    int deleteDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerStorageList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 설정 리스트 조회 */
    List<DefaultMap<String>> getDeliveryChargerStorageAllList(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 등록 */
    int insertDeliveryChargerStorage(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 삭제 */
    int deleteDeliveryChargerStorage(DeliveryChargerManageVO deliveryChargerManageVO);

    /** 배송기사 담당 창고 전체 삭제 */
    int deleteAllDeliveryChargerStorage(DeliveryChargerManageVO deliveryChargerManageVO);

}
