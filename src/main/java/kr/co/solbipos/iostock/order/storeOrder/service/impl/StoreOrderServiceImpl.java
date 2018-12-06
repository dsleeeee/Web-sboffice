package kr.co.solbipos.iostock.order.storeOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.impl.DstbCloseStoreMapper;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.iostock.order.outstockData.service.impl.OutstockDataMapper;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("storeOrderService")
public class StoreOrderServiceImpl implements StoreOrderService {
    private final StoreOrderMapper storeOrderMapper;
    private final DstbCloseStoreMapper dstbCloseStoreMapper;
    private final OutstockDataMapper outstockDataMapper;
    private final MessageService messageService;

    @Autowired
    public StoreOrderServiceImpl(StoreOrderMapper storeOrderMapper, DstbCloseStoreMapper dstbCloseStoreMapper, OutstockDataMapper outstockDataMapper, MessageService messageService) {
        this.storeOrderMapper = storeOrderMapper;
        this.dstbCloseStoreMapper = dstbCloseStoreMapper;
        this.outstockDataMapper = outstockDataMapper;
        this.messageService = messageService;
    }

    /** 주문등록 HD 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreOrderList(storeOrderVO);
    }

    /** 주문등록 DT 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO) {
        return storeOrderMapper.getStoreOrderDtlList(storeOrderDtlVO);
    }

    /** 주문등록 상품추가 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO) {
        return storeOrderMapper.getStoreOrderRegistList(storeOrderDtlVO);
    }

    /** 주문등록 주문상품 저장 */
    @Override
    public int saveStoreOrderRegist(StoreOrderDtlVO[] storeOrderDtlVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        StoreOrderVO storeOrderVO = new StoreOrderVO();

        int i = 0;
        for (StoreOrderDtlVO storeOrderDtlVO : storeOrderDtlVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                storeOrderVO.setReqDate(storeOrderDtlVO.getReqDate());
                storeOrderVO.setSlipFg(storeOrderDtlVO.getSlipFg());
                storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
                storeOrderVO.setEmpNo("0000");
                storeOrderVO.setProcFg("00");
                storeOrderVO.setRemark(storeOrderDtlVO.getHdRemark());
                storeOrderVO.setRegId(sessionInfoVO.getUserId());
                storeOrderVO.setRegDt(currentDt);
                storeOrderVO.setModId(sessionInfoVO.getUserId());
                storeOrderVO.setModDt(currentDt);
            }

            String insFg = "";
            // 기주문수량이 있는 경우 수정
            if(storeOrderDtlVO.getPrevOrderTotQty() != null) {
                insFg = "U";
                // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
                if(storeOrderDtlVO.getOrderTotQty() == 0 || storeOrderDtlVO.getOrderTotQty() == null) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = storeOrderDtlVO.getSlipFg();
                int poUnitQty    = storeOrderDtlVO.getPoUnitQty();
                int prevUnitQty  = (storeOrderDtlVO.getPrevOrderUnitQty() == null ? 0 : storeOrderDtlVO.getPrevOrderUnitQty());
                int prevEtcQty   = (storeOrderDtlVO.getPrevOrderEtcQty()  == null ? 0 : storeOrderDtlVO.getPrevOrderEtcQty());
                int unitQty      = (storeOrderDtlVO.getOrderUnitQty()     == null ? 0 : storeOrderDtlVO.getOrderUnitQty());
                int etcQty       = (storeOrderDtlVO.getOrderEtcQty()      == null ? 0 : storeOrderDtlVO.getOrderEtcQty());
                int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                int orderTotQty  = (storeOrderDtlVO.getOrderTotQty()   == null ? 0 : storeOrderDtlVO.getOrderTotQty()) * slipFg;
                Long orderAmt    = (storeOrderDtlVO.getOrderAmt()      == null ? 0 : storeOrderDtlVO.getOrderAmt())    * slipFg;
                Long orderVat    = (storeOrderDtlVO.getOrderVat()      == null ? 0 : storeOrderDtlVO.getOrderVat())    * slipFg;
                Long orderTot    = (storeOrderDtlVO.getOrderTot()      == null ? 0 : storeOrderDtlVO.getOrderTot())    * slipFg;

                storeOrderDtlVO.setOrderUnitQty(orderUnitQty);
                storeOrderDtlVO.setOrderEtcQty(orderEtcQty);
                storeOrderDtlVO.setOrderTotQty(orderTotQty);
                storeOrderDtlVO.setOrderAmt(orderAmt);
                storeOrderDtlVO.setOrderVat(orderVat);
                storeOrderDtlVO.setOrderTot(orderTot);
                storeOrderDtlVO.setRegId(sessionInfoVO.getUserId());
                storeOrderDtlVO.setRegDt(currentDt);
                storeOrderDtlVO.setModId(sessionInfoVO.getUserId());
                storeOrderDtlVO.setModDt(currentDt);
            }
            storeOrderDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeOrderDtlVO.setStoreCd(sessionInfoVO.getStoreCd());

            // 추가
            if(insFg.equals("I")) {
                result = storeOrderMapper.insertStoreOrderDtl(storeOrderDtlVO);
            }
            // 수정
            else if(insFg.equals("U")) {
                result = storeOrderMapper.updateStoreOrderDtl(storeOrderDtlVO);
            }
            // 삭제
            else if(insFg.equals("D")) {
                result = storeOrderMapper.deleteStoreOrderDtl(storeOrderDtlVO);
            }

            returnResult += result;
            i++;
        }

