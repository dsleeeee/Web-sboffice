package kr.co.solbipos.stock.adj.adj.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.stock.adj.adj.service.AdjService;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("adjServiceImpl")
@Transactional
public class AdjServiceImpl implements AdjService {
    private final AdjMapper adjMapper;
    private final MessageService messageService;

    @Autowired
    public AdjServiceImpl(AdjMapper adjMapper, MessageService messageService) {
        this.adjMapper = adjMapper;
        this.messageService = messageService;
    }

    /** 조정관리 - 조정관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAdjList(AdjVO adjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = adjMapper.getHqAdjList(adjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = adjMapper.getStAdjList(adjVO);
        }
        return result;
    }



    /** 조정관리 - 조정 삭제 */
    @Override
    public int deleteAdj(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (AdjVO adjVO : adjVOs) {
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            adjVO.setRegId(sessionInfoVO.getUserId());
            adjVO.setRegDt(currentDt);
            adjVO.setModId(sessionInfoVO.getUserId());
            adjVO.setModDt(currentDt);

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // DTL 삭제
                result = adjMapper.deleteHqAllAdjDtl(adjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = adjMapper.deleteHqAdjHd(adjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // DTL 삭제
                result = adjMapper.deleteStAllAdjDtl(adjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = adjMapper.deleteStAdjHd(adjVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }


    /** 조정관리 - 조정 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(AdjVO adjVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = adjMapper.getHqProcFgCheck(adjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = adjMapper.getStProcFgCheck(adjVO);
        }
        return result;
    }


    /** 조정관리 - 조정등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAdjRegistList(AdjVO adjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(adjVO.getVendrCd()).equals("")) {
            adjVO.setArrVendrCd(adjVO.getVendrCd().split(","));
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            adjVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = adjMapper.getHqAdjRegistList(adjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = adjMapper.getStAdjRegistList(adjVO);
        }
        return result;
    }


    /** 조정관리 - 조정상품 저장 */
    @Override
    public int saveAdjRegist(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        AdjVO adjHdVO = new AdjVO();
        String seqNo = "";

        for (AdjVO adjVO : adjVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(adjVO.getSeqNo());
                adjHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                adjHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                adjHdVO.setAdjDate(adjVO.getAdjDate());
                adjHdVO.setAdjTitle(adjVO.getAdjTitle());
                adjHdVO.setSeqNo(adjVO.getSeqNo());
                adjHdVO.setProcFg("0");
                adjHdVO.setStorageCd(adjVO.getStorageCd());
                adjHdVO.setRegId(sessionInfoVO.getUserId());
                adjHdVO.setRegDt(currentDt);
                adjHdVO.setModId(sessionInfoVO.getUserId());
                adjHdVO.setModDt(currentDt);
                adjHdVO.setAdjStorageCd(adjVO.getAdjStorageCd());

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    AdjVO newSeqNoVO = new AdjVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    newSeqNoVO.setAdjDate(adjVO.getAdjDate());
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        seqNo = adjMapper.getHqNewSeqNo(newSeqNoVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        seqNo = adjMapper.getStNewSeqNo(newSeqNoVO);
                    }
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(adjVO.getSeqNo()).equals("")) {
                adjVO.setSeqNo(Integer.parseInt(seqNo));
            }
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            adjVO.setRegId(sessionInfoVO.getUserId());
            adjVO.setRegDt(currentDt);
            adjVO.setModId(sessionInfoVO.getUserId());
            adjVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(adjVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = adjMapper.deleteHqAdjDtl(adjVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = adjMapper.deleteStAdjDtl(adjVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(adjVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(adjVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = adjMapper.insertHqAdjDtl(adjVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = adjMapper.insertStAdjDtl(adjVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = adjMapper.updateHqAdjDtl(adjVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = adjMapper.updateStAdjDtl(adjVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 조정 신규등록인 경우
        if(StringUtil.getOrBlank(adjHdVO.getSeqNo()).equals("")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                adjHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = adjMapper.insertHqAdjHd(adjHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
                adjHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = adjMapper.insertStAdjHd(adjHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = adjMapper.updateHqAdjHd(adjHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
                result = adjMapper.updateStAdjHd(adjHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 조정관리 - 조정등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(AdjVO adjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            adjVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = adjMapper.getHqAdjRegistList(adjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = adjMapper.getStAdjRegistList(adjVO);
        }

        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 조정관리 - 조정상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAdjDtlList(AdjVO adjVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = adjMapper.getHqAdjDtlList(adjVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = adjMapper.getStAdjDtlList(adjVO);
        }
        return result;
    }


    /** 조정관리 - 조정상세 상품 저장 */
    @Override
    public int saveAdjDtl(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        AdjVO adjHdVO = new AdjVO();
        String confirmFg = "";

        for (AdjVO adjVO : adjVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(adjVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    adjHdVO.setProcFg("1");
                }
                else {
                    adjHdVO.setProcFg("0");
                }
                adjHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                adjHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                adjHdVO.setAdjDate(adjVO.getAdjDate());
                adjHdVO.setAdjTitle(adjVO.getAdjTitle());
                adjHdVO.setSeqNo(adjVO.getSeqNo());
                adjHdVO.setStorageCd(adjVO.getStorageCd());
                adjHdVO.setRegId(sessionInfoVO.getUserId());
                adjHdVO.setRegDt(currentDt);
                adjHdVO.setModId(sessionInfoVO.getUserId());
                adjHdVO.setModDt(currentDt);
                adjHdVO.setStatus(adjVO.getStatus());
            }

            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
            adjVO.setRegId(sessionInfoVO.getUserId());
            adjVO.setRegDt(currentDt);
            adjVO.setModId(sessionInfoVO.getUserId());
            adjVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(adjVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = adjMapper.deleteHqAdjDtl(adjVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = adjMapper.deleteStAdjDtl(adjVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(adjVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(adjVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = adjMapper.insertHqAdjDtl(adjVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = adjMapper.insertStAdjDtl(adjVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = adjMapper.updateHqAdjDtl(adjVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = adjMapper.updateStAdjDtl(adjVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }
        
        //삭제가 아닐경우만
        if(!StringUtil.getOrBlank(adjHdVO.getStatus()).equals("DELETE")) {
	        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
	            // HD 수정
	            result = adjMapper.updateHqAdjHd(adjHdVO);
	        }
	        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
	            // HD 수정
	            result = adjMapper.updateStAdjHd(adjHdVO);
	        }
	        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
        return returnResult;
    }


    /** 조정관리 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadVO.setRegId(sessionInfoVO.getUserId());
        excelUploadVO.setRegDt(currentDt);
        excelUploadVO.setModId(sessionInfoVO.getUserId());
        excelUploadVO.setModDt(currentDt);

        String seqNo = StringUtil.getOrBlank(excelUploadVO.getSeqNo());
        String insFg = (seqNo.equals("") ? "I" : "U");
        if(!seqNo.equals("")) {
            // 수량추가인 경우
            if(StringUtil.getOrBlank(excelUploadVO.getAddQtyFg()).equals("add")) {
                result = adjMapper.insertExcelUploadAddQty(excelUploadVO);
            }

            // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
            result = adjMapper.deleteAdjToExcelUploadData(excelUploadVO);
        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            AdjVO newSeqNoVO = new AdjVO();
            newSeqNoVO.setHqOfficeCd(excelUploadVO.getHqOfficeCd());
            newSeqNoVO.setStoreCd(excelUploadVO.getStoreCd());
            newSeqNoVO.setAdjDate(excelUploadVO.getDate());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                seqNo = adjMapper.getHqNewSeqNo(newSeqNoVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                seqNo = adjMapper.getStNewSeqNo(newSeqNoVO);
            }
        }

        excelUploadVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 조정수량으로 입력
        result = adjMapper.insertAdjToExcelUploadData(excelUploadVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = adjMapper.deleteExcelUploadCompleteData(excelUploadVO);

        AdjVO adjHdVO = new AdjVO();
        adjHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        adjHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        adjHdVO.setAdjDate(excelUploadVO.getDate());
        adjHdVO.setAdjTitle(excelUploadVO.getTitle());
        adjHdVO.setSeqNo(Integer.parseInt(seqNo));
        adjHdVO.setProcFg("0");
        adjHdVO.setStorageCd("999");
        adjHdVO.setRegId(sessionInfoVO.getUserId());
        adjHdVO.setRegDt(currentDt);
        adjHdVO.setModId(sessionInfoVO.getUserId());
        adjHdVO.setModDt(currentDt);

        // 조정 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                result = adjMapper.insertHqAdjHd(adjHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
            	adjHdVO.setAdjStorageCd("001");
                result = adjMapper.insertStAdjHd(adjHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = adjMapper.updateHqAdjHd(adjHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
            	adjHdVO.setAdjStorageCd("001");
                result = adjMapper.updateStAdjHd(adjHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }
}
