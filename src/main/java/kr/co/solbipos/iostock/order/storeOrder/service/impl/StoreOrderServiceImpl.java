package kr.co.solbipos.iostock.order.storeOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.DstbCloseStoreVO;
import kr.co.solbipos.iostock.order.dstbCloseStore.service.impl.DstbCloseStoreMapper;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.iostock.order.outstockData.service.impl.OutstockDataMapper;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("storeOrderService")
@Transactional
public class StoreOrderServiceImpl implements StoreOrderService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
            //HD 저장을 위한 파라미터 세팅
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
                storeOrderVO.setVendrCd(storeOrderDtlVO.getVendrCd());

                //주문진행구분 체크
                DefaultMap<String> orderProcFg = getOrderProcFgCheck(storeOrderVO);
                if(orderProcFg != null && !StringUtil.getOrBlank(orderProcFg.get("procFg")).equals("00")) {
                    throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.dstbConfirm.msg")); //주문이 본사에서 분배완료 되었습니다.
                }
            }
            LOGGER.debug("### EmpNo: " + sessionInfoVO.getEmpNo());

            String insFg = "";
            //기주문수량이 있는 경우 수정
            if(storeOrderDtlVO.getPrevOrderTotQty() != null) {
                insFg = "U";
                //기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
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

            //추가
            if(insFg.equals("I")) {
                result = storeOrderMapper.insertStoreOrderDtl(storeOrderDtlVO);
            }
            //수정
            else if(insFg.equals("U")) {
                result = storeOrderMapper.updateStoreOrderDtl(storeOrderDtlVO);
            }
            //삭제
            else if(insFg.equals("D")) {
                result = storeOrderMapper.deleteStoreOrderDtl(storeOrderDtlVO);
            }

            returnResult += result;
            i++;
        }

        int dtlCnt = 0;
        String hdExist = "N";

        //주문요청일의 상품건수 조회
        dtlCnt = storeOrderMapper.getDtlCnt(storeOrderVO);

        //상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = storeOrderMapper.deleteStoreOrder(storeOrderVO);
        }
        //상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = storeOrderMapper.getHdExist(storeOrderVO);
            //HD 내용이 존재하는 경우 update
            if(hdExist.equals("Y")) {
                result = storeOrderMapper.updateStoreOrder(storeOrderVO);
            }
            //HD 내용이 없는 경우 insert
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

        storeOrderVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()	);
        storeOrderVO.setStoreCd		(sessionInfoVO.getStoreCd	()	);
        storeOrderVO.setRegId		(sessionInfoVO.getUserId	()	);
        storeOrderVO.setRegDt		(currentDt						);
        storeOrderVO.setModId		(sessionInfoVO.getUserId	()	);
        storeOrderVO.setModDt		(currentDt						);

        LOGGER.debug("reqDate  : " + storeOrderVO.getReqDate	()	);
        LOGGER.debug("slipFg   : " + storeOrderVO.getSlipFg 	()	);
        LOGGER.debug("remark   : " + storeOrderVO.getRemark		()	);
        LOGGER.debug("EmpNo    : " + sessionInfoVO.getEmpNo		()	);
        LOGGER.debug("OrderTot : " + storeOrderVO.getOrderTot	()	);

        //매장 주문마감 및 발주중지 여부 체크
        String orderCloseFg = "N";
        orderCloseFg = storeOrderMapper.getOrderCloseCheck(storeOrderVO);

        if(orderCloseFg.equals("Y")) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.orderClose")); //주문등록이 마감 되었습니다.
        }

        //주문진행구분 체크
        DefaultMap<String> orderProcFg = getOrderProcFgCheck(storeOrderVO);
        if(orderProcFg != null && !StringUtil.getOrBlank(orderProcFg.get("procFg")).equals("00")) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.not.orderProcEnd")); //요청내역이 처리중입니다.
        }

        //여신 체크 - START (기존에는 Excel Upload에서만 했음. 추후 필요없으면 주석처리)
	        StoreOrderVO	storeOrderLoanVO 	= new StoreOrderVO();
	        storeOrderLoanVO.setReqDate		(storeOrderVO.getReqDate	()	);
	        storeOrderLoanVO.setSlipFg		(storeOrderVO.getSlipFg		()	);
	        storeOrderLoanVO.setHqOfficeCd	(storeOrderVO.getHqOfficeCd	() 	);
	        storeOrderLoanVO.setStoreCd		(storeOrderVO.getStoreCd	() 	);

	        DefaultMap<String> storeLoan = storeOrderMapper.getStoreLoan(storeOrderLoanVO);

	        //금액은 BigDecimal로 하는것이 맞으나, NUMBER(9,0)이기에 long으로 해도 상관없음 (cf> long 범위: –2,147,483,648 ~ 2,147,483,647)
	        if(storeLoan != null   &&   storeLoan.getStr("availableOrderAmt") != null) {
	            Long availableOrderAmt	= storeLoan.getLong("availableOrderAmt");	//'주문가능액' - 주문액 산정방식에 따른 '주문가능액'
	            Long orderTot			= 0L;										//'주문 총금액'

	            //미출고금액 고려여부 (사용     : 주문가능액은 주문액 산정방식에 따른 주문가능액 - 미출고금액
	            //                  미사용 : 주문가능액은 주문액 산정방식에 따름)
	            if(storeLoan.getStr("noOutstockAmtFg").equals("Y")) {
	            	availableOrderAmt = availableOrderAmt - storeLoan.getLong("prevOrderTot");
	            }

	            orderTot = Long.parseLong( String.valueOf(storeOrderVO.getOrderTot()) );

	            if(orderTot > availableOrderAmt) {
	                throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.excelLoanOverForOrderConfirm"));	//storeOrder.dtl.excelLoanOverForOrderConfirm=주문총금액이 주문가능금액을 초과하였습니다.
	            }
	        }
        //여신 체크 - END


        //MD수량을 주문수량값으로 setting (ex: SET MD_TOT_QTY = ORDER_TOT_QTY, ....)
        result = storeOrderMapper.updateOrderQtyMdQty(storeOrderVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        //HD 진행구분 변경 및 집계 수정 (TB_PO_HQ_STORE_ORDER_DTL)
        storeOrderVO.setProcFg("20");	//처리구분 (00:등록, 10:등록완료, 20:분배완료)
        result = storeOrderMapper.updateStoreOrder(storeOrderVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        //분배자료 생성
        DstbReqVO dstbReqVO = new DstbReqVO();
        dstbReqVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd());
        dstbReqVO.setStoreCd	(sessionInfoVO.getStoreCd());
        dstbReqVO.setReqDate	(storeOrderVO.getReqDate());
        dstbReqVO.setSlipFg		(storeOrderVO.getSlipFg());
        dstbReqVO.setDstbFg		("0");		//분배구분 (0:일반, 1:증정, 2:전시, 9:기타)              > TB_CM_NMCODE.NMCODE_GRP_CD='111'
        dstbReqVO.setProcFg		("10");		//처리구분 (00:등록, 10:MD확정, 20:분배마감, 30:전표수거) > TB_CM_NMCODE.NMCODE_GRP_CD='110'
        dstbReqVO.setEmpNo		("0000");
        dstbReqVO.setStorageCd	("999");	//전체재고용 창고코드 ('001' -> '000' -> '999')
        dstbReqVO.setHqBrandCd	("00");
        dstbReqVO.setRegId		(sessionInfoVO.getUserId());
        dstbReqVO.setRegDt		(currentDt);
        dstbReqVO.setModId		(sessionInfoVO.getUserId());
        dstbReqVO.setModDt		(currentDt);
        dstbReqVO.setVendrCd(storeOrderVO.getVendrCd());

        result = storeOrderMapper.insertDstbRegist(dstbReqVO);				//TB_PO_HQ_STORE_DISTRIBUTE
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        //분배자료 진행구분 변경 10 -> 20	:	//처리구분 (00:등록, 10:MD확정, 20:분배마감, 30:전표수거)	 -> 왜 Insert, Update를 별도로 진행하지?? 한번에 안하고
        DstbCloseStoreVO dstbCloseStoreVO = new DstbCloseStoreVO();
        dstbCloseStoreVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd());
        dstbCloseStoreVO.setStoreCd		(sessionInfoVO.getStoreCd());
        dstbCloseStoreVO.setProcFg		("10");
        dstbCloseStoreVO.setUpdateProcFg("20");
        dstbCloseStoreVO.setRegId		(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setRegDt		(currentDt);
        dstbCloseStoreVO.setModId		(sessionInfoVO.getUserId());
        dstbCloseStoreVO.setModDt		(currentDt);
        dstbCloseStoreVO.setReqDate		(storeOrderVO.getReqDate());
        dstbCloseStoreVO.setSlipFg		(storeOrderVO.getSlipFg ());
        dstbCloseStoreVO.setEmpNo		(dstbReqVO.getEmpNo   	());
        dstbCloseStoreVO.setVendrCd     (dstbReqVO.getVendrCd());

        result = dstbCloseStoreMapper.updateDstbCloseConfirm(dstbCloseStoreVO);	//TB_PO_HQ_STORE_DISTRIBUTE
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        //수발주옵션 환경변수
        String envst1042 = storeOrderVO.getEnvst1042();

        //매장확정시 출고 환경변수가 출고자료생성인 경우 출고자료를 생성한다.
        if(StringUtil.getOrBlank(envst1042).equals("2")) {				//1042 수발주옵션 - 0:매장확정안함, 1:매장확정(분배마감), 2:매장확정(출고자료생성)
            //전표번호 조회
            String 			yymm 		= DateUtil.currentDateString().substring(2,6); //새로운 전표번호 생성을 위한 년월(YYMM)

            OutstockDataVO 	maxSlipNoVO = new OutstockDataVO();
				            maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
				            maxSlipNoVO.setYymm(yymm);

            String 			maxSlipNo 	= outstockDataMapper.getMaxSlipNo(maxSlipNoVO);
            Long 			maxSlipNoIdx= Long.valueOf(maxSlipNo.substring(4));
            int 			slipNoIdx 	= 0;

            OutstockDataVO 	outstockDataVO = new OutstockDataVO();
            outstockDataVO.setHqOfficeCd(dstbCloseStoreVO.getHqOfficeCd	() );
            outstockDataVO.setStoreCd	(dstbCloseStoreVO.getStoreCd	() );
            outstockDataVO.setSlipFg	(dstbCloseStoreVO.getSlipFg		() );
            outstockDataVO.setEmpNo		(dstbCloseStoreVO.getEmpNo   	() );
            outstockDataVO.setRegId		(dstbCloseStoreVO.getRegId		() );
            outstockDataVO.setRegDt		(dstbCloseStoreVO.getRegDt		() );
            outstockDataVO.setModId		(dstbCloseStoreVO.getModId		() );
            outstockDataVO.setModDt		(dstbCloseStoreVO.getModDt		() );
            outstockDataVO.setReqDate(dstbCloseStoreVO.getReqDate());
            outstockDataVO.setVendrCd(dstbCloseStoreVO.getVendrCd());
            outstockDataVO.setDateFg("req");
            outstockDataVO.setStartDate(dstbCloseStoreVO.getReqDate());
            outstockDataVO.setEndDate(dstbCloseStoreVO.getReqDate());

            //직배송거래처 및 배송기사 조회
            //List<DefaultMap<String>> storeVendrDlvrList = outstockDataMapper.getStoreVendrDlvr(outstockDataVO);
            //LOGGER.debug("### storeVendrDlvrList.size(): " + storeVendrDlvrList.size());

            //for(int i=0; i < storeVendrDlvrList.size(); i++) {
                slipNoIdx++;
                String slipNo    = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx + slipNoIdx), 6, "0");
                //String vendrCd   = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("vendrCd"));
                //String dlvrCd    = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("dlvrCd"));

                //TB_PO_HQ_STORE_DISTRIBUTE 수정
                outstockDataVO.setProcFg		("20");	//00:등록, 10:MD확정, 20:분배마감, 30:전표수거
                outstockDataVO.setUpdateProcFg	("30");	//00:등록, 10:MD확정, 20:분배마감, 30:전표수거
                outstockDataVO.setSlipNo		(slipNo);
                //outstockDataVO.setVendrCd		(vendrCd);
                result = outstockDataMapper.updateDstbDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
                
                outstockDataVO.setOutDate	(storeOrderVO.getReqDate());	//출고일자 (수불기준일자)
                //TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = outstockDataMapper.insertOutstockDtlDataCreate(outstockDataVO);	//TB_PO_HQ_STORE_DISTRIBUTE의 '전표수거(PROC_FG='30')인 자료를, TB_PO_HQ_STORE_OUTSTOCK_DTL에 Insert
                //if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                //TB_PO_HQ_STORE_OUTSTOCK 자료입력
                //outstockDataVO.setDlvrCd	(dlvrCd);
                outstockDataVO.setSlipKind	("0");							//전표종류	0:일반 1:물량오류 2:이동
