package kr.co.solbipos.stock.acins.acins.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.stock.acins.acins.service.AcinsService;
import kr.co.solbipos.stock.acins.acins.service.AcinsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("acinsService")
@Transactional
public class AcinsServiceImpl implements AcinsService {
    private final AcinsMapper acinsMapper;
    private final MessageService messageService;

    @Autowired
    public AcinsServiceImpl(AcinsMapper acinsMapper, MessageService messageService) {
        this.acinsMapper = acinsMapper;
        this.messageService = messageService;
    }


    /** 실사관리 - 실사관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAcinsList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = acinsMapper.getHqAcinsList(acinsVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = acinsMapper.getStAcinsList(acinsVO);
        }
        return result;
    }


    /** 실사관리 - 실사 삭제 */
    @Override
    public int deleteAcins(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (AcinsVO acinsVO : acinsVOs) {
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            acinsVO.setRegId(sessionInfoVO.getUserId());
            acinsVO.setRegDt(currentDt);
            acinsVO.setModId(sessionInfoVO.getUserId());
            acinsVO.setModDt(currentDt);

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // DTL 삭제
                result = acinsMapper.deleteHqAllAcinsDtl(acinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = acinsMapper.deleteHqAcinsHd(acinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // DTL 삭제
                result = acinsMapper.deleteStAllAcinsDtl(acinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD 삭제
                result = acinsMapper.deleteStAcinsHd(acinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }


    /** 실사관리 - 실사 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(AcinsVO acinsVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = acinsMapper.getHqProcFgCheck(acinsVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = acinsMapper.getStProcFgCheck(acinsVO);
        }
        return result;
    }


    /** 실사관리 - 실사등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAcinsRegistList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(acinsVO.getVendrCd()).equals("")) {
            acinsVO.setArrVendrCd(acinsVO.getVendrCd().split(","));
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acinsVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = acinsMapper.getHqAcinsRegistList(acinsVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = acinsMapper.getStAcinsRegistList(acinsVO);
        }
        return result;
    }


    /** 실사관리 - 실사상품 저장 */
    @Override
    public int saveAcinsRegist(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        AcinsVO acinsHdVO = new AcinsVO();
        String seqNo = "";

        for (AcinsVO acinsVO : acinsVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(acinsVO.getSeqNo());
                acinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                acinsHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                acinsHdVO.setAcinsDate(acinsVO.getAcinsDate());
                acinsHdVO.setAcinsTitle(acinsVO.getAcinsTitle());
                acinsHdVO.setAcinsReason(acinsVO.getAcinsReason());
                acinsHdVO.setSeqNo(acinsVO.getSeqNo());
                acinsHdVO.setProcFg("0");
                acinsHdVO.setAdjStorageCd(acinsVO.getAdjStorageCd());
                acinsHdVO.setStorageCd(acinsVO.getStorageCd());
                acinsHdVO.setRegId(sessionInfoVO.getUserId());
                acinsHdVO.setRegDt(currentDt);
                acinsHdVO.setModId(sessionInfoVO.getUserId());
                acinsHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    AcinsVO newSeqNoVO = new AcinsVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    newSeqNoVO.setAcinsDate(acinsVO.getAcinsDate());
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        seqNo = acinsMapper.getHqNewSeqNo(newSeqNoVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        seqNo = acinsMapper.getStNewSeqNo(newSeqNoVO);
                    }
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(acinsVO.getSeqNo()).equals("")) {
                acinsVO.setSeqNo(Integer.parseInt(seqNo));
            }
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            acinsVO.setRegId(sessionInfoVO.getUserId());
            acinsVO.setRegDt(currentDt);
            acinsVO.setModId(sessionInfoVO.getUserId());
            acinsVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(acinsVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = acinsMapper.deleteHqAcinsDtl(acinsVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = acinsMapper.deleteStAcinsDtl(acinsVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(acinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(acinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = acinsMapper.insertHqAcinsDtl(acinsVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = acinsMapper.insertStAcinsDtl(acinsVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = acinsMapper.updateHqAcinsDtl(acinsVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = acinsMapper.updateStAcinsDtl(acinsVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 실사 신규등록인 경우
        if(StringUtil.getOrBlank(acinsHdVO.getSeqNo()).equals("")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                acinsHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = acinsMapper.insertHqAcinsHd(acinsHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
                acinsHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = acinsMapper.insertStAcinsHd(acinsHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = acinsMapper.updateHqAcinsHd(acinsHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
                result = acinsMapper.updateStAcinsHd(acinsHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 실사관리 - 실사등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(AcinsVO acinsVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acinsVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = acinsMapper.getHqAcinsRegistList(acinsVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = acinsMapper.getStAcinsRegistList(acinsVO);
        }

        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 실사관리 - 실사상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAcinsDtlList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = acinsMapper.getHqAcinsDtlList(acinsVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = acinsMapper.getStAcinsDtlList(acinsVO);
        }
        return result;
    }


    /** 실사관리 - 실사상세 상품 저장 */
    @Override
    public int saveAcinsDtl(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        AcinsVO acinsHdVO = new AcinsVO();
        String confirmFg = "";

        for (AcinsVO acinsVO : acinsVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(acinsVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    acinsHdVO.setProcFg("1");
                }
                else {
                    acinsHdVO.setProcFg("0");
                }
                acinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                acinsHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                acinsHdVO.setAcinsDate(acinsVO.getAcinsDate());
                acinsHdVO.setAcinsTitle(acinsVO.getAcinsTitle());
                acinsHdVO.setAcinsReason(acinsVO.getAcinsReason());
                acinsHdVO.setSeqNo(acinsVO.getSeqNo());
                acinsHdVO.setStorageCd(acinsVO.getStorageCd());
                acinsHdVO.setAdjStorageCd(acinsVO.getAdjStorageCd());
                acinsHdVO.setRegId(sessionInfoVO.getUserId());
                acinsHdVO.setRegDt(currentDt);
                acinsHdVO.setModId(sessionInfoVO.getUserId());
                acinsHdVO.setModDt(currentDt);
                acinsHdVO.setStatus(acinsVO.getStatus());
            }

            acinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            acinsVO.setRegId(sessionInfoVO.getUserId());
            acinsVO.setRegDt(currentDt);
            acinsVO.setModId(sessionInfoVO.getUserId());
            acinsVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(acinsVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = acinsMapper.deleteHqAcinsDtl(acinsVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = acinsMapper.deleteStAcinsDtl(acinsVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(acinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(acinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = acinsMapper.insertHqAcinsDtl(acinsVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = acinsMapper.insertStAcinsDtl(acinsVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = acinsMapper.updateHqAcinsDtl(acinsVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = acinsMapper.updateStAcinsDtl(acinsVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }
        
        //삭제가 아닐경우만
        if(!StringUtil.getOrBlank(acinsHdVO.getStatus()).equals("DELETE")) {
	        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
	            // HD 수정
	            result = acinsMapper.updateHqAcinsHd(acinsHdVO);
	        }
	        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
	            // HD 수정
	            result = acinsMapper.updateStAcinsHd(acinsHdVO);
	        }
	        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
        return returnResult;
    }


    /** 실사관리 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadStoreVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadStoreVO.setRegId(sessionInfoVO.getUserId());
        excelUploadStoreVO.setRegDt(currentDt);
        excelUploadStoreVO.setModId(sessionInfoVO.getUserId());
        excelUploadStoreVO.setModDt(currentDt);

        String seqNo = StringUtil.getOrBlank(excelUploadStoreVO.getSeqNo());
        String insFg = (seqNo.equals("") ? "I" : "U");
        if(!seqNo.equals("")) {
            // 수량추가인 경우
            if(StringUtil.getOrBlank(excelUploadStoreVO.getAddQtyFg()).equals("add")) {
//                result = acinsMapper.insertExcelUploadAddQty(excelUploadStoreVO);
            } else {
            // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
            result = acinsMapper.deleteAcinsToExcelUploadData(excelUploadStoreVO);
            }

        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            AcinsVO newSeqNoVO = new AcinsVO();
            newSeqNoVO.setHqOfficeCd(excelUploadStoreVO.getHqOfficeCd());
            newSeqNoVO.setStoreCd(excelUploadStoreVO.getStoreCd());
            newSeqNoVO.setAcinsDate(excelUploadStoreVO.getDate());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                seqNo = acinsMapper.getHqNewSeqNo(newSeqNoVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                seqNo = acinsMapper.getStNewSeqNo(newSeqNoVO);
            }
        }

        excelUploadStoreVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 실사수량으로 입력
        result = acinsMapper.insertAcinsToExcelUploadData(excelUploadStoreVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = acinsMapper.deleteExcelUploadCompleteData(excelUploadStoreVO);

        AcinsVO acinsHdVO = new AcinsVO();
        acinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        acinsHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        acinsHdVO.setAcinsDate(excelUploadStoreVO.getDate());
        acinsHdVO.setAcinsTitle(excelUploadStoreVO.getTitle());
        acinsHdVO.setAcinsReason(excelUploadStoreVO.getReason());
        acinsHdVO.setSeqNo(Integer.parseInt(seqNo));
        acinsHdVO.setProcFg("0");
        acinsHdVO.setStorageCd("999");
        acinsHdVO.setAdjStorageCd(excelUploadStoreVO.getAdjStorageCd());
        acinsHdVO.setRegId(sessionInfoVO.getUserId());
        acinsHdVO.setRegDt(currentDt);
        acinsHdVO.setModId(sessionInfoVO.getUserId());
        acinsHdVO.setModDt(currentDt);

        // 실사 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                result = acinsMapper.insertHqAcinsHd(acinsHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
            	acinsHdVO.setAdjStorageCd("001");
                result = acinsMapper.insertStAcinsHd(acinsHdVO);
            }
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = acinsMapper.updateHqAcinsHd(acinsHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
            	acinsHdVO.setAdjStorageCd("001");
                result = acinsMapper.updateStAcinsHd(acinsHdVO);
            }
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    @Override
    public List<DefaultMap<String>> getAcinsReason(SessionInfoVO sessionInfoVO) {
        return acinsMapper.getAcinsReason(sessionInfoVO);
    }
}
