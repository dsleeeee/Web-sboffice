package kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.HqSetProdAdjService;
import kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.HqSetProdAdjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("hqSetProdAdjService")
public class HqSetProdAdjServiceImpl implements HqSetProdAdjService {
    private final HqSetProdAdjMapper hqSetProdAdjMapper;
    private final MessageService messageService;

    @Autowired
    public HqSetProdAdjServiceImpl(HqSetProdAdjMapper hqSetProdAdjMapper, MessageService messageService) {
        this.hqSetProdAdjMapper = hqSetProdAdjMapper;
        this.messageService = messageService;
    }

    /** 세트재고조정 - 세트재고조정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqSetProdAdjList(HqSetProdAdjVO hqSetProdAdjVO) {
        return hqSetProdAdjMapper.getHqSetProdAdjList(hqSetProdAdjVO);
    }


    /** 세트재고조정 - 세트재고 삭제 */
    @Override
    public int deleteHqSetProdAdj(HqSetProdAdjVO[] hqSetProdAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqSetProdAdjVO hqSetProdAdjVO : hqSetProdAdjVOs) {
            hqSetProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSetProdAdjVO.setRegId(sessionInfoVO.getUserId());
            hqSetProdAdjVO.setRegDt(currentDt);
            hqSetProdAdjVO.setModId(sessionInfoVO.getUserId());
            hqSetProdAdjVO.setModDt(currentDt);

            // DTL 삭제
            result = hqSetProdAdjMapper.deleteAllHqSetProdAdjDtl(hqSetProdAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 삭제
            result = hqSetProdAdjMapper.deleteHqSetProdAdjHd(hqSetProdAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }


    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqSetProdAdjRegistList(HqSetProdAdjVO hqSetProdAdjVO) {
        return hqSetProdAdjMapper.getHqSetProdAdjRegistList(hqSetProdAdjVO);
    }


    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqSetProdAdjRegistCompstList(HqSetProdAdjVO hqSetProdAdjVO) {
        return hqSetProdAdjMapper.getHqSetProdAdjRegistCompstList(hqSetProdAdjVO);
    }


    /** 세트재고조정 - 세트재고조정 등록 */
    @Override
    public int saveHqSetProdAdjRegist(HqSetProdAdjVO[] hqSetProdAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqSetProdAdjVO hqSetProdAdjVO : hqSetProdAdjVOs) {
            // 신규 seq 조회
            HqSetProdAdjVO newSeqNoVO = new HqSetProdAdjVO();
            newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            newSeqNoVO.setSetDate(hqSetProdAdjVO.getSetDate());
            String seqNo = hqSetProdAdjMapper.getNewSeqNo(newSeqNoVO);

            hqSetProdAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSetProdAdjVO.setSeqNo(Integer.parseInt(seqNo));
            hqSetProdAdjVO.setProcFg("1");
            hqSetProdAdjVO.setRegId(sessionInfoVO.getUserId());
            hqSetProdAdjVO.setRegDt(currentDt);
            hqSetProdAdjVO.setModId(sessionInfoVO.getUserId());
            hqSetProdAdjVO.setModDt(currentDt);

            // DTL 등록
            result = hqSetProdAdjMapper.insertHqSetProdAdjDtl(hqSetProdAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 등록
            result = hqSetProdAdjMapper.insertHqSetProdAdjHd(hqSetProdAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }
}