//                outstockDataVO.setOutDate	(storeOrderVO.getReqDate());	//출고일자 (수불기준일자)
                outstockDataVO.setRemark	(storeOrderVO.getRemark ());	//비고
              //outstockDataVO.setHqRemark	();								//본사비고 (매장은 열람불가)
                result = outstockDataMapper.insertOutstockDataCreate(outstockDataVO);		//TB_PO_HQ_STORE_OUTSTOCK_DTL SUM값등을 Insert
                //if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            //}

        }

        //For TEST
        //if(true)	throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 주문등록 - 엑셀업로드 */
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

        //수량추가인 경우
        if(StringUtil.getOrBlank(excelUploadMPSVO.getAddQtyFg()).equals("add")) {
            //result = storeOrderMapper.insertExcelUploadAddQty(excelUploadMPSVO);
        }else {
            //기존 주문데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
            result = storeOrderMapper.deleteStoreOrderToExcelUploadData(excelUploadMPSVO);
        }
        //여신 체크
        StoreOrderVO storeOrderLoanVO = new StoreOrderVO();
        storeOrderLoanVO.setReqDate		(excelUploadMPSVO.getDate		()	);
        storeOrderLoanVO.setSlipFg		(excelUploadMPSVO.getSlipFg	()	);
        storeOrderLoanVO.setHqOfficeCd	(excelUploadMPSVO.getHqOfficeCd() 	);
        storeOrderLoanVO.setStoreCd		(excelUploadMPSVO.getStoreCd	() 	);

        DefaultMap<String> storeLoan = storeOrderMapper.getStoreLoan(storeOrderLoanVO);
        /*
        if(storeLoan != null   &&   storeLoan.getStr("availableOrderAmt") != null) {
            Long availableOrderAmt = 0L;
            Long orderAmt = 0L;
            if(storeLoan.getStr("noOutstockAmtFg").equals("Y")) {
                if (storeLoan.getLong("availableOrderAmt") <= (storeLoan.getLong("currLoanAmt") - storeLoan.getLong("prevOrderTot"))) {
                    availableOrderAmt = storeLoan.getLong("availableOrderAmt");
                } else if (storeLoan.getLong("availableOrderAmt") >= (storeLoan.getLong("currLoanAmt") - storeLoan.getLong("prevOrderTot")) && storeLoan.getLong("maxOrderAmt") != 0) {
                    availableOrderAmt = storeLoan.getLong("currLoanAmt") - storeLoan.getLong("prevOrderTot");
                } else {
                    availableOrderAmt = storeLoan.getLong("availableOrderAmt") - storeLoan.getLong("prevOrderTot");
                }
            }
            DefaultMap<String> storeOrder = storeOrderMapper.storeLoanCheck(excelUploadMPSVO);
            orderAmt = storeOrder.getLong("orderTot");
            if(orderAmt > availableOrderAmt) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.excelLoanOver")); // 주문총금액이 여신잔여 금액을 초과하였습니다. 업로드 된 자료는 처리되지 않았습니다.
            }
        }

//        DefaultMap<String> storeLoan = storeOrderMapper.storeLoanCheck(excelUploadMPSVO);
//        if(storeLoan.getStr("isExist").equals("Y")) {
//            if(storeLoan.getLong("orderTot") > storeLoan.getLong("currLoanAmt")) {
//                throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.excelLoanOver")); // 주문총금액이 여신잔여 금액을 초과하였습니다. 업로드 된 자료는 처리되지 않았습니다.
//            }
//        }
		*/

        //금액은 BigDecimal로 하는것이 맞으나, NUMBER(9,0)이기에 long으로 해도 상관없음 (cf> long 범위: –2,147,483,648 ~ 2,147,483,647)
        if(storeLoan != null   &&   storeLoan.getStr("availableOrderAmt") != null) {
            Long availableOrderAmt	= storeLoan.getLong("availableOrderAmt");	//'주문가능액' - 주문액 산정방식에 따른 '주문가능액'
            Long orderAmt			= 0L;										//'주문 총금액'

            //미출고금액 고려여부 (사용     : 주문가능액은 주문액 산정방식에 따른 주문가능액 - 미출고금액
            //                  미사용 : 주문가능액은 주문액 산정방식에 따름)
            if(storeLoan.getStr("noOutstockAmtFg").equals("Y")) {
            	availableOrderAmt = availableOrderAmt - storeLoan.getLong("prevOrderTot");
            }

            DefaultMap<String> storeOrder = storeOrderMapper.storeLoanCheck(excelUploadMPSVO);
            orderAmt = storeOrder.getLong("orderTot");

            if(orderAmt > availableOrderAmt) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("storeOrder.dtl.excelLoanOver"));	//주문총금액이 주문가능금액을 초과하였습니다. 업로드 된 자료는 처리되지 않았습니다.
            }
        }

        //엑셀업로드 한 수량을 주문수량으로 입력
        result = storeOrderMapper.insertStoreOrderToExcelUploadData(excelUploadMPSVO);

        //주문수량으로 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = storeOrderMapper.deleteExcelUploadCompleteData(excelUploadMPSVO);

        //엑셀업로드 한 내용이 있으면 DTL 자료를 기반으로 주문 HD 생성, 업데이트, 삭제
        int dtlCnt = 0;
        String hdExist = "N";

        StoreOrderVO storeOrderVO = new StoreOrderVO();
        storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeOrderVO.setRegId(sessionInfoVO.getUserId());
        storeOrderVO.setRegDt(currentDt);
        storeOrderVO.setModId(sessionInfoVO.getUserId());
        storeOrderVO.setModDt(currentDt);
        storeOrderVO.setReqDate(excelUploadMPSVO.getDate());
        storeOrderVO.setSlipFg(excelUploadMPSVO.getSlipFg());
        storeOrderVO.setEmpNo("0000");
        storeOrderVO.setProcFg("00");
        storeOrderVO.setRemark(excelUploadMPSVO.getHdRemark());
        storeOrderVO.setVendrCd(excelUploadMPSVO.getVendrCd());

        //주문요청일의 상품건수 조회
        dtlCnt = storeOrderMapper.getDtlCnt(storeOrderVO);

        //상품건수가 없으면 HD 삭제
        if(dtlCnt == 0) {
            result = storeOrderMapper.deleteStoreOrder(storeOrderVO);
        }
        //상품건수가 있는경우 HD 내용이 존재하는지 여부 조회
        else {
            hdExist = storeOrderMapper.getHdExist(storeOrderVO);
            //HD 내용이 존재하는 경우 update
            if(hdExist.equals("Y")) {
                result = storeOrderMapper.updateStoreOrder(storeOrderVO);
            }
            //HD 내용이 없는 경우 insert
            else if(hdExist.equals("N")) {
                result = storeOrderMapper.insertStoreOrder(storeOrderVO);
            }
        }

        return result;
    }

    /** 주문등록 출고요청일자에 등록한 주문 총 합계 금액 조회 */
    @Override
    public String getOrderTotAmt(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO) {

        storeOrderVO.setStoreCd(sessionInfoVO.getStoreCd());

        return storeOrderMapper.getOrderTotAmt(storeOrderVO);
    }

    /** 본사 거래처 조회(콤보박스용) */
    public List<DefaultMap<String>> getHqVendrCombo(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO){

        storeOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeOrderMapper.getHqVendrCombo(storeOrderVO);
    }
}
