package kr.co.solbipos.stock.adj.hqAdj.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.adj.hqAdj.service.HqAdjService;
import kr.co.solbipos.stock.adj.hqAdj.service.HqAdjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("hqAdjService")
public class HqAdjServiceImpl implements HqAdjService {
    private final HqAdjMapper hqAdjMapper;
    private final MessageService messageService;

    @Autowired
    public HqAdjServiceImpl(HqAdjMapper hqAdjMapper, MessageService messageService) {
        this.hqAdjMapper = hqAdjMapper;
        this.messageService = messageService;
    }

    /** 조정관리 - 조정관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAdjList(HqAdjVO hqAdjVO) {
        return hqAdjMapper.getHqAdjList(hqAdjVO);
    }



    /** 조정관리 - 조정 삭제 */
    @Override
    public int deleteHqAdj(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqAdjVO hqAdjVO : hqAdjVOs) {
            hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAdjVO.setRegId(sessionInfoVO.getUserId());
            hqAdjVO.setRegDt(currentDt);
            hqAdjVO.setModId(sessionInfoVO.getUserId());
            hqAdjVO.setModDt(currentDt);

            // DTL 삭제
            result = hqAdjMapper.deleteAllHqAdjDtl(hqAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 삭제
            result = hqAdjMapper.deleteHqAdjHd(hqAdjVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }


    /** 조정관리 - 조정 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(HqAdjVO hqAdjVO) {
        return hqAdjMapper.getProcFgCheck(hqAdjVO);
    }


    /** 조정관리 - 조정등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAdjRegistList(HqAdjVO hqAdjVO) {
        return hqAdjMapper.getHqAdjRegistList(hqAdjVO);
    }


    /** 조정관리 - 조정상품 저장 */
    @Override
    public int saveHqAdjRegist(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqAdjVO hqAdjHdVO = new HqAdjVO();
        String seqNo = "";

        for (HqAdjVO hqAdjVO : hqAdjVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(hqAdjVO.getSeqNo());
                hqAdjHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqAdjHdVO.setAdjDate(hqAdjVO.getAdjDate());
                hqAdjHdVO.setAdjTitle(hqAdjVO.getAdjTitle());
                hqAdjHdVO.setSeqNo(hqAdjVO.getSeqNo());
                hqAdjHdVO.setProcFg("0");
                hqAdjHdVO.setStorageCd(hqAdjVO.getStorageCd());
                hqAdjHdVO.setRegId(sessionInfoVO.getUserId());
                hqAdjHdVO.setRegDt(currentDt);
                hqAdjHdVO.setModId(sessionInfoVO.getUserId());
                hqAdjHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    HqAdjVO newSeqNoVO = new HqAdjVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setAdjDate(hqAdjVO.getAdjDate());
                    seqNo = hqAdjMapper.getNewSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(hqAdjVO.getSeqNo()).equals("")) {
                hqAdjVO.setSeqNo(Integer.parseInt(seqNo));
            }
            hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAdjVO.setRegId(sessionInfoVO.getUserId());
            hqAdjVO.setRegDt(currentDt);
            hqAdjVO.setModId(sessionInfoVO.getUserId());
            hqAdjVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqAdjVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqAdjMapper.deleteHqAdjDtl(hqAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqAdjVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqAdjVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqAdjMapper.insertHqAdjDtl(hqAdjVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqAdjMapper.updateHqAdjDtl(hqAdjVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 조정 신규등록인 경우
        if(StringUtil.getOrBlank(hqAdjHdVO.getSeqNo()).equals("")) {
            // HD 등록
            hqAdjHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = hqAdjMapper.insertHqAdjHd(hqAdjHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = hqAdjMapper.updateHqAdjHd(hqAdjHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 조정관리 - 조정등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(HqAdjVO hqAdjVO) {
        List<DefaultMap<String>> result = hqAdjMapper.getHqAdjRegistList(hqAdjVO);
        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 조정관리 - 조정상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAdjDtlList(HqAdjVO hqAdjVO) {
        return hqAdjMapper.getHqAdjDtlList(hqAdjVO);
    }


    /** 조정관리 - 조정상세 상품 저장 */
    @Override
    public int saveHqAdjDtl(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqAdjVO hqAdjHdVO = new HqAdjVO();
        String confirmFg = "";

        for (HqAdjVO hqAdjVO : hqAdjVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqAdjVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    hqAdjHdVO.setProcFg("1");
                }
                else {
                    hqAdjHdVO.setProcFg("0");
                }
                hqAdjHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqAdjHdVO.setAdjDate(hqAdjVO.getAdjDate());
                hqAdjHdVO.setAdjTitle(hqAdjVO.getAdjTitle());
                hqAdjHdVO.setSeqNo(hqAdjVO.getSeqNo());
                hqAdjHdVO.setStorageCd(hqAdjVO.getStorageCd());
                hqAdjHdVO.setRegId(sessionInfoVO.getUserId());
                hqAdjHdVO.setRegDt(currentDt);
                hqAdjHdVO.setModId(sessionInfoVO.getUserId());
                hqAdjHdVO.setModDt(currentDt);
            }

            hqAdjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAdjVO.setRegId(sessionInfoVO.getUserId());
            hqAdjVO.setRegDt(currentDt);
            hqAdjVO.setModId(sessionInfoVO.getUserId());
            hqAdjVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqAdjVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqAdjMapper.deleteHqAdjDtl(hqAdjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqAdjVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqAdjVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqAdjMapper.insertHqAdjDtl(hqAdjVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqAdjMapper.updateHqAdjDtl(hqAdjVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // HD 수정
        result = hqAdjMapper.updateHqAdjHd(hqAdjHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
