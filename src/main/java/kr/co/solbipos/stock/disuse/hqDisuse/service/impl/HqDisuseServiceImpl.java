package kr.co.solbipos.stock.disuse.hqDisuse.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.disuse.hqDisuse.service.HqDisuseService;
import kr.co.solbipos.stock.disuse.hqDisuse.service.HqDisuseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("hqDisuseService")
public class HqDisuseServiceImpl implements HqDisuseService {
    private final HqDisuseMapper hqDisuseMapper;
    private final MessageService messageService;

    @Autowired
    public HqDisuseServiceImpl(HqDisuseMapper hqDisuseMapper, MessageService messageService) {
        this.hqDisuseMapper = hqDisuseMapper;
        this.messageService = messageService;
    }

    /** 폐기관리 - 폐기관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqDisuseList(HqDisuseVO hqDisuseVO) {
        return hqDisuseMapper.getHqDisuseList(hqDisuseVO);
    }



    /** 폐기관리 - 폐기 삭제 */
    @Override
    public int deleteHqDisuse(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqDisuseVO hqDisuseVO : hqDisuseVOs) {
            hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqDisuseVO.setRegId(sessionInfoVO.getUserId());
            hqDisuseVO.setRegDt(currentDt);
            hqDisuseVO.setModId(sessionInfoVO.getUserId());
            hqDisuseVO.setModDt(currentDt);

            // DTL 삭제
            result = hqDisuseMapper.deleteAllHqDisuseDtl(hqDisuseVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 삭제
            result = hqDisuseMapper.deleteHqDisuseHd(hqDisuseVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }


    /** 폐기관리 - 폐기 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(HqDisuseVO hqDisuseVO) {
        return hqDisuseMapper.getProcFgCheck(hqDisuseVO);
    }


    /** 폐기관리 - 폐기등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqDisuseRegistList(HqDisuseVO hqDisuseVO) {
        return hqDisuseMapper.getHqDisuseRegistList(hqDisuseVO);
    }


    /** 폐기관리 - 폐기상품 저장 */
    @Override
    public int saveHqDisuseRegist(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqDisuseVO hqDisuseHdVO = new HqDisuseVO();
        String seqNo = "";

        for (HqDisuseVO hqDisuseVO : hqDisuseVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(hqDisuseVO.getSeqNo());
                hqDisuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqDisuseHdVO.setDisuseDate(hqDisuseVO.getDisuseDate());
                hqDisuseHdVO.setDisuseTitle(hqDisuseVO.getDisuseTitle());
                hqDisuseHdVO.setSeqNo(hqDisuseVO.getSeqNo());
                hqDisuseHdVO.setProcFg("0");
                hqDisuseHdVO.setStorageCd(hqDisuseVO.getStorageCd());
                hqDisuseHdVO.setRegId(sessionInfoVO.getUserId());
                hqDisuseHdVO.setRegDt(currentDt);
                hqDisuseHdVO.setModId(sessionInfoVO.getUserId());
                hqDisuseHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    HqDisuseVO newSeqNoVO = new HqDisuseVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setDisuseDate(hqDisuseVO.getDisuseDate());
                    seqNo = hqDisuseMapper.getNewSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(hqDisuseVO.getSeqNo()).equals("")) {
                hqDisuseVO.setSeqNo(Integer.parseInt(seqNo));
            }
            hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqDisuseVO.setRegId(sessionInfoVO.getUserId());
            hqDisuseVO.setRegDt(currentDt);
            hqDisuseVO.setModId(sessionInfoVO.getUserId());
            hqDisuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqDisuseVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqDisuseMapper.deleteHqDisuseDtl(hqDisuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqDisuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqDisuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqDisuseMapper.insertHqDisuseDtl(hqDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqDisuseMapper.updateHqDisuseDtl(hqDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 폐기 신규등록인 경우
        if(StringUtil.getOrBlank(hqDisuseHdVO.getSeqNo()).equals("")) {
            // HD 등록
            hqDisuseHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = hqDisuseMapper.insertHqDisuseHd(hqDisuseHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = hqDisuseMapper.updateHqDisuseHd(hqDisuseHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 폐기관리 - 폐기등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(HqDisuseVO hqDisuseVO) {
        List<DefaultMap<String>> result = hqDisuseMapper.getHqDisuseRegistList(hqDisuseVO);
        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 폐기관리 - 폐기상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqDisuseDtlList(HqDisuseVO hqDisuseVO) {
        return hqDisuseMapper.getHqDisuseDtlList(hqDisuseVO);
    }


    /** 폐기관리 - 폐기상세 상품 저장 */
    @Override
    public int saveHqDisuseDtl(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqDisuseVO hqDisuseHdVO = new HqDisuseVO();
        String confirmFg = "";

        for (HqDisuseVO hqDisuseVO : hqDisuseVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqDisuseVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    hqDisuseHdVO.setProcFg("1");
                }
                else {
                    hqDisuseHdVO.setProcFg("0");
                }
                hqDisuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqDisuseHdVO.setDisuseDate(hqDisuseVO.getDisuseDate());
                hqDisuseHdVO.setDisuseTitle(hqDisuseVO.getDisuseTitle());
                hqDisuseHdVO.setSeqNo(hqDisuseVO.getSeqNo());
                hqDisuseHdVO.setStorageCd(hqDisuseVO.getStorageCd());
                hqDisuseHdVO.setRegId(sessionInfoVO.getUserId());
                hqDisuseHdVO.setRegDt(currentDt);
                hqDisuseHdVO.setModId(sessionInfoVO.getUserId());
                hqDisuseHdVO.setModDt(currentDt);
            }

            hqDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqDisuseVO.setRegId(sessionInfoVO.getUserId());
            hqDisuseVO.setRegDt(currentDt);
            hqDisuseVO.setModId(sessionInfoVO.getUserId());
            hqDisuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqDisuseVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqDisuseMapper.deleteHqDisuseDtl(hqDisuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqDisuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqDisuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqDisuseMapper.insertHqDisuseDtl(hqDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqDisuseMapper.updateHqDisuseDtl(hqDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // HD 수정
        result = hqDisuseMapper.updateHqDisuseHd(hqDisuseHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
