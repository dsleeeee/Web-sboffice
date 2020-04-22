package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnDstbCloseStoreService")
@Transactional
public class RtnDstbCloseStoreServiceImpl implements RtnDstbCloseStoreService {
    private final RtnDstbCloseStoreMapper rtnDstbCloseStoreMapper;
    private final MessageService messageService;

    public RtnDstbCloseStoreServiceImpl(RtnDstbCloseStoreMapper rtnDstbCloseStoreMapper, MessageService messageService) {
        this.rtnDstbCloseStoreMapper = rtnDstbCloseStoreMapper;
        this.messageService = messageService;
    }

    /** 반품마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseStoreList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getRtnDstbCloseStoreList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 리스트 확정 */
    @Override
    public int saveRtnDstbCloseStoreConfirm(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseStoreVO.setUpdateProcFg("20");
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);

            result = rtnDstbCloseStoreMapper.updateDstbCloseConfirm(rtnDstbCloseStoreVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseStoreDtlList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getRtnDstbCloseStoreDtlList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 상세 리스트 저장 */
    @Override
    public int saveRtnDstbCloseStoreDtl(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            int slipFg     = rtnDstbCloseStoreVO.getSlipFg();
            int mgrUnitQty = (rtnDstbCloseStoreVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseStoreVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (rtnDstbCloseStoreVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (rtnDstbCloseStoreVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseStoreVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (rtnDstbCloseStoreVO.getMgrAmt()     == null ? 0 : rtnDstbCloseStoreVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (rtnDstbCloseStoreVO.getMgrVat()     == null ? 0 : rtnDstbCloseStoreVO.getMgrVat()) * slipFg;
            Long mgrTot    = (rtnDstbCloseStoreVO.getMgrTot()     == null ? 0 : rtnDstbCloseStoreVO.getMgrTot()) * slipFg;

            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
            rtnDstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
            rtnDstbCloseStoreVO.setMgrTotQty(mgrTotQty);
            rtnDstbCloseStoreVO.setMgrAmt(mgrAmt);
            rtnDstbCloseStoreVO.setMgrVat(mgrVat);
            rtnDstbCloseStoreVO.setMgrTot(mgrTot);
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);

            // 분배수량이 0 이나 null 인 경우 삭제
//            if(rtnDstbCloseStoreVO.getMgrTotQty() == 0 || rtnDstbCloseStoreVO.getMgrTotQty() == null) {
//                result = rtnDstbCloseStoreMapper.deleteDstbCloseDtl(rtnDstbCloseStoreVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
                result = rtnDstbCloseStoreMapper.updateDstbCloseDtl(rtnDstbCloseStoreVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(rtnDstbCloseStoreVO.getConfirmYn()) {
                    rtnDstbCloseStoreVO.setProcFg("20");
                    result = rtnDstbCloseStoreMapper.updateDstbCloseDtlConfirm(rtnDstbCloseStoreVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
//            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 - 추가등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDstbAddList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO) {
        return rtnDstbCloseStoreMapper.getDstbAddList(rtnDstbCloseStoreVO);
    }

    /** 반품마감 - 추가등록 상세 리스트 저장 */
    @Override
    public int saveDstbAdd(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        
        String[] storageCd;
        String[] storageNm;
        String[] storageOrderUnitQty;
        String[] storageOrderEtcQty;
        String[] storageOrderTotQty;
        String[] storageOrderAmt;
        String[] storageOrderVat;
        String[] storageOrderTot;

        for (RtnDstbCloseStoreVO rtnDstbCloseStoreVO : rtnDstbCloseStoreVOs) {
            int slipFg       = rtnDstbCloseStoreVO.getSlipFg();
            int poUnitQty    = rtnDstbCloseStoreVO.getPoUnitQty();
            int mgrSplyUprc  = rtnDstbCloseStoreVO.getOrderSplyUprc();
            int prevUnitQty  = (rtnDstbCloseStoreVO.getPrevOrderUnitQty() == null ? 0 : rtnDstbCloseStoreVO.getPrevOrderUnitQty());
            int prevEtcQty   = (rtnDstbCloseStoreVO.getPrevOrderEtcQty()  == null ? 0 : rtnDstbCloseStoreVO.getPrevOrderEtcQty());
            int unitQty      = (rtnDstbCloseStoreVO.getOrderUnitQty()     == null ? 0 : rtnDstbCloseStoreVO.getOrderUnitQty());
            int etcQty       = (rtnDstbCloseStoreVO.getOrderEtcQty()      == null ? 0 : rtnDstbCloseStoreVO.getOrderEtcQty());
            int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
            int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
            int orderTotQty  = (rtnDstbCloseStoreVO.getOrderTotQty()   == null ? 0 : rtnDstbCloseStoreVO.getOrderTotQty()) * slipFg;
            Long orderAmt    = (rtnDstbCloseStoreVO.getOrderAmt()      == null ? 0 : rtnDstbCloseStoreVO.getOrderAmt())    * slipFg;
            Long orderVat    = (rtnDstbCloseStoreVO.getOrderVat()      == null ? 0 : rtnDstbCloseStoreVO.getOrderVat())    * slipFg;
            Long orderTot    = (rtnDstbCloseStoreVO.getOrderTot()      == null ? 0 : rtnDstbCloseStoreVO.getOrderTot())    * slipFg;
            
            rtnDstbCloseStoreVO.setOrderUnitQty(orderUnitQty);
            rtnDstbCloseStoreVO.setOrderEtcQty(orderEtcQty);
            rtnDstbCloseStoreVO.setOrderTotQty(orderTotQty);
            rtnDstbCloseStoreVO.setOrderAmt(orderAmt);
            rtnDstbCloseStoreVO.setOrderVat(orderVat);
            rtnDstbCloseStoreVO.setOrderTot(orderTot);
            rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setRegDt(currentDt);
            rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseStoreVO.setModDt(currentDt);
            rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                
        	if(rtnDstbCloseStoreVO.getOrderTotQty() != 0 && rtnDstbCloseStoreVO.getOrderTotQty() != null) {

                //TB_PO_HQ_STORE_ORDER_PROD - START
            	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
	            storageCd           	= rtnDstbCloseStoreVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
	            storageNm           	= rtnDstbCloseStoreVO.getArrStorageNm().split("\\^");
	            storageOrderUnitQty     = rtnDstbCloseStoreVO.getArrOrderUnitQty().split("\\^");
	            storageOrderEtcQty      = rtnDstbCloseStoreVO.getArrOrderEtcQty().split("\\^");
	            storageOrderTotQty      = rtnDstbCloseStoreVO.getArrOrderTotQty().split("\\^");
	            storageOrderAmt         = rtnDstbCloseStoreVO.getArrOrderAmt().split("\\^");
	            storageOrderVat         = rtnDstbCloseStoreVO.getArrOrderVat().split("\\^");
	            storageOrderTot         = rtnDstbCloseStoreVO.getArrOrderTot().split("\\^");
	
	            for(int k=0; k<storageCd.length; k++) {

	            	rtnDstbCloseStoreVO.setStorageCd				(storageCd[k]					);	//창고코드
	            	rtnDstbCloseStoreVO.setSlipFg		        	(1								);	//전표구분 1:주문 -1:반품

	            	rtnDstbCloseStoreVO.setOrderUnitQty		        (Integer.parseInt	(storageOrderUnitQty	[k]));	//입고수량 주문단위
	            	rtnDstbCloseStoreVO.setOrderEtcQty		        (Integer.parseInt	(storageOrderEtcQty		[k]));	//입고수량 나머지
	            	rtnDstbCloseStoreVO.setOrderTotQty		        (Integer.parseInt	(storageOrderTotQty		[k]));	//입고수량합계 낱개
	            	rtnDstbCloseStoreVO.setOrderAmt			        (Long.parseLong		(storageOrderAmt		[k]));	//입고금액
	            	rtnDstbCloseStoreVO.setOrderVat			        (Long.parseLong		(storageOrderVat		[k]));	//입고금액VAT
	            	rtnDstbCloseStoreVO.setOrderTot			        (Long.parseLong		(storageOrderTot		[k]));	//입고금액합계

	            	rtnDstbCloseStoreVO.setRegId			        (sessionInfoVO.getUserId());
	            	rtnDstbCloseStoreVO.setRegDt			        (currentDt	);
	            	rtnDstbCloseStoreVO.setModId			        (sessionInfoVO.getUserId());
	            	rtnDstbCloseStoreVO.setModDt			        (currentDt	);
		            			            	
	            	if(rtnDstbCloseStoreVO.getPrevOrderTotQty() != null) {
	            		result = rtnDstbCloseStoreMapper.updateRtnStoreOrderProd(rtnDstbCloseStoreVO);
	            		 if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            	}else {
	            		result = rtnDstbCloseStoreMapper.savetRtnStoreOrderProd(rtnDstbCloseStoreVO);
	            		 if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            	}
          
	            	rtnDstbCloseStoreVO.setOrderUnitQty		        (Integer.parseInt	(storageOrderUnitQty	[k]));	//입고수량 주문단위
	            	rtnDstbCloseStoreVO.setOrderEtcQty		        (Integer.parseInt	(storageOrderEtcQty		[k]));	//입고수량 나머지
	            	rtnDstbCloseStoreVO.setOrderTotQty		        (Integer.parseInt	(storageOrderTotQty		[k]));	//입고수량합계 낱개
	            	rtnDstbCloseStoreVO.setOrderAmt			        (Long.parseLong		(storageOrderAmt		[k]));	//입고금액
	            	rtnDstbCloseStoreVO.setOrderVat			        (Long.parseLong		(storageOrderVat		[k]));	//입고금액VAT
	            	rtnDstbCloseStoreVO.setOrderTot			        (Long.parseLong		(storageOrderTot		[k]));	//입고금액합계

	            	rtnDstbCloseStoreVO.setRegId			        (sessionInfoVO.getUserId());
	            	rtnDstbCloseStoreVO.setRegDt			        (currentDt	);
	            	rtnDstbCloseStoreVO.setModId			        (sessionInfoVO.getUserId());
	            	rtnDstbCloseStoreVO.setModDt			        (currentDt	);
	            			                
	                int mgrUnitQty   = (rtnDstbCloseStoreVO.getOrderUnitQty() == null ? 0 : rtnDstbCloseStoreVO.getOrderUnitQty()) * slipFg;
	                int mgrEtcQty    = (rtnDstbCloseStoreVO.getOrderEtcQty()  == null ? 0 : rtnDstbCloseStoreVO.getOrderEtcQty())  * slipFg;
	                int mgrTotQty    = (rtnDstbCloseStoreVO.getOrderTotQty()  == null ? 0 : rtnDstbCloseStoreVO.getOrderTotQty())  * slipFg;
	                Long mgrAmt      = (rtnDstbCloseStoreVO.getOrderAmt()     == null ? 0 : rtnDstbCloseStoreVO.getOrderAmt())     * slipFg;
	                Long mgrVat      = (rtnDstbCloseStoreVO.getOrderVat()     == null ? 0 : rtnDstbCloseStoreVO.getOrderVat())     * slipFg;
	                Long mgrTot      = (rtnDstbCloseStoreVO.getOrderTot()     == null ? 0 : rtnDstbCloseStoreVO.getOrderTot())     * slipFg;
		                
	                if(mgrTotQty < 0) {
	                	rtnDstbCloseStoreVO.setStorageCd("999");
	                    rtnDstbCloseStoreVO.setMgrUnitQty(mgrUnitQty);
	                    rtnDstbCloseStoreVO.setMgrEtcQty(mgrEtcQty);
	                    rtnDstbCloseStoreVO.setMgrTotQty(mgrTotQty);
	                    rtnDstbCloseStoreVO.setMgrAmt(mgrAmt);
	                    rtnDstbCloseStoreVO.setMgrVat(mgrVat);
	                    rtnDstbCloseStoreVO.setMgrTot(mgrTot);
	                    rtnDstbCloseStoreVO.setMgrSplyUprc(mgrSplyUprc);
	                    rtnDstbCloseStoreVO.setProcFg("10");
	                    rtnDstbCloseStoreVO.setDstbFg("0");
	                    rtnDstbCloseStoreVO.setRegId(sessionInfoVO.getUserId());
	                    rtnDstbCloseStoreVO.setRegDt(currentDt);
	                    rtnDstbCloseStoreVO.setModId(sessionInfoVO.getUserId());
	                    rtnDstbCloseStoreVO.setModDt(currentDt);
	                    rtnDstbCloseStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

	                    result = rtnDstbCloseStoreMapper.insertDstbAdd(rtnDstbCloseStoreVO);
	                    if (result <= 0)
	                        throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
	                
	                }
	            }                   
            }          
            returnResult += result;
        }
        return returnResult;
    }


    /** 반품마감 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadVO.setRegId(sessionInfoVO.getUserId());
        excelUploadVO.setRegDt(currentDt);
        excelUploadVO.setModId(sessionInfoVO.getUserId());
        excelUploadVO.setModDt(currentDt);

        // 엑셀업로드 한 수량을 분배수량으로 입력
        result = rtnDstbCloseStoreMapper.insertRtnDstbToExcelUploadData(excelUploadVO);

        // 분배수량으로 정상 입력된 데이터 TEMP 테이블에서 삭제
        result = rtnDstbCloseStoreMapper.deleteExcelUploadCompleteData(excelUploadVO);

        return result;
    }
}
