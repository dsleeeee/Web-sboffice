package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageService;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("DeliveryChargerManageService")
public class DeliveryChargerManageServiceImpl implements DeliveryChargerManageService {

    @Autowired
    DeliveryChargerManageMapper deliveryChargerManageMapper;

    @Autowired
    MessageService messageService;

    /** 배송기사 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDeliveryChargerList(DeliveryChargerManageVO deliveryChargerManageVO) {
        return deliveryChargerManageMapper.getDeliveryChargerList(deliveryChargerManageVO);
    }

    /** 배송기사 상세 조회 */
    @Override
    public DefaultMap<String> getDlvrInfo(DeliveryChargerManageVO deliveryChargerManageVO) {
        return deliveryChargerManageMapper.getDlvrInfo(deliveryChargerManageVO);
    }

    /** 배송기사 저장 */
    @Override
    public int saveDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        deliveryChargerManageVO.setRegId(sessionInfoVO.getUserId());
        deliveryChargerManageVO.setRegDt(currentDt);
        deliveryChargerManageVO.setModId(sessionInfoVO.getUserId());
        deliveryChargerManageVO.setModDt(currentDt);

        if (StringUtil.getOrBlank(deliveryChargerManageVO.getDlvrCd()).equals("")) {
            // 신규 배송기사코드 조회
            String dlvrCd = deliveryChargerManageMapper.getNewDlvrCd(sessionInfoVO);
            deliveryChargerManageVO.setDlvrCd(dlvrCd);
            // 추가
            result = deliveryChargerManageMapper.insertDeliveryCharger(deliveryChargerManageVO);
        }
        else {
            // 수정
            result = deliveryChargerManageMapper.updateDeliveryCharger(deliveryChargerManageVO);
        }

        if ( result > 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 배송기사 담당 창고 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDeliveryChargerStorageList(DeliveryChargerManageVO deliveryChargerManageVO) {
        return deliveryChargerManageMapper.getDeliveryChargerStorageList(deliveryChargerManageVO);
    }

    /** 배송기사 담당 창고 설정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDeliveryChargerStorageAllList(DeliveryChargerManageVO deliveryChargerManageVO) {
        return deliveryChargerManageMapper.getDeliveryChargerStorageAllList(deliveryChargerManageVO);
    }

    /** 배송기사 담당 창고 저장 */
    @Override
    public int saveDeliveryChargerStorage(DeliveryChargerManageVO[] deliveryChargerManageVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DeliveryChargerManageVO deliveryChargerManageVO : deliveryChargerManageVOs) {
            deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            deliveryChargerManageVO.setRegId(sessionInfoVO.getUserId());
            deliveryChargerManageVO.setRegDt(currentDt);
            deliveryChargerManageVO.setModId(sessionInfoVO.getUserId());
            deliveryChargerManageVO.setModDt(currentDt);

            if (!StringUtil.getOrBlank(deliveryChargerManageVO.getDlvrCd()).equals("")) {
                // 추가
                result = deliveryChargerManageMapper.insertDeliveryChargerStorage(deliveryChargerManageVO);

                returnResult += result;

                if (result <= 0) {
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
//            else if(!StringUtil.getOrBlank(deliveryChargerManageVO.getDlvrCd()).equals("") &&
//                    !StringUtil.getOrBlank(deliveryChargerManageVO.getDlvrCd()).equals(dlvrCd)) {
//                result = deliveryChargerManageMapper.deleteDeliveryChargerStorage(deliveryChargerManageVO);
//                if(result > 0) {
//                    deliveryChargerManageVO.setDlvrCd(dlvrCd);
//                    result = deliveryChargerManageMapper.insertDeliveryChargerStorage(deliveryChargerManageVO);
//                } else {
//                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//                }
//            }

        }

        return returnResult;
    }

    /** 배송기사 담당 창고 삭제 */
    @Override
    public int deleteDeliveryChargerStorage(DeliveryChargerManageVO[] deliveryChargerManageVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DeliveryChargerManageVO deliveryChargerManageVO : deliveryChargerManageVOs) {
            deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            deliveryChargerManageVO.setRegId(sessionInfoVO.getUserId());
            deliveryChargerManageVO.setRegDt(currentDt);
            deliveryChargerManageVO.setModId(sessionInfoVO.getUserId());
            deliveryChargerManageVO.setModDt(currentDt);

            result = deliveryChargerManageMapper.deleteDeliveryChargerStorage(deliveryChargerManageVO);
            returnResult += result;
        }

        if ( returnResult == deliveryChargerManageVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
