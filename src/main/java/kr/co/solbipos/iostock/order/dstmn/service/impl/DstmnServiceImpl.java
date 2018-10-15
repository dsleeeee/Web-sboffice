package kr.co.solbipos.iostock.order.dstmn.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnService;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service
public class DstmnServiceImpl implements DstmnService {
    private final DstmnMapper dstmnMapper;
    private final MessageService messageService;

    public DstmnServiceImpl(DstmnMapper dstmnMapper, MessageService messageService) {
        this.dstmnMapper = dstmnMapper;
        this.messageService = messageService;
    }

    /** 거래명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    @Override
    public DefaultMap<String> getReqNoConfirmCnt(DstmnVO dstmnVO) {
        return dstmnMapper.getReqNoConfirmCnt(dstmnVO);
    }

    /** 거래명세표 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstmnList(DstmnVO dstmnVO) {
        return dstmnMapper.getDstmnList(dstmnVO);
    }

    /** 거래명세표 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(DstmnVO dstmnVO) {
        return dstmnMapper.getSlipNoInfo(dstmnVO);
    }

    /** 거래명세표 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstmnDtlList(DstmnVO dstmnVO) {
        return dstmnMapper.getDstmnDtlList(dstmnVO);
    }

    /** 거래명세표 - 거래명세표 상세 리스트 저장 */
    @Override
    public int saveDstmnDtl(DstmnVO[] dstmnVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 자동입고 환경변수 조회
        DstmnVO env176VO = new DstmnVO();
        env176VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String env176 = dstmnMapper.getEnv176(env176VO);

        DstmnVO dstmnHdVO = new DstmnVO();

        for (DstmnVO dstmnVO : dstmnVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(dstmnVO.getConfirmFg());

                dstmnHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                dstmnHdVO.setSlipNo(dstmnVO.getSlipNo());
                dstmnHdVO.setHdRemark(dstmnVO.getHdRemark());
                dstmnHdVO.setHqRemark(dstmnVO.getHqRemark());
                dstmnHdVO.setDlvrCd(dstmnVO.getDlvrCd());
                dstmnHdVO.setOutDate(dstmnVO.getOutDate());
                dstmnHdVO.setRegId(sessionInfoVO.getUserId());
                dstmnHdVO.setRegDt(currentDt);
                dstmnHdVO.setModId(sessionInfoVO.getUserId());
                dstmnHdVO.setModDt(currentDt);
            }

            int slipFg     = dstmnVO.getSlipFg();
            int outUnitQty = (dstmnVO.getOutUnitQty() == null ? 0 : dstmnVO.getOutUnitQty()) * slipFg;
            int outEtcQty  = (dstmnVO.getOutEtcQty()  == null ? 0 : dstmnVO.getOutEtcQty()) * slipFg;
            int outTotQty  = (dstmnVO.getOutTotQty()  == null ? 0 : dstmnVO.getOutTotQty()) * slipFg;
            Long outAmt    = (dstmnVO.getOutAmt() == null ? 0 : dstmnVO.getOutAmt()) * slipFg;
            Long outVat    = (dstmnVO.getOutVat() == null ? 0 : dstmnVO.getOutVat()) * slipFg;
            Long outTot    = (dstmnVO.getOutTot() == null ? 0 : dstmnVO.getOutTot()) * slipFg;

            dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dstmnVO.setOutUnitQty(outUnitQty);
            dstmnVO.setOutEtcQty(outEtcQty);
            dstmnVO.setOutTotQty(outTotQty);
            dstmnVO.setOutAmt(outAmt);
            dstmnVO.setOutVat(outVat);
            dstmnVO.setOutTot(outTot);
            dstmnVO.setRegId(sessionInfoVO.getUserId());
            dstmnVO.setRegDt(currentDt);
            dstmnVO.setModId(sessionInfoVO.getUserId());
            dstmnVO.setModDt(currentDt);

            // DTL 수정
            result = dstmnMapper.updateOutstockDtl(dstmnVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 수정
            result = dstmnMapper.updateOutstockHd(dstmnHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            dstmnHdVO.setProcFg("10");
            dstmnHdVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = dstmnMapper.updateOutstockDtlConfirm(dstmnHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = dstmnMapper.updateOutstockConfirm(dstmnHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(env176).equals("Y")) {
                dstmnHdVO.setProcFg("20");
                dstmnHdVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = dstmnMapper.updateAutoInstockDtl(dstmnHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = dstmnMapper.updateAutoInstock(dstmnHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return returnResult;
    }

    /** 거래명세표 - 출고확정 이후 저장 */
    @Override
    public int saveOutstockAfter(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        dstmnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dstmnVO.setHdRemark(dstmnVO.getHdRemark());
        dstmnVO.setHqRemark(dstmnVO.getHqRemark());
        dstmnVO.setDlvrCd(dstmnVO.getDlvrCd());
        dstmnVO.setRegId(sessionInfoVO.getUserId());
        dstmnVO.setRegDt(currentDt);
        dstmnVO.setModId(sessionInfoVO.getUserId());
        dstmnVO.setModDt(currentDt);

        // HD 수정
        result = dstmnMapper.updateOutstockAfterHd(dstmnVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        returnResult += result;

        return returnResult;
    }
}
