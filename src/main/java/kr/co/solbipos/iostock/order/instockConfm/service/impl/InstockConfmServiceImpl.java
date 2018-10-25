package kr.co.solbipos.iostock.order.instockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmService;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("InstockConfmService")
public class InstockConfmServiceImpl implements InstockConfmService {
    private final InstockConfmMapper instockConfmMapper;
    private final MessageService messageService;

    public InstockConfmServiceImpl(InstockConfmMapper instockConfmMapper, MessageService messageService) {
        this.instockConfmMapper = instockConfmMapper;
        this.messageService = messageService;
    }

    /** 입고확정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInstockConfmList(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getInstockConfmList(instockConfmVO);
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getSlipNoInfo(instockConfmVO);
    }

    /** 입고확정 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInstockConfmDtlList(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getInstockConfmDtlList(instockConfmVO);
    }

    /** 입고확정 - 입고확정 상세 리스트 저장 */
    @Override
    public int saveInstockConfmDtl(InstockConfmVO[] instockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";
        int instockErrCnt = 0;

        InstockConfmVO InstockConfmHdVO = new InstockConfmVO();

        for (InstockConfmVO instockConfmVO : instockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(instockConfmVO.getConfirmFg());

                InstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                InstockConfmHdVO.setSlipNo(instockConfmVO.getSlipNo());
                InstockConfmHdVO.setHdRemark(instockConfmVO.getHdRemark());
                InstockConfmHdVO.setInDate(instockConfmVO.getInDate());
                InstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                InstockConfmHdVO.setRegDt(currentDt);
                InstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                InstockConfmHdVO.setModDt(currentDt);
            }

            int slipFg    = instockConfmVO.getSlipFg();
            int inUnitQty = (instockConfmVO.getInUnitQty() == null ? 0 : instockConfmVO.getInUnitQty()) * slipFg;
            int inEtcQty  = (instockConfmVO.getInEtcQty()  == null ? 0 : instockConfmVO.getInEtcQty()) * slipFg;
            int inTotQty  = (instockConfmVO.getInTotQty()  == null ? 0 : instockConfmVO.getInTotQty()) * slipFg;
            int outTotQty = (instockConfmVO.getOutTotQty() == null ? 0 : instockConfmVO.getOutTotQty()) * slipFg;
            Long inAmt    = (instockConfmVO.getInAmt()     == null ? 0 : instockConfmVO.getInAmt()) * slipFg;
            Long inVat    = (instockConfmVO.getInVat()     == null ? 0 : instockConfmVO.getInVat()) * slipFg;
            Long inTot    = (instockConfmVO.getInTot()     == null ? 0 : instockConfmVO.getInTot()) * slipFg;

            // 출고수량과 입고수량이 다르면 카운트
            if(inTotQty != outTotQty) instockErrCnt++;

            instockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            instockConfmVO.setInUnitQty(inUnitQty);
            instockConfmVO.setInEtcQty(inEtcQty);
            instockConfmVO.setInTotQty(inTotQty);
            instockConfmVO.setInAmt(inAmt);
            instockConfmVO.setInVat(inVat);
            instockConfmVO.setInTot(inTot);
            instockConfmVO.setRegId(sessionInfoVO.getUserId());
            instockConfmVO.setRegDt(currentDt);
            instockConfmVO.setModId(sessionInfoVO.getUserId());
            instockConfmVO.setModDt(currentDt);

            // DTL 수정
            result = instockConfmMapper.updateInstockConfmDtl(instockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = instockConfmMapper.updateInstockConfmHd(InstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 입고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            InstockConfmHdVO.setProcFg("20");
            InstockConfmHdVO.setUpdateProcFg("30");

            // DTL의 진행구분 수정. 출고확정 -> 입고확정
            result = instockConfmMapper.updateInstockDtlConfirm(InstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 출고확정 -> 입고확정
            result = instockConfmMapper.updateInstockConfirm(InstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 출고수량과 입고수량이 다른 상품이 있는 경우 물량오류로 등록
            if(instockErrCnt > 0) {
                // 물량오류 DTL 등록
                result = instockConfmMapper.insertInstockErrDtl(InstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 물량오류 HD 등록
                result = instockConfmMapper.insertInstockErrHd(InstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

        }

        return returnResult;
    }
}
