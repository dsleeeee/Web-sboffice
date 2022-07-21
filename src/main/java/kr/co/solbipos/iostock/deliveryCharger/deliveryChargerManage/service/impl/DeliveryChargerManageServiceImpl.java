package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageService;
import kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service.DeliveryChargerManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("deliveryChargerManageService")
public class DeliveryChargerManageServiceImpl implements DeliveryChargerManageService {

    private final DeliveryChargerManageMapper deliveryChargerManageMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DeliveryChargerManageServiceImpl(DeliveryChargerManageMapper deliveryChargerManageMapper,
        MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.deliveryChargerManageMapper = deliveryChargerManageMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

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

        //deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        deliveryChargerManageVO.setRegId(sessionInfoVO.getUserId());
        deliveryChargerManageVO.setRegDt(currentDt);
        deliveryChargerManageVO.setModId(sessionInfoVO.getUserId());
        deliveryChargerManageVO.setModDt(currentDt);

        if (StringUtil.getOrBlank(deliveryChargerManageVO.getDlvrCd()).equals("")) {
            // 신규 배송기사코드 조회
        	sessionInfoVO.setHqOfficeCd(deliveryChargerManageVO.getHqOfficeCd());
            String dlvrCd = deliveryChargerManageMapper.getNewDlvrCd(sessionInfoVO);
            deliveryChargerManageVO.setDlvrCd(dlvrCd);
            // 추가
            result = deliveryChargerManageMapper.insertDeliveryCharger(deliveryChargerManageVO);

            // [1241 창고사용여부] 환경설정값 으로 조건에 따라 창고 로직 히든하여 무조건 창고 등록하도록
            deliveryChargerManageVO.setStorageCd("001");
            result = deliveryChargerManageMapper.insertDeliveryChargerStorage(deliveryChargerManageVO);

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

    /** 배송기사 삭제 */
    @Override
    public int deleteDeliveryCharger(DeliveryChargerManageVO deliveryChargerManageVO) {
        int result = 0;

        // 배송기사 관리창고 삭제
        result = deliveryChargerManageMapper.deleteAllDeliveryChargerStorage(deliveryChargerManageVO);
        // 관리 창고가 없을수도 있기때문에 result 가 0 일때 exception 내는것은 주석처리
//        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 배송기사 삭제
        result = deliveryChargerManageMapper.deleteDeliveryCharger(deliveryChargerManageVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
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
//            deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
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
//            deliveryChargerManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
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
