package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdService;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service.RtnDstbCloseProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnDstbCloseProdService")
@Transactional
public class RtnDstbCloseProdServiceImpl implements RtnDstbCloseProdService {
    private final RtnDstbCloseProdMapper rtnDstbCloseProdMapper;
    private final MessageService messageService;

    public RtnDstbCloseProdServiceImpl(RtnDstbCloseProdMapper rtnDstbCloseProdMapper, MessageService messageService) {
        this.rtnDstbCloseProdMapper = rtnDstbCloseProdMapper;
        this.messageService = messageService;
    }

    /** 반품마감 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdList(rtnDstbCloseProdVO);
    }

    /** 반품마감 리스트 확정 */
    @Override
    public int saveRtnDstbCloseProdConfirm(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {
            rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseProdVO.setUpdateProcFg("20");
            rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setRegDt(currentDt);
            rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setModDt(currentDt);

            result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdConfirm(rtnDstbCloseProdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdDtlList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdDtlList(rtnDstbCloseProdVO);
    }

    /** 반품마감 상세 리스트 저장 */
    @Override
    public int saveRtnDstbCloseProdDtl(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {
            int slipFg     = rtnDstbCloseProdVO.getSlipFg();
            int mgrUnitQty = (rtnDstbCloseProdVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty  = (rtnDstbCloseProdVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrEtcQty()) * slipFg;
            int mgrTotQty  = (rtnDstbCloseProdVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrTotQty()) * slipFg;
            Long mgrAmt    = (rtnDstbCloseProdVO.getMgrAmt()     == null ? 0 : rtnDstbCloseProdVO.getMgrAmt()) * slipFg;
            Long mgrVat    = (rtnDstbCloseProdVO.getMgrVat()     == null ? 0 : rtnDstbCloseProdVO.getMgrVat()) * slipFg;
            Long mgrTot    = (rtnDstbCloseProdVO.getMgrTot()     == null ? 0 : rtnDstbCloseProdVO.getMgrTot()) * slipFg;

            rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnDstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
            rtnDstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
            rtnDstbCloseProdVO.setMgrTotQty(mgrTotQty);
            rtnDstbCloseProdVO.setMgrAmt(mgrAmt);
            rtnDstbCloseProdVO.setMgrVat(mgrVat);
            rtnDstbCloseProdVO.setMgrTot(mgrTot);
            rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setRegDt(currentDt);
            rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setModDt(currentDt);

            // 분배수량이 0 이나 null 인 경우 삭제
//            if(rtnDstbCloseProdVO.getMgrTotQty() == 0 || rtnDstbCloseProdVO.getMgrTotQty() == null) {
//                result = rtnDstbCloseProdMapper.deleteRtnDstbCloseProdDtl(rtnDstbCloseProdVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
                result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdDtl(rtnDstbCloseProdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                if(rtnDstbCloseProdVO.getConfirmYn()) {
                    rtnDstbCloseProdVO.setProcFg("20");
                    result = rtnDstbCloseProdMapper.updateRtnDstbCloseProdDtlConfirm(rtnDstbCloseProdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
//            }
            returnResult += result;
        }

        return returnResult;
    }

    /** 반품마감 추가등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdAddProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdAddProdList(rtnDstbCloseProdVO);
    }

    /** 반품마감 추가등록 분배등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnDstbCloseProdAddRegistList(RtnDstbCloseProdVO rtnDstbCloseProdVO) {
        if(!StringUtil.getOrBlank(rtnDstbCloseProdVO.getStoreCd()).equals("")) {
            rtnDstbCloseProdVO.setArrStoreCd(rtnDstbCloseProdVO.getStoreCd().split(","));
        }
        return rtnDstbCloseProdMapper.getRtnDstbCloseProdAddRegistList(rtnDstbCloseProdVO);
    }

    /** 반품마감 - 추가등록 분배등록 리스트 저장 */
    @Override
    public int saveRtnDstbCloseProdAddRegist(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        
//        String[] storageCd;
//        String[] storageNm;
//        String[] storageOrderUnitQty;
//        String[] storageOrderEtcQty;
//        String[] storageOrderTotQty;
//        String[] storageOrderAmt;
//        String[] storageOrderVat;
//        String[] storageOrderTot;
        
        for (RtnDstbCloseProdVO rtnDstbCloseProdVO : rtnDstbCloseProdVOs) {

            int slipFg       = rtnDstbCloseProdVO.getSlipFg();
            int poUnitQty    = rtnDstbCloseProdVO.getPoUnitQty();
            int mgrSplyUprc  = rtnDstbCloseProdVO.getMgrSplyUprc();
            int prevMgrUnitQty  = (rtnDstbCloseProdVO.getPrevMgrUnitQty() == null ? 0 : rtnDstbCloseProdVO.getPrevMgrUnitQty());
            int prevMgrEtcQty   = (rtnDstbCloseProdVO.getPrevMgrEtcQty()  == null ? 0 : rtnDstbCloseProdVO.getPrevMgrEtcQty());
            int prevMgrTotQty	= prevMgrUnitQty + prevMgrEtcQty;
//            int unitQty      = (rtnDstbCloseProdVO.getOrderUnitQty()     == null ? 0 : rtnDstbCloseProdVO.getOrderUnitQty());
//            int etcQty       = (rtnDstbCloseProdVO.getOrderEtcQty()      == null ? 0 : rtnDstbCloseProdVO.getOrderEtcQty());
//            int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
//            int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
//            int orderTotQty  = (rtnDstbCloseProdVO.getOrderTotQty()   == null ? 0 : rtnDstbCloseProdVO.getOrderTotQty()) * slipFg;
//            Long orderAmt    = (rtnDstbCloseProdVO.getOrderAmt()      == null ? 0 : rtnDstbCloseProdVO.getOrderAmt())    * slipFg;
//            Long orderVat    = (rtnDstbCloseProdVO.getOrderVat()      == null ? 0 : rtnDstbCloseProdVO.getOrderVat())    * slipFg;
//            Long orderTot    = (rtnDstbCloseProdVO.getOrderTot()      == null ? 0 : rtnDstbCloseProdVO.getOrderTot())    * slipFg;
//            
//            rtnDstbCloseProdVO.setOrderUnitQty(orderUnitQty);
//            rtnDstbCloseProdVO.setOrderEtcQty(orderEtcQty);
//            rtnDstbCloseProdVO.setOrderTotQty(orderTotQty);
//            rtnDstbCloseProdVO.setOrderAmt(orderAmt);
//            rtnDstbCloseProdVO.setOrderVat(orderVat);
//            rtnDstbCloseProdVO.setOrderTot(orderTot);
            rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setRegDt(currentDt);
            rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
            rtnDstbCloseProdVO.setModDt(currentDt);
            rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            
            int mgrUnitQty   = (rtnDstbCloseProdVO.getMgrUnitQty() == null ? 0 : rtnDstbCloseProdVO.getMgrUnitQty()) * slipFg;
            int mgrEtcQty    = (rtnDstbCloseProdVO.getMgrEtcQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrEtcQty())  * slipFg;
            int mgrTotQty    = (rtnDstbCloseProdVO.getMgrTotQty()  == null ? 0 : rtnDstbCloseProdVO.getMgrTotQty())  * slipFg;
            Long mgrAmt      = (rtnDstbCloseProdVO.getMgrAmt()     == null ? 0 : rtnDstbCloseProdVO.getMgrAmt())     * slipFg;
            Long mgrVat      = (rtnDstbCloseProdVO.getMgrVat()     == null ? 0 : rtnDstbCloseProdVO.getMgrVat())     * slipFg;
            Long mgrTot      = (rtnDstbCloseProdVO.getMgrTot()     == null ? 0 : rtnDstbCloseProdVO.getMgrTot())     * slipFg;

            if(mgrTotQty < 0) {

                rtnDstbCloseProdVO.setOrderSplyUprc             (mgrSplyUprc);
            	rtnDstbCloseProdVO.setOrderUnitQty		        (mgrUnitQty);	//입고수량 주문단위
            	rtnDstbCloseProdVO.setOrderEtcQty		        (mgrEtcQty);	//입고수량 나머지
            	rtnDstbCloseProdVO.setOrderTotQty		        (mgrTotQty);	//입고수량합계 낱개
            	rtnDstbCloseProdVO.setOrderAmt			        (mgrAmt);	//입고금액
            	rtnDstbCloseProdVO.setOrderVat			        (mgrVat);	//입고금액VAT
            	rtnDstbCloseProdVO.setOrderTot			        (mgrTot);	//입고금액합계
                rtnDstbCloseProdVO.setStorageCd("001"); //매장주문 정보는 재고처리안함 기본값 001 세팅
                rtnDstbCloseProdVO.setOutStorageCd("001");

                if(prevMgrTotQty > 0) {
            		result = rtnDstbCloseProdMapper.updateRtnStoreOrderProd(rtnDstbCloseProdVO);
            		 if(result < 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            	}else {
            		result = rtnDstbCloseProdMapper.savetRtnStoreOrderProd(rtnDstbCloseProdVO);
            		 if(result < 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            	}
            	
            	
            	rtnDstbCloseProdVO.setStorageCd("999");		            	
                rtnDstbCloseProdVO.setMgrUnitQty(mgrUnitQty);
                rtnDstbCloseProdVO.setMgrEtcQty(mgrEtcQty);
                rtnDstbCloseProdVO.setMgrTotQty(mgrTotQty);
                rtnDstbCloseProdVO.setMgrAmt(mgrAmt);
                rtnDstbCloseProdVO.setMgrVat(mgrVat);
                rtnDstbCloseProdVO.setMgrTot(mgrTot);
                rtnDstbCloseProdVO.setMgrSplyUprc(mgrSplyUprc);
                rtnDstbCloseProdVO.setProcFg("10");
                rtnDstbCloseProdVO.setDstbFg("0");
                rtnDstbCloseProdVO.setRegId(sessionInfoVO.getUserId());
                rtnDstbCloseProdVO.setRegDt(currentDt);
                rtnDstbCloseProdVO.setModId(sessionInfoVO.getUserId());
                rtnDstbCloseProdVO.setModDt(currentDt);
                rtnDstbCloseProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                result = rtnDstbCloseProdMapper.insertRtnDstbCloseProdAddRegist(rtnDstbCloseProdVO);
                if (result <= 0)
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));		
            }
	        returnResult += result;
        }
        return returnResult;
    }
}
