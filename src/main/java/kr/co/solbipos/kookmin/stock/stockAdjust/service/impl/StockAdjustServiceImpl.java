package kr.co.solbipos.kookmin.stock.stockAdjust.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockAdjust.service.StockAdjustService;
import kr.co.solbipos.kookmin.stock.stockAdjust.service.StockAdjustVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : StockAdjustServiceImpl.java
 * @Description : 국민대 > 재고관리 > 재고조정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StockAdjustService")
@Transactional
public class StockAdjustServiceImpl implements StockAdjustService {

    private final StockAdjustMapper stockAdjustMapper;
    private final MessageService messageService;

    @Autowired
    public StockAdjustServiceImpl(StockAdjustMapper stockAdjustMapper, MessageService messageService) {
        this.stockAdjustMapper = stockAdjustMapper;
        this.messageService = messageService;
    }

    /** 사유값 */
    @Override
    public List<DefaultMap<String>> getAdjustReason(SessionInfoVO sessionInfoVO) {
        return stockAdjustMapper.getAdjustReason(sessionInfoVO);
    }

    /** 재고조정 - 재고조정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAdjustList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO) {
        stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAdjustMapper.getSearchAdjustList(stockAdjustVO);
    }

    /** 재고조정 - 조정 삭제 */
    @Override
    public int deleteAdjust(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO) {
        System.out.println("재고조정 >>> 조정 삭제 start");

        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (StockAdjustVO stockAdjustVO : stockAdjustVOs) {
            stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockAdjustVO.setRegId(sessionInfoVO.getUserId());
            stockAdjustVO.setRegDt(currentDt);
            stockAdjustVO.setModId(sessionInfoVO.getUserId());
            stockAdjustVO.setModDt(currentDt);

            System.out.println("재고조정 >>> 조정 삭제 >>> DTL count : " + stockAdjustVO.getDtlCnt());

            if(stockAdjustVO.getDtlCnt() > 0) {
                // DTL 삭제
                result = stockAdjustMapper.deleteAllAdjustDtl(stockAdjustVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // HD 삭제
            result = stockAdjustMapper.deleteAdjustHd(stockAdjustVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        System.out.println("재고조정 >>> 조정 삭제 end");

        return returnResult;
    }

    /** 재고조정 - 조정 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO) {
        stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAdjustMapper.getProcFgCheck(stockAdjustVO);
    }

    /** 재고조정 - 조정 상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAdjustDtlList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO) {
        stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAdjustMapper.getSearchAdjustDtlList(stockAdjustVO);
    }

    /** 재고조정 - 조정 상세 상품 저장 */
    @Override
    public int saveAdjustDtl(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO) {
        System.out.println("재고조정 >>> 조정상세 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockAdjustVO stockAdjustHdVO = new StockAdjustVO();
        String confirmFg = "";

        for (StockAdjustVO stockAdjustVO : stockAdjustVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(stockAdjustVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    stockAdjustHdVO.setProcFg("1");
                }
                else {
                    stockAdjustHdVO.setProcFg("0");
                }
                stockAdjustHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockAdjustHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    stockAdjustHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                stockAdjustHdVO.setAdjDate(stockAdjustVO.getAdjDate());
                stockAdjustHdVO.setAdjTitle(stockAdjustVO.getAdjTitle());
                stockAdjustHdVO.setAdjReason(stockAdjustVO.getAdjReason());
                stockAdjustHdVO.setSeqNo(stockAdjustVO.getSeqNo());
                stockAdjustHdVO.setStorageCd(stockAdjustVO.getStorageCd());
                stockAdjustHdVO.setAdjStorageCd(stockAdjustVO.getAdjStorageCd());
                stockAdjustHdVO.setRegId(sessionInfoVO.getUserId());
                stockAdjustHdVO.setRegDt(currentDt);
                stockAdjustHdVO.setModId(sessionInfoVO.getUserId());
                stockAdjustHdVO.setModDt(currentDt);
                stockAdjustHdVO.setStatus(stockAdjustVO.getStatus());
            }

            stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockAdjustVO.setRegId(sessionInfoVO.getUserId());
            stockAdjustVO.setRegDt(currentDt);
            stockAdjustVO.setModId(sessionInfoVO.getUserId());
            stockAdjustVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockAdjustVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockAdjustMapper.deleteAdjustDtl(stockAdjustVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockAdjustVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockAdjustVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockAdjustMapper.insertAdjustDtl(stockAdjustVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = stockAdjustMapper.updateAdjustDtl(stockAdjustVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        //삭제가 아닐경우만
//        if(!StringUtil.getOrBlank(stockAdjustHdVO.getStatus()).equals("DELETE")) {
        // HD 수정
        result = stockAdjustMapper.updateAdjustHd(stockAdjustHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }

        System.out.println("재고조정 >>> 조정상세 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 재고조정 - 조정등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchAdjustRegistList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO) {
        stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockAdjustVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockAdjustMapper.getSearchAdjustRegistList(stockAdjustVO);
    }

    /** 재고조정 - 조정상품 저장 */
    @Override
    public int saveAdjustRegist(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO) {
        System.out.println("재고조정 >>> 조정등록 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockAdjustVO stockAdjustHdVO = new StockAdjustVO();
        String seqNo = "";

        for (StockAdjustVO stockAdjustVO : stockAdjustVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(stockAdjustVO.getSeqNo());
                stockAdjustHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockAdjustHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                    stockAdjustHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                stockAdjustHdVO.setAdjDate(stockAdjustVO.getAdjDate());
                stockAdjustHdVO.setAdjTitle(stockAdjustVO.getAdjTitle());
                stockAdjustHdVO.setAdjReason(stockAdjustVO.getAdjReason());
                stockAdjustHdVO.setSeqNo(stockAdjustVO.getSeqNo());
                stockAdjustHdVO.setProcFg("0");
                stockAdjustHdVO.setAdjStorageCd(stockAdjustVO.getAdjStorageCd());
                stockAdjustHdVO.setStorageCd(stockAdjustVO.getStorageCd());
                stockAdjustHdVO.setRegId(sessionInfoVO.getUserId());
                stockAdjustHdVO.setRegDt(currentDt);
                stockAdjustHdVO.setModId(sessionInfoVO.getUserId());
                stockAdjustHdVO.setModDt(currentDt);
                stockAdjustHdVO.setAdjStorageCd(stockAdjustVO.getAdjStorageCd());
                stockAdjustHdVO.setVendrCd(stockAdjustVO.getVendrCd());
                stockAdjustHdVO.setStoreCd(stockAdjustVO.getStoreCd());

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    StockAdjustVO newSeqNoVO = new StockAdjustVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                        newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    }
                    newSeqNoVO.setAdjDate(stockAdjustVO.getAdjDate());
                    seqNo = stockAdjustMapper.getMaxSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(stockAdjustVO.getSeqNo()).equals("")) {
                stockAdjustVO.setSeqNo(Integer.parseInt(seqNo));
            }
            stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockAdjustVO.setRegId(sessionInfoVO.getUserId());
            stockAdjustVO.setRegDt(currentDt);
            stockAdjustVO.setModId(sessionInfoVO.getUserId());
            stockAdjustVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockAdjustVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockAdjustMapper.deleteAdjustDtl(stockAdjustVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockAdjustVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockAdjustVO.getAdjProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockAdjustMapper.insertAdjustDtl(stockAdjustVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = stockAdjustMapper.updateAdjustDtl(stockAdjustVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 조정 신규등록인 경우
        if(StringUtil.getOrBlank(stockAdjustHdVO.getSeqNo()).equals("")) {
            // HD 등록
            stockAdjustHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = stockAdjustMapper.insertAdjustHd(stockAdjustHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = stockAdjustMapper.updateAdjustHd(stockAdjustHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        System.out.println("재고조정 >>> 조정등록 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 재고조정 - 조정등록시 상품정보 조회 */
    @Override
    public DefaultMap<String> getAdjustInfo(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        stockAdjustVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockAdjustVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockAdjustVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        result = stockAdjustMapper.getSearchAdjustRegistList(stockAdjustVO);

        DefaultMap<String> returnData = null;
        if(!result.isEmpty()) {
            returnData = result.get(0);
        }
        return returnData;
    }

    /** 재고조정 - 엑셀업로드 */
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
//                result = stockAdjustMapper.insertExcelUploadAddQty(stockAcinsVO);
            } else {
                // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
                result = stockAdjustMapper.deleteAdjustToExcelUploadData(stockAcinsVO);
            }

        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            StockAdjustVO newSeqNoVO = new StockAdjustVO();
            newSeqNoVO.setHqOfficeCd(stockAcinsVO.getHqOfficeCd());
            newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                newSeqNoVO.setStoreCd(stockAcinsVO.getStoreCd());
            }
            newSeqNoVO.setAdjDate(stockAcinsVO.getDate());
            seqNo = stockAdjustMapper.getMaxSeqNo(newSeqNoVO);
        }

        stockAcinsVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 조정수량으로 입력
        result = stockAdjustMapper.insertAdjustToExcelUploadData(stockAcinsVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = stockAdjustMapper.deleteExcelUploadCompleteData(stockAcinsVO);

        StockAdjustVO stockAdjustHdVO = new StockAdjustVO();
        stockAdjustHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockAdjustHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            stockAdjustHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        stockAdjustHdVO.setAdjDate(stockAcinsVO.getDate());
        stockAdjustHdVO.setAdjTitle(stockAcinsVO.getTitle());
        stockAdjustHdVO.setAdjReason(stockAcinsVO.getReason());
        stockAdjustHdVO.setSeqNo(Integer.parseInt(seqNo));
        stockAdjustHdVO.setProcFg("0");
        stockAdjustHdVO.setStorageCd("999");
        stockAdjustHdVO.setAdjStorageCd(stockAcinsVO.getAdjStorageCd());
        stockAdjustHdVO.setRegId(sessionInfoVO.getUserId());
        stockAdjustHdVO.setRegDt(currentDt);
        stockAdjustHdVO.setModId(sessionInfoVO.getUserId());
        stockAdjustHdVO.setModDt(currentDt);

        // 조정 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
            // HD 등록
            result = stockAdjustMapper.insertAdjustHd(stockAdjustHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = stockAdjustMapper.updateAdjustHd(stockAdjustHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }
}
