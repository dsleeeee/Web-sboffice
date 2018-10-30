package kr.co.solbipos.iostock.move.storeMove.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.storeMove.service.StoreMoveService;
import kr.co.solbipos.iostock.move.storeMove.service.StoreMoveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("StoreMoveService")
public class StoreMoveServiceImpl implements StoreMoveService {
    private final StoreMoveMapper storeMoveMapper;
    private final MessageService messageService;

    @Autowired
    public StoreMoveServiceImpl(StoreMoveMapper storeMoveMapper, MessageService messageService) {
        this.storeMoveMapper = storeMoveMapper;
        this.messageService = messageService;
    }

    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreMoveList(StoreMoveVO storeMoveVO) {
        return storeMoveMapper.getStoreMoveList(storeMoveVO);
    }

    /** 매장이동관리 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(StoreMoveVO storeMoveVO) {
        return storeMoveMapper.getSlipNoInfo(storeMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreMoveDtlList(StoreMoveVO storeMoveVO) {
        return storeMoveMapper.getStoreMoveDtlList(storeMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    @Override
    public int saveStoreMoveDtl(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        StoreMoveVO storeMoveHdVO = new StoreMoveVO();

        for (StoreMoveVO storeMoveVO : storeMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(storeMoveVO.getConfirmFg());

                storeMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                storeMoveHdVO.setSlipNo(storeMoveVO.getSlipNo());
                storeMoveHdVO.setDlvrFg(storeMoveVO.getDlvrFg());
                storeMoveHdVO.setRemark(storeMoveVO.getRemark());
                storeMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                storeMoveHdVO.setRegId(sessionInfoVO.getUserId());
                storeMoveHdVO.setRegDt(currentDt);
                storeMoveHdVO.setModId(sessionInfoVO.getUserId());
                storeMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (storeMoveVO.getOutEtcQty() == null ? 0 : storeMoveVO.getOutEtcQty());

            storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMoveVO.setOutEtcQty(outEtcQty);
            storeMoveVO.setInUnitQty(storeMoveVO.getOutUnitQty());
            storeMoveVO.setInEtcQty(outEtcQty);
            storeMoveVO.setInTotQty(storeMoveVO.getOutTotQty());
            storeMoveVO.setRegId(sessionInfoVO.getUserId());
            storeMoveVO.setRegDt(currentDt);
            storeMoveVO.setModId(sessionInfoVO.getUserId());
            storeMoveVO.setModDt(currentDt);

            // DTL 수정
            result = storeMoveMapper.updateStoreMoveDtl(storeMoveVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = storeMoveMapper.updateStoreMoveHd(storeMoveHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 확정인 경우
        if(confirmFg.equals("Y")) {
            storeMoveHdVO.setProcFg("3");

            // HD의 수정
            result = storeMoveMapper.updateStoreMoveConfirm(storeMoveHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    @Override
    public int deleteStoreMoveDtl(StoreMoveVO storeMoveVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // DTL 삭제
        result = storeMoveMapper.deleteAllStoreMoveDtl(storeMoveVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // HD 삭제
        result = storeMoveMapper.deleteStoreMoveHd(storeMoveVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreMoveRegistList(StoreMoveVO storeMoveVO) {
        return storeMoveMapper.getStoreMoveRegistList(storeMoveVO);
    }


    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    @Override
    public int saveStoreMoveRegist(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 전표번호 생성
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        StoreMoveVO newSlipNoVO = new StoreMoveVO();
        newSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        newSlipNoVO.setYymm(yymm);
        String newSlipNo = storeMoveMapper.getNewSlipNo(newSlipNoVO);

        StoreMoveVO storeMoveHdVO = new StoreMoveVO();

        for (StoreMoveVO storeMoveVO : storeMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(storeMoveVO.getConfirmFg());

                storeMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                storeMoveHdVO.setSlipNo(newSlipNo);
                storeMoveHdVO.setMoveDate(storeMoveVO.getMoveDate());
                storeMoveHdVO.setOutStoreCd(storeMoveVO.getOutStoreCd());
                storeMoveHdVO.setInStoreCd(storeMoveVO.getInStoreCd());
                storeMoveHdVO.setProcFg("0");
                storeMoveHdVO.setDlvrFg(storeMoveVO.getDlvrFg());
                storeMoveHdVO.setRemark(storeMoveVO.getRemark());
                storeMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                storeMoveHdVO.setRegId(sessionInfoVO.getUserId());
                storeMoveHdVO.setRegDt(currentDt);
                storeMoveHdVO.setModId(sessionInfoVO.getUserId());
                storeMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (storeMoveVO.getOutEtcQty() == null ? 0 : storeMoveVO.getOutEtcQty());

            storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMoveVO.setSlipNo(newSlipNo);
            storeMoveVO.setOutEtcQty(outEtcQty);
            storeMoveVO.setInUnitQty(storeMoveVO.getOutUnitQty());
            storeMoveVO.setInEtcQty(outEtcQty);
            storeMoveVO.setInTotQty(storeMoveVO.getOutTotQty());
            storeMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            storeMoveVO.setRegId(sessionInfoVO.getUserId());
            storeMoveVO.setRegDt(currentDt);
            storeMoveVO.setModId(sessionInfoVO.getUserId());
            storeMoveVO.setModDt(currentDt);

            // DTL 등록
            result = storeMoveMapper.insertStoreMoveDtl(storeMoveVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 등록
        result = storeMoveMapper.insertStoreMoveHd(storeMoveHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 본사확정인 경우
        if(confirmFg.equals("Y")) {
            // 출고, 반품에 사용할 전표번호 생성
            StoreMoveVO iostockNewSlipNoVO = new StoreMoveVO();
            iostockNewSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            iostockNewSlipNoVO.setYymm(yymm);
            String iostockNewSlipNo = storeMoveMapper.getIostockNewSlipNo(iostockNewSlipNoVO);

            String outSlipNo = iostockNewSlipNo;
            String inSlipNo = String.valueOf(Long.parseLong(iostockNewSlipNo) + 1);

            storeMoveHdVO.setProcFg("3");
            storeMoveHdVO.setOutSlipNo(outSlipNo);
            storeMoveHdVO.setInSlipNo(inSlipNo);

            // HD의 수정
            result = storeMoveMapper.updateStoreMoveConfirm(storeMoveHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreMoveAddProdList(StoreMoveVO storeMoveVO) {
        return storeMoveMapper.getStoreMoveRegistList(storeMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    @Override
    public int saveStoreMoveAddProd(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StoreMoveVO storeMoveHdVO = new StoreMoveVO();

        for (StoreMoveVO storeMoveVO : storeMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {

                storeMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                storeMoveHdVO.setSlipNo(storeMoveVO.getSlipNo());
                storeMoveHdVO.setRegId(sessionInfoVO.getUserId());
                storeMoveHdVO.setRegDt(currentDt);
                storeMoveHdVO.setModId(sessionInfoVO.getUserId());
                storeMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (storeMoveVO.getOutEtcQty() == null ? 0 : storeMoveVO.getOutEtcQty());

            storeMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMoveVO.setOutEtcQty(outEtcQty);
            storeMoveVO.setInUnitQty(storeMoveVO.getOutUnitQty());
            storeMoveVO.setInEtcQty(outEtcQty);
            storeMoveVO.setInTotQty(storeMoveVO.getOutTotQty());
            storeMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            storeMoveVO.setRegId(sessionInfoVO.getUserId());
            storeMoveVO.setRegDt(currentDt);
            storeMoveVO.setModId(sessionInfoVO.getUserId());
            storeMoveVO.setModDt(currentDt);

            // DTL 등록
            result = storeMoveMapper.insertStoreMoveDtl(storeMoveVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = storeMoveMapper.updateStoreMoveAddProdHd(storeMoveHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
