package kr.co.solbipos.iostock.vendr.vendrOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderService;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("vendrOrderService")
@Transactional
public class VendrOrderServiceImpl implements VendrOrderService {
    private final VendrOrderMapper vendrOrderMapper;
    private final MessageService messageService;

    @Autowired
    public VendrOrderServiceImpl(VendrOrderMapper vendrOrderMapper, MessageService messageService) {
        this.vendrOrderMapper = vendrOrderMapper;
        this.messageService = messageService;
    }

    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getVendrOrderList(vendrOrderVO);
    }


    /** 거래처 발주등록 - 발주정보 상세 조회 */
    @Override
    public DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getSlipInfo(vendrOrderVO);
    }


    /** 거래처 발주등록 - 발주정보 저장 */
    @Override
    public DefaultMap<String> saveVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        // 신규등록
        if(StringUtil.getOrBlank(vendrOrderVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            VendrOrderVO maxSlipNoVO = new VendrOrderVO();
            maxSlipNoVO.setYymm(yymm);

            // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
            maxSlipNoVO = setSessionValue(maxSlipNoVO, sessionInfoVO, null);

            slipNo = vendrOrderMapper.getNewSlipNo(maxSlipNoVO);

            vendrOrderVO.setSlipNo(slipNo);
            vendrOrderVO.setProcFg("0");
            result = vendrOrderMapper.insertVendrOrderHd(vendrOrderVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = vendrOrderVO.getSlipNo();
            result = vendrOrderMapper.updateVendrOrderHd(vendrOrderVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        resultMap.put("slipFg", String.valueOf(vendrOrderVO.getSlipFg()));
        resultMap.put("vendrCd", String.valueOf(vendrOrderVO.getVendrCd()));
        return resultMap;
    }


    /** 거래처 발주등록 - 발주정보 삭제 */
    @Override
    public int deleteVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        String dtlProdExist = "N";
        dtlProdExist = vendrOrderMapper.getDtlProdExist(vendrOrderVO);
        if(dtlProdExist.equals("Y")) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("vendrOrder.dtl.prodExist"));
        }

        result = vendrOrderMapper.deleteVendrOrderHd(vendrOrderVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 거래처 발주등록 - 발주정보 진행상태 변경 */
    @Override
    public int saveProcFg(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        String vendrInstockExist = "N";
        vendrInstockExist = vendrOrderMapper.getVendrInstockExist(vendrOrderVO);
        if(vendrInstockExist.equals("Y")) {
            String errMsg = (vendrOrderVO.getProcFg().equals("5") ? messageService.get("vendrOrder.dtl.noConfmExist") : messageService.get("vendrOrder.dtl.regExist"));
            throw new JsonException(Status.SERVER_ERROR, errMsg);
        }

        result = vendrOrderMapper.updateProcFg(vendrOrderVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 거래처 발주등록 - 발주상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderProdList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getVendrOrderProdList(vendrOrderVO);
    }


    /** 거래처 발주등록 - 진행구분 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getProcFgCheck(vendrOrderVO);
    }


    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderProdRegList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getVendrOrderProdRegList(vendrOrderVO);
    }


    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 저장 */
    @Override
    public int saveVendrOrderProdReg(VendrOrderVO[] vendrOrderVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        VendrOrderVO vendrOrderHdVO = new VendrOrderVO();

        int i = 0;
        for (VendrOrderVO vendrOrderVO : vendrOrderVOs) {
            // HD 저장을 위한 파라미터 세팅
            if (i == 0) {
                // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
                vendrOrderHdVO = setSessionValue(vendrOrderVO, sessionInfoVO, currentDt);

                vendrOrderHdVO.setSlipNo(vendrOrderVO.getSlipNo());
            }

            // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
            vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, currentDt);
            
            String vendrChkProcFg = "N";
            vendrChkProcFg = vendrOrderMapper.getChkProcFg(vendrOrderVO);
            if(vendrChkProcFg.equals("Y")) {
                String errMsg = messageService.get("vendrOrder.dtl.procFgChkMsg");
                throw new JsonException(Status.SERVER_ERROR, errMsg);
            }
            
            String insFg = "";
            int orderTotQty  = vendrOrderVO.getOrderTotQty()   == null ? 0 : vendrOrderVO.getOrderTotQty();
            
            // 기주문수량이 있는 경우 수정
            if(vendrOrderVO.getPrevOrderTotQty() != null) {
                insFg = "U";
                // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
//                if(vendrOrderVO.getOrderTotQty() == 0 || vendrOrderVO.getOrderTotQty() == null) {
                if(orderTotQty == 0) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = vendrOrderVO.getSlipFg();
                int poUnitQty    = vendrOrderVO.getPoUnitQty();
                int prevUnitQty  = (vendrOrderVO.getPrevOrderUnitQty() == null ? 0 : vendrOrderVO.getPrevOrderUnitQty());
                int prevEtcQty   = (vendrOrderVO.getPrevOrderEtcQty()  == null ? 0 : vendrOrderVO.getPrevOrderEtcQty());
                int unitQty      = (vendrOrderVO.getOrderUnitQty()     == null ? 0 : vendrOrderVO.getOrderUnitQty());
                int etcQty       = (vendrOrderVO.getOrderEtcQty()      == null ? 0 : vendrOrderVO.getOrderEtcQty());
                int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                	orderTotQty  = (vendrOrderVO.getOrderTotQty()   == null ? 0 : vendrOrderVO.getOrderTotQty()) * slipFg;
                Long orderAmt    = (vendrOrderVO.getOrderAmt()      == null ? 0 : vendrOrderVO.getOrderAmt())    * slipFg;
                Long orderVat    = (vendrOrderVO.getOrderVat()      == null ? 0 : vendrOrderVO.getOrderVat())    * slipFg;
                Long orderTot    = (vendrOrderVO.getOrderTot()      == null ? 0 : vendrOrderVO.getOrderTot())    * slipFg;

                vendrOrderVO.setOrderUnitQty(orderUnitQty);
                vendrOrderVO.setOrderEtcQty(orderEtcQty);
                vendrOrderVO.setOrderTotQty(orderTotQty);
                vendrOrderVO.setOrderAmt(orderAmt);
                vendrOrderVO.setOrderVat(orderVat);
                vendrOrderVO.setOrderTot(orderTot);
            }

            // 추가
            if(insFg.equals("I")) {
                result = vendrOrderMapper.insertVendrOrderDtl(vendrOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 수정
            else if(insFg.equals("U")) {
                result = vendrOrderMapper.updateVendrOrderDtl(vendrOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 삭제
            else if(insFg.equals("D")) {
                result = vendrOrderMapper.deleteVendrOrderDtl(vendrOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
            i++;
        }

        // 발주정보 DTL의 집계정보 HD에 수정
        result = vendrOrderMapper.updateVendrOrderDtlSumHd(vendrOrderHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }


    /** 거래처 발주등록 - 발주서 발주정보 조회(발주처, 공급자 정보) */
    @Override
    public DefaultMap<String> getVendrOrderReportInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrOrderVO = setSessionValue(vendrOrderVO, sessionInfoVO, null);

        return vendrOrderMapper.getVendrOrderReportInfo(vendrOrderVO);
    }


    /** 거래처 발주등록 - 엑셀업로드 */
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
            result = vendrOrderMapper.insertExcelUploadAddQty(excelUploadMPSVO);
        }

        // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
        result = vendrOrderMapper.deleteVendrOrderToExcelUploadData(excelUploadMPSVO);

        // 엑셀업로드 한 수량을 발주수량으로 입력
        result = vendrOrderMapper.insertVendrOrderToExcelUploadData(excelUploadMPSVO);

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = vendrOrderMapper.deleteExcelUploadCompleteData(excelUploadMPSVO);

        VendrOrderVO vendrOrderHdVO = new VendrOrderVO();
        vendrOrderHdVO.setSlipNo(excelUploadMPSVO.getSlipNo());

        /** regId, regDt, modId, modDt, hqOfficd, storeCd, orgnFg 세팅  */
        vendrOrderHdVO = setSessionValue(vendrOrderHdVO, sessionInfoVO, currentDt);

        // 발주정보 DTL의 집계정보 HD에 수정
        result = vendrOrderMapper.updateVendrOrderDtlSumHd(vendrOrderHdVO);

        return result;
    }


    /** regId, regDt, modId, modDt, hqOfficd, storeCd, orgnFg 세팅  */
    public VendrOrderVO setSessionValue(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO, String currentDt) {
        if(StringUtil.getOrBlank(currentDt).equals("")) {
            currentDt = currentDateTimeString();
        }

        vendrOrderVO.setRegId(sessionInfoVO.getUserId());
        vendrOrderVO.setRegDt(currentDt);
        vendrOrderVO.setModId(sessionInfoVO.getUserId());
        vendrOrderVO.setModDt(currentDt);

        vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        vendrOrderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return vendrOrderVO;
    }

    /** 거래처 발주타입관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getVendrOrderTypeCdList(CdVO cdVO, SessionInfoVO sessionInfoVO) {

        cdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            cdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            cdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return vendrOrderMapper.getVendrOrderTypeCdList(cdVO);
    }

    /** 거래처 발주타입관리 - 저장 */
    @Override
    public int saveVendrOrderTypeCdList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdVO cdVO : cdVOs) {

            cdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdVO.setRegDt(currentDt);
            cdVO.setRegId(sessionInfoVO.getUserId());
            cdVO.setModDt(currentDt);
            cdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdVO.getStatus() == GridDataFg.INSERT ) {
                result += vendrOrderMapper.insertVendrOrderTypeCd(cdVO);
                // 수정
            } else if ( cdVO.getStatus() == GridDataFg.UPDATE ) {
                result += vendrOrderMapper.updateVendrOrderTypeCd(cdVO);
                // 삭제
            } else if ( cdVO.getStatus() == GridDataFg.DELETE ) {
                result += vendrOrderMapper.deleteVendrOrderTypeCd(cdVO);
            }
        }

        if ( result == cdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
