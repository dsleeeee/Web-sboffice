package kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.code.impl.CmmEnvMapper;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.RtnOutstockConfmService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service.RtnOutstockConfmVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnOutstockConfmService")
@Transactional
public class RtnOutstockConfmServiceImpl implements RtnOutstockConfmService {
    private final RtnOutstockConfmMapper rtnOutstockConfmMapper;
    private final CmmEnvMapper cmmEnvMapper;
    private final MessageService messageService;

    public RtnOutstockConfmServiceImpl(RtnOutstockConfmMapper rtnOutstockConfmMapper, CmmEnvMapper cmmEnvMapper, MessageService messageService) {
        this.rtnOutstockConfmMapper = rtnOutstockConfmMapper;
        this.cmmEnvMapper = cmmEnvMapper;
        this.messageService = messageService;
    }

    /** 반품매장출고 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    @Override
    public DefaultMap<String> getReqNoConfirmCnt(RtnOutstockConfmVO rtnOutstockConfmVO) {
        return rtnOutstockConfmMapper.getReqNoConfirmCnt(rtnOutstockConfmVO);
    }

    /** 반품매장출고 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnOutstockConfmList(RtnOutstockConfmVO rtnOutstockConfmVO) {
        return rtnOutstockConfmMapper.getRtnOutstockConfmList(rtnOutstockConfmVO);
    }

    /** 반품매장출고 - 반품매장출고 */
    @Override
    public int saveOutstockConfirm(RtnOutstockConfmVO[] rtnOutstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        for (RtnOutstockConfmVO rtnOutstockConfmVO : rtnOutstockConfmVOs) {
            rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnOutstockConfmVO.setRegId(sessionInfoVO.getUserId());
            rtnOutstockConfmVO.setRegDt(currentDt);
            rtnOutstockConfmVO.setModId(sessionInfoVO.getUserId());
            rtnOutstockConfmVO.setModDt(currentDt);

            rtnOutstockConfmVO.setProcFg("10");
            rtnOutstockConfmVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnOutstockConfmMapper.updateOutstockDtlConfirm(rtnOutstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnOutstockConfmMapper.updateOutstockConfirm(rtnOutstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(envst1043).equals("A")) {
                rtnOutstockConfmVO.setProcFg("20");
                rtnOutstockConfmVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnOutstockConfmMapper.updateAutoInstockDtl(rtnOutstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnOutstockConfmMapper.updateAutoInstock(rtnOutstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(RtnOutstockConfmVO rtnOutstockConfmVO) {
        return rtnOutstockConfmMapper.getSlipNoInfo(rtnOutstockConfmVO);
    }

    /** 반품매장출고 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnOutstockConfmDtlList(RtnOutstockConfmVO rtnOutstockConfmVO) {
        return rtnOutstockConfmMapper.getRtnOutstockConfmDtlList(rtnOutstockConfmVO);
    }

    /** 반품매장출고 - 반품매장출고 상세 리스트 저장 */
    @Override
    public int saveRtnOutstockConfmDtl(RtnOutstockConfmVO[] rtnOutstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";
        
        String[] storageCd;
        String[] storageNm;
        String[] storageOrderUnitQty;
        String[] storageOrderEtcQty;
        String[] storageOrderTotQty;
        String[] storageOrderAmt;
        String[] storageOrderVat;
        String[] storageOrderTot;
        
        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        RtnOutstockConfmVO rtnOutstockConfmHdVO = new RtnOutstockConfmVO();
             
        for (RtnOutstockConfmVO rtnOutstockConfmVO : rtnOutstockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(rtnOutstockConfmVO.getConfirmFg());

                rtnOutstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                rtnOutstockConfmHdVO.setSlipNo(rtnOutstockConfmVO.getSlipNo());
                rtnOutstockConfmHdVO.setReqDate(rtnOutstockConfmVO.getReqDate());
                rtnOutstockConfmHdVO.setStoreCd(rtnOutstockConfmVO.getStoreCd());
                rtnOutstockConfmHdVO.setProdCd(rtnOutstockConfmVO.getProdCd());
                rtnOutstockConfmHdVO.setHdRemark(rtnOutstockConfmVO.getHdRemark());
                rtnOutstockConfmHdVO.setHqRemark(rtnOutstockConfmVO.getHqRemark());
                rtnOutstockConfmHdVO.setDlvrCd(rtnOutstockConfmVO.getDlvrCd());
                rtnOutstockConfmHdVO.setOutDate(rtnOutstockConfmVO.getOutDate());
                rtnOutstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                rtnOutstockConfmHdVO.setRegDt(currentDt);
                rtnOutstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                rtnOutstockConfmHdVO.setModDt(currentDt);
            }

            int slipFg     = rtnOutstockConfmVO.getSlipFg();
            int outUnitQty = (rtnOutstockConfmVO.getOutUnitQty() == null ? 0 : rtnOutstockConfmVO.getOutUnitQty()) * slipFg;
            int outEtcQty  = (rtnOutstockConfmVO.getOutEtcQty()  == null ? 0 : rtnOutstockConfmVO.getOutEtcQty()) * slipFg;
            int outTotQty  = (rtnOutstockConfmVO.getOutTotQty()  == null ? 0 : rtnOutstockConfmVO.getOutTotQty()) * slipFg;
            Long outAmt    = (rtnOutstockConfmVO.getOutAmt()     == null ? 0 : rtnOutstockConfmVO.getOutAmt()) * slipFg;
            Long outVat    = (rtnOutstockConfmVO.getOutVat()     == null ? 0 : rtnOutstockConfmVO.getOutVat()) * slipFg;
            Long outTot    = (rtnOutstockConfmVO.getOutTot()     == null ? 0 : rtnOutstockConfmVO.getOutTot()) * slipFg;

            rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnOutstockConfmVO.setOutUnitQty(outUnitQty);
            rtnOutstockConfmVO.setOutEtcQty(outEtcQty);
            rtnOutstockConfmVO.setOutTotQty(outTotQty);
            rtnOutstockConfmVO.setOutAmt(outAmt);
            rtnOutstockConfmVO.setOutVat(outVat);
            rtnOutstockConfmVO.setOutTot(outTot);
            rtnOutstockConfmVO.setRegId(sessionInfoVO.getUserId());
            rtnOutstockConfmVO.setRegDt(currentDt);
            rtnOutstockConfmVO.setModId(sessionInfoVO.getUserId());
            rtnOutstockConfmVO.setModDt(currentDt);
            rtnOutstockConfmVO.setPoUnitQty(rtnOutstockConfmVO.getOrderTotQty());
            
            // DTL 수정
            result = rtnOutstockConfmMapper.updateRtnOutstockConfmDtl(rtnOutstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                       
            returnResult += result;
            i++;
        }
        
        // HD 수정
        result = rtnOutstockConfmMapper.updateRtnOutstockConfmHd(rtnOutstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        
        // PROD 삭제
        result = rtnOutstockConfmMapper.deleteOrderProd(rtnOutstockConfmHdVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        
        // PROD 삭제
        result = rtnOutstockConfmMapper.deleteOutStockProd(rtnOutstockConfmHdVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                
        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            rtnOutstockConfmHdVO.setProcFg("10");
            rtnOutstockConfmHdVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnOutstockConfmMapper.updateOutstockDtlConfirm(rtnOutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        
            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnOutstockConfmMapper.updateOutstockConfirm(rtnOutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(envst1043).equals("A")) {
                rtnOutstockConfmHdVO.setProcFg("20");
                rtnOutstockConfmHdVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnOutstockConfmMapper.updateAutoInstockDtl(rtnOutstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnOutstockConfmMapper.updateAutoInstock(rtnOutstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        
        for (RtnOutstockConfmVO rtnOutstockConfmVO : rtnOutstockConfmVOs) {
          
            int orderTotQty = (rtnOutstockConfmVO.getOrderTotQty() == null ? 0 : rtnOutstockConfmVO.getOrderTotQty());
            if(orderTotQty != 0) {
            	
            	//TB_PO_HQ_STORE_ORDER_PROD - START
            	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
	            storageCd           	= rtnOutstockConfmVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
	            storageNm           	= rtnOutstockConfmVO.getArrStorageNm().split("\\^");
	            storageOrderUnitQty     = rtnOutstockConfmVO.getArrOrderUnitQty().split("\\^");
	            storageOrderEtcQty      = rtnOutstockConfmVO.getArrOrderEtcQty().split("\\^");
	            storageOrderTotQty      = rtnOutstockConfmVO.getArrOrderTotQty().split("\\^");
	            storageOrderAmt         = rtnOutstockConfmVO.getArrOrderAmt().split("\\^");
	            storageOrderVat         = rtnOutstockConfmVO.getArrOrderVat().split("\\^");
	            storageOrderTot         = rtnOutstockConfmVO.getArrOrderTot().split("\\^");
	            
	            for(int k=0; k<storageCd.length; k++) {

	            	rtnOutstockConfmVO.setStorageCd					(storageCd[k]					);	//창고코드
	            	rtnOutstockConfmVO.setSlipFg		        	(1								);	//전표구분 1:주문 -1:반품

	            	rtnOutstockConfmVO.setOrderUnitQty		        (Integer.parseInt	(storageOrderUnitQty	[k]));	//입고수량 주문단위
	            	rtnOutstockConfmVO.setOrderEtcQty		        (Integer.parseInt	(storageOrderEtcQty		[k]));	//입고수량 나머지
	            	rtnOutstockConfmVO.setOrderTotQty		        (Integer.parseInt	(storageOrderTotQty		[k]));	//입고수량합계 낱개
	            	rtnOutstockConfmVO.setOrderStorageAmt			(Long.parseLong		(storageOrderAmt		[k]));	//입고금액
	            	rtnOutstockConfmVO.setOrderStorageVat			(Long.parseLong		(storageOrderVat		[k]));	//입고금액VAT
	            	rtnOutstockConfmVO.setOrderStorageTot			(Long.parseLong		(storageOrderTot		[k]));	//입고금액합계

	            	rtnOutstockConfmVO.setRegId			        	(sessionInfoVO.getUserId());
	            	rtnOutstockConfmVO.setRegDt			        	(currentDt	);
	            	rtnOutstockConfmVO.setModId			        	(sessionInfoVO.getUserId());
	            	rtnOutstockConfmVO.setModDt			        	(currentDt	);
		            
            		result = rtnOutstockConfmMapper.savetRtnStoreOrderProd(rtnOutstockConfmVO);
            		if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

	            }
            }
            
            returnResult += result;
            i++;
        }
        
        // PROD 입력
        result = rtnOutstockConfmMapper.insertOutStockProd(rtnOutstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        
        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            rtnOutstockConfmHdVO.setProcFg("10");
            rtnOutstockConfmHdVO.setUpdateProcFg("20");          
            // PROD의 진행구분 수정. 수주확정 -> 출고확정
            result = rtnOutstockConfmMapper.updateOutstockProdConfirm(rtnOutstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));        
        }
        
        return returnResult;
    }

    /** 반품매장출고 이후 저장 */
    @Override
    public int saveOutstockAfter(RtnOutstockConfmVO rtnOutstockConfmVO, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        rtnOutstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        rtnOutstockConfmVO.setHdRemark(rtnOutstockConfmVO.getHdRemark());
        rtnOutstockConfmVO.setHqRemark(rtnOutstockConfmVO.getHqRemark());
        rtnOutstockConfmVO.setDlvrCd(rtnOutstockConfmVO.getDlvrCd());
        rtnOutstockConfmVO.setRegId(sessionInfoVO.getUserId());
        rtnOutstockConfmVO.setRegDt(currentDt);
        rtnOutstockConfmVO.setModId(sessionInfoVO.getUserId());
        rtnOutstockConfmVO.setModDt(currentDt);

        // HD 수정
        result = rtnOutstockConfmMapper.updateOutstockAfterHd(rtnOutstockConfmVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        returnResult += result;

        return returnResult;
    }
}
