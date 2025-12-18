package kr.co.solbipos.kookmin.stock.stockDisuse.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockDisuse.service.StockDisuseService;
import kr.co.solbipos.kookmin.stock.stockDisuse.service.StockDisuseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : StockDisuseServiceImpl.java
 * @Description : 국민대 > 재고관리 > 재고폐기
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StockDisuseService")
@Transactional
public class StockDisuseServiceImpl implements StockDisuseService {

    private final StockDisuseMapper stockDisuseMapper;
    private final MessageService messageService;

    @Autowired
    public StockDisuseServiceImpl(StockDisuseMapper stockDisuseMapper, MessageService messageService) {
        this.stockDisuseMapper = stockDisuseMapper;
        this.messageService = messageService;
    }

    /** 사유값 */
    @Override
    public List<DefaultMap<String>> getDisuseReason(SessionInfoVO sessionInfoVO) {
        return stockDisuseMapper.getDisuseReason(sessionInfoVO);
    }

    /** 재고폐기 - 재고폐기 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchStockDisuseList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO) {
        stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockDisuseMapper.getSearchStockDisuseList(stockDisuseVO);
    }

    /** 재고폐기 - 폐기 삭제 */
    @Override
    public int deleteDisuse(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고폐기 >>> 폐기 삭제 start");

        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (StockDisuseVO stockDisuseVO : stockDisuseVOS) {
            stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            stockDisuseVO.setRegId(sessionInfoVO.getUserId());
            stockDisuseVO.setRegDt(currentDt);
            stockDisuseVO.setModId(sessionInfoVO.getUserId());
            stockDisuseVO.setModDt(currentDt);

            System.out.println("재고폐기 >>> 폐기 삭제 >>> DTL count : " + stockDisuseVO.getDtlCnt());

            if(stockDisuseVO.getDtlCnt() > 0) {
                // DTL 삭제
                result = stockDisuseMapper.deleteAllDisuseDtl(stockDisuseVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // HD 삭제
            result = stockDisuseMapper.deleteDisuseHd(stockDisuseVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        System.out.println("재고폐기 >>> 폐기 삭제 end");

        return returnResult;
    }

    /** 재고폐기 - 폐기 진행구분 및 제목 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO) {
        stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockDisuseMapper.getProcFgCheck(stockDisuseVO);
    }

    /** 재고폐기 - 폐기등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchDisuseRegistList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO) {
        stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockDisuseVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockDisuseMapper.getSearchDisuseRegistList(stockDisuseVO);
    }

    /** 재고폐기 - 폐기상품 저장 */
    @Override
    public int saveDisuse(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고폐기 >>> 폐기등록 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockDisuseVO stockDisuseHdVO = new StockDisuseVO();
        String seqNo = "";

        for (StockDisuseVO stockDisuseVO : stockDisuseVOS) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                seqNo = StringUtil.getOrBlank(stockDisuseVO.getSeqNo());
                stockDisuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockDisuseHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                stockDisuseHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                stockDisuseHdVO.setDisuseDate(stockDisuseVO.getDisuseDate());
                stockDisuseHdVO.setDisuseTitle(stockDisuseVO.getDisuseTitle());
                stockDisuseHdVO.setDisuseReason(stockDisuseVO.getDisuseReason());
                stockDisuseHdVO.setSeqNo(stockDisuseVO.getSeqNo());
                stockDisuseHdVO.setProcFg("0");
                stockDisuseHdVO.setDisuseStorageCd(stockDisuseVO.getDisuseStorageCd());
                stockDisuseHdVO.setStorageCd(stockDisuseVO.getStorageCd());
                stockDisuseHdVO.setRegId(sessionInfoVO.getUserId());
                stockDisuseHdVO.setRegDt(currentDt);
                stockDisuseHdVO.setModId(sessionInfoVO.getUserId());
                stockDisuseHdVO.setModDt(currentDt);
                // 매장, 거래처 추가
                stockDisuseHdVO.setStoreCd(stockDisuseVO.getStoreCd());
                stockDisuseHdVO.setVendrCd(stockDisuseVO.getVendrCd());

                // 신규등록인 경우
                if(seqNo.equals("")) {
                    // 신규 seq 조회
                    StockDisuseVO newSeqNoVO = new StockDisuseVO();
                    newSeqNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                    newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                    newSeqNoVO.setStoreCd(sessionInfoVO.getStoreCd());
                    newSeqNoVO.setDisuseDate(stockDisuseVO.getDisuseDate());
                    seqNo = stockDisuseMapper.getMaxSeqNo(newSeqNoVO);
                }
            }

            // 신규등록인 경우 조회 해온 신규 seq 를 적용
            if(StringUtil.getOrBlank(stockDisuseVO.getSeqNo()).equals("")) {
                stockDisuseVO.setSeqNo(Integer.parseInt(seqNo));
            }
            stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            stockDisuseVO.setRegId(sessionInfoVO.getUserId());
            stockDisuseVO.setRegDt(currentDt);
            stockDisuseVO.setModId(sessionInfoVO.getUserId());
            stockDisuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockDisuseVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockDisuseMapper.deleteDisuseDtl(stockDisuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockDisuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockDisuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockDisuseMapper.insertDisuseDtl(stockDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = stockDisuseMapper.updateDisuseDtl(stockDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 폐기 신규등록인 경우
        if(StringUtil.getOrBlank(stockDisuseHdVO.getSeqNo()).equals("")) {
            // HD 등록
            stockDisuseHdVO.setSeqNo(Integer.parseInt(seqNo));
            result = stockDisuseMapper.insertDisuseHd(stockDisuseHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            // HD 수정
            result = stockDisuseMapper.updateDisuseHd(stockDisuseHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        System.out.println("재고폐기 >>> 폐기등록 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 재고폐기 - 폐기 상세 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchDisuseDtlList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO) {
        stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockDisuseVO.setAreaFg(sessionInfoVO.getAreaFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return stockDisuseMapper.getSearchDisuseDtlList(stockDisuseVO);
    }

    /** 재고폐기 - 폐기 상세 상품 저장 */
    @Override
    public int saveDisuseDtl(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO) {
        System.out.println("재고폐기 >>> 폐기상세 팝업 >>> 저장 start");

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StockDisuseVO stockDisuseHdVO = new StockDisuseVO();
        String confirmFg = "";

        for (StockDisuseVO stockDisuseVO : stockDisuseVOS) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(stockDisuseVO.getConfirmFg());
                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    stockDisuseHdVO.setProcFg("1");
                }
                else {
                    stockDisuseHdVO.setProcFg("0");
                }
                stockDisuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                stockDisuseHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                stockDisuseHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                stockDisuseHdVO.setDisuseDate(stockDisuseVO.getDisuseDate());
                stockDisuseHdVO.setDisuseTitle(stockDisuseVO.getDisuseTitle());
                stockDisuseHdVO.setDisuseReason(stockDisuseVO.getDisuseReason());
                stockDisuseHdVO.setSeqNo(stockDisuseVO.getSeqNo());
                stockDisuseHdVO.setStorageCd(stockDisuseVO.getStorageCd());
                stockDisuseHdVO.setDisuseStorageCd(stockDisuseVO.getDisuseStorageCd());
                stockDisuseHdVO.setRegId(sessionInfoVO.getUserId());
                stockDisuseHdVO.setRegDt(currentDt);
                stockDisuseHdVO.setModId(sessionInfoVO.getUserId());
                stockDisuseHdVO.setModDt(currentDt);
                stockDisuseHdVO.setStatus(stockDisuseVO.getStatus());
            }

            stockDisuseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            stockDisuseVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            stockDisuseVO.setStoreCd(sessionInfoVO.getStoreCd());
            stockDisuseVO.setRegId(sessionInfoVO.getUserId());
            stockDisuseVO.setRegDt(currentDt);
            stockDisuseVO.setModId(sessionInfoVO.getUserId());
            stockDisuseVO.setModDt(currentDt);

            // 상품 삭제인 경우
            if(StringUtil.getOrBlank(stockDisuseVO.getStatus()).equals("DELETE")) {
                // DTL 삭제
                result = stockDisuseMapper.deleteDisuseDtl(stockDisuseVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 상품 수정인 경우
            else if(StringUtil.getOrBlank(stockDisuseVO.getStatus()).equals("UPDATE")) {
                // 그리드 조회시 상품의 상태(등록 되어있지 않은 경우 I, 이미 등록된 경우 U)
                String acinsProdStatus = StringUtil.getOrBlank(stockDisuseVO.getDisuseProdStatus());
                if(acinsProdStatus.equals("I")) {
                    // DTL 등록
                    result = stockDisuseMapper.insertDisuseDtl(stockDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(acinsProdStatus.equals("U")) {
                    // DTL 수정
                    result = stockDisuseMapper.updateDisuseDtl(stockDisuseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        //삭제가 아닐경우만
//        if(!StringUtil.getOrBlank(stockDisuseHdVO.getStatus()).equals("DELETE")) {
        // HD 수정
        result = stockDisuseMapper.updateDisuseHd(stockDisuseHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//        }

        System.out.println("재고폐기 >>> 폐기상세 팝업 >>> 저장 end");

        return returnResult;
    }

    /** 재고폐기 - 엑셀업로드 */
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
//                result = disuseMapper.insertExcelUploadAddQty(stockAcinsVO);
            } else {
                // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
                result = stockDisuseMapper.deleteDisuseToExcelUploadData(stockAcinsVO);
            }

        }

        // 신규등록인 경우
        if(seqNo.equals("")) {
            // 신규 seq 조회
            StockDisuseVO newSeqNoVO = new StockDisuseVO();
            newSeqNoVO.setHqOfficeCd(stockAcinsVO.getHqOfficeCd());
            newSeqNoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                newSeqNoVO.setStoreCd(stockAcinsVO.getStoreCd());
            }
            newSeqNoVO.setDisuseDate(stockAcinsVO.getDate());
            seqNo = stockDisuseMapper.getMaxSeqNo(newSeqNoVO);
        }

        stockAcinsVO.setSeqNo(Integer.parseInt(seqNo));
        // 엑셀업로드 한 수량을 폐기수량으로 입력
        result = stockDisuseMapper.insertDisuseToExcelUploadData(stockAcinsVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = stockDisuseMapper.deleteExcelUploadCompleteData(stockAcinsVO);

        StockDisuseVO stockDisuseHdVO = new StockDisuseVO();
        stockDisuseHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        stockDisuseHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            stockDisuseHdVO.setStoreCd(stockAcinsVO.getStoreCd());
        }
        stockDisuseHdVO.setDisuseDate(stockAcinsVO.getDate());
        stockDisuseHdVO.setDisuseTitle(stockAcinsVO.getTitle());
        stockDisuseHdVO.setDisuseReason(stockAcinsVO.getReason());
        stockDisuseHdVO.setSeqNo(Integer.parseInt(seqNo));
        stockDisuseHdVO.setProcFg("0");
        stockDisuseHdVO.setStorageCd("999");
        stockDisuseHdVO.setDisuseStorageCd(stockAcinsVO.getDisuseStorageCd());
        stockDisuseHdVO.setRegId(sessionInfoVO.getUserId());
        stockDisuseHdVO.setRegDt(currentDt);
        stockDisuseHdVO.setModId(sessionInfoVO.getUserId());
        stockDisuseHdVO.setModDt(currentDt);

        // 폐기 신규등록인 경우
        if(StringUtil.getOrBlank(insFg).equals("I")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 등록
                stockDisuseHdVO.setDisuseStorageCd("001");
            }
            result = stockDisuseMapper.insertDisuseHd(stockDisuseHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // HD 수정
                stockDisuseHdVO.setDisuseStorageCd("001");
            }
            result = stockDisuseMapper.updateDisuseHd(stockDisuseHdVO);
            if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }
}
