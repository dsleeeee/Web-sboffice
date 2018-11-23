package kr.co.solbipos.stock.setProdAdj.setProdAdj.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.stock.setProdAdj.setProdAdj.service.SetProdAdjService;
import kr.co.solbipos.stock.setProdAdj.setProdAdj.service.SetProdAdjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("setProdAdjService")
public class SetProdAdjServiceImpl implements SetProdAdjService {
    private final SetProdAdjMapper setProdAdjMapper;
    private final MessageService messageService;

    @Autowired
    public SetProdAdjServiceImpl(SetProdAdjMapper setProdAdjMapper, MessageService messageService) {
        this.setProdAdjMapper = setProdAdjMapper;
        this.messageService = messageService;
    }

    /** 세트재고조정 - 세트재고조정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSetProdAdjList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            setProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = setProdAdjMapper.getHqSetProdAdjList(setProdAdjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            setProdAdjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = setProdAdjMapper.getStSetProdAdjList(setProdAdjVO);
        }
        return result;
    }


    /** 세트재고조정 - 세트재고 삭제 */
    @Override
    public int deleteSetProdAdj(SetProdAdjVO[] setProdAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (SetProdAdjVO setProdAdjVO : setProdAdjVOs) {
            setProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            setProdAdjVO.setStoreCd(sessionInfoVO.getStoreCd());
            setProdAdjVO.setRegId(sessionInfoVO.getUserId());
            setProdAdjVO.setRegDt(currentDt);
            setProdAdjVO.setModId(sessionInfoVO.getUserId());
            setProdAdjVO.setModDt(currentDt);

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // DTL 삭제
                result = setProdAdjMapper.deleteHqAllSetProdAdjDtl(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = setProdAdjMapper.deleteHqSetProdAdjHd(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // DTL 삭제
                result = setProdAdjMapper.deleteStAllSetProdAdjDtl(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = setProdAdjMapper.deleteStSetProdAdjHd(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }


    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSetProdAdjRegistList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            setProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = setProdAdjMapper.getHqSetProdAdjRegistList(setProdAdjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            setProdAdjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = setProdAdjMapper.getStSetProdAdjRegistList(setProdAdjVO);
        }
        return result;
    }


    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSetProdAdjRegistCompstList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            setProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = setProdAdjMapper.getHqSetProdAdjRegistCompstList(setProdAdjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            setProdAdjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = setProdAdjMapper.getStSetProdAdjRegistCompstList(setProdAdjVO);
        }
        return result;
    }


    /** 세트재고조정 - 세트재고조정 등록 */
    @Override
    public int saveSetProdAdjRegist(SetProdAdjVO[] setProdAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        String seqNo = "";

        for (SetProdAdjVO setProdAdjVO : setProdAdjVOs) {
            // 신규 seq 조회
            SetProdAdjVO newSeqNoVO = new SetProdAdjVO();
            newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
            newSeqNoVO.setSetDate(setProdAdjVO.getSetDate());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                seqNo = setProdAdjMapper.getHqNewSeqNo(newSeqNoVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                seqNo = setProdAdjMapper.getStNewSeqNo(newSeqNoVO);
            }

            setProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            setProdAdjVO.setStoreCd(sessionInfoVO.getStoreCd());
            setProdAdjVO.setSeqNo(Integer.parseInt(seqNo));
            setProdAdjVO.setProcFg("1");
            setProdAdjVO.setRegId(sessionInfoVO.getUserId());
            setProdAdjVO.setRegDt(currentDt);
            setProdAdjVO.setModId(sessionInfoVO.getUserId());
            setProdAdjVO.setModDt(currentDt);

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // DTL 등록
                result = setProdAdjMapper.insertHqSetProdAdjDtl(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 등록
                result = setProdAdjMapper.insertHqSetProdAdjHd(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // DTL 등록
                result = setProdAdjMapper.insertStSetProdAdjDtl(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 등록
                result = setProdAdjMapper.insertStSetProdAdjHd(setProdAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }
}
