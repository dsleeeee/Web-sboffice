package kr.co.solbipos.iostock.order.dstbReq.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.iostock.order.storeOrder.service.impl.StoreOrderMapper;

@Service("dstbReqService")
@Transactional
public class DstbReqServiceImpl implements DstbReqService {
    private final DstbReqMapper dstbReqMapper;
    private final StoreOrderMapper storeOrderMapper;
    private final StoreOrderService storeOrderService;
    private final MessageService messageService;

    public DstbReqServiceImpl(DstbReqMapper dstbReqMapper, StoreOrderMapper storeOrderMapper, StoreOrderService storeOrderService, MessageService messageService) {
        this.dstbReqMapper = dstbReqMapper;
        this.storeOrderMapper = storeOrderMapper;
        this.storeOrderService = storeOrderService;
        this.messageService = messageService;
    }

    /** 분배등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbReqList(DstbReqVO dstbReqVO) {
        if(!StringUtil.getOrBlank(dstbReqVO.getProcFg()).equals("")) {
            dstbReqVO.setArrProcFg(dstbReqVO.getProcFg().split(","));
        }
        return dstbReqMapper.getDstbReqList(dstbReqVO);
    }

    /** 분배등록 분배완료 */
    @Override
    public int saveDstbConfirm(DstbReqVO[] dstbReqVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbReqVO dstbReqVO : dstbReqVOs) {
            // 처리구분 조회
            StoreOrderVO storeOrderVO = new StoreOrderVO();
            storeOrderVO.setReqDate(dstbReqVO.getReqDate());
            storeOrderVO.setStoreCd(dstbReqVO.getStoreCd());
            storeOrderVO.setSlipFg(dstbReqVO.getSlipFg());
            DefaultMap<String> orderProcFg = storeOrderService.getOrderProcFgCheck(storeOrderVO);

            // 처리구분이 분배마감이 아닌 경우 실행
            if(!orderProcFg.get("procFg").equals("20")) {
                dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                dstbReqVO.setProcFg("10");
                dstbReqVO.setDstbFg("0");
                dstbReqVO.setRegId(sessionInfoVO.getUserId());
                dstbReqVO.setRegDt(currentDt);
                dstbReqVO.setModId(sessionInfoVO.getUserId());
                dstbReqVO.setModDt(currentDt);

                if(orderProcFg.get("procFg").equals("00")) {
                    // MD수량 관련 내용을 주문내역으로 수정
                    result = dstbReqMapper.updateDstbConfirm(dstbReqVO);
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
                result = dstbReqMapper.insertDstbReqRegist(dstbReqVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                returnResult += result;
            }
        }

        return returnResult;
    }

    /** 분배등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbReqDtlList(DstbReqVO dstbReqVO) {
        return dstbReqMapper.getDstbReqDtlList(dstbReqVO);
    }

    /** 분배등록 상세 리스트 저장 */
    @Override
    public int saveDstbReqDtl(DstbReqVO[] dstbReqVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        StoreOrderVO storeOrderVO = new StoreOrderVO();

        int i = 0;
        String dstbConfirmFg = "";

        for (DstbReqVO dstbReqVO : dstbReqVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                String procFg = (StringUtil.getOrBlank(dstbReqVO.getDstbConfirmFg()).equals("Y") ? "20" : "10");
                dstbConfirmFg = StringUtil.getOrBlank(dstbReqVO.getDstbConfirmFg());

                storeOrderVO.setReqDate(dstbReqVO.getReqDate());
                storeOrderVO.setSlipFg(dstbReqVO.getSlipFg());
                storeOrderVO.setStoreCd(dstbReqVO.getStoreCd());
                storeOrderVO.setProcFg(procFg);
                storeOrderVO.setEmpNo(dstbReqVO.getEmpNo());
                storeOrderVO.setRemark(dstbReqVO.getHdRemark());
                storeOrderVO.setRegId(sessionInfoVO.getUserId());
                storeOrderVO.setRegDt(currentDt);
                storeOrderVO.setModId(sessionInfoVO.getUserId());
                storeOrderVO.setModDt(currentDt);
            }

            int slipFg       = dstbReqVO.getSlipFg();
            int mdUnitQty    = (dstbReqVO.getMdUnitQty() == null ? 0 : dstbReqVO.getMdUnitQty()) * slipFg;
            int mdEtcQty     = (dstbReqVO.getMdEtcQty()  == null ? 0 : dstbReqVO.getMdEtcQty()) * slipFg;
            int mdTotQty     = (dstbReqVO.getMdTotQty()  == null ? 0 : dstbReqVO.getMdTotQty()) * slipFg;
            Long mdAmt       = (dstbReqVO.getMdAmt() == null ? 0 : dstbReqVO.getMdAmt()) * slipFg;
            Long mdVat       = (dstbReqVO.getMdVat() == null ? 0 : dstbReqVO.getMdVat()) * slipFg;
            Long mdTot       = (dstbReqVO.getMdTot() == null ? 0 : dstbReqVO.getMdTot()) * slipFg;

            dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbReqVO.setMdUnitQty(mdUnitQty);
            dstbReqVO.setMdEtcQty(mdEtcQty);
            dstbReqVO.setMdTotQty(mdTotQty);
            dstbReqVO.setMdAmt(mdAmt);
            dstbReqVO.setMdVat(mdVat);
            dstbReqVO.setMdTot(mdTot);
            dstbReqVO.setRegId(sessionInfoVO.getUserId());
            dstbReqVO.setRegDt(currentDt);
            dstbReqVO.setModId(sessionInfoVO.getUserId());
            dstbReqVO.setModDt(currentDt);

            result = dstbReqMapper.updateDstbReqDtl(dstbReqVO);
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
            DstbReqVO dstbReqVO = new DstbReqVO();
            dstbReqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbReqVO.setStoreCd(storeOrderVO.getStoreCd());
            dstbReqVO.setReqDate(storeOrderVO.getReqDate());
            dstbReqVO.setSlipFg(storeOrderVO.getSlipFg());
            dstbReqVO.setProcFg("10");
            dstbReqVO.setEmpNo(storeOrderVO.getEmpNo());
            dstbReqVO.setDstbFg("0");
//            dstbReqVO.setRemark(storeOrderVO.getRemark());
            dstbReqVO.setRegId(sessionInfoVO.getUserId());
            dstbReqVO.setRegDt(currentDt);
            dstbReqVO.setModId(sessionInfoVO.getUserId());
            dstbReqVO.setModDt(currentDt);

            result = dstbReqMapper.insertDstbReqRegist(dstbReqVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

        return returnResult;
    }


}
