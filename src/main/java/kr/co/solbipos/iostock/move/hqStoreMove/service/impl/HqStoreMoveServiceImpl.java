package kr.co.solbipos.iostock.move.hqStoreMove.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveService;
import kr.co.solbipos.iostock.move.hqStoreMove.service.HqStoreMoveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("hqStoreMoveService")
@Transactional
public class HqStoreMoveServiceImpl implements HqStoreMoveService {
    private final HqStoreMoveMapper hqStoreMoveMapper;
    private final MessageService messageService;

    @Autowired
    public HqStoreMoveServiceImpl(HqStoreMoveMapper hqStoreMoveMapper, MessageService messageService) {
        this.hqStoreMoveMapper = hqStoreMoveMapper;
        this.messageService = messageService;
    }

    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqStoreMoveList(HqStoreMoveVO hqStoreMoveVO) {
        return hqStoreMoveMapper.getHqStoreMoveList(hqStoreMoveVO);
    }

    /** 매장이동관리 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(HqStoreMoveVO hqStoreMoveVO) {
        return hqStoreMoveMapper.getSlipNoInfo(hqStoreMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqStoreMoveDtlList(HqStoreMoveVO hqStoreMoveVO) {
        return hqStoreMoveMapper.getHqStoreMoveDtlList(hqStoreMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    @Override
    public int saveHqStoreMoveDtl(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        HqStoreMoveVO hqStoreMoveHdVO = new HqStoreMoveVO();

        for (HqStoreMoveVO hqStoreMoveVO : hqStoreMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqStoreMoveVO.getConfirmFg());

                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(hqStoreMoveVO.getSlipNo());
                hqStoreMoveHdVO.setDlvrFg(hqStoreMoveVO.getDlvrFg());
                hqStoreMoveHdVO.setRemark(hqStoreMoveVO.getRemark());
                hqStoreMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (hqStoreMoveVO.getOutEtcQty() == null ? 0 : hqStoreMoveVO.getOutEtcQty());

            hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqStoreMoveVO.setOutEtcQty(outEtcQty);
            hqStoreMoveVO.setInUnitQty(hqStoreMoveVO.getOutUnitQty());
            hqStoreMoveVO.setInEtcQty(outEtcQty);
            hqStoreMoveVO.setInTotQty(hqStoreMoveVO.getOutTotQty());
            hqStoreMoveVO.setRegId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setRegDt(currentDt);
            hqStoreMoveVO.setModId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setModDt(currentDt);

            // DTL 수정
            result = hqStoreMoveMapper.updateHqStoreMoveDtl(hqStoreMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = hqStoreMoveMapper.updateHqStoreMoveHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 본사확정인 경우
        if(confirmFg.equals("Y")) {
            // 출고, 반품에 사용할 전표번호 생성
            String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            HqStoreMoveVO iostockNewSlipNoVO = new HqStoreMoveVO();
            iostockNewSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            iostockNewSlipNoVO.setYymm(yymm);
            String iostockNewSlipNo = hqStoreMoveMapper.getIostockNewSlipNo(iostockNewSlipNoVO);

            String outSlipNo = iostockNewSlipNo;
            String inSlipNo = String.valueOf(Long.parseLong(iostockNewSlipNo) + 1);

            hqStoreMoveHdVO.setProcFg("3");
            hqStoreMoveHdVO.setOutSlipNo(outSlipNo);
            hqStoreMoveHdVO.setInSlipNo(inSlipNo);

            // HD의 수정
            result = hqStoreMoveMapper.updateHqStoreMoveConfirm(hqStoreMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    @Override
    public int deleteHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // DTL 삭제
        result = hqStoreMoveMapper.deleteAllHqStoreMoveDtl(hqStoreMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // HD 삭제
        result = hqStoreMoveMapper.deleteHqStoreMoveHd(hqStoreMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqStoreMoveRegistList(HqStoreMoveVO hqStoreMoveVO) {
        return hqStoreMoveMapper.getHqStoreMoveRegistList(hqStoreMoveVO);
    }


    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    @Override
    public int saveHqStoreMoveRegist(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 전표번호 생성
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        HqStoreMoveVO newSlipNoVO = new HqStoreMoveVO();
        newSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        newSlipNoVO.setYymm(yymm);
        String newSlipNo = hqStoreMoveMapper.getNewSlipNo(newSlipNoVO);

        HqStoreMoveVO hqStoreMoveHdVO = new HqStoreMoveVO();

        for (HqStoreMoveVO hqStoreMoveVO : hqStoreMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqStoreMoveVO.getConfirmFg());

                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(newSlipNo);
                hqStoreMoveHdVO.setMoveDate(hqStoreMoveVO.getMoveDate());
                hqStoreMoveHdVO.setOutStoreCd(hqStoreMoveVO.getOutStoreCd());
                hqStoreMoveHdVO.setInStoreCd(hqStoreMoveVO.getInStoreCd());
                hqStoreMoveHdVO.setProcFg("0");
                hqStoreMoveHdVO.setDlvrFg(hqStoreMoveVO.getDlvrFg());
                hqStoreMoveHdVO.setRemark(hqStoreMoveVO.getRemark());
                hqStoreMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (hqStoreMoveVO.getOutEtcQty() == null ? 0 : hqStoreMoveVO.getOutEtcQty());

            hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqStoreMoveVO.setSlipNo(newSlipNo);
            hqStoreMoveVO.setOutEtcQty(outEtcQty);
            hqStoreMoveVO.setInUnitQty(hqStoreMoveVO.getOutUnitQty());
            hqStoreMoveVO.setInEtcQty(outEtcQty);
            hqStoreMoveVO.setInTotQty(hqStoreMoveVO.getOutTotQty());
            hqStoreMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            hqStoreMoveVO.setRegId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setRegDt(currentDt);
            hqStoreMoveVO.setModId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setModDt(currentDt);

            // DTL 등록
            result = hqStoreMoveMapper.insertHqStoreMoveDtl(hqStoreMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 등록
        result = hqStoreMoveMapper.insertHqStoreMoveHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 본사확정인 경우
        if(confirmFg.equals("Y")) {
            // 출고, 반품에 사용할 전표번호 생성
            HqStoreMoveVO iostockNewSlipNoVO = new HqStoreMoveVO();
            iostockNewSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            iostockNewSlipNoVO.setYymm(yymm);
            String iostockNewSlipNo = hqStoreMoveMapper.getIostockNewSlipNo(iostockNewSlipNoVO);

            String outSlipNo = iostockNewSlipNo;
            String inSlipNo = String.valueOf(Long.parseLong(iostockNewSlipNo) + 1);

            hqStoreMoveHdVO.setProcFg("3");
            hqStoreMoveHdVO.setOutSlipNo(outSlipNo);
            hqStoreMoveHdVO.setInSlipNo(inSlipNo);

            // HD의 수정
            result = hqStoreMoveMapper.updateHqStoreMoveConfirm(hqStoreMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqStoreMoveAddProdList(HqStoreMoveVO hqStoreMoveVO) {
        return hqStoreMoveMapper.getHqStoreMoveRegistList(hqStoreMoveVO);
    }

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    @Override
    public int saveHqStoreMoveAddProd(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqStoreMoveVO hqStoreMoveHdVO = new HqStoreMoveVO();

        for (HqStoreMoveVO hqStoreMoveVO : hqStoreMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                hqStoreMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqStoreMoveHdVO.setSlipNo(hqStoreMoveVO.getSlipNo());
                hqStoreMoveHdVO.setRegId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setRegDt(currentDt);
                hqStoreMoveHdVO.setModId(sessionInfoVO.getUserId());
                hqStoreMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (hqStoreMoveVO.getOutEtcQty() == null ? 0 : hqStoreMoveVO.getOutEtcQty());

            hqStoreMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqStoreMoveVO.setOutEtcQty(outEtcQty);
            hqStoreMoveVO.setInUnitQty(hqStoreMoveVO.getOutUnitQty());
            hqStoreMoveVO.setInEtcQty(outEtcQty);
            hqStoreMoveVO.setInTotQty(hqStoreMoveVO.getOutTotQty());
            hqStoreMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            hqStoreMoveVO.setRegId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setRegDt(currentDt);
            hqStoreMoveVO.setModId(sessionInfoVO.getUserId());
            hqStoreMoveVO.setModDt(currentDt);

            // DTL 등록
            result = hqStoreMoveMapper.insertHqStoreMoveDtl(hqStoreMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = hqStoreMoveMapper.updateHqStoreMoveAddProdHd(hqStoreMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
