package kr.co.solbipos.dlvr.info.deliveryTelNo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.deliveryTelNo.service.DeliveryTelNoService;
import kr.co.solbipos.dlvr.info.deliveryTelNo.service.DeliveryTelNoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("deliveryTelNoService")
@Transactional
public class DeliveryTelNoServiceImpl implements DeliveryTelNoService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DeliveryTelNoMapper deliveryTelNoMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DeliveryTelNoServiceImpl(DeliveryTelNoMapper deliveryTelNoMapper, MessageService messageService) {
        this.deliveryTelNoMapper = deliveryTelNoMapper;
        this.messageService = messageService;
    }

    /** 배달지정보관리 조회 */
    @Override
    public List<DefaultMap<Object>> getDeliveryTelNoList(DeliveryTelNoVO deliveryTelNoVO, SessionInfoVO sessionInfoVO) {

        deliveryTelNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        deliveryTelNoVO.setStoreCd(sessionInfoVO.getStoreCd());

        System.out.println("값 : " + deliveryTelNoVO.getCidTelNo());
        System.out.println("값 : " + deliveryTelNoVO.getDlvrAddr());
        System.out.println("값 : " + deliveryTelNoVO.getDlvrMemo());

        return deliveryTelNoMapper.getDeliveryTelNoList(deliveryTelNoVO);
    }

    /** 배달지정보관리 수정 */
    @Override
    public int updateDeliveryTelNo(DeliveryTelNoVO[] deliveryTelNoVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;

        for(DeliveryTelNoVO deliveryTelNoVO : deliveryTelNoVOs) {

            deliveryTelNoVO.setStoreCd(sessionInfoVO.getStoreCd());

            if (deliveryTelNoVO.getStatus() == GridDataFg.UPDATE) { // 수정
                resultCnt += deliveryTelNoMapper.updateDeliveryTelNo(deliveryTelNoVO);
            } else if (deliveryTelNoVO.getStatus() == GridDataFg.DELETE) {  // 삭제
                resultCnt += deliveryTelNoMapper.deleteDeliveryTelNo(deliveryTelNoVO);
            }
        }

        return resultCnt;
    }

    /** 배달지정보관리 전체삭제 */
    @Override
    public int deleteAllDeliveryTelNo(DeliveryTelNoVO deliveryTelNoVO, SessionInfoVO sessionInfoVO) {

        deliveryTelNoVO.setStoreCd(sessionInfoVO.getStoreCd());

        return deliveryTelNoMapper.deleteAllDeliveryTelNo(deliveryTelNoVO);
    }

}
