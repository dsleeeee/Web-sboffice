package kr.co.solbipos.iostock.order.storeOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("StoreOrderService")
public class StoreOrderServiceImpl implements StoreOrderService {
    private final StoreOrderMapper storeOrderMapper;
    private final MessageService messageService;

    @Autowired
    public StoreOrderServiceImpl(StoreOrderMapper storeOrderMapper, MessageService messageService) {
        this.storeOrderMapper = storeOrderMapper;
        this.messageService = messageService;
    }

    /** 주문등록 HD 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreOrderList(storeOrderVO);
    }

    /** 주문등록 DT 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO) {
        return storeOrderMapper.getStoreOrderDtlList(storeOrderDtlVO);
    }

    /** 주문등록 상품추가 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO) {
        return storeOrderMapper.getStoreOrderRegistList(storeOrderDtlVO);
    }

    /** 주문등록 주문상품 저장 */
    @Override
    public int saveStoreOrderRegist(StoreOrderDtlVO[] storeOrderDtlVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        StoreOrderVO storeOrderVO = new StoreOrderVO();

        int i = 0;
        for (StoreOrderDtlVO storeOrderDtlVO : storeOrderDtlVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                storeOrderVO.setReqDate(storeOrderDtlVO.getReqDate());
                storeOrderVO.setSlipFg(storeOrderDtlVO.getSlipFg());
                storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
                storeOrderVO.setProcFg("0");
                storeOrderVO.setRemark(storeOrderDtlVO.getHdRemark());
                storeOrderVO.setRegId(sessionInfoVO.getUserId());
                storeOrderVO.setRegDt(currentDt);
                storeOrderVO.setModId(sessionInfoVO.getUserId());
                storeOrderVO.setModDt(currentDt);
            }

            // DTL 파라미터 세팅
            String insFg = "";
            // 기주문수량이 있는 경우 수정
            if(storeOrderDtlVO.getPrevOrderUnitQty() != null || storeOrderDtlVO.getPrevOrderEtcQty() != null) {
                insFg = "U";
            }
            else {
                insFg = "I";
            }

            int slipFg       = storeOrderDtlVO.getSlipFg();
            int poUnitQty    = storeOrderDtlVO.getPoUnitQty();
            int prevUnitQty  = (storeOrderDtlVO.getPrevOrderUnitQty() == null ? 0 : storeOrderDtlVO.getPrevOrderUnitQty());
            int prevEtcQty   = (storeOrderDtlVO.getPrevOrderEtcQty()  == null ? 0 : storeOrderDtlVO.getPrevOrderEtcQty());
            int unitQty      = (storeOrderDtlVO.getOrderUnitQty()     == null ? 0 : storeOrderDtlVO.getOrderUnitQty());
            int etcQty       = (storeOrderDtlVO.getOrderEtcQty()      == null ? 0 : storeOrderDtlVO.getOrderEtcQty());
            int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
            int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;

            storeOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeOrderDtlVO.setOrderUnitQty(orderUnitQty);
            storeOrderDtlVO.setOrderEtcQty(orderEtcQty);
            storeOrderDtlVO.setRegId(sessionInfoVO.getUserId());
            storeOrderDtlVO.setRegDt(currentDt);
            storeOrderDtlVO.setModId(sessionInfoVO.getUserId());
            storeOrderDtlVO.setModDt(currentDt);

            // 기주문수량이 있는 경우 수정
            if(insFg.equals("U")) {
                // 수정
                result = storeOrderMapper.updateStoreOrderDtl(storeOrderDtlVO);
            }
            else {
                // 추가
                result = storeOrderMapper.insertStoreOrderDtl(storeOrderDtlVO);
            }

            returnResult += result;
            i++;
        }

        int dtlCnt = 0;
        String hdExist = "N";

        // 주문요청일의 상품건수 조회
        dtlCnt = storeOrderMapper.getDtlCnt(storeOrderVO);

        // 상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = storeOrderMapper.deleteStoreOrder(storeOrderVO);
        }
        // 상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = storeOrderMapper.getHdExist(storeOrderVO);
            if(hdExist.equals("Y")) {
                result = storeOrderMapper.updateStoreOrder(storeOrderVO);
            }
            else if(hdExist.equals("N")) {
                result = storeOrderMapper.insertStoreOrder(storeOrderVO);
            }
        }


        if ( returnResult == storeOrderDtlVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 주문등록 매장마감여부 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCloseCheck(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreCloseCheck(storeOrderVO);
    }

    /** 주문등록 주문진행구분 조회 */
    @Override
    public List<DefaultMap<String>> getOrderProcFgCheck(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getOrderProcFgCheck(storeOrderVO);
    }

    /** 주문등록 매장여신 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLoan(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreLoan(storeOrderVO);
    }
}
