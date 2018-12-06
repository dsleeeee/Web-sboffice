package kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.RtnDstmnService;
import kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.RtnDstmnVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnDstmnService")
public class RtnDstmnServiceImpl implements RtnDstmnService {
    private final RtnDstmnMapper rtnDstmnMapper;
    private final MessageService messageService;

    public RtnDstmnServiceImpl(RtnDstmnMapper rtnDstmnMapper, MessageService messageService) {
        this.rtnDstmnMapper = rtnDstmnMapper;
        this.messageService = messageService;
    }

    /** 반품명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    @Override
    public DefaultMap<String> getReqNoConfirmCnt(RtnDstmnVO rtnDstmnVO) {
        return rtnDstmnMapper.getReqNoConfirmCnt(rtnDstmnVO);
    }

    /** 반품명세표 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstmnList(RtnDstmnVO rtnDstmnVO) {
        return rtnDstmnMapper.getRtnDstmnList(rtnDstmnVO);
    }

    /** 반품명세표 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(RtnDstmnVO rtnDstmnVO) {
        return rtnDstmnMapper.getSlipNoInfo(rtnDstmnVO);
    }

    /** 반품명세표 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstmnDtlList(RtnDstmnVO rtnDstmnVO) {
        return rtnDstmnMapper.getRtnDstmnDtlList(rtnDstmnVO);
    }

    /** 반품명세표 - 반품명세표 상세 리스트 저장 */
    @Override
    public int saveRtnDstmnDtl(RtnDstmnVO[] rtnDstmnVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 매장입고 환경변수 조회
        RtnDstmnVO env176VO = new RtnDstmnVO();
        env176VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String env176 = rtnDstmnMapper.getEnv176(env176VO);

        RtnDstmnVO rtnDstmnHdVO = new RtnDstmnVO();

        for (RtnDstmnVO rtnDstmnVO : rtnDstmnVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(rtnDstmnVO.getConfirmFg());

                rtnDstmnHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                rtnDstmnHdVO.setSlipNo(rtnDstmnVO.getSlipNo());
                rtnDstmnHdVO.setHdRemark(rtnDstmnVO.getHdRemark());
                rtnDstmnHdVO.setHqRemark(rtnDstmnVO.getHqRemark());
                rtnDstmnHdVO.setDlvrCd(rtnDstmnVO.getDlvrCd());
                rtnDstmnHdVO.setOutDate(rtnDstmnVO.getOutDate());
                rtnDstmnHdVO.setRegId(sessionInfoVO.getUserId());
                rtnDstmnHdVO.setRegDt(currentDt);
                rtnDstmnHdVO.setModId(sessionInfoVO.getUserId());
                rtnDstmnHdVO.setModDt(currentDt);
            }

            int slipFg     = rtnDstmnVO.getSlipFg();
            int outUnitQty = (rtnDstmnVO.getOutUnitQty() == null ? 0 : rtnDstmnVO.getOutUnitQty()) * slipFg;
            int outEtcQty  = (rtnDstmnVO.getOutEtcQty()  == null ? 0 : rtnDstmnVO.getOutEtcQty()) * slipFg;
            int outTotQty  = (rtnDstmnVO.getOutTotQty()  == null ? 0 : rtnDstmnVO.getOutTotQty()) * slipFg;
            Long outAmt    = (rtnDstmnVO.getOutAmt() == null ? 0 : rtnDstmnVO.getOutAmt()) * slipFg;
            Long outVat    = (rtnDstmnVO.getOutVat() == null ? 0 : rtnDstmnVO.getOutVat()) * slipFg;
            Long outTot    = (rtnDstmnVO.getOutTot() == null ? 0 : rtnDstmnVO.getOutTot()) * slipFg;

            rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstmnVO.setOutUnitQty(outUnitQty);
            rtnDstmnVO.setOutEtcQty(outEtcQty);
            rtnDstmnVO.setOutTotQty(outTotQty);
            rtnDstmnVO.setOutAmt(outAmt);
            rtnDstmnVO.setOutVat(outVat);
            rtnDstmnVO.setOutTot(outTot);
            rtnDstmnVO.setRegId(sessionInfoVO.getUserId());
            rtnDstmnVO.setRegDt(currentDt);
            rtnDstmnVO.setModId(sessionInfoVO.getUserId());
            rtnDstmnVO.setModDt(currentDt);

            // DTL 수정
            result = rtnDstmnMapper.updateOutstockDtl(rtnDstmnVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = rtnDstmnMapper.updateOutstockHd(rtnDstmnHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            rtnDstmnHdVO.setProcFg("10");
            rtnDstmnHdVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnDstmnMapper.updateOutstockDtlConfirm(rtnDstmnHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnDstmnMapper.updateOutstockConfirm(rtnDstmnHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(env176).equals("A")) {
                rtnDstmnHdVO.setProcFg("20");
                rtnDstmnHdVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnDstmnMapper.updateAutoInstockDtl(rtnDstmnHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnDstmnMapper.updateAutoInstock(rtnDstmnHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return returnResult;
    }

    /** 반품명세표 - 반품매장출고 이후 저장 */
    @Override
    public int saveOutstockAfter(RtnDstmnVO rtnDstmnVO, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        rtnDstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        rtnDstmnVO.setHdRemark(rtnDstmnVO.getHdRemark());
        rtnDstmnVO.setHqRemark(rtnDstmnVO.getHqRemark());
        rtnDstmnVO.setDlvrCd(rtnDstmnVO.getDlvrCd());
        rtnDstmnVO.setRegId(sessionInfoVO.getUserId());
        rtnDstmnVO.setRegDt(currentDt);
        rtnDstmnVO.setModId(sessionInfoVO.getUserId());
        rtnDstmnVO.setModDt(currentDt);

        // HD 수정
        result = rtnDstmnMapper.updateOutstockAfterHd(rtnDstmnVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        returnResult += result;

        return returnResult;
    }
}
