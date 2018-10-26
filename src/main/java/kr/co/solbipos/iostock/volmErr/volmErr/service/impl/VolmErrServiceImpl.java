package kr.co.solbipos.iostock.volmErr.volmErr.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.impl.OutstockConfmMapper;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("VolmErrService")
public class VolmErrServiceImpl implements VolmErrService {
    private final VolmErrMapper volmErrMapper;
    private final MessageService messageService;
    private final OutstockConfmMapper outstockConfmMapper;

    @Autowired
    public VolmErrServiceImpl(VolmErrMapper volmErrMapper, MessageService messageService, OutstockConfmMapper outstockConfmMapper) {
        this.volmErrMapper = volmErrMapper;
        this.messageService = messageService;
        this.outstockConfmMapper = outstockConfmMapper;
    }

    /** 물량오류관리 - 물량오류관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVolmErrList(VolmErrVO volmErrVO) {
        if(!StringUtil.getOrBlank(volmErrVO.getStoreCd()).equals("")) {
            volmErrVO.setArrStoreCd(volmErrVO.getStoreCd().split(","));
        }
        return volmErrMapper.getVolmErrList(volmErrVO);
    }

    /** 물량오류관리 - 물량오류관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVolmErrDtlList(VolmErrVO volmErrVO) {
        return volmErrMapper.getVolmErrDtlList(volmErrVO);
    }

    /** 물량오류관리 - 물량오류 상세 저장 */
    @Override
    public int saveVolmErrDtl(VolmErrVO[] volmErrVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";
        String newSlipNoFg      = "N";
        String hqNewAdjustFg    = "N";
        String storeNewAdjustFg = "N";

        VolmErrVO volmErrHdVO = new VolmErrVO();

        int i = 0;
        for (VolmErrVO volmErrVO : volmErrVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(volmErrVO.getConfirmFg());

                volmErrHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                volmErrHdVO.setSlipNo(volmErrVO.getSlipNo());
                volmErrHdVO.setSlipFg(volmErrVO.getSlipFg());
                volmErrHdVO.setStoreCd(volmErrVO.getStoreCd());
                volmErrHdVO.setProcFg(volmErrVO.getProcFg());
                volmErrHdVO.setOutDate(volmErrVO.getOutDate());
                volmErrHdVO.setRegId(sessionInfoVO.getUserId());
                volmErrHdVO.setRegDt(currentDt);
                volmErrHdVO.setModId(sessionInfoVO.getUserId());
                volmErrHdVO.setModDt(currentDt);
            }
            volmErrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            volmErrVO.setRegId(sessionInfoVO.getUserId());
            volmErrVO.setRegDt(currentDt);
            volmErrVO.setModId(sessionInfoVO.getUserId());
            volmErrVO.setModDt(currentDt);

            if(newSlipNoFg.equals("N") && volmErrVO.getNewSlipNoFg().equals("Y")) newSlipNoFg = "Y";
            if(hqNewAdjustFg.equals("N") && volmErrVO.getHqNewAdjustFg().equals("Y")) hqNewAdjustFg = "Y";
            if(storeNewAdjustFg.equals("N") && volmErrVO.getStoreNewAdjustFg().equals("Y")) storeNewAdjustFg = "Y";

            // DTL 수정
            result = volmErrMapper.updateVolmErrDtl(volmErrVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = volmErrMapper.updateVolmErrHd(volmErrHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            String newSlipNo = "";
            if(newSlipNoFg.equals("Y")) {
                // 전표번호 생성
                String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
                VolmErrVO newSlipNoVO = new VolmErrVO();
                newSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                newSlipNoVO.setYymm(yymm);
                newSlipNo = volmErrMapper.getNewSlipNo(newSlipNoVO);
                volmErrHdVO.setNewSlipNo(newSlipNo);
                volmErrHdVO.setUpdateProcFg("1");

                result = volmErrMapper.updateVolmErrNewSlipNo(volmErrHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 출고수량을 입고수량으로 수정
            result = volmErrMapper.updateOutToIn(volmErrHdVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 입고수량을 출고수량으로 수정
            result = volmErrMapper.updateInToOut(volmErrHdVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 출고정보 HD 집계 수정
            result = volmErrMapper.updateVolmErrHdSum(volmErrHdVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            if(!newSlipNo.equals("")) {
                // 출고정보 DTL 등록
                result = volmErrMapper.insertVolmErrOutstockDtl(volmErrHdVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 출고정보 HD 등록
                result = volmErrMapper.insertVolmErrOutstockHd(volmErrHdVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 주문구분이 반품인 경우 출고확정까지 처리
            if(volmErrHdVO.getSlipFg() == -1) {
                OutstockConfmVO outstockConfmVO = new OutstockConfmVO();
                outstockConfmVO.setHqOfficeCd(volmErrHdVO.getHqOfficeCd());
                outstockConfmVO.setSlipNo(newSlipNo);
                outstockConfmVO.setProcFg("10");
                outstockConfmVO.setUpdateProcFg("20");
                outstockConfmVO.setOutDate(volmErrHdVO.getOutDate());
                outstockConfmVO.setRegId(sessionInfoVO.getUserId());
                outstockConfmVO.setRegDt(currentDt);
                outstockConfmVO.setModId(sessionInfoVO.getUserId());
                outstockConfmVO.setModDt(currentDt);

                // DTL 의 진행구분 출고확정으로 수정
                outstockConfmMapper.updateOutstockDtlConfirm(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 의 진행구분 출고확정으로 수정
                outstockConfmMapper.updateOutstockConfirm(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 본사 신규조정 생성
            if(hqNewAdjustFg.equals("Y")) {
                // 본사 조정 DTL 등록
                result = volmErrMapper.insertVolmErrHqAdjustDtl(volmErrHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 본사 조정 HD 등록
                result = volmErrMapper.insertVolmErrHqAdjustHd(volmErrHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 매장 신규조정 생성
            if(storeNewAdjustFg.equals("Y")) {
                // 매장 조정 DTL 등록
                result = volmErrMapper.insertVolmErrStoreAdjustDtl(volmErrHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 매장 조정 HD 등록
                result = volmErrMapper.insertVolmErrStoreAdjustHd(volmErrHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

        }

        return returnResult;
    }






    /** 콤보조회 */
    @Override
    public List<DefaultMap<String>> selectCmmCodeList(VolmErrVO volmErrVO) {
        return volmErrMapper.selectCmmCodeList(volmErrVO);
    }

}
