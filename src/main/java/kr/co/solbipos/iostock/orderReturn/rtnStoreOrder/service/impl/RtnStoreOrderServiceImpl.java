package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.impl.DstbCloseStoreMapper;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.iostock.order.outstockData.service.impl.OutstockDataMapper;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderDtlVO;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderService;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnStoreOrderService")
public class RtnStoreOrderServiceImpl implements RtnStoreOrderService {
    private final RtnStoreOrderMapper rtnStoreOrderMapper;
    private final DstbCloseStoreMapper dstbCloseStoreMapper;
    private final OutstockDataMapper outstockDataMapper;
    private final MessageService messageService;

    @Autowired
    public RtnStoreOrderServiceImpl(RtnStoreOrderMapper rtnStoreOrderMapper, DstbCloseStoreMapper dstbCloseStoreMapper, OutstockDataMapper outstockDataMapper, MessageService messageService) {
        this.rtnStoreOrderMapper = rtnStoreOrderMapper;
        this.dstbCloseStoreMapper = dstbCloseStoreMapper;
        this.outstockDataMapper = outstockDataMapper;
        this.messageService = messageService;
    }

    /** 반품등록 HD 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnStoreOrderList(RtnStoreOrderVO rtnStoreOrderVO) {
        return rtnStoreOrderMapper.getRtnStoreOrderList(rtnStoreOrderVO);
    }

    /** 반품등록 DT 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnStoreOrderDtlList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO) {
        return rtnStoreOrderMapper.getRtnStoreOrderDtlList(rtnStoreOrderDtlVO);
    }

    /** 반품등록 상품추가 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnStoreOrderRegistList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO) {
        return rtnStoreOrderMapper.getRtnStoreOrderRegistList(rtnStoreOrderDtlVO);
    }

    /** 반품등록 반품상품 저장 */
    @Override
    public int saveRtnStoreOrderRegist(RtnStoreOrderDtlVO[] rtnStoreOrderDtlVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        RtnStoreOrderVO rtnStoreOrderVO = new RtnStoreOrderVO();

        int i = 0;
        for (RtnStoreOrderDtlVO rtnStoreOrderDtlVO : rtnStoreOrderDtlVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                rtnStoreOrderVO.setReqDate(rtnStoreOrderDtlVO.getReqDate());
                rtnStoreOrderVO.setSlipFg(rtnStoreOrderDtlVO.getSlipFg());
                rtnStoreOrderVO.setStoreCd(rtnStoreOrderDtlVO.getStoreCd());
                rtnStoreOrderVO.setEmpNo("0000");
                rtnStoreOrderVO.setProcFg("00");
                rtnStoreOrderVO.setRemark(rtnStoreOrderDtlVO.getHdRemark());
                rtnStoreOrderVO.setRegId(sessionInfoVO.getUserId());
                rtnStoreOrderVO.setRegDt(currentDt);
                rtnStoreOrderVO.setModId(sessionInfoVO.getUserId());
                rtnStoreOrderVO.setModDt(currentDt);
            }

            String insFg = "";
            // 기반품수량이 있는 경우 수정
            if(rtnStoreOrderDtlVO.getPrevOrderTotQty() != null) {
                insFg = "U";
                // 기반품수량이 있으면서 반품수량이 0 이나 null 인 경우 삭제
                if(rtnStoreOrderDtlVO.getOrderTotQty() == 0 || rtnStoreOrderDtlVO.getOrderTotQty() == null) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = rtnStoreOrderDtlVO.getSlipFg();
                int poUnitQty    = rtnStoreOrderDtlVO.getPoUnitQty();
                int prevUnitQty  = (rtnStoreOrderDtlVO.getPrevOrderUnitQty() == null ? 0 : rtnStoreOrderDtlVO.getPrevOrderUnitQty());
                int prevEtcQty   = (rtnStoreOrderDtlVO.getPrevOrderEtcQty()  == null ? 0 : rtnStoreOrderDtlVO.getPrevOrderEtcQty());
                int unitQty      = (rtnStoreOrderDtlVO.getOrderUnitQty()     == null ? 0 : rtnStoreOrderDtlVO.getOrderUnitQty());
                int etcQty       = (rtnStoreOrderDtlVO.getOrderEtcQty()      == null ? 0 : rtnStoreOrderDtlVO.getOrderEtcQty());
                int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                int orderTotQty  = (rtnStoreOrderDtlVO.getOrderTotQty()   == null ? 0 : rtnStoreOrderDtlVO.getOrderTotQty()) * slipFg;
                Long orderAmt    = (rtnStoreOrderDtlVO.getOrderAmt()      == null ? 0 : rtnStoreOrderDtlVO.getOrderAmt())    * slipFg;
                Long orderVat    = (rtnStoreOrderDtlVO.getOrderVat()      == null ? 0 : rtnStoreOrderDtlVO.getOrderVat())    * slipFg;
                Long orderTot    = (rtnStoreOrderDtlVO.getOrderTot()      == null ? 0 : rtnStoreOrderDtlVO.getOrderTot())    * slipFg;

                rtnStoreOrderDtlVO.setOrderUnitQty(orderUnitQty);
                rtnStoreOrderDtlVO.setOrderEtcQty(orderEtcQty);
                rtnStoreOrderDtlVO.setOrderTotQty(orderTotQty);
                rtnStoreOrderDtlVO.setOrderAmt(orderAmt);
                rtnStoreOrderDtlVO.setOrderVat(orderVat);
                rtnStoreOrderDtlVO.setOrderTot(orderTot);
                rtnStoreOrderDtlVO.setRegId(sessionInfoVO.getUserId());
                rtnStoreOrderDtlVO.setRegDt(currentDt);
                rtnStoreOrderDtlVO.setModId(sessionInfoVO.getUserId());
                rtnStoreOrderDtlVO.setModDt(currentDt);
            }
            rtnStoreOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 추가
            if(insFg.equals("I")) {
                result = rtnStoreOrderMapper.insertRtnStoreOrderDtl(rtnStoreOrderDtlVO);
            }
            // 수정
            else if(insFg.equals("U")) {
                result = rtnStoreOrderMapper.updateRtnStoreOrderDtl(rtnStoreOrderDtlVO);
            }
            // 삭제
            else if(insFg.equals("D")) {
                result = rtnStoreOrderMapper.deleteRtnStoreOrderDtl(rtnStoreOrderDtlVO);
            }

            returnResult += result;
            i++;
        }

