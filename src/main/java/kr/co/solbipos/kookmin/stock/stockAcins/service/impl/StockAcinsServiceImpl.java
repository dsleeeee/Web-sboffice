package kr.co.solbipos.kookmin.stock.stockAcins.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsService;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : StockAcinsServiceImpl.java
 * @Description : 국민대 > 재고관리 > 재고실사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StockAcinsService")
@Transactional
public class StockAcinsServiceImpl implements StockAcinsService {

    private final StockAcinsMapper stockAcinsMapper;
    private final MessageService messageService;

    @Autowired
    public StockAcinsServiceImpl(StockAcinsMapper stockAcinsMapper, MessageService messageService) {
        this.stockAcinsMapper = stockAcinsMapper;
        this.messageService = messageService;
    }

    /** 실사사유 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAcinsReason(SessionInfoVO sessionInfoVO) {
        return stockAcinsMapper.getSearchAcinsReason(sessionInfoVO);
    }

    /** 재고실사 - 실사관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAcinsList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAcinsMapper.getSearchAcinsList(stockAcinsVO);
    }

    /** 재고실사 - 실사 삭제 */
    @Override
    public int deleteAcins(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고실사 >>> 실사 삭제 start");

        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (StockAcinsVO stockAcinsVO : stockAcinsVOS) {

            stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            stockAcinsVO.setRegId(sessionInfoVO.getUserId());
            stockAcinsVO.setRegDt(currentDt);
            stockAcinsVO.setModId(sessionInfoVO.getUserId());
            stockAcinsVO.setModDt(currentDt);

            System.out.println("재고실사 >>> 실사 삭제 >>> DTL count : " + stockAcinsVO.getDtlCnt());

            if(stockAcinsVO.getDtlCnt() > 0) {
                // DTL 삭제
                result = stockAcinsMapper.deleteAllAcinsDtl(stockAcinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // HD 삭제
            result = stockAcinsMapper.deleteAcinsHd(stockAcinsVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        System.out.println("재고실사 >>> 실사 삭제 end");

        return returnResult;
    }

    /**  재고실사 - 실사등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAcinsRegistList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockAcinsVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAcinsMapper.getSearchAcinsRegistList(stockAcinsVO);
    }

    /** 재고실사 - 실사상품 저장 */
    @Override
    public int saveAcins(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고실사 >>> 실사등록 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockAcinsVO stockAcinsHdVO = new StockAcinsVO();
        String seqNo = "";

        for (StockAcinsVO stockAcinsVO : stockAcinsVOS) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(stockAcinsVO.getSeqNo());
                stockAcinsHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                stockAcinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockAcinsHdVO.setStoreCd(stockAcinsVO.getStoreCd());
                stockAcinsHdVO.setVendrCd(stockAcinsVO.getVendrCd());
                stockAcinsHdVO.setAcinsDate(stockAcinsVO.getAcinsDate());
                stockAcinsHdVO.setAcinsTitle(stockAcinsVO.getAcinsTitle());
                stockAcinsHdVO.setAcinsReason(stockAcinsVO.getAcinsReason());
                stockAcinsHdVO.setSeqNo(stockAcinsVO.getSeqNo());
                stockAcinsHdVO.setProcFg("0");
                stockAcinsHdVO.setAdjStorageCd(stockAcinsVO.getAdjStorageCd());
                stockAcinsHdVO.setStorageCd(stockAcinsVO.getStorageCd());
                stockAcinsHdVO.setRegId(sessionInfoVO.getUserId());
                stockAcinsHdVO.setRegDt(currentDt);
                stockAcinsHdVO.setModId(sessionInfoVO.getUserId());
                stockAcinsHdVO.setModDt(currentDt);

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    StockAcinsVO newSeqNoVO = new StockAcinsVO();
                    newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    newSeqNoVO.setAcinsDate(stockAcinsVO.getAcinsDate());
                    seqNo = stockAcinsMapper.getMaxSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(stockAcinsVO.getSeqNo()).equals("")) {
                stockAcinsVO.setSeqNo(Integer.parseInt(seqNo));
            }
            stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            stockAcinsVO.setRegId(sessionInfoVO.getUserId());
            stockAcinsVO.setRegDt(currentDt);
            stockAcinsVO.setModId(sessionInfoVO.getUserId());
            stockAcinsVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockAcinsVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockAcinsMapper.deleteAcinsDtl(stockAcinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockAcinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockAcinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockAcinsMapper.insertAcinsDtl(stockAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                        // DTL 수정
                        result = stockAcinsMapper.updateAcinsDtl(stockAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 실사 신규등록인 경우
        if(StringUtil.getOrBlank(stockAcinsHdVO.getSeqNo()).equals("")) {
            // HD 등록
            stockAcinsHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = stockAcinsMapper.insertAcinsHd(stockAcinsHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = stockAcinsMapper.updateAcinsHd(stockAcinsHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        System.out.println("재고실사 >>> 실사등록 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 재고실사 - 실사 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAcinsMapper.getProcFgCheck(stockAcinsVO);
    }

    /** 재고실사 - 실사등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getAcinsInfo(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        stockAcinsVO.setAreaFg(sessionInfoVO.getAreaFg());
        result = stockAcinsMapper.getSearchAcinsRegistList(stockAcinsVO);

        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }

    /** 재고실사 - 실사 상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAcinsDtlList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockAcinsVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAcinsMapper.getSearchAcinsDtlList(stockAcinsVO);
    }

    /** 재고실사 - 실사 상세 상품 저장 */
    @Override
    public int saveAcinsDtl(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고실사 >>> 실사상세 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockAcinsVO stockAcinsHdVO = new StockAcinsVO();
        String confirmFg = "";

        for (StockAcinsVO stockAcinsVO : stockAcinsVOS) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(stockAcinsVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    stockAcinsHdVO.setProcFg("1");
                }
                else {
                    stockAcinsHdVO.setProcFg("0");
                }
                stockAcinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockAcinsHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                    stockAcinsHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                stockAcinsHdVO.setAcinsDate(stockAcinsVO.getAcinsDate());
                stockAcinsHdVO.setAcinsTitle(stockAcinsVO.getAcinsTitle());
                stockAcinsHdVO.setAcinsReason(stockAcinsVO.getAcinsReason());
                stockAcinsHdVO.setSeqNo(stockAcinsVO.getSeqNo());
                stockAcinsHdVO.setStorageCd(stockAcinsVO.getStorageCd());
                stockAcinsHdVO.setAdjStorageCd(stockAcinsVO.getAdjStorageCd());
                stockAcinsHdVO.setRegId(sessionInfoVO.getUserId());
                stockAcinsHdVO.setRegDt(currentDt);
                stockAcinsHdVO.setModId(sessionInfoVO.getUserId());
                stockAcinsHdVO.setModDt(currentDt);
                stockAcinsHdVO.setStatus(stockAcinsVO.getStatus());
            }

            stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockAcinsVO.setRegId(sessionInfoVO.getUserId());
            stockAcinsVO.setRegDt(currentDt);
            stockAcinsVO.setModId(sessionInfoVO.getUserId());
            stockAcinsVO.setModDt(currentDt);

            System.out.println("재고실사 >>> 실사상세 팝업 >>> status : " + stockAcinsVO.getStatus());
            System.out.println("재고실사 >>> 실사상세 팝업 >>> acinsProdStatus : " + stockAcinsVO.getAcinsProdStatus());

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockAcinsVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockAcinsMapper.deleteAcinsDtl(stockAcinsVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockAcinsVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockAcinsVO.getAcinsProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockAcinsMapper.insertAcinsDtl(stockAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = stockAcinsMapper.updateAcinsDtl(stockAcinsVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        //삭제가 아닐경우만
//        if(!StringUtil.getOrBlank(stockAcinsHdVO.getStatus()).equals("DELETE")) {
            // HD 수정
            result = stockAcinsMapper.updateAcinsHd(stockAcinsHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }

        System.out.println("재고실사 >>> 실사상세 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    @Override
    public int deleteExcelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        stockAcinsVO.setSessionId(sessionInfoVO.getSessionId());
        stockAcinsVO.setRegId(sessionInfoVO.getUserId());
        stockAcinsVO.setRegDt(currentDt);
        stockAcinsVO.setModId(sessionInfoVO.getUserId());
        stockAcinsVO.setModDt(currentDt);

        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        result = stockAcinsMapper.deleteExcelUpload(stockAcinsVO);
//        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 엑셀업로드 - 엑셀업로드 TEMP 저장 */
    @Override
    public int saveExcelUpload(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int returnResult = 0;
        int i = 1;

        String currentDt = currentDateTimeString();

        for (StockAcinsVO stockAcinsVO : stockAcinsVOS) {
            stockAcinsVO.setSessionId(sessionInfoVO.getSessionId());
            stockAcinsVO.setRegId(sessionInfoVO.getUserId());
            stockAcinsVO.setRegDt(currentDt);
            stockAcinsVO.setModId(sessionInfoVO.getUserId());
            stockAcinsVO.setModDt(currentDt);
            stockAcinsVO.setSeq(i);

            stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            // 상품코드 or 바코드 "'" 문자 제거
            if (stockAcinsVO.getProdBarcdCd() != null && !"".equals(stockAcinsVO.getProdBarcdCd())) {
                if(stockAcinsVO.getProdBarcdCd().contains("'")) {
                    stockAcinsVO.setProdBarcdCd(stockAcinsVO.getProdBarcdCd().replaceAll("'",""));
                }
            }

            // 상품코드 "'" 문자 제거
            if (stockAcinsVO.getProdCd() != null && !"".equals(stockAcinsVO.getProdCd())) {
                if(stockAcinsVO.getProdCd().contains("'")) {
                    stockAcinsVO.setProdCd(stockAcinsVO.getProdCd().replaceAll("'",""));
                }
            }

            // 바코드 "'" 문자 제거
            if (stockAcinsVO.getBarcdCd() != null && !"".equals(stockAcinsVO.getBarcdCd())) {
                if(stockAcinsVO.getBarcdCd().contains("'")) {
                    stockAcinsVO.setBarcdCd(stockAcinsVO.getBarcdCd().replaceAll("'",""));
                }
            }

            result = stockAcinsMapper.insertExcelUpload(stockAcinsVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
            i++;
        }

        return returnResult;
    }

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    @Override
    public int saveUpdateProdCd(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        stockAcinsVO.setSessionId(sessionInfoVO.getSessionId());
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다.
        result = stockAcinsMapper.updateExcelUploadProdCd(stockAcinsVO);

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다.
        result = stockAcinsMapper.updateExcelUploadBarcdCd(stockAcinsVO);

        // 거래처 수불인 경우 UPRC 를 LAST_COST_UPRC 로 UPDATE 한다.
        if(stockAcinsVO.getUploadFg().equals("vendrOrder")) {
            result = stockAcinsMapper.updateExcelUploadUprc(stockAcinsVO);
        }

        return result;
    }

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    @Override
    public List<DefaultMap<String>> getExcelUploadErrInfoList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        stockAcinsVO.setSessionId(sessionInfoVO.getSessionId());
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return stockAcinsMapper.getExcelUploadErrInfoList(stockAcinsVO);
    }

    /** 재고실사 - 엑셀업로드 */
    @Override
    public int excelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        stockAcinsVO.setSessionId(sessionInfoVO.getSessionId());
        stockAcinsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsVO.setStoreCd(sessionInfoVO.getStoreCd());
        stockAcinsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockAcinsVO.setRegId(sessionInfoVO.getUserId());
        stockAcinsVO.setRegDt(currentDt);
        stockAcinsVO.setModId(sessionInfoVO.getUserId());
        stockAcinsVO.setModDt(currentDt);

        String seqNo = StringUtil.getOrBlank(stockAcinsVO.getSeqNo());
        String insFg = (seqNo.equals("") ? "I" : "U");
        if(!seqNo.equals("")) {
            // 수량추가인 경우
            if(StringUtil.getOrBlank(stockAcinsVO.getAddQtyFg()).equals("add")) {
//                result = stockAcinsMapper.updateExcelUploadAdjQty(stockAcinsVO);
            } else {
                // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
                result = stockAcinsMapper.deleteAcinsToExcelUploadData(stockAcinsVO);
            }

        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            StockAcinsVO newSeqNoVO = new StockAcinsVO();
            newSeqNoVO.setHqOfficeCd(stockAcinsVO.getHqOfficeCd());
            newSeqNoVO.setStoreCd(stockAcinsVO.getStoreCd());
            newSeqNoVO.setAcinsDate(stockAcinsVO.getDate());
            newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            seqNo = stockAcinsMapper.getMaxSeqNo(newSeqNoVO);
        }

        stockAcinsVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 실사수량으로 입력
        result = stockAcinsMapper.insertAcinsToExcelUploadData(stockAcinsVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = stockAcinsMapper.deleteExcelUploadCompleteData(stockAcinsVO);

        StockAcinsVO stockAcinsHdVO = new StockAcinsVO();
        stockAcinsHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAcinsHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            stockAcinsHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        stockAcinsHdVO.setAcinsDate(stockAcinsVO.getDate());
        stockAcinsHdVO.setAcinsTitle(stockAcinsVO.getTitle());
        stockAcinsHdVO.setAcinsReason(stockAcinsVO.getReason());
        stockAcinsHdVO.setSeqNo(Integer.parseInt(seqNo));
        stockAcinsHdVO.setProcFg("0");
        stockAcinsHdVO.setStorageCd("999");
        stockAcinsHdVO.setAdjStorageCd(stockAcinsVO.getAdjStorageCd());
        stockAcinsHdVO.setRegId(sessionInfoVO.getUserId());
        stockAcinsHdVO.setRegDt(currentDt);
        stockAcinsHdVO.setModId(sessionInfoVO.getUserId());
        stockAcinsHdVO.setModDt(currentDt);

        // 실사 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
           if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
                stockAcinsHdVO.setAdjStorageCd("001");
            }
            // HD 등록
            result = stockAcinsMapper.insertAcinsHd(stockAcinsHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
           if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
                stockAcinsHdVO.setAdjStorageCd("001");
            }
            // HD 수정
            result = stockAcinsMapper.updateAcinsHd(stockAcinsHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }
}
