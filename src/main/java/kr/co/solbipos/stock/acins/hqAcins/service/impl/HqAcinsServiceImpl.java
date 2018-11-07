package kr.co.solbipos.stock.acins.hqAcins.service.impl;


import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.acins.hqAcins.service.HqAcinsService;
import kr.co.solbipos.stock.acins.hqAcins.service.HqAcinsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("hqAcinsService")
public class HqAcinsServiceImpl implements HqAcinsService {
    private final HqAcinsMapper hqAcinsMapper;
    private final MessageService messageService;

    @Autowired
    public HqAcinsServiceImpl(HqAcinsMapper hqAcinsMapper, MessageService messageService) {
        this.hqAcinsMapper = hqAcinsMapper;
        this.messageService = messageService;
    }

    /** 실사관리 - 실사관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAcinsList(HqAcinsVO hqAcinsVO) {
        return hqAcinsMapper.getHqAcinsList(hqAcinsVO);
    }



    /** 실사관리 - 실사 삭제 */
    @Override
    public int deleteHqAcins(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqAcinsVO hqAcinsVO : hqAcinsVOs) {
            hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAcinsVO.setRegId(sessionInfoVO.getUserId());
            hqAcinsVO.setRegDt(currentDt);
            hqAcinsVO.setModId(sessionInfoVO.getUserId());
            hqAcinsVO.setModDt(currentDt);

            // DTL 삭제
            result = hqAcinsMapper.deleteAllHqAcinsDtl(hqAcinsVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 삭제
            result = hqAcinsMapper.deleteHqAcinsHd(hqAcinsVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }


    /** 실사관리 - 실사 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(HqAcinsVO hqAcinsVO) {
        return hqAcinsMapper.getProcFgCheck(hqAcinsVO);
    }


    /** 실사관리 - 실사등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAcinsRegistList(HqAcinsVO hqAcinsVO) {
        return hqAcinsMapper.getHqAcinsRegistList(hqAcinsVO);
    }


    /** 실사관리 - 실사상품 저장 */
    @Override
    public int saveHqAcinsRegist(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqAcinsVO hqAcinsHdVO = new HqAcinsVO();
        String seqNo = "";

        for (HqAcinsVO hqAcinsVO : hqAcinsVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(hqAcinsVO.getSeqNo());
                hqAcinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqAcinsHdVO.setAcinsDate(hqAcinsVO.getAcinsDate());
                hqAcinsHdVO.setAcinsTitle(hqAcinsVO.getAcinsTitle());
                hqAcinsHdVO.setSeqNo(hqAcinsVO.getSeqNo());
                hqAcinsHdVO.setProcFg("0");
                hqAcinsHdVO.setStorageCd(hqAcinsVO.getStorageCd());
                hqAcinsHdVO.setRegId(sessionInfoVO.getUserId());
                hqAcinsHdVO.setRegDt(currentDt);
                hqAcinsHdVO.setModId(sessionInfoVO.getUserId());
                hqAcinsHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    HqAcinsVO newSeqNoVO = new HqAcinsVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setAcinsDate(hqAcinsVO.getAcinsDate());
                    seqNo = hqAcinsMapper.getNewSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(hqAcinsVO.getSeqNo()).equals("")) {
                hqAcinsVO.setSeqNo(Integer.parseInt(seqNo));
            }
            hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAcinsVO.setRegId(sessionInfoVO.getUserId());
            hqAcinsVO.setRegDt(currentDt);
            hqAcinsVO.setModId(sessionInfoVO.getUserId());
            hqAcinsVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqAcinsVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqAcinsMapper.deleteHqAcinsDtl(hqAcinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqAcinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqAcinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqAcinsMapper.insertHqAcinsDtl(hqAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqAcinsMapper.updateHqAcinsDtl(hqAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 실사 신규등록인 경우
        if(StringUtil.getOrBlank(hqAcinsHdVO.getSeqNo()).equals("")) {
            // HD 등록
            hqAcinsHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = hqAcinsMapper.insertHqAcinsHd(hqAcinsHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = hqAcinsMapper.updateHqAcinsHd(hqAcinsHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 실사관리 - 실실사등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(HqAcinsVO hqAcinsVO) {
        List<DefaultMap<String>> result = hqAcinsMapper.getHqAcinsRegistList(hqAcinsVO);
        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 실사관리 - 실사상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqAcinsDtlList(HqAcinsVO hqAcinsVO) {
        return hqAcinsMapper.getHqAcinsDtlList(hqAcinsVO);
    }


    /** 실사관리 - 실사상세 상품 저장 */
    @Override
    public int saveHqAcinsDtl(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        HqAcinsVO hqAcinsHdVO = new HqAcinsVO();
        String confirmFg = "";

        for (HqAcinsVO hqAcinsVO : hqAcinsVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(hqAcinsVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    hqAcinsHdVO.setProcFg("1");
                }
                else {
                    hqAcinsHdVO.setProcFg("0");
                }
                hqAcinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                hqAcinsHdVO.setAcinsDate(hqAcinsVO.getAcinsDate());
                hqAcinsHdVO.setAcinsTitle(hqAcinsVO.getAcinsTitle());
                hqAcinsHdVO.setSeqNo(hqAcinsVO.getSeqNo());
                hqAcinsHdVO.setStorageCd(hqAcinsVO.getStorageCd());
                hqAcinsHdVO.setRegId(sessionInfoVO.getUserId());
                hqAcinsHdVO.setRegDt(currentDt);
                hqAcinsHdVO.setModId(sessionInfoVO.getUserId());
                hqAcinsHdVO.setModDt(currentDt);
            }

            hqAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqAcinsVO.setRegId(sessionInfoVO.getUserId());
            hqAcinsVO.setRegDt(currentDt);
            hqAcinsVO.setModId(sessionInfoVO.getUserId());
            hqAcinsVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(hqAcinsVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = hqAcinsMapper.deleteHqAcinsDtl(hqAcinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(hqAcinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(hqAcinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = hqAcinsMapper.insertHqAcinsDtl(hqAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = hqAcinsMapper.updateHqAcinsDtl(hqAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // HD 수정
        result = hqAcinsMapper.updateHqAcinsHd(hqAcinsHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
