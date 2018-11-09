package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("RtnDstbCloseProdService")
public class RtnDstbCloseProdServiceImpl implements RtnDstbCloseProdService {
    private final RtnDstbCloseProdMapper rtnDstbCloseProdMapper;
    private final MessageService messageService;

    public RtnDstbCloseProdServiceImpl(RtnDstbCloseProdMapper rtnDstbCloseProdMapper, MessageService messageService) {
        this.rtnDstbCloseProdMapper = rtnDstbCloseProdMapper;
        this.messageService = messageService;
    }

    /** 반품마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdList(rtnDstbCloseProdVO);
    }

    /** 반품마감 리스트 확정 */
    @Override
    public int saveRtnDstbCloseProdConfirm(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {
            rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseProdVO.setUpdateProcFg("20");
            rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setRegDt(currentDt);
            rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setModDt(currentDt);

            result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdConfirm(rtnDstbCloseProdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdDtlList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdDtlList(rtnDstbCloseProdVO);
    }

    /** 반품마감 상세 리스트 저장 */
    @Override
    public int saveRtnDstbCloseProdDtl(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {
            int slipFg     = rtnDstbCloseProdVO.getSlipFg();
            int mgrUnitQty = (rtnDstbCloseProdVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (rtnDstbCloseProdVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (rtnDstbCloseProdVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (rtnDstbCloseProdVO.getMgrAmt()     == null ? 0 : rtnDstbCloseProdVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (rtnDstbCloseProdVO.getMgrVat()     == null ? 0 : rtnDstbCloseProdVO.getMgrVat()) * slipFg;
            Long mgrTot    = (rtnDstbCloseProdVO.getMgrTot()     == null ? 0 : rtnDstbCloseProdVO.getMgrTot()) * slipFg;

            rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
            rtnDstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
            rtnDstbCloseProdVO.setMgrTotQty(mgrTotQty);
            rtnDstbCloseProdVO.setMgrAmt(mgrAmt);
            rtnDstbCloseProdVO.setMgrVat(mgrVat);
            rtnDstbCloseProdVO.setMgrTot(mgrTot);
            rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setRegDt(currentDt);
            rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setModDt(currentDt);

            // 분배수량이 0 이나 null 인 경우 삭제
//            if(rtnDstbCloseProdVO.getMgrTotQty() == 0 || rtnDstbCloseProdVO.getMgrTotQty() == null) {
//                result = rtnDstbCloseProdMapper.deleteRtnDstbCloseProdDtl(rtnDstbCloseProdVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
                result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdDtl(rtnDstbCloseProdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(rtnDstbCloseProdVO.getConfirmYn()) {
                    rtnDstbCloseProdVO.setProcFg("20");
                    result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdDtlConfirm(rtnDstbCloseProdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
//            }
            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 추가등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdAddProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdAddProdList(rtnDstbCloseProdVO);
    }

    /** 반품마감 추가등록 분배등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdAddRegistList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        if(!StringUtil.getOrBlank(rtnDstbCloseProdVO.getStoreCd()).equals("")) {
            rtnDstbCloseProdVO.setArrStoreCd(rtnDstbCloseProdVO.getStoreCd().split(","));
        }
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdAddRegistList(rtnDstbCloseProdVO);
    }

    /** 반품마감 - 추가등록 분배등록 리스트 저장 */
    @Override
    public int saveRtnDstbCloseProdAddRegist(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {

            int slipFg       = rtnDstbCloseProdVO.getSlipFg();
            int mgrUnitQty   = (rtnDstbCloseProdVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty    = (rtnDstbCloseProdVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrEtcQty())  * slipFg;
            int mgrTotQty    = (rtnDstbCloseProdVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrTotQty())  * slipFg;
            Long mgrAmt      = (rtnDstbCloseProdVO.getMgrAmt()     == null ? 0 : rtnDstbCloseProdVO.getMgrAmt())     * slipFg;
            Long mgrVat      = (rtnDstbCloseProdVO.getMgrVat()     == null ? 0 : rtnDstbCloseProdVO.getMgrVat())     * slipFg;
            Long mgrTot      = (rtnDstbCloseProdVO.getMgrTot()     == null ? 0 : rtnDstbCloseProdVO.getMgrTot())     * slipFg;

            if(mgrTotQty > 0) {
                rtnDstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
                rtnDstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
                rtnDstbCloseProdVO.setMgrTotQty(mgrTotQty);
                rtnDstbCloseProdVO.setMgrAmt(mgrAmt);
                rtnDstbCloseProdVO.setMgrVat(mgrVat);
                rtnDstbCloseProdVO.setMgrTot(mgrTot);
                rtnDstbCloseProdVO.setProcFg("10");
                rtnDstbCloseProdVO.setDstbFg("0");
                rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
                rtnDstbCloseProdVO.setRegDt(currentDt);
                rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
                rtnDstbCloseProdVO.setModDt(currentDt);
                rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                result = rtnDstbCloseProdMapper.insertRtnDstbCloseProdAddRegist(rtnDstbCloseProdVO);
                if (result <= 0)
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if (rtnDstbCloseProdVO.getConfirmYn()) {
                    rtnDstbCloseProdVO.setProcFg("20");
                    result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdDtlConfirm(rtnDstbCloseProdVO);
                    if (result <= 0)
                        throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                returnResult += result;
            }
        }

        return returnResult;
    }
}
