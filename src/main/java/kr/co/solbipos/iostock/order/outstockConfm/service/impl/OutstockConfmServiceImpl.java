package kr.co.solbipos.iostock.order.outstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmService;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("OutstockConfmService")
public class OutstockConfmServiceImpl implements OutstockConfmService {
    private final OutstockConfmMapper outstockConfmMapper;
    private final MessageService messageService;

    public OutstockConfmServiceImpl(OutstockConfmMapper outstockConfmMapper, MessageService messageService) {
        this.outstockConfmMapper = outstockConfmMapper;
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
        OutstockConfmVO env176VO = new OutstockConfmVO();
        env176VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String env176 = outstockConfmMapper.getEnv176(env176VO);

        for (OutstockConfmVO outstockConfmVO : outstockConfmVOs) {
            outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockConfmVO.setRegId(sessionInfoVO.getUserId());
            outstockConfmVO.setRegDt(currentDt);
            outstockConfmVO.setModId(sessionInfoVO.getUserId());
            outstockConfmVO.setModDt(currentDt);

            outstockConfmVO.setProcFg("10");
            outstockConfmVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정
            result = outstockConfmMapper.updateOutstockDtlConfirm(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정
            result = outstockConfmMapper.updateOutstockConfirm(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(env176).equals("Y")) {
                outstockConfmVO.setProcFg("20");
                outstockConfmVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정
                result = outstockConfmMapper.updateAutoInstockDtl(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정
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
}
