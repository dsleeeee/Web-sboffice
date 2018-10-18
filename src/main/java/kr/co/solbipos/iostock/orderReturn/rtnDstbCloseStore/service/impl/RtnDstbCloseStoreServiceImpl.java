package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("RtnDstbCloseStoreService")
public class RtnDstbCloseStoreServiceImpl implements RtnDstbCloseStoreService {
    private final RtnDstbCloseStoreMapper rtnDstbCloseStoreMapper;
    private final MessageService messageService;

    public RtnDstbCloseStoreServiceImpl(RtnDstbCloseStoreMapper rtnDstbCloseStoreMapper, MessageService messageService) {
        this.rtnDstbCloseStoreMapper = rtnDstbCloseStoreMapper;
        this.messageService = messageService;
    }

    /** 반품마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseStoreList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getRtnDstbCloseStoreList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 리스트 확정 */
    @Override
    public int saveRtnDstbCloseStoreConfirm(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseStoreVO.setUpdateProcFg("20");
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);

            result = rtnDstbCloseStoreMapper.updateDstbCloseConfirm(rtnDstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseStoreDtlList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getRtnDstbCloseStoreDtlList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 상세 리스트 저장 */
    @Override
    public int saveRtnDstbCloseStoreDtl(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            int slipFg     = rtnDstbCloseStoreVO.getSlipFg();
            int mgrUnitQty = (rtnDstbCloseStoreVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (rtnDstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (rtnDstbCloseStoreVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (rtnDstbCloseStoreVO.getMgrAmt()     == null ? 0 : rtnDstbCloseStoreVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (rtnDstbCloseStoreVO.getMgrVat()     == null ? 0 : rtnDstbCloseStoreVO.getMgrVat()) * slipFg;
            Long mgrTot    = (rtnDstbCloseStoreVO.getMgrTot()     == null ? 0 : rtnDstbCloseStoreVO.getMgrTot()) * slipFg;

            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
            rtnDstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
            rtnDstbCloseStoreVO.setMgrTotQty(mgrTotQty);
            rtnDstbCloseStoreVO.setMgrAmt(mgrAmt);
            rtnDstbCloseStoreVO.setMgrVat(mgrVat);
            rtnDstbCloseStoreVO.setMgrTot(mgrTot);
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);

            // 분배수량이 0 이나 null 인 경우 삭제
            if(rtnDstbCloseStoreVO.getMgrTotQty() == 0 || rtnDstbCloseStoreVO.getMgrTotQty() == null) {
                result = rtnDstbCloseStoreMapper.deleteDstbCloseDtl(rtnDstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else {
                result = rtnDstbCloseStoreMapper.updateDstbCloseDtl(rtnDstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(rtnDstbCloseStoreVO.getConfirmYn()) {
                    rtnDstbCloseStoreVO.setProcFg("20");
                    result = rtnDstbCloseStoreMapper.updateDstbCloseDtlConfirm(rtnDstbCloseStoreVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 - 추가등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbAddList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getDstbAddList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 - 추가등록 상세 리스트 저장 */
    @Override
    public int saveDstbAdd(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            int slipFg       = rtnDstbCloseStoreVO.getSlipFg();
            int mgrUnitQty   = (rtnDstbCloseStoreVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty    = (rtnDstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrEtcQty())  * slipFg;
            int mgrTotQty    = (rtnDstbCloseStoreVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrTotQty())  * slipFg;
            Long mgrAmt      = (rtnDstbCloseStoreVO.getMgrAmt()     == null ? 0 : rtnDstbCloseStoreVO.getMgrAmt())     * slipFg;
            Long mgrVat      = (rtnDstbCloseStoreVO.getMgrVat()     == null ? 0 : rtnDstbCloseStoreVO.getMgrVat())     * slipFg;
            Long mgrTot      = (rtnDstbCloseStoreVO.getMgrTot()     == null ? 0 : rtnDstbCloseStoreVO.getMgrTot())     * slipFg;

            rtnDstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
            rtnDstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
            rtnDstbCloseStoreVO.setMgrTotQty(mgrTotQty);
            rtnDstbCloseStoreVO.setMgrAmt(mgrAmt);
            rtnDstbCloseStoreVO.setMgrVat(mgrVat);
            rtnDstbCloseStoreVO.setMgrTot(mgrTot);
            rtnDstbCloseStoreVO.setProcFg("10");
            rtnDstbCloseStoreVO.setDstbFg("0");
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);
            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result = rtnDstbCloseStoreMapper.insertDstbAdd(rtnDstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }
}
