package kr.co.solbipos.iostock.move.standMove.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.move.standMove.service.StandMoveService;
import kr.co.solbipos.iostock.move.standMove.service.StandMoveVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("standMoveService")
@Transactional
public class StandMoveServiceImpl implements StandMoveService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    private final StandMoveMapper standMoveMapper;
    private final MessageService messageService;

    @Autowired
    public StandMoveServiceImpl(StandMoveMapper standMoveMapper, MessageService messageService) {
        this.standMoveMapper = standMoveMapper;
        this.messageService = messageService;
    }

    /** 매대이동관리 - 매대이동관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStandMoveList(StandMoveVO standMoveVO) {
        return standMoveMapper.getStandMoveList(standMoveVO);
    }

    /** 매대이동관리 - 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(StandMoveVO standMoveVO) {
        return standMoveMapper.getSlipNoInfo(standMoveVO);
    }

    /** 매대이동관리 - 매대이동관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStandMoveDtlList(StandMoveVO standMoveVO) {
    	
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";
        
        List<DefaultMap<String>> storage = standMoveMapper.getStorageList(standMoveVO);
    	
        for(int i = 0; i < storage.size(); i++) {

        	String k = storage.get(i).get("storageCd");
        	
        	sQuery1 += ", A.CURR_QTY_"+ k +"\n";
        	sQuery2 += ", NVL(B.CURR_QTY_"+ k +",0) AS CURR_QTY_"+ k +"\n";
        	sQuery3 += ", SUM(DECODE(B.STORAGE_CD,'"+ k +"',B.CURR_QTY,0))   AS   CURR_QTY_"+ k +"\n";
        }
        
        standMoveVO.setsQuery1(sQuery1);
        standMoveVO.setsQuery2(sQuery2);
        standMoveVO.setsQuery3(sQuery3);
    	
        return standMoveMapper.getStandMoveDtlList(standMoveVO);
    }

    /** 매대이동관리 - 매대이동관리 상세 리스트 저장 */
    @Override
    public int saveStandMoveDtl(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        StandMoveVO standMoveHdVO = new StandMoveVO();

        for (StandMoveVO standMoveVO : standMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(standMoveVO.getConfirmFg());

                standMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                standMoveHdVO.setSlipNo(standMoveVO.getSlipNo());
                standMoveHdVO.setDlvrFg(standMoveVO.getDlvrFg());
                standMoveHdVO.setRemark(standMoveVO.getRemark());
                standMoveHdVO.setProcFg(standMoveVO.getProcFg());
                standMoveHdVO.setRegFg(sessionInfoVO.getOrgnFg());
                standMoveHdVO.setRegId(sessionInfoVO.getUserId());
                standMoveHdVO.setRegDt(currentDt);
                standMoveHdVO.setModId(sessionInfoVO.getUserId());
                standMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outUnitQty    = (standMoveVO.getOutUnitQty()    == null ? 0 : standMoveVO.getOutUnitQty());
            int outEtcQty     = (standMoveVO.getOutEtcQty()     == null ? 0 : standMoveVO.getOutEtcQty());
            int outTotQty     = (standMoveVO.getOutTotQty()     == null ? 0 : standMoveVO.getOutTotQty());
            int prevOutTotQty = (standMoveVO.getPrevOutTotQty() == null ? 0 : standMoveVO.getPrevOutTotQty());

            // 수량 변경이 있는 경우만 DTL 수정
            if(prevOutTotQty != outTotQty) {
                int outSplyUprc  = standMoveVO.getOutSplyUprc();
                int inSplyUprc   = standMoveVO.getInSplyUprc();
                int poUnitQty    = standMoveVO.getPoUnitQty();
                int vat01        = Integer.parseInt(standMoveVO.getVatFg01());
                int outEnvst0011 = Integer.parseInt(standMoveVO.getOutEnvst0011());
                int inEnvst0011  = Integer.parseInt(standMoveVO.getInEnvst0011());

                int unitQty     = outUnitQty * poUnitQty;
                int etcQty      = outEtcQty;
                int totQty      = unitQty + etcQty;
                Long tempOutAmt = Long.valueOf(totQty * outSplyUprc / poUnitQty);
                Long tempInAmt  = Long.valueOf(totQty * inSplyUprc / poUnitQty);
                Long outAmt     = tempOutAmt - (tempOutAmt * vat01 * outEnvst0011 / 11);
                Long outVat     = tempOutAmt * vat01 / (10 + outEnvst0011);
                Long outTot     = outAmt + outVat;
                Long inAmt      = tempInAmt - (tempInAmt * vat01 * inEnvst0011 / 11);
                Long inVat      = tempInAmt * vat01 / (10 + inEnvst0011);
                Long inTot      = inAmt + inVat;

                standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                standMoveVO.setOutUnitQty(unitQty);
                standMoveVO.setOutEtcQty(etcQty);
                standMoveVO.setOutTotQty(totQty);
                standMoveVO.setOutAmt(outAmt);
                standMoveVO.setOutVat(outVat);
                standMoveVO.setOutTot(outTot);
                standMoveVO.setInUnitQty(unitQty);
                standMoveVO.setInEtcQty(etcQty);
                standMoveVO.setInTotQty(totQty);
                standMoveVO.setInAmt(inAmt);
                standMoveVO.setInVat(inVat);
                standMoveVO.setInTot(inTot);
                standMoveVO.setRegId(sessionInfoVO.getUserId());
                standMoveVO.setRegDt(currentDt);
                standMoveVO.setModId(sessionInfoVO.getUserId());
                standMoveVO.setModDt(currentDt);

                // DTL 수정
                result = standMoveMapper.updateStandMoveDtl(standMoveVO);
                if (result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                returnResult += result;
            }
            i++;
        }

        // HD 수정
        result = standMoveMapper.updateStandMoveHd(standMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 확정인 경우
        if(confirmFg.equals("Y")) {
            String procFg = String.valueOf(Integer.parseInt(standMoveHdVO.getProcFg())+1);
            standMoveHdVO.setProcFg(procFg);

            // HD의 수정
            result = standMoveMapper.updateStandMoveConfirm(standMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 매대이동관리 - 매대이동관리 상세 삭제 */
    @Override
    public int deleteStandMoveDtl(StandMoveVO standMoveVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // DTL 삭제
        result = standMoveMapper.deleteAllStandMoveDtl(standMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // HD 삭제
        result = standMoveMapper.deleteStandMoveHd(standMoveVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return result;
    }


    /** 매대이동관리 - 매대이동관리 신규등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStandMoveRegistList(StandMoveVO standMoveVO) {
    	
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";
        
        List<DefaultMap<String>> storage = standMoveMapper.getStorageList(standMoveVO);
    	
        for(int i = 0; i < storage.size(); i++) {

        	String k = storage.get(i).get("storageCd");
        	
        	sQuery1 += ", A.CURR_QTY_"+ k +"\n";
        	sQuery2 += ", NVL(B.CURR_QTY_"+ k +",0) AS CURR_QTY_"+ k +"\n";
        	sQuery3 += ", SUM(DECODE(B.STORAGE_CD,'"+ k +"',B.CURR_QTY,0))   AS   CURR_QTY_"+ k +"\n";
        }
        
        standMoveVO.setsQuery1(sQuery1);
        standMoveVO.setsQuery2(sQuery2);
        standMoveVO.setsQuery3(sQuery3);
        
        return standMoveMapper.getStandMoveRegistList(standMoveVO);
    }


    /** 매대이동관리 - 매대이동관리 신규등록 리스트 저장 */
    @Override
    public int saveStandMoveRegist(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";
        
        String[] storageCd;
        String[] storageNm;
        String[] storageUnitQty;
        String[] storageEtcQty;
        String[] storageTotQty;
        
        // 전표번호 생성
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        StandMoveVO newSlipNoVO = new StandMoveVO();
        newSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        newSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
        newSlipNoVO.setYymm(yymm);
        String newSlipNo = standMoveMapper.getNewSlipNo(newSlipNoVO);

        StandMoveVO standMoveHdVO = new StandMoveVO();

        for (StandMoveVO standMoveVO : standMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(standMoveVO.getConfirmFg());

                standMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                standMoveHdVO.setStoreCd(standMoveVO.getStoreCd());
                standMoveHdVO.setSlipNo(newSlipNo);
                standMoveHdVO.setSlipFg(standMoveVO.getSlipFg());
                standMoveHdVO.setMoveDate(standMoveVO.getMoveDate());
                standMoveHdVO.setRegId(sessionInfoVO.getUserId());
                standMoveHdVO.setRegDt(currentDt);
                standMoveHdVO.setModId(sessionInfoVO.getUserId());
                standMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (standMoveVO.getOutEtcQty() == null ? 0 : standMoveVO.getOutEtcQty());

            standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            standMoveVO.setSlipNo(newSlipNo);
            standMoveVO.setOutEtcQty(outEtcQty);
            standMoveVO.setInUnitQty(standMoveVO.getOutUnitQty());
            standMoveVO.setInEtcQty(outEtcQty);
            standMoveVO.setInTotQty(standMoveVO.getOutTotQty());
            standMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            standMoveVO.setRegId(sessionInfoVO.getUserId());
            standMoveVO.setRegDt(currentDt);
            standMoveVO.setModId(sessionInfoVO.getUserId());
            standMoveVO.setModDt(currentDt);
            
            int standTotQty = (standMoveVO.getStandTotQty() == null ? 0 : standMoveVO.getStandTotQty());
            
            // DTL 등록
            if(standTotQty != 0) {
	            //TB_PO_HQ_STORE_ORDER_PROD - START
	        	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
	            storageCd           	= standMoveVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
	            storageNm           	= standMoveVO.getArrStorageNm().split("\\^");
	            storageUnitQty    		= standMoveVO.getArrUnitQty().split("\\^");
	            storageEtcQty     		= standMoveVO.getArrEtcQty().split("\\^");
	            storageTotQty     		= standMoveVO.getArrTotQty().split("\\^");
	
	            for(int k=0; k<storageCd.length; k++) {
		            LOGGER.debug("### storageUnitQty: " + storageUnitQty[k]	);
		            LOGGER.debug("### storageEtcQty : " + storageEtcQty	[k]	);
		            LOGGER.debug("### storageTotQty : " + storageTotQty	[k]	);
	
		            standMoveVO.setStorageCd			(storageCd[k]					);	//창고코드
	
		            standMoveVO.setUnitQty		        (Integer.parseInt	(storageUnitQty	[k]));	//입고수량 주문단위
		            standMoveVO.setEtcQty		        (Integer.parseInt	(storageEtcQty		[k]));	//입고수량 나머지
		            standMoveVO.setTotQty		        (Integer.parseInt	(storageTotQty		[k]));	//입고수량합계 낱개
	
		            standMoveVO.setRegId			        (sessionInfoVO.getUserId());
		            standMoveVO.setRegDt			        (currentDt	);
		            standMoveVO.setModId			        (sessionInfoVO.getUserId());
		            standMoveVO.setModDt			        (currentDt	);
	
	            	LOGGER.debug("### getProperties: " + standMoveVO.getProperties() );
	
	            	result = standMoveMapper.mergeStandMoveDtl(standMoveVO);
	                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            }
	            //TB_PO_HQ_STORE_ORDER_PROD - END                      
	            
	            returnResult += result;
	            i++;
            }
        }

        // HD 등록
        result = standMoveMapper.insertStandMoveHd(standMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        // 확정인 경우
        if(confirmFg.equals("Y")) {

            // HD의 수정
            result = standMoveMapper.updateStandMoveConfirm(standMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            
            // PROD 입력
            if(standMoveHdVO.getSlipFg().equals(1)) {
            	standMoveHdVO.setOccrFg("51");
            }else if(standMoveHdVO.getSlipFg().equals(-1)) {
            	standMoveHdVO.setOccrFg("52");
            }  
            standMoveHdVO.setConfmYn("Y");
            result = standMoveMapper.insertRtnStoreOutStockProd(standMoveHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }

    /** 매대이동관리 - 매대이동관리 상품추가 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStandMoveAddProdList(StandMoveVO standMoveVO) {
        return standMoveMapper.getStandMoveRegistList(standMoveVO);
    }

    /** 매대이동관리 - 매대이동관리 상품추가 리스트 저장 */
    @Override
    public int saveStandMoveAddProd(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        StandMoveVO standMoveHdVO = new StandMoveVO();

        for (StandMoveVO standMoveVO : standMoveVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                standMoveHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                standMoveHdVO.setSlipNo(standMoveVO.getSlipNo());
                standMoveHdVO.setRegId(sessionInfoVO.getUserId());
                standMoveHdVO.setRegDt(currentDt);
                standMoveHdVO.setModId(sessionInfoVO.getUserId());
                standMoveHdVO.setModDt(currentDt);
            }

            // 나머지수량은 null 이 들어올수 있으므로 null 인 경우 0으로 변환.
            int outEtcQty = (standMoveVO.getOutEtcQty() == null ? 0 : standMoveVO.getOutEtcQty());

            standMoveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            standMoveVO.setOutEtcQty(outEtcQty);
            standMoveVO.setInUnitQty(standMoveVO.getOutUnitQty());
            standMoveVO.setInEtcQty(outEtcQty);
            standMoveVO.setInTotQty(standMoveVO.getOutTotQty());
            standMoveVO.setRegFg(sessionInfoVO.getOrgnFg());
            standMoveVO.setRegId(sessionInfoVO.getUserId());
            standMoveVO.setRegDt(currentDt);
            standMoveVO.setModId(sessionInfoVO.getUserId());
            standMoveVO.setModDt(currentDt);

            // DTL 등록
            result = standMoveMapper.insertStandMoveDtl(standMoveVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        // HD 수정
        result = standMoveMapper.updateStandMoveAddProdHd(standMoveHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
