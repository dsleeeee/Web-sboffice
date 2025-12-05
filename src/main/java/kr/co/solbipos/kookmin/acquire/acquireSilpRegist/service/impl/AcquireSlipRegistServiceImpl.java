package kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.AcquireSlipRegistService;
import kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.AcquireSlipRegistVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : AcquireSlipRegistServiceImpl.java
 * @Description : 국민대 > 매입관리 > 매입전표등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("AcquireSlipRegistService")
@Transactional
public class AcquireSlipRegistServiceImpl implements AcquireSlipRegistService {

    private final AcquireSlipRegistMapper acquireSlipRegistMapper;
    private final MessageService messageService;

    public AcquireSlipRegistServiceImpl(AcquireSlipRegistMapper acquireSlipRegistMapper, MessageService messageService) {
        this.acquireSlipRegistMapper = acquireSlipRegistMapper;
        this.messageService = messageService;
    }

    /** 매입전표등록 - 거래처 입고/반출 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchInOutStockList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return acquireSlipRegistMapper.getSearchInOutStockList(acquireSlipRegistVO);
    }

    /** 매입전표등록 - 입고/반출정보 상세 조회 */
    @Override
    public DefaultMap<String> getInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return acquireSlipRegistMapper.getInOutStockSlipInfo(acquireSlipRegistVO);
    }

    /** 매입전표등록 - 입고/반출정보 저장 */
    @Override
    public DefaultMap<String> saveInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;
        String currentDt = currentDateTimeString();

        acquireSlipRegistVO.setRegDt(currentDt);
        acquireSlipRegistVO.setRegId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setModDt(currentDt);
        acquireSlipRegistVO.setModId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 신규등록
        if(StringUtil.getOrBlank(acquireSlipRegistVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            acquireSlipRegistVO.setYymm(yymm);

            slipNo = acquireSlipRegistMapper.getNewSlipNo(acquireSlipRegistVO);

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사

                acquireSlipRegistVO.setSlipNo(slipNo);
                acquireSlipRegistVO.setProcFg("0");
                // 무발주입고 시
                if(StringUtil.getOrBlank(acquireSlipRegistVO.getInstockType()).equals("N")) {
                    acquireSlipRegistVO.setOrderSlipNo("");
                }
                // 등록
                result = acquireSlipRegistMapper.insertVendrInstock(acquireSlipRegistVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장

                acquireSlipRegistVO.setSlipNo(slipNo);
                acquireSlipRegistVO.setProcFg("0");
                result = acquireSlipRegistMapper.insertVendrInstock(acquireSlipRegistVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = acquireSlipRegistVO.getSlipNo();
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                // 수정
                result = acquireSlipRegistMapper.updateVendrInstock(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                // 수정
                result = acquireSlipRegistMapper.updateVendrInstock(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        // 저장 후 조회 시 필요
        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        resultMap.put("slipFg", String.valueOf(acquireSlipRegistVO.getSlipFg()));
        resultMap.put("vendrCd", String.valueOf(acquireSlipRegistVO.getVendrCd()));
        return resultMap;
    }

    /** 매입전표등록 - 입고/반출정보 삭제 */
    @Override
    public int deleteInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        acquireSlipRegistVO.setRegDt(currentDt);
        acquireSlipRegistVO.setRegId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setModDt(currentDt);
        acquireSlipRegistVO.setModId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        acquireSlipRegistVO.setStoreCd(sessionInfoVO.getStoreCd());

        String dtlProdExist = "N";

         /* 전표의 기존 진행상태를 조회하여, 정상로직인 경우만 전표삭제
        chkProcFg = 0 (등록) 인 경우만 전표삭제 가능 */
        String chkProcFg = acquireSlipRegistMapper.getInOutStockProcFg(acquireSlipRegistVO);

        if(chkProcFg.equals("0")) {

            // 입고/반출 삭제시 상품이 있는지 여부 조회
            dtlProdExist = acquireSlipRegistMapper.getInOutStockProdExist(acquireSlipRegistVO);
            if (dtlProdExist.equals("Y")) {
                throw new JsonException(Status.FAIL, messageService.get("acquireSlipRegist.dtl.prodExist"));
            }

            // 입고/반출정보 삭제
            result = acquireSlipRegistMapper.deleteInOutStockSlipInfo(acquireSlipRegistVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 입고/반출정보 상품 삭제
            result = acquireSlipRegistMapper.deleteInOutStockProdInfo(acquireSlipRegistVO);
            //if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }else{
            throw new JsonException(Status.FAIL, messageService.get("acquireSlipRegist.dtl.change.procFg"));
        }
        return result;
    }

    /** 매입전표등록 - 입고/반출정보 진행상태 변경 */
    @Override
    public int saveInOutStockProcFg(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procFg = StringUtil.getOrBlank(acquireSlipRegistVO.getProcFg());

        String currentDt = currentDateTimeString();

        acquireSlipRegistVO.setRegDt(currentDt);
        acquireSlipRegistVO.setRegId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setModDt(currentDt);
        acquireSlipRegistVO.setModId(sessionInfoVO.getUserId());
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        /* 전표의 기존 진행상태를 조회하여, 정상로직인 경우만 변경 처리
            procFg = 1 && chkProcFg = 0 인 경우만 확정 가능(등록 -> 확정)
            procFg = 0 && chkProcFg = 1 인 경우만 확정취소 가능(확정 -> 등록(확정취소)) */
        String chkProcFg = acquireSlipRegistMapper.getInOutStockProcFg(acquireSlipRegistVO);

        if((procFg.equals("1") && chkProcFg.equals("0")) || (procFg.equals("0") && chkProcFg.equals("1"))) {

            result = acquireSlipRegistMapper.updateInOutStockProcFg(acquireSlipRegistVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 발주번호가 있는 경우 (무발주 입고만 사용중)
//                if (!StringUtil.getOrBlank(acquireSlipRegistVO.getOrderSlipNo()).equals("")) {
//                    // 발주 DT 내역의 입고관련 정보 초기화
//                    result = acquireSlipRegistMapper.updateDefaultVendrOrderDtl(acquireSlipRegistVO);
//
//                    // 발주 DT 내역의 입고관련 정보 갱신
//                    result = acquireSlipRegistMapper.updateVendrInstockToOrderDtl(acquireSlipRegistVO);
//
//                    // 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성
//                    result = acquireSlipRegistMapper.insertVendrInstockToOrderDtl(acquireSlipRegistVO);
//
//                    // 발주 DT 내역의 집계정보 HD에 수정
//                    result = acquireSlipRegistMapper.updateVendrOrderDtlSumHd(acquireSlipRegistVO);
//
//                    // 발주 테이블의 진행구분 입고중으로 수정
//                    acquireSlipRegistVO.setProcFg("4");
//                    result = acquireSlipRegistMapper.updateVendrOrderProcFg(acquireSlipRegistVO);
//                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//                }

            String getOutStorageCd = "";

            // 확정
            if (procFg.equals("1")) {
                acquireSlipRegistVO.setConfmYn("1");
                acquireSlipRegistVO.setDelFg("N");
                // 거래처정산 입력
                result = acquireSlipRegistMapper.insertVendrExact(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                result = acquireSlipRegistMapper.deleteVendrInstockProdConfm(acquireSlipRegistVO);
                if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                // TB_PO_HQ_VENDR_INSTOCK_PROD 확정여부 'Y'
//                result = acquireSlipRegistMapper.saveVendrInstockProd(acquireSlipRegistVO);
                result = acquireSlipRegistMapper.mergeVendrInstockProdConfm(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 입고확정일 경우에만 입력한 원가단가 > 마스터의 최종판매단가에 update
                if (acquireSlipRegistVO.getSlipFg() == 1 && procFg.equals("1")) {
                    result = acquireSlipRegistMapper.updateLastCostUprc(acquireSlipRegistVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
            // 확정취소
            else if (procFg.equals("0")) {
                getOutStorageCd = acquireSlipRegistMapper.getOutStorageCd(acquireSlipRegistVO);

                acquireSlipRegistVO.setConfmYn("0");
                acquireSlipRegistVO.setDelFg("Y");
                acquireSlipRegistVO.setOutStorageCd(getOutStorageCd);

                // 거래처정산 삭제
                result = acquireSlipRegistMapper.deleteVendrExact(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_VENDR_INSTOCK_PROD 확정여부 'Y'
//              result = acquireSlipRegistMapper.saveVendrInstockProd(acquireSlipRegistVO);
                result = acquireSlipRegistMapper.mergeVendrInstockProdConfm(acquireSlipRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }else{
            throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.change.procFg"));
        }

        return result;
    }

    /** 매입전표등록 - 진행구분 조회 */
    @Override
    public DefaultMap<String> getChkProcFg(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return acquireSlipRegistMapper.getChkProcFg(acquireSlipRegistVO);
    }

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        String currentDay = currentDateString();
        acquireSlipRegistVO.setToday(currentDay);

        return acquireSlipRegistMapper.getSearchInOutStockProdList(acquireSlipRegistVO);
    }

    /** 매입전표등록 - 입고/반출상품 등록 리스트 저장 */
    @Override
    public int saveInOutStockProd(AcquireSlipRegistVO[] acquireSlipRegistVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        AcquireSlipRegistVO acquireSlipRegistHdVO = new AcquireSlipRegistVO();

        acquireSlipRegistHdVO.setRegDt(currentDt);
        acquireSlipRegistHdVO.setRegId(sessionInfoVO.getUserId());
        acquireSlipRegistHdVO.setModDt(currentDt);
        acquireSlipRegistHdVO.setModId(sessionInfoVO.getUserId());
        acquireSlipRegistHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        int i = 0;
        for (AcquireSlipRegistVO acquireSlipRegistVO : acquireSlipRegistVOs) {

            acquireSlipRegistVO.setRegDt(currentDt);
            acquireSlipRegistVO.setRegId(sessionInfoVO.getUserId());
            acquireSlipRegistVO.setModDt(currentDt);
            acquireSlipRegistVO.setModId(sessionInfoVO.getUserId());
            acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            acquireSlipRegistVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                acquireSlipRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // HD 저장을 위한 파라미터 세팅
            if (i == 0) {
                acquireSlipRegistHdVO.setSlipNo(acquireSlipRegistVO.getSlipNo());
            }

            String insFg = "";
            if(!StringUtil.getOrBlank(acquireSlipRegistVO.getInTotQty()).equals("")) {
                // 기주문수량이 있는 경우 수정
                if(acquireSlipRegistVO.getPrevInTotQty() != null) {
                    insFg = "U";
                    // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
                    if(acquireSlipRegistVO.getInTotQty() == 0 || acquireSlipRegistVO.getInTotQty() == null) {
                        // '입고상품'탭 화면에서 등록한 경우만 삭제가능
                        if("1".equals(acquireSlipRegistVO.getProdRegFg())) {
                            insFg = "D";
                        }
                    }
                }
                else if(acquireSlipRegistVO.getInTotQty() != null) {
                    insFg = "I";
                }

                int prevUnitQty  = (acquireSlipRegistVO.getPrevInUnitQty() == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInUnitQty()));
                int prevEtcQty   = (acquireSlipRegistVO.getPrevInEtcQty()  == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInEtcQty()));
                int prevTotQty  = (acquireSlipRegistVO.getPrevInTotQty() == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInTotQty()));

                Long prevAmt    = (acquireSlipRegistVO.getPrevInAmt()      == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInAmt()));
                Long prevVat    = (acquireSlipRegistVO.getPrevInVat()      == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInVat()));
                Long prevTot    = (acquireSlipRegistVO.getPrevInTot()      == null ? 0 : Math.abs(acquireSlipRegistVO.getPrevInTot()));

                if(!insFg.equals("D")) {
                    int slipFg       = acquireSlipRegistVO.getSlipFg();
                    int poUnitQty    = Math.abs(acquireSlipRegistVO.getPoUnitQty());

                    int unitQty      = (acquireSlipRegistVO.getInUnitQty()     == null ? 0 : Math.abs(acquireSlipRegistVO.getInUnitQty()));
                    int etcQty       = (acquireSlipRegistVO.getInEtcQty()      == null ? 0 : Math.abs(acquireSlipRegistVO.getInEtcQty()));


                    int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                    int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                    int orderTotQty  = ((poUnitQty * orderUnitQty) + orderEtcQty);
                    Long orderAmt    = (acquireSlipRegistVO.getInAmt()      == null ? 0 : prevAmt	+	Math.abs(acquireSlipRegistVO.getInAmt()))    * slipFg;
                    Long orderVat    = (acquireSlipRegistVO.getInVat()      == null ? 0 : prevVat	+	Math.abs(acquireSlipRegistVO.getInVat()))    * slipFg;
                    Long orderTot    = (acquireSlipRegistVO.getInTot()      == null ? 0 : prevTot	+	Math.abs(acquireSlipRegistVO.getInTot()))    * slipFg;

                    acquireSlipRegistVO.setPrevInUnitQty(prevUnitQty);
                    acquireSlipRegistVO.setPrevInEtcQty(prevEtcQty);
                    acquireSlipRegistVO.setPrevInTotQty(prevTotQty);
                    acquireSlipRegistVO.setPrevInAmt(prevAmt);
                    acquireSlipRegistVO.setPrevInVat(prevVat);
                    acquireSlipRegistVO.setPrevInTot(prevTot);

                    acquireSlipRegistVO.setInUnitQty(orderUnitQty);
                    acquireSlipRegistVO.setInEtcQty(orderEtcQty);
                    acquireSlipRegistVO.setInTotQty(orderTotQty);
                    acquireSlipRegistVO.setInAmt(orderAmt);
                    acquireSlipRegistVO.setInVat(orderVat);
                    acquireSlipRegistVO.setInTot(orderTot);
                }

                // 추가
                if(insFg.equals("I")) {
                    result = acquireSlipRegistMapper.insertVendrInstockDtl(acquireSlipRegistVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    acquireSlipRegistVO.setOccrFg				(acquireSlipRegistVO.getSlipFg()	== 1 ? "01" : "16"	);	//발생구분(03:매장입고)
                    acquireSlipRegistVO.setDelFg					("Y");
                }
                // 수정
                else if(insFg.equals("U")) {
                        result = acquireSlipRegistMapper.updateVendrInstockDtl(acquireSlipRegistVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        acquireSlipRegistVO.setOccrFg				(acquireSlipRegistVO.getSlipFg()	== 1 ? "01" : "16"	);	//발생구분(03:매장입고)
                        acquireSlipRegistVO.setDelFg					("Y");
                }
                // 삭제
                else if(insFg.equals("D")) {
                        result = acquireSlipRegistMapper.deleteVendrInstockDtl(acquireSlipRegistVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        acquireSlipRegistVO.setOccrFg				(acquireSlipRegistVO.getSlipFg()	== 1 ? "01" : "16"	);	//발생구분(03:매장입고)
                        acquireSlipRegistVO.setDelFg					("N");
                }
            }

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                if(!StringUtil.getOrBlank(acquireSlipRegistVO.getSplyUprc()).equals("")) {
                    // 상품 공급가 수정(기존 공급가와 다른 경우만 수정 된다.)
                    result = acquireSlipRegistMapper.updateProdSplyUprc(acquireSlipRegistVO);

                    // 매장공급가 동시저장 저장인 경우
                    if (StringUtil.getOrBlank(acquireSlipRegistVO.getStoreSplyFg()).equals("Y")) {
                        // 매장상품 공급가 수정(기존 공급가와 다른 경우만 수정 된다.)
                        result = acquireSlipRegistMapper.updateStProdSplyUprc(acquireSlipRegistVO);
                    }
                }
            }

            returnResult += result;
            i++;
        }

        // 입고/반출정보 DTL의 집계정보 HD에 수정
        result = acquireSlipRegistMapper.updateVendrInstockDtlSumHd(acquireSlipRegistHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }

    /** 매입전표등록 - 입고/반출상품 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSearchInOutStockRegList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        String currentDay = currentDateString();
        acquireSlipRegistVO.setToday(currentDay);

        return acquireSlipRegistMapper.getSearchInOutStockRegList(acquireSlipRegistVO);
    }

    /** 매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAcquireSelectStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {
        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setEmpNo(sessionInfoVO.getEmpNo());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = acquireSlipRegistMapper.getAcquireSelectStoreList(selectStoreVO);
        }

        return resultList;
    }

    /** 거래처 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAcquireSelectVendrList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO) {

        iostockCmmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return acquireSlipRegistMapper.getAcquireSelectVendrList(iostockCmmVO);
    }

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    @Override
    public int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setRegId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setRegDt(currentDt);
        excelUploadMPSVO.setModId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setModDt(currentDt);

        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        result = acquireSlipRegistMapper.deleteExcelUpload(excelUploadMPSVO);
//        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 엑셀업로드 - 엑셀업로드 TEMP 저장 */
    @Override
    public int saveExcelUpload(ExcelUploadMPSVO[] excelUploadMPSVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int returnResult = 0;

        String currentDt = currentDateTimeString();

        for (ExcelUploadMPSVO excelUploadMPSVO : excelUploadMPSVOs) {
            excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
            excelUploadMPSVO.setRegId(sessionInfoVO.getUserId());
            excelUploadMPSVO.setRegDt(currentDt);
            excelUploadMPSVO.setModId(sessionInfoVO.getUserId());
            excelUploadMPSVO.setModDt(currentDt);

            excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            // 상품코드 or 바코드 "'" 문자 제거
            if (excelUploadMPSVO.getProdBarcdCd() != null && !"".equals(excelUploadMPSVO.getProdBarcdCd())) {
                if(excelUploadMPSVO.getProdBarcdCd().contains("'")) {
                    excelUploadMPSVO.setProdBarcdCd(excelUploadMPSVO.getProdBarcdCd().replaceAll("'",""));
                }
            }

            // 상품코드 "'" 문자 제거
            if (excelUploadMPSVO.getProdCd() != null && !"".equals(excelUploadMPSVO.getProdCd())) {
                if(excelUploadMPSVO.getProdCd().contains("'")) {
                    excelUploadMPSVO.setProdCd(excelUploadMPSVO.getProdCd().replaceAll("'",""));
                }
            }

            // 바코드 "'" 문자 제거
            if (excelUploadMPSVO.getBarcdCd() != null && !"".equals(excelUploadMPSVO.getBarcdCd())) {
                if(excelUploadMPSVO.getBarcdCd().contains("'")) {
                    excelUploadMPSVO.setBarcdCd(excelUploadMPSVO.getBarcdCd().replaceAll("'",""));
                }
            }

            result = acquireSlipRegistMapper.insertExcelUpload(excelUploadMPSVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

        return returnResult;
    }

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    @Override
    public int saveUpdateProdCd(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다.
        result = acquireSlipRegistMapper.updateExcelUploadProdCd(excelUploadMPSVO);

        // 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다.
        result = acquireSlipRegistMapper.updateExcelUploadBarcdCd(excelUploadMPSVO);

        // 거래처 수불인 경우 UPRC 를 LAST_COST_UPRC 로 UPDATE 한다.
        if(excelUploadMPSVO.getUploadFg().equals("vendrOrder")) {
            result = acquireSlipRegistMapper.updateExcelUploadUprc(excelUploadMPSVO);
        }

        return result;
    }

    /** 매입전표등록 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadMPSVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadMPSVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadMPSVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadMPSVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadMPSVO.setRegId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setRegDt(currentDt);
        excelUploadMPSVO.setModId(sessionInfoVO.getUserId());
        excelUploadMPSVO.setModDt(currentDt);

        // 수량추가인 경우
        if(StringUtil.getOrBlank(excelUploadMPSVO.getAddQtyFg()).equals("add")) {
//            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
//                result = vendrInstockHqMapper.insertExcelUploadAddQty(excelUploadMPSVO);
//            }
//            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                result = vendrInstockStoreMapper.insertExcelUploadAddQty(excelUploadMPSVO);
//            }
        } else {
            // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                result = acquireSlipRegistMapper.deleteInOutStockToExcelUploadData(excelUploadMPSVO);
            }
        }

        // 엑셀업로드 한 수량을 입고수량으로 입력
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = acquireSlipRegistMapper.insertInOutStockToExcelUploadData(excelUploadMPSVO);
        }

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = acquireSlipRegistMapper.deleteExcelUploadCompleteData(excelUploadMPSVO);
        }

        AcquireSlipRegistVO acquireSlipRegistHdVO = new AcquireSlipRegistVO();
        acquireSlipRegistHdVO.setSlipNo(excelUploadMPSVO.getSlipNo());

        acquireSlipRegistHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        acquireSlipRegistHdVO.setStoreCd(sessionInfoVO.getStoreCd());
        acquireSlipRegistHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        acquireSlipRegistHdVO.setRegId(sessionInfoVO.getUserId());
        acquireSlipRegistHdVO.setRegDt(currentDt);
        acquireSlipRegistHdVO.setModId(sessionInfoVO.getUserId());
        acquireSlipRegistHdVO.setModDt(currentDt);


        // 입고/반출정보 DTL의 집계정보 HD에 수정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = acquireSlipRegistMapper.updateVendrInstockDtlSumHd(acquireSlipRegistHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 매입전표등록 - 반출서 반출정보 조회(반출처, 공급자 정보) */
    @Override
    public DefaultMap<String> getInOutStockInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();

        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = acquireSlipRegistMapper.getInOutStockInfo(acquireSlipRegistVO);
        }
        return result;
    }

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        acquireSlipRegistVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = acquireSlipRegistMapper.getInOutStockProdList(acquireSlipRegistVO);
        }
        return result;
    }
}
