package kr.co.solbipos.stock.disuse.disuse.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.stock.disuse.disuse.service.DisuseService;
import kr.co.solbipos.stock.disuse.disuse.service.DisuseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("disuseService")
@Transactional
public class DisuseServiceImpl implements DisuseService {
    private final DisuseMapper disuseMapper;
    private final MessageService messageService;

    @Autowired
    public DisuseServiceImpl(DisuseMapper disuseMapper, MessageService messageService) {
        this.disuseMapper = disuseMapper;
        this.messageService = messageService;
    }

    /** 폐기관리 - 폐기관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDisuseList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = disuseMapper.getHqDisuseList(disuseVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = disuseMapper.getStDisuseList(disuseVO);
        }
        return result;
    }


    /** 폐기관리 - 폐기 삭제 */
    @Override
    public int deleteDisuse(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO) {

        System.out.println("폐기관리 >>> 폐기 삭제 start");

        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (DisuseVO disuseVO : disuseVOs) {
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            disuseVO.setRegId(sessionInfoVO.getUserId());
            disuseVO.setRegDt(currentDt);
            disuseVO.setModId(sessionInfoVO.getUserId());
            disuseVO.setModDt(currentDt);

            System.out.println("폐기관리 >>> 폐기 삭제 >>> DTL count : " + disuseVO.getDtlCnt());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                if(disuseVO.getDtlCnt() > 0) {
                    // DTL 삭제
                    result = disuseMapper.deleteHqAllDisuseDtl(disuseVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // HD 삭제
                result = disuseMapper.deleteHqDisuseHd(disuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                if(disuseVO.getDtlCnt() > 0) {
                    // DTL 삭제
                    result = disuseMapper.deleteStAllDisuseDtl(disuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // HD 삭제
                result = disuseMapper.deleteStDisuseHd(disuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        System.out.println("폐기관리 >>> 폐기 삭제 end");

        return returnResult;
    }


    /** 폐기관리 - 폐기 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(DisuseVO disuseVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = disuseMapper.getHqProcFgCheck(disuseVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = disuseMapper.getStProcFgCheck(disuseVO);
        }
        return result;
    }


    /** 폐기관리 - 폐기등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDisuseRegistList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(disuseVO.getVendrCd()).equals("")) {
            disuseVO.setArrVendrCd(disuseVO.getVendrCd().split(","));
        }

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            disuseVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = disuseMapper.getHqDisuseRegistList(disuseVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = disuseMapper.getStDisuseRegistList(disuseVO);
        }
        return result;
    }


    /** 폐기관리 - 폐기상품 저장 */
    @Override
    public int saveDisuseRegist(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO) {

        System.out.println("폐기관리 >>> 폐기등록 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        DisuseVO disuseHdVO = new DisuseVO();
        String seqNo = "";

        for (DisuseVO disuseVO : disuseVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(disuseVO.getSeqNo());
                disuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                disuseHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                disuseHdVO.setDisuseDate(disuseVO.getDisuseDate());
                disuseHdVO.setDisuseTitle(disuseVO.getDisuseTitle());
                disuseHdVO.setDisuseReason(disuseVO.getDisuseReason());
                disuseHdVO.setSeqNo(disuseVO.getSeqNo());
                disuseHdVO.setProcFg("0");
                disuseHdVO.setDisuseStorageCd(disuseVO.getDisuseStorageCd());
                disuseHdVO.setStorageCd(disuseVO.getStorageCd());
                disuseHdVO.setRegId(sessionInfoVO.getUserId());
                disuseHdVO.setRegDt(currentDt);
                disuseHdVO.setModId(sessionInfoVO.getUserId());
                disuseHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    DisuseVO newSeqNoVO = new DisuseVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    newSeqNoVO.setDisuseDate(disuseVO.getDisuseDate());
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        seqNo = disuseMapper.getHqNewSeqNo(newSeqNoVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        seqNo = disuseMapper.getStNewSeqNo(newSeqNoVO);
                    }
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(disuseVO.getSeqNo()).equals("")) {
                disuseVO.setSeqNo(Integer.parseInt(seqNo));
            }
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            disuseVO.setRegId(sessionInfoVO.getUserId());
            disuseVO.setRegDt(currentDt);
            disuseVO.setModId(sessionInfoVO.getUserId());
            disuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(disuseVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = disuseMapper.deleteHqDisuseDtl(disuseVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = disuseMapper.deleteStDisuseDtl(disuseVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(disuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(disuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = disuseMapper.insertHqDisuseDtl(disuseVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = disuseMapper.insertStDisuseDtl(disuseVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = disuseMapper.updateHqDisuseDtl(disuseVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = disuseMapper.updateStDisuseDtl(disuseVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 폐기 신규등록인 경우
        if(StringUtil.getOrBlank(disuseHdVO.getSeqNo()).equals("")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                disuseHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = disuseMapper.insertHqDisuseHd(disuseHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
                disuseHdVO.setSeqNo(Integer.parseInt(seqNo));
                result = disuseMapper.insertStDisuseHd(disuseHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = disuseMapper.updateHqDisuseHd(disuseHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
                result = disuseMapper.updateStDisuseHd(disuseHdVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        System.out.println("폐기관리 >>> 폐기등록 팝업 >>> 저장 end");

        return returnResult;
    }


    /** 폐기관리 - 폐기등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(DisuseVO disuseVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            disuseVO.setAreaFg(sessionInfoVO.getAreaFg());
            result = disuseMapper.getHqDisuseRegistList(disuseVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = disuseMapper.getStDisuseRegistList(disuseVO);
        }

        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }


    /** 폐기관리 - 폐기상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDisuseDtlList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = disuseMapper.getHqDisuseDtlList(disuseVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = disuseMapper.getStDisuseDtlList(disuseVO);
        }
        return result;
    }


    /** 폐기관리 - 폐기상세 상품 저장 */
    @Override
    public int saveDisuseDtl(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO) {

        System.out.println("폐기관리 >>> 폐기상세 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        DisuseVO disuseHdVO = new DisuseVO();
        String confirmFg = "";

        for (DisuseVO disuseVO : disuseVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(disuseVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    disuseHdVO.setProcFg("1");
                }
                else {
                    disuseHdVO.setProcFg("0");
                }
                disuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                disuseHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                disuseHdVO.setDisuseDate(disuseVO.getDisuseDate());
                disuseHdVO.setDisuseTitle(disuseVO.getDisuseTitle());
                disuseHdVO.setDisuseReason(disuseVO.getDisuseReason());
                disuseHdVO.setSeqNo(disuseVO.getSeqNo());
                disuseHdVO.setStorageCd(disuseVO.getStorageCd());
                disuseHdVO.setDisuseStorageCd(disuseVO.getDisuseStorageCd());
                disuseHdVO.setRegId(sessionInfoVO.getUserId());
                disuseHdVO.setRegDt(currentDt);
                disuseHdVO.setModId(sessionInfoVO.getUserId());
                disuseHdVO.setModDt(currentDt);
                disuseHdVO.setStatus(disuseVO.getStatus());
            }

            disuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            disuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            disuseVO.setRegId(sessionInfoVO.getUserId());
            disuseVO.setRegDt(currentDt);
            disuseVO.setModId(sessionInfoVO.getUserId());
            disuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(disuseVO.getStatus()).equals("DELETE")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    // DTL 삭제
                    result = disuseMapper.deleteHqDisuseDtl(disuseVO);
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    // DTL 삭제
                    result = disuseMapper.deleteStDisuseDtl(disuseVO);
                }
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(disuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(disuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 등록
                        result = disuseMapper.insertHqDisuseDtl(disuseVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 등록
                        result = disuseMapper.insertStDisuseDtl(disuseVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        // DTL 수정
                        result = disuseMapper.updateHqDisuseDtl(disuseVO);
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        // DTL 수정
                        result = disuseMapper.updateStDisuseDtl(disuseVO);
                    }
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }
        
        //삭제가 아닐경우만
//        if(!StringUtil.getOrBlank(disuseHdVO.getStatus()).equals("DELETE")) {
	        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
	            // HD 수정
	            result = disuseMapper.updateHqDisuseHd(disuseHdVO);
	        }
	        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
	            // HD 수정
	            result = disuseMapper.updateStDisuseHd(disuseHdVO);
	        }
	        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }

        System.out.println("폐기관리 >>> 폐기상세 팝업 >>> 저장 end");

        return returnResult;
    }


    /** 폐기관리 - 엑셀업로드 */
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
//                result = disuseMapper.insertExcelUploadAddQty(excelUploadStoreVO);
            } else {
            // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
            result = disuseMapper.deleteDisuseToExcelUploadData(excelUploadStoreVO);
            }

        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            DisuseVO newSeqNoVO = new DisuseVO();
            newSeqNoVO.setHqOfficeCd(excelUploadStoreVO.getHqOfficeCd());
            newSeqNoVO.setStoreCd(excelUploadStoreVO.getStoreCd());
            newSeqNoVO.setDisuseDate(excelUploadStoreVO.getDate());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                seqNo = disuseMapper.getHqNewSeqNo(newSeqNoVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                seqNo = disuseMapper.getStNewSeqNo(newSeqNoVO);
            }
        }

        excelUploadStoreVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 폐기수량으로 입력
        result = disuseMapper.insertDisuseToExcelUploadData(excelUploadStoreVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = disuseMapper.deleteExcelUploadCompleteData(excelUploadStoreVO);

        DisuseVO disuseHdVO = new DisuseVO();
        disuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        disuseHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        disuseHdVO.setDisuseDate(excelUploadStoreVO.getDate());
        disuseHdVO.setDisuseTitle(excelUploadStoreVO.getTitle());
        disuseHdVO.setDisuseReason(excelUploadStoreVO.getReason());
        disuseHdVO.setSeqNo(Integer.parseInt(seqNo));
        disuseHdVO.setProcFg("0");
        disuseHdVO.setStorageCd("999");
        disuseHdVO.setDisuseStorageCd(excelUploadStoreVO.getDisuseStorageCd());
        disuseHdVO.setRegId(sessionInfoVO.getUserId());
        disuseHdVO.setRegDt(currentDt);
        disuseHdVO.setModId(sessionInfoVO.getUserId());
        disuseHdVO.setModDt(currentDt);

        // 폐기 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 등록
                result = disuseMapper.insertHqDisuseHd(disuseHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
            	disuseHdVO.setDisuseStorageCd("001");
                result = disuseMapper.insertStDisuseHd(disuseHdVO);
            }
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // HD 수정
                result = disuseMapper.updateHqDisuseHd(disuseHdVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
            	disuseHdVO.setDisuseStorageCd("001");
                result = disuseMapper.updateStDisuseHd(disuseHdVO);
            }
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    @Override
    public List<DefaultMap<String>> getDisuseReason(SessionInfoVO sessionInfoVO) {
        return disuseMapper.getDisuseReason(sessionInfoVO);
    }
}