        int dtlCnt = 0;
        String hdExist = "N";

        // 주문요청일의 상품건수 조회
        dtlCnt = storeOrderMapper.getDtlCnt(storeOrderVO);

        // 상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = storeOrderMapper.deleteStoreOrder(storeOrderVO);
        }
        // 상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = storeOrderMapper.getHdExist(storeOrderVO);
            // HD 내용이 존재하는 경우 update
            if(hdExist.equals("Y")) {
                result = storeOrderMapper.updateStoreOrder(storeOrderVO);
            }
            // HD 내용이 없는 경우 insert
            else if(hdExist.equals("N")) {
                result = storeOrderMapper.insertStoreOrder(storeOrderVO);
            }
        }

        if ( returnResult == storeOrderDtlVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 주문등록 매장마감여부 조회 */
    @Override
    public DefaultMap<String> getStoreCloseCheck(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreCloseCheck(storeOrderVO);
    }

    /** 주문등록 주문진행구분 조회 */
    @Override
    public DefaultMap<String> getOrderProcFgCheck(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getOrderProcFgCheck(storeOrderVO);
    }

    /** 주문등록 매장여신 조회 */
    @Override
    public DefaultMap<String> getStoreLoan(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreLoan(storeOrderVO);
    }

    /** 주문등록 출고요청가능일인지 여부 조회 */
    @Override
    public DefaultMap<String> getStoreOrderDateCheck(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getStoreOrderDateCheck(storeOrderVO);
    }

    /** 주문등록 출고요청가능일 조회 */
    @Override
    public String getReqDate(StoreOrderVO storeOrderVO) {
        return storeOrderMapper.getReqDate(storeOrderVO);
    }

    /** 주문등록 확정 */
    @Override
    public int saveStoreOrderConfirm(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        storeOrderVO.setRegId(sessionInfoVO.getUserId());
        storeOrderVO.setRegDt(currentDt);
        storeOrderVO.setModId(sessionInfoVO.getUserId());
        storeOrderVO.setModDt(currentDt);

        // 매장 주문마감 및 발주중지 여부 체크
        String orderCloseFg = "N";
        orderCloseFg = storeOrderMapper.getOrderCloseCheck(storeOrderVO);

        if(orderCloseFg.equals("Y")) {
            throw new JsonException(Status.FAIL, messageService.get("storeOrder.dtl.orderClose")); //주문등록이 마감 되었습니다.
        }

        // 주문진행구분 체크
        DefaultMap<String> orderProcFg = getOrderProcFgCheck(storeOrderVO);
        if(orderProcFg != null && !StringUtil.getOrBlank(orderProcFg.get("procFg")).equals("00")) {
            throw new JsonException(Status.FAIL, messageService.get("storeOrder.dtl.not.orderProcEnd")); //요청내역이 처리중입니다.
        }

        // 주문수량을 MD 수량으로 수정
        result = storeOrderMapper.updateOrderQtyMdQty(storeOrderVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // HD 진행구분 변경 및 집계 수정
        storeOrderVO.setProcFg("20");
        result = storeOrderMapper.updateStoreOrder(storeOrderVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 분배자료 생성
        // TODO 분배자료 생성시 매장을 관리하는 MD 의 사원번호와 창고코드 조회하여 데이터 넣어줘야함.
        DstbReqVO dstbReqVO = new DstbReqVO();
        dstbReqVO.setHqBrandCd(sessionInfoVO.getHqOfficeCd());
        dstbReqVO.setStoreCd(sessionInfoVO.getStoreCd());
        dstbReqVO.setReqDate(storeOrderVO.getReqDate());
        dstbReqVO.setSlipFg(storeOrderVO.getSlipFg());
        dstbReqVO.setDstbFg("0");
        dstbReqVO.setProcFg("10");
        dstbReqVO.setEmpNo("0000");
        dstbReqVO.setStorageCd("001");
        dstbReqVO.setHqBrandCd("00");
        dstbReqVO.setRegId(sessionInfoVO.getUserId());
        dstbReqVO.setRegDt(currentDt);
        dstbReqVO.setModId(sessionInfoVO.getUserId());
        dstbReqVO.setModDt(currentDt);

        result = storeOrderMapper.insertDstbRegist(dstbReqVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 분배자료 진행구분 변경 10 -> 20
        DstbCloseStoreVO dstbCloseStoreVO = new DstbCloseStoreVO();
        dstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dstbCloseStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        dstbCloseStoreVO.setProcFg("10");
        dstbCloseStoreVO.setUpdateProcFg("20");
        dstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setRegDt(currentDt);
        dstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setModDt(currentDt);

        result = dstbCloseStoreMapper.updateDstbCloseConfirm(dstbCloseStoreVO);
        if(result > 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 수발주옵션 환경변수
        String envst1042 = storeOrderVO.getEnvst1042();

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

}