        int dtlCnt = 0;
        String hdExist = "N";

        // 반품요청일의 상품건수 조회
        dtlCnt = rtnStoreOrderMapper.getDtlCnt(rtnStoreOrderVO);

        // 상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = rtnStoreOrderMapper.deleteRtnStoreOrder(rtnStoreOrderVO);
        }
        // 상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = rtnStoreOrderMapper.getHdExist(rtnStoreOrderVO);
            // HD 내용이 존재하는 경우 update
            if(hdExist.equals("Y")) {
                result = rtnStoreOrderMapper.updateRtnStoreOrder(rtnStoreOrderVO);
            }
            // HD 내용이 없는 경우 insert
            else if(hdExist.equals("N")) {
                result = rtnStoreOrderMapper.insertRtnStoreOrder(rtnStoreOrderVO);
            }
        }

        if ( returnResult == rtnStoreOrderDtlVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 반품등록 반품진행구분 조회 */
    @Override
    public DefaultMap<String> getOrderProcFgCheck(RtnStoreOrderVO rtnStoreOrderVO) {
        return rtnStoreOrderMapper.getOrderProcFgCheck(rtnStoreOrderVO);
    }

    /** 반품등록 출고요청가능일 조회 */
    @Override
    public String getReqDate(RtnStoreOrderVO rtnStoreOrderVO) {
        return rtnStoreOrderMapper.getReqDate(rtnStoreOrderVO);
    }

    /** 반품등록 확정 */
    @Override
    public int saveRtnStoreOrderConfirm(RtnStoreOrderVO rtnStoreOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        rtnStoreOrderVO.setRegId(sessionInfoVO.getUserId());
        rtnStoreOrderVO.setRegDt(currentDt);
        rtnStoreOrderVO.setModId(sessionInfoVO.getUserId());
        rtnStoreOrderVO.setModDt(currentDt);

        // 반품진행구분 체크
        DefaultMap<String> orderProcFg = getOrderProcFgCheck(rtnStoreOrderVO);
        if(orderProcFg != null && !StringUtil.getOrBlank(orderProcFg.get("procFg")).equals("00")) {
            throw new JsonException(Status.FAIL, messageService.get("rtnStoreOrder.dtl.not.orderProcEnd")); //요청내역이 처리중입니다.
        }

        // 반품수량을 MD 수량으로 수정
        result = rtnStoreOrderMapper.updateOrderQtyMdQty(rtnStoreOrderVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // HD 진행구분 변경 및 집계 수정
        rtnStoreOrderVO.setProcFg("20");
        result = rtnStoreOrderMapper.updateRtnStoreOrder(rtnStoreOrderVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 분배자료 생성
        // TODO 분배자료 생성시 매장을 관리하는 MD 의 사원번호와 창고코드 조회하여 데이터 넣어줘야함.
        DstbReqVO dstbReqVO = new DstbReqVO();
        dstbReqVO.setHqBrandCd(sessionInfoVO.getHqOfficeCd());
        dstbReqVO.setStoreCd(rtnStoreOrderVO.getStoreCd());
        dstbReqVO.setReqDate(rtnStoreOrderVO.getReqDate());
        dstbReqVO.setSlipFg(rtnStoreOrderVO.getSlipFg());
        dstbReqVO.setDstbFg("0");
        dstbReqVO.setProcFg("10");
        dstbReqVO.setEmpNo("0000");
        dstbReqVO.setStorageCd("001");
        dstbReqVO.setHqBrandCd("00");
        dstbReqVO.setRegId(sessionInfoVO.getUserId());
        dstbReqVO.setRegDt(currentDt);
        dstbReqVO.setModId(sessionInfoVO.getUserId());
        dstbReqVO.setModDt(currentDt);

        result = rtnStoreOrderMapper.insertDstbRegist(dstbReqVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 분배자료 진행구분 변경 10 -> 20
        DstbCloseStoreVO dstbCloseStoreVO = new DstbCloseStoreVO();
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dstbCloseStoreVO.setStoreCd(rtnStoreOrderVO.getStoreCd());
        dstbCloseStoreVO.setProcFg("10");
        dstbCloseStoreVO.setUpdateProcFg("20");
        dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setRegDt(currentDt);
        dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setModDt(currentDt);

        result = dstbCloseStoreMapper.updateDstbCloseConfirm(dstbCloseStoreVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 수발주옵션 환경변수
        String envst1042 = rtnStoreOrderVO.getEnvst1042();

        // 매장확정시 출고 환경변수가 출고자료생성인 경우 출고자료를 생성한다.
        if(StringUtil.getOrBlank(envst1042).equals("2")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            OutstockDataVO maxSlipNoVO = new OutstockDataVO();
            maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            maxSlipNoVO.setYymm(yymm);
            String maxSlipNo = outstockDataMapper.getMaxSlipNo(maxSlipNoVO);
            Long maxSlipNoIdx = Long.valueOf(maxSlipNo.substring(4));
            int slipNoIdx = 0;

            OutstockDataVO outstockDataVO = new OutstockDataVO();
            // 직배송거래처 및 배송기사 조회
            List<DefaultMap<String>> storeVendrDlvrList = outstockDataMapper.getStoreVendrDlvr(outstockDataVO);

            for(int i=0; i < storeVendrDlvrList.size(); i++) {
                slipNoIdx++;
                String slipNo    = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx+slipNoIdx), 6, "0");
                String vendrCd   = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("vendrCd"));
                String dlvrCd    = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("dlvrCd"));

                // TB_PO_HQ_STORE_DISTRIBUTE 수정
                outstockDataVO.setProcFg("20");
                outstockDataVO.setUpdateProcFg("30");
                outstockDataVO.setSlipNo(slipNo);
                outstockDataVO.setVendrCd(vendrCd);
                result = outstockDataMapper.updateDstbDataCreate(outstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = outstockDataMapper.insertOutstockDtlDataCreate(outstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                outstockDataVO.setDlvrCd(dlvrCd);
                outstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                result = outstockDataMapper.insertOutstockDataCreate(outstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

        }

        return result;
    }


    /** 반품등록 - 엑셀업로드 */
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

        // 수량추가인 경우
        if(StringUtil.getOrBlank(excelUploadVO.getAddQtyFg()).equals("add")) {
            result = rtnStoreOrderMapper.insertExcelUploadAddQty(excelUploadVO);
        }

        // 기존 주문데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
        result = rtnStoreOrderMapper.deleteStoreOrderToExcelUploadData(excelUploadVO);

        // 여신 체크
//        DefaultMap<String> storeLoan = rtnStoreOrderMapper.storeLoanCheck(excelUploadVO);
//        if(storeLoan.getStr("isExist").equals("Y")) {
//            if (storeLoan.getLong("orderTot") > storeLoan.getLong("currLoanAmt")) {
//                throw new JsonException(Status.SERVER_ERROR, messageService.get(
//                    "storeOrder.dtl.excelLoanOver")); // 주문총금액이 여신잔여 금액을 초과하였습니다. 업로드 된 자료는 처리되지 않았습니다.
//            }
//        }

        // 엑셀업로드 한 수량을 주문수량으로 입력
        result = rtnStoreOrderMapper.insertRtnStoreOrderToExcelUploadData(excelUploadVO);

        // 주문수량으로 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = rtnStoreOrderMapper.deleteExcelUploadCompleteData(excelUploadVO);

        // 엑셀업로드 한 내용이 있으면 DTL 자료를 기반으로 주문 HD 생성, 업데이트, 삭제
        int dtlCnt = 0;
        String hdExist = "N";

        RtnStoreOrderVO rtnStoreOrderVO = new RtnStoreOrderVO();
        rtnStoreOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        rtnStoreOrderVO.setRegId(sessionInfoVO.getUserId());
        rtnStoreOrderVO.setRegDt(currentDt);
        rtnStoreOrderVO.setModId(sessionInfoVO.getUserId());
        rtnStoreOrderVO.setModDt(currentDt);
        rtnStoreOrderVO.setReqDate(excelUploadVO.getDate());
        rtnStoreOrderVO.setSlipFg(excelUploadVO.getSlipFg());
        rtnStoreOrderVO.setEmpNo("0000");
        rtnStoreOrderVO.setProcFg("00");
        rtnStoreOrderVO.setRemark(excelUploadVO.getHdRemark());

        // 주문요청일의 상품건수 조회
        dtlCnt = rtnStoreOrderMapper.getDtlCnt(rtnStoreOrderVO);

        // 상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = rtnStoreOrderMapper.deleteRtnStoreOrder(rtnStoreOrderVO);
        }
        // 상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = rtnStoreOrderMapper.getHdExist(rtnStoreOrderVO);
            // HD 내용이 존재하는 경우 update
            if(hdExist.equals("Y")) {
                result = rtnStoreOrderMapper.updateRtnStoreOrder(rtnStoreOrderVO);
            }
            // HD 내용이 없는 경우 insert
            else if(hdExist.equals("N")) {
                result = rtnStoreOrderMapper.insertRtnStoreOrder(rtnStoreOrderVO);
            }
        }

        return result;
    }
}
