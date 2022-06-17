package kr.co.solbipos.dlvr.info.deliveryTelNo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.info.deliveryTelNo.service.DeliveryTelNoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeliveryTelNoMapper {
    /** 배달지정보관리 조회 */
    List<DefaultMap<Object>> getDeliveryTelNoList(DeliveryTelNoVO deliveryTelNoVO);

    /** 배달지정보관리 수정 */
    int updateDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO);

    /** 배달지정보관리 삭제 */
    int deleteDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO);
    
    /** 배달지정보관리 전체삭제 */
    int deleteAllDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO);
}
