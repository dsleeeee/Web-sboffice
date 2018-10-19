package kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmVO;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.RtnInstockConfmService;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.RtnInstockConfmVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("RtnInstockConfmService")
public class RtnInstockConfmServiceImpl implements RtnInstockConfmService {
    private final RtnInstockConfmMapper rtnInstockConfmMapper;
    private final MessageService messageService;

    public RtnInstockConfmServiceImpl(RtnInstockConfmMapper rtnInstockConfmMapper, MessageService messageService) {
        this.rtnInstockConfmMapper = rtnInstockConfmMapper;
        this.messageService = messageService;
    }

    /** 입고확정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmList(RtnInstockConfmVO rtnInstockConfmVO) {
        return rtnInstockConfmMapper.getRtnInstockConfmList(rtnInstockConfmVO);
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(RtnInstockConfmVO rtnInstockConfmVO) {
        return rtnInstockConfmMapper.getSlipNoInfo(rtnInstockConfmVO);
    }

    /** 입고확정 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmDtlList(RtnInstockConfmVO rtnInstockConfmVO) {
        return rtnInstockConfmMapper.getRtnInstockConfmDtlList(rtnInstockConfmVO);
    }

    /** 출고확정 - 출고확정 상세 리스트 저장 */
    @Override
    public int saveRtnInstockConfmDtl(RtnInstockConfmVO[] rtnInstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        RtnInstockConfmVO rtnInstockConfmHdVO = new RtnInstockConfmVO();

        for (RtnInstockConfmVO rtnInstockConfmVO : rtnInstockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(rtnInstockConfmVO.getConfirmFg());

                rtnInstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                rtnInstockConfmHdVO.setSlipNo(rtnInstockConfmVO.getSlipNo());
                rtnInstockConfmHdVO.setHdRemark(rtnInstockConfmVO.getHdRemark());
                rtnInstockConfmHdVO.setInDate(rtnInstockConfmVO.getInDate());
                rtnInstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                rtnInstockConfmHdVO.setRegDt(currentDt);
                rtnInstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                rtnInstockConfmHdVO.setModDt(currentDt);
            }

            int slipFg    = rtnInstockConfmVO.getSlipFg();
            int inUnitQty = (rtnInstockConfmVO.getInUnitQty() == null ? 0 : rtnInstockConfmVO.getInUnitQty()) * slipFg;
            int inEtcQty  = (rtnInstockConfmVO.getInEtcQty()  == null ? 0 : rtnInstockConfmVO.getInEtcQty()) * slipFg;
            int inTotQty  = (rtnInstockConfmVO.getInTotQty()  == null ? 0 : rtnInstockConfmVO.getInTotQty()) * slipFg;
            Long inAmt    = (rtnInstockConfmVO.getInAmt()     == null ? 0 : rtnInstockConfmVO.getInAmt()) * slipFg;
            Long inVat    = (rtnInstockConfmVO.getInVat()     == null ? 0 : rtnInstockConfmVO.getInVat()) * slipFg;
            Long inTot    = (rtnInstockConfmVO.getInTot()     == null ? 0 : rtnInstockConfmVO.getInTot()) * slipFg;

            rtnInstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnInstockConfmVO.setInUnitQty(inUnitQty);
            rtnInstockConfmVO.setInEtcQty(inEtcQty);
            rtnInstockConfmVO.setInTotQty(inTotQty);
            rtnInstockConfmVO.setInAmt(inAmt);
            rtnInstockConfmVO.setInVat(inVat);
            rtnInstockConfmVO.setInTot(inTot);
            rtnInstockConfmVO.setRegId(sessionInfoVO.getUserId());
            rtnInstockConfmVO.setRegDt(currentDt);
            rtnInstockConfmVO.setModId(sessionInfoVO.getUserId());
            rtnInstockConfmVO.setModDt(currentDt);

            // DTL 수정
            result = rtnInstockConfmMapper.updateRtnInstockConfmDtl(rtnInstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 수정
            result = rtnInstockConfmMapper.updateRtnInstockConfmHd(rtnInstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        // 입고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            rtnInstockConfmHdVO.setProcFg("20");
            rtnInstockConfmHdVO.setUpdateProcFg("30");

            // DTL의 진행구분 수정. 출고확정 -> 입고확정
            result = rtnInstockConfmMapper.updateInstockDtlConfirm(rtnInstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 출고확정 -> 입고확정
            result = rtnInstockConfmMapper.updateInstockConfirm(rtnInstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }
}
