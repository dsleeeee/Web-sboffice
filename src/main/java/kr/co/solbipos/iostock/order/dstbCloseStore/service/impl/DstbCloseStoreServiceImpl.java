package kr.co.solbipos.iostock.order.dstbCloseStore.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreService;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("DstbCloseStoreService")
public class DstbCloseStoreServiceImpl implements DstbCloseStoreService {
    private final DstbCloseStoreMapper dstbCloseStoreMapper;
    private final MessageService messageService;

    public DstbCloseStoreServiceImpl(DstbCloseStoreMapper dstbCloseStoreMapper, MessageService messageService) {
        this.dstbCloseStoreMapper = dstbCloseStoreMapper;
        this.messageService = messageService;
    }

    /** 분배마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseStoreList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbCloseStoreList(dstbCloseStoreVO);
    }

    /** 분배마감 리스트 확정 */
    @Override
    public int saveDstbCloseStoreConfirm(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseStoreVO.setUpdateProcFg("20");
            dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setRegDt(currentDt);
            dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setModDt(currentDt);

            result = dstbCloseStoreMapper.updateDstbCloseConfirm(dstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 분배마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseStoreDtlList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbCloseStoreDtlList(dstbCloseStoreVO);
    }

    /** 분배마감 상세 리스트 저장 */
    @Override
    public int saveDstbCloseStoreDtl(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            int slipFg     = dstbCloseStoreVO.getSlipFg();
            int mgrUnitQty = (dstbCloseStoreVO.getMgrUnitQty() == null ? 0 : dstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (dstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : dstbCloseStoreVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (dstbCloseStoreVO.getMgrTotQty()  == null ? 0 : dstbCloseStoreVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (dstbCloseStoreVO.getMgrAmt()     == null ? 0 : dstbCloseStoreVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (dstbCloseStoreVO.getMgrVat()     == null ? 0 : dstbCloseStoreVO.getMgrVat()) * slipFg;
            Long mgrTot    = (dstbCloseStoreVO.getMgrTot()     == null ? 0 : dstbCloseStoreVO.getMgrTot()) * slipFg;

            dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
            dstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
            dstbCloseStoreVO.setMgrTotQty(mgrTotQty);
            dstbCloseStoreVO.setMgrAmt(mgrAmt);
            dstbCloseStoreVO.setMgrVat(mgrVat);
            dstbCloseStoreVO.setMgrTot(mgrTot);
            dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setRegDt(currentDt);
            dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            dstbCloseStoreVO.setModDt(currentDt);

            result = dstbCloseStoreMapper.updateDstbCloseDtl(dstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            if(dstbCloseStoreVO.getConfirmYn()) {
                dstbCloseStoreVO.setProcFg("20");
                result = dstbCloseStoreMapper.updateDstbCloseDtlConfirm(dstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            returnResult += result;
        }

        return returnResult;
    }

    /** 추가분배시 주문가능여부 조회 */
    @Override
    public DefaultMap<String> getOrderFg(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getOrderFg(dstbCloseStoreVO);
    }

    /** 분배마감 - 추가분배 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbAddProdList(DstbCloseStoreVO dstbCloseStoreVO) {
        return dstbCloseStoreMapper.getDstbAddProdList(dstbCloseStoreVO);
    }

    /** 분배마감 - 추가분배 상세 리스트 저장 */
    @Override
    public int saveDstbAddProd(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseStoreVO dstbCloseStoreVO : dstbCloseStoreVOs) {
            String insFg = "";
            // 기분배수량이 있는 경우 수정
            if(dstbCloseStoreVO.getPrevMgrTotQty() != null) {
                insFg = "U";
                // 기분배수량이 있으면서 분배수량이 0 이나 null 인 경우 삭제
                if(dstbCloseStoreVO.getMgrTotQty() == 0 || dstbCloseStoreVO.getMgrTotQty() == null) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = dstbCloseStoreVO.getSlipFg();
                int poUnitQty    = dstbCloseStoreVO.getPoUnitQty();
                int prevUnitQty  = (dstbCloseStoreVO.getPrevMgrUnitQty() == null ? 0 : dstbCloseStoreVO.getPrevMgrUnitQty());
                int prevEtcQty   = (dstbCloseStoreVO.getPrevMgrEtcQty()  == null ? 0 : dstbCloseStoreVO.getPrevMgrEtcQty());
                int unitQty      = (dstbCloseStoreVO.getMgrUnitQty()     == null ? 0 : dstbCloseStoreVO.getMgrUnitQty());
                int etcQty       = (dstbCloseStoreVO.getMgrEtcQty()      == null ? 0 : dstbCloseStoreVO.getMgrEtcQty());
                int mgrUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int mgrEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                int mgrTotQty  = (dstbCloseStoreVO.getMgrTotQty()  == null ? 0 : dstbCloseStoreVO.getMgrTotQty())  * slipFg;
                Long mgrAmt    = (dstbCloseStoreVO.getMgrAmt()     == null ? 0 : dstbCloseStoreVO.getMgrAmt())     * slipFg;
                Long mgrVat    = (dstbCloseStoreVO.getMgrVat()     == null ? 0 : dstbCloseStoreVO.getMgrVat())     * slipFg;
                Long mgrTot    = (dstbCloseStoreVO.getMgrTot()     == null ? 0 : dstbCloseStoreVO.getMgrTot())     * slipFg;

                dstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
                dstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
                dstbCloseStoreVO.setMgrTotQty(mgrTotQty);
                dstbCloseStoreVO.setMgrAmt(mgrAmt);
                dstbCloseStoreVO.setMgrVat(mgrVat);
                dstbCloseStoreVO.setMgrTot(mgrTot);
                dstbCloseStoreVO.setProcFg("10");
                dstbCloseStoreVO.setDstbFg("0");
                dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
                dstbCloseStoreVO.setRegDt(currentDt);
                dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
                dstbCloseStoreVO.setModDt(currentDt);
            }
            dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if(insFg.equals("I")) {
                result = dstbCloseStoreMapper.insertDstbAddProd(dstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(insFg.equals("U")) {
                result = dstbCloseStoreMapper.updateDstbAddProd(dstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(insFg.equals("D")) {
                result = dstbCloseStoreMapper.deleteDstbAddProd(dstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }
}
