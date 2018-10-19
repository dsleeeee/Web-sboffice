package kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.order.storeOrder.service.impl.StoreOrderMapper;
import kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.RtnDstbReqService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.RtnDstbReqVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("RtnDstbReqService")
public class RtnDstbReqServiceImpl implements RtnDstbReqService {
    private final RtnDstbReqMapper rtnDstbReqMapper;
    private final StoreOrderMapper storeOrderMapper;
    private final StoreOrderService storeOrderService;
    private final MessageService messageService;

    public RtnDstbReqServiceImpl(RtnDstbReqMapper rtnDstbReqMapper, StoreOrderMapper storeOrderMapper, StoreOrderService storeOrderService, MessageService messageService) {
        this.rtnDstbReqMapper = rtnDstbReqMapper;
        this.storeOrderMapper = storeOrderMapper;
        this.storeOrderService = storeOrderService;
        this.messageService = messageService;
    }

    /** 분배등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbReqList(RtnDstbReqVO rtnDstbReqVO) {
        if(!StringUtil.getOrBlank(rtnDstbReqVO.getProcFg()).equals("")) {
            rtnDstbReqVO.setArrProcFg(rtnDstbReqVO.getProcFg().split(","));
        }
        return rtnDstbReqMapper.getRtnDstbReqList(rtnDstbReqVO);
    }

    /** 분배등록 분배완료 */
    @Override
    public int saveDstbConfirm(RtnDstbReqVO[] rtnDstbReqVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbReqVO rtnDstbReqVO : rtnDstbReqVOs) {
            // 처리구분 조회
            StoreOrderVO storeOrderVO = new StoreOrderVO();
            storeOrderVO.setReqDate(rtnDstbReqVO.getReqDate());
            storeOrderVO.setStoreCd(rtnDstbReqVO.getStoreCd());
            storeOrderVO.setSlipFg(rtnDstbReqVO.getSlipFg());
            DefaultMap<String> orderProcFg = storeOrderService.getOrderProcFgCheck(storeOrderVO);

            // 처리구분이 분배마감이 아닌 경우 실행
            if(!orderProcFg.get("procFg").equals("20")) {
                rtnDstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                rtnDstbReqVO.setProcFg("10");
                rtnDstbReqVO.setDstbFg("0");
                rtnDstbReqVO.setRegId(sessionInfoVO.getUserId());
                rtnDstbReqVO.setRegDt(currentDt);
                rtnDstbReqVO.setModId(sessionInfoVO.getUserId());
                rtnDstbReqVO.setModDt(currentDt);

                if(orderProcFg.get("procFg").equals("00")) {
                    // MD수량 관련 내용을 주문내역으로 수정
                    result = rtnDstbReqMapper.updateDstbConfirm(rtnDstbReqVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    returnResult += result;
                }

                // 주문등록 HD 내용 수정
                storeOrderVO.setProcFg("20");
                storeOrderVO.setModId(sessionInfoVO.getUserId());
                storeOrderVO.setModDt(currentDt);
                result = storeOrderMapper.updateStoreOrder(storeOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                returnResult += result;

                // 분배자료 생성
                result = rtnDstbReqMapper.insertRtnDstbReqRegist(rtnDstbReqVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                returnResult += result;
            }
        }

        return returnResult;
    }

    /** 분배등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbReqDtlList(RtnDstbReqVO rtnDstbReqVO) {
        return rtnDstbReqMapper.getRtnDstbReqDtlList(rtnDstbReqVO);
    }

    /** 분배등록 상세 리스트 저장 */
    @Override
    public int saveRtnDstbReqDtl(RtnDstbReqVO[] rtnDstbReqVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        StoreOrderVO storeOrderVO = new StoreOrderVO();

        int i = 0;
        String dstbConfirmFg = "";

        for (RtnDstbReqVO rtnDstbReqVO : rtnDstbReqVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                String procFg = (StringUtil.getOrBlank(rtnDstbReqVO.getDstbConfirmFg()).equals("Y") ? "20" : "10");
                dstbConfirmFg = StringUtil.getOrBlank(rtnDstbReqVO.getDstbConfirmFg());

                storeOrderVO.setReqDate(rtnDstbReqVO.getReqDate());
                storeOrderVO.setSlipFg(rtnDstbReqVO.getSlipFg());
                storeOrderVO.setStoreCd(rtnDstbReqVO.getStoreCd());
                storeOrderVO.setProcFg(procFg);
                storeOrderVO.setEmpNo(rtnDstbReqVO.getEmpNo());
                storeOrderVO.setRemark(rtnDstbReqVO.getHdRemark());
                storeOrderVO.setRegId(sessionInfoVO.getUserId());
                storeOrderVO.setRegDt(currentDt);
                storeOrderVO.setModId(sessionInfoVO.getUserId());
                storeOrderVO.setModDt(currentDt);
            }

            int slipFg       = rtnDstbReqVO.getSlipFg();
            int mdUnitQty    = (rtnDstbReqVO.getMdUnitQty() == null ? 0 : rtnDstbReqVO.getMdUnitQty()) * slipFg;
            int mdEtcQty     = (rtnDstbReqVO.getMdEtcQty()  == null ? 0 : rtnDstbReqVO.getMdEtcQty()) * slipFg;
            int mdTotQty     = (rtnDstbReqVO.getMdTotQty()  == null ? 0 : rtnDstbReqVO.getMdTotQty()) * slipFg;
            Long mdAmt       = (rtnDstbReqVO.getMdAmt() == null ? 0 : rtnDstbReqVO.getMdAmt()) * slipFg;
            Long mdVat       = (rtnDstbReqVO.getMdVat() == null ? 0 : rtnDstbReqVO.getMdVat()) * slipFg;
            Long mdTot       = (rtnDstbReqVO.getMdTot() == null ? 0 : rtnDstbReqVO.getMdTot()) * slipFg;

            rtnDstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbReqVO.setMdUnitQty(mdUnitQty);
            rtnDstbReqVO.setMdEtcQty(mdEtcQty);
            rtnDstbReqVO.setMdTotQty(mdTotQty);
            rtnDstbReqVO.setMdAmt(mdAmt);
            rtnDstbReqVO.setMdVat(mdVat);
            rtnDstbReqVO.setMdTot(mdTot);
            rtnDstbReqVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbReqVO.setRegDt(currentDt);
            rtnDstbReqVO.setModId(sessionInfoVO.getUserId());
            rtnDstbReqVO.setModDt(currentDt);

            result = rtnDstbReqMapper.updateRtnDstbReqDtl(rtnDstbReqVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // 주문등록 HD 내용 수정
        result = storeOrderMapper.updateStoreOrder(storeOrderVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        returnResult += result;

        // 분배완료인 경우 분배자료 생성
        if(dstbConfirmFg.equals("Y")) {
            RtnDstbReqVO rtnDstbReqVO = new RtnDstbReqVO();
            rtnDstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbReqVO.setStoreCd(storeOrderVO.getStoreCd());
            rtnDstbReqVO.setReqDate(storeOrderVO.getReqDate());
            rtnDstbReqVO.setSlipFg(storeOrderVO.getSlipFg());
            rtnDstbReqVO.setProcFg("10");
            rtnDstbReqVO.setEmpNo(storeOrderVO.getEmpNo());
            rtnDstbReqVO.setDstbFg("0");
            //            rtnDstbReqVO.setRemark(storeOrderVO.getRemark());
            rtnDstbReqVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbReqVO.setRegDt(currentDt);
            rtnDstbReqVO.setModId(sessionInfoVO.getUserId());
            rtnDstbReqVO.setModDt(currentDt);

            result = rtnDstbReqMapper.insertRtnDstbReqRegist(rtnDstbReqVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

        return returnResult;
    }
}
