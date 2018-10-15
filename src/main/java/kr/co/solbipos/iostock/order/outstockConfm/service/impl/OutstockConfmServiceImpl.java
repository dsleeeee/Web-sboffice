package kr.co.solbipos.iostock.order.outstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.impl.CmmEnvMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmService;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("OutstockConfmService")
public class OutstockConfmServiceImpl implements OutstockConfmService {
    private final OutstockConfmMapper outstockConfmMapper;
    private final CmmEnvMapper cmmEnvMapper;
    private final MessageService messageService;

    public OutstockConfmServiceImpl(OutstockConfmMapper outstockConfmMapper, CmmEnvMapper cmmEnvMapper, MessageService messageService) {
        this.outstockConfmMapper = outstockConfmMapper;
        this.cmmEnvMapper = cmmEnvMapper;
        this.messageService = messageService;
    }

    /** 출고확정 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    @Override
    public DefaultMap<String> getReqNoConfirmCnt(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getReqNoConfirmCnt(outstockConfmVO);
    }

    /** 출고확정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockConfmList(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getOutstockConfmList(outstockConfmVO);
    }

    /** 출고확정 - 출고확정 */
    @Override
    public int saveOutstockConfirm(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        // 자동입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("176");
        String envst176 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        for (OutstockConfmVO outstockConfmVO : outstockConfmVOs) {
            outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockConfmVO.setRegId(sessionInfoVO.getUserId());
            outstockConfmVO.setRegDt(currentDt);
            outstockConfmVO.setModId(sessionInfoVO.getUserId());
            outstockConfmVO.setModDt(currentDt);

            outstockConfmVO.setProcFg("10");
            outstockConfmVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockDtlConfirm(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockConfirm(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(envst176).equals("Y")) {
                outstockConfmVO.setProcFg("20");
                outstockConfmVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = outstockConfmMapper.updateAutoInstockDtl(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = outstockConfmMapper.updateAutoInstock(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getSlipNoInfo(outstockConfmVO);
    }

    /** 출고확정 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockConfmDtlList(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getOutstockConfmDtlList(outstockConfmVO);
    }

    /** 출고확정 - 출고확정 상세 리스트 저장 */
    @Override
    public int saveOutstockConfmDtl(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 자동입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("176");
        String envst176 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        OutstockConfmVO OutstockConfmHdVO = new OutstockConfmVO();

        for (OutstockConfmVO outstockConfmVO : outstockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(outstockConfmVO.getConfirmFg());

                OutstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                OutstockConfmHdVO.setSlipNo(outstockConfmVO.getSlipNo());
                OutstockConfmHdVO.setHdRemark(outstockConfmVO.getHdRemark());
                OutstockConfmHdVO.setHqRemark(outstockConfmVO.getHqRemark());
                OutstockConfmHdVO.setDlvrCd(outstockConfmVO.getDlvrCd());
                OutstockConfmHdVO.setOutDate(outstockConfmVO.getOutDate());
                OutstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                OutstockConfmHdVO.setRegDt(currentDt);
                OutstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                OutstockConfmHdVO.setModDt(currentDt);
            }

            int slipFg     = outstockConfmVO.getSlipFg();
            int outUnitQty = (outstockConfmVO.getOutUnitQty() == null ? 0 : outstockConfmVO.getOutUnitQty()) * slipFg;
            int outEtcQty  = (outstockConfmVO.getOutEtcQty()  == null ? 0 : outstockConfmVO.getOutEtcQty()) * slipFg;
            int outTotQty  = (outstockConfmVO.getOutTotQty()  == null ? 0 : outstockConfmVO.getOutTotQty()) * slipFg;
            Long outAmt    = (outstockConfmVO.getOutAmt() == null ? 0 : outstockConfmVO.getOutAmt()) * slipFg;
            Long outVat    = (outstockConfmVO.getOutVat() == null ? 0 : outstockConfmVO.getOutVat()) * slipFg;
            Long outTot    = (outstockConfmVO.getOutTot() == null ? 0 : outstockConfmVO.getOutTot()) * slipFg;

            outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockConfmVO.setOutUnitQty(outUnitQty);
            outstockConfmVO.setOutEtcQty(outEtcQty);
            outstockConfmVO.setOutTotQty(outTotQty);
            outstockConfmVO.setOutAmt(outAmt);
            outstockConfmVO.setOutVat(outVat);
            outstockConfmVO.setOutTot(outTot);
            outstockConfmVO.setRegId(sessionInfoVO.getUserId());
            outstockConfmVO.setRegDt(currentDt);
            outstockConfmVO.setModId(sessionInfoVO.getUserId());
            outstockConfmVO.setModDt(currentDt);

            // DTL 수정
            result = outstockConfmMapper.updateOutstockConfmDtl(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 수정
            result = outstockConfmMapper.updateOutstockConfmHd(OutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            OutstockConfmHdVO.setProcFg("10");
            OutstockConfmHdVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockDtlConfirm(OutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockConfirm(OutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(envst176).equals("Y")) {
                OutstockConfmHdVO.setProcFg("20");
                OutstockConfmHdVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = outstockConfmMapper.updateAutoInstockDtl(OutstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = outstockConfmMapper.updateAutoInstock(OutstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return returnResult;
    }

    /** 출고확정 이후 저장 */
    @Override
    public int saveOutstockAfter(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        outstockConfmVO.setHdRemark(outstockConfmVO.getHdRemark());
        outstockConfmVO.setHqRemark(outstockConfmVO.getHqRemark());
        outstockConfmVO.setDlvrCd(outstockConfmVO.getDlvrCd());
        outstockConfmVO.setRegId(sessionInfoVO.getUserId());
        outstockConfmVO.setRegDt(currentDt);
        outstockConfmVO.setModId(sessionInfoVO.getUserId());
        outstockConfmVO.setModDt(currentDt);

        // HD 수정
        result = outstockConfmMapper.updateOutstockAfterHd(outstockConfmVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        returnResult += result;

        return returnResult;
    }
}
