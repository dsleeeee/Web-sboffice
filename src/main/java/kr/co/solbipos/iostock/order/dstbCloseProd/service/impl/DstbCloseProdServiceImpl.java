package kr.co.solbipos.iostock.order.dstbCloseProd.service.impl;

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
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdService;
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdVO;

@Service("dstbCloseProdService")
@Transactional
public class DstbCloseProdServiceImpl implements DstbCloseProdService {
    private final DstbCloseProdMapper dstbCloseProdMapper;
    private final MessageService messageService;

    public DstbCloseProdServiceImpl(DstbCloseProdMapper dstbCloseProdMapper, MessageService messageService) {
        this.dstbCloseProdMapper = dstbCloseProdMapper;
        this.messageService = messageService;
    }

    /** 분배마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseProdList(DstbCloseProdVO dstbCloseProdVO) {
        return dstbCloseProdMapper.getDstbCloseProdList(dstbCloseProdVO);
    }

    /** 분배마감 리스트 확정 */
    @Override
    public int saveDstbCloseProdConfirm(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseProdVO dstbCloseProdVO : dstbCloseProdVOs) {
            dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseProdVO.setUpdateProcFg("20");
            dstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseProdVO.setRegDt(currentDt);
            dstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            dstbCloseProdVO.setModDt(currentDt);

            result = dstbCloseProdMapper.updateDstbCloseProdConfirm(dstbCloseProdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 분배마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseProdDtlList(DstbCloseProdVO dstbCloseProdVO) {
        return dstbCloseProdMapper.getDstbCloseProdDtlList(dstbCloseProdVO);
    }

    /** 분배마감 상세 리스트 저장 */
    @Override
    public int saveDstbCloseProdDtl(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseProdVO dstbCloseProdVO : dstbCloseProdVOs) {
            int slipFg     = dstbCloseProdVO.getSlipFg();
            int mgrUnitQty = (dstbCloseProdVO.getMgrUnitQty() == null ? 0 : dstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (dstbCloseProdVO.getMgrEtcQty()  == null ? 0 : dstbCloseProdVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (dstbCloseProdVO.getMgrTotQty()  == null ? 0 : dstbCloseProdVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (dstbCloseProdVO.getMgrAmt()     == null ? 0 : dstbCloseProdVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (dstbCloseProdVO.getMgrVat()     == null ? 0 : dstbCloseProdVO.getMgrVat()) * slipFg;
            Long mgrTot    = (dstbCloseProdVO.getMgrTot()     == null ? 0 : dstbCloseProdVO.getMgrTot()) * slipFg;

            dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
            dstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
            dstbCloseProdVO.setMgrTotQty(mgrTotQty);
            dstbCloseProdVO.setMgrAmt(mgrAmt);
            dstbCloseProdVO.setMgrVat(mgrVat);
            dstbCloseProdVO.setMgrTot(mgrTot);
            dstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            dstbCloseProdVO.setRegDt(currentDt);
            dstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            dstbCloseProdVO.setModDt(currentDt);

            // 분배수량이 0 이나 null 인 경우 삭제
//            if(dstbCloseProdVO.getMgrTotQty() == 0 || dstbCloseProdVO.getMgrTotQty() == null) {
//                result = dstbCloseProdMapper.deleteDstbCloseProdDtl(dstbCloseProdVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
                result = dstbCloseProdMapper.updateDstbCloseProdDtl(dstbCloseProdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(dstbCloseProdVO.getConfirmYn()) {
                    dstbCloseProdVO.setProcFg("20");
                    result = dstbCloseProdMapper.updateDstbCloseProdDtlConfirm(dstbCloseProdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
//            }
            returnResult += result;
        }

        return returnResult;
    }

    /** 분배마감 추가등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseProdAddProdList(DstbCloseProdVO dstbCloseProdVO) {
        return dstbCloseProdMapper.getDstbCloseProdAddProdList(dstbCloseProdVO);
    }

    /** 분배마감 추가등록 분배등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbCloseProdAddRegistList(DstbCloseProdVO dstbCloseProdVO) {
        if(!StringUtil.getOrBlank(dstbCloseProdVO.getStoreCd()).equals("")) {
            dstbCloseProdVO.setArrStoreCd(dstbCloseProdVO.getStoreCd().split(","));
        }
        return dstbCloseProdMapper.getDstbCloseProdAddRegistList(dstbCloseProdVO);
    }

    /** 분배마감 - 추가등록 분배등록 리스트 저장 */
    @Override
    public int saveDstbCloseProdAddRegist(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DstbCloseProdVO dstbCloseProdVO : dstbCloseProdVOs) {

            int slipFg       = dstbCloseProdVO.getSlipFg();
            int mgrUnitQty   = (dstbCloseProdVO.getMgrUnitQty() == null ? 0 : dstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty    = (dstbCloseProdVO.getMgrEtcQty()  == null ? 0 : dstbCloseProdVO.getMgrEtcQty())  * slipFg;
            int mgrTotQty    = (dstbCloseProdVO.getMgrTotQty()  == null ? 0 : dstbCloseProdVO.getMgrTotQty())  * slipFg;
            Long mgrAmt      = (dstbCloseProdVO.getMgrAmt()     == null ? 0 : dstbCloseProdVO.getMgrAmt())     * slipFg;
            Long mgrVat      = (dstbCloseProdVO.getMgrVat()     == null ? 0 : dstbCloseProdVO.getMgrVat())     * slipFg;
            Long mgrTot      = (dstbCloseProdVO.getMgrTot()     == null ? 0 : dstbCloseProdVO.getMgrTot())     * slipFg;

            if(mgrTotQty > 0) {
                dstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
                dstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
                dstbCloseProdVO.setMgrTotQty(mgrTotQty);
                dstbCloseProdVO.setMgrAmt(mgrAmt);
                dstbCloseProdVO.setMgrVat(mgrVat);
                dstbCloseProdVO.setMgrTot(mgrTot);
                dstbCloseProdVO.setProcFg("10");
                dstbCloseProdVO.setDstbFg("0");
                dstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
                dstbCloseProdVO.setRegDt(currentDt);
                dstbCloseProdVO.setModId(sessionInfoVO.getUserId());
                dstbCloseProdVO.setModDt(currentDt);
                dstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                result = dstbCloseProdMapper.insertDstbCloseProdAddRegist(dstbCloseProdVO);
                if (result <= 0)
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if (dstbCloseProdVO.getConfirmYn()) {
                    dstbCloseProdVO.setProcFg("20");
                    result = dstbCloseProdMapper.updateDstbCloseProdDtlConfirm(dstbCloseProdVO);
                    if (result <= 0)
                        throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                returnResult += result;
            }
        }

        return returnResult;
    }
}
