package kr.co.solbipos.iostock.order.outstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.code.impl.CmmEnvMapper;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmService;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("outstockConfmService")
@Transactional
public class OutstockConfmServiceImpl implements OutstockConfmService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OutstockConfmMapper outstockConfmMapper;
    private final CmmEnvMapper cmmEnvMapper;
    private final MessageService messageService;

    public OutstockConfmServiceImpl(OutstockConfmMapper outstockConfmMapper, CmmEnvMapper cmmEnvMapper, MessageService messageService) {
        this.outstockConfmMapper = outstockConfmMapper;
        this.cmmEnvMapper = cmmEnvMapper;
        this.messageService = messageService;
    }

    /** 출고확정 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    @Override
    public DefaultMap<String> getReqNoConfirmCnt(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getReqNoConfirmCnt(outstockConfmVO);
    }

    /** 출고확정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockConfmList(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getOutstockConfmList(outstockConfmVO);
    }

    /** 출고확정 - 출고확정 */
    @Override
    public int saveOutstockConfirm(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        for (OutstockConfmVO outstockConfmVO : outstockConfmVOs) {
            outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockConfmVO.setRegId(sessionInfoVO.getUserId());
            outstockConfmVO.setRegDt(currentDt);
            outstockConfmVO.setModId(sessionInfoVO.getUserId());
            outstockConfmVO.setModDt(currentDt);

            outstockConfmVO.setProcFg("10");
            outstockConfmVO.setUpdateProcFg("20");

            String slipKind	= outstockConfmVO.getSlipKind();
            String occrFg	= (slipKind.equals("1") ? "13" : "13");
            outstockConfmVO.setAreaFg(sessionInfoVO.getAreaFg());
            outstockConfmVO.setOccrFg(occrFg);
            // 확정상태 확인 PROC_FG > 10
            result = outstockConfmMapper.getOutstockConfirmCnt(outstockConfmVO);

            // result 1이상이면 이미 출고확정 이후 상태임.[처리구분] TB_CM_NMCODE(NMCODE_GRP_CD='113') 10:수주확정 20:출고확정 30:입고확정]
            if(result <= 0)
            {
                // DTL의 진행구분 수정. 수주확정 -> 출고확정
                result = outstockConfmMapper.updateOutstockDtlConfirm(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 수주확정 -> 출고확정
                result = outstockConfmMapper.updateOutstockConfirm(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //PROD 입력전 다른 창고데이터는 삭제 진행(SLIP_NO, PROD_CD, <> STORAGE_CD, <> OCCR_FG)
                //저장시 불필요한 창고 정보는 삭제 하기 때문에 필요없는 로직
                //result = outstockConfmMapper.deleteOutstockProdConfirm(outstockConfmVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //PROD 입력. 수주확정 -> 출고확정
                //result = outstockConfmMapper.insertOutstockProdConfirm(outstockConfmVO);
                result = outstockConfmMapper.mergeOutstockProdConfirm(outstockConfmVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 자동입고인 경우 입고로 수정
                if(StringUtil.getOrBlank(envst1043).equals("A")) {
                    outstockConfmVO.setProcFg("20");
                    outstockConfmVO.setUpdateProcFg("30");

                    // DTL의 진행구분 수정. 출고확정 -> 입고확정
                    result = outstockConfmMapper.updateAutoInstockDtl(outstockConfmVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    //dtl->prod 시에 dtl(seq)과prod(prod)의 키값이 달라서 전체 삭제후 적제 필요 20200916
                    result = outstockConfmMapper.deleteAutoInstockProdAll(outstockConfmVO);

                    result = outstockConfmMapper.mergeAutoInstockConfmProd(outstockConfmVO);
                    if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                    //HD 수정
                    result = outstockConfmMapper.updateAutoInstockConfmHd(outstockConfmVO);
                    if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                    // 입고확정상태 확인 PROC_FG > 20
                    // 이미 확정한 경우 저장 및 확정하면 안됨 exception 처리 진행.
                    result = outstockConfmMapper.getAutoInstockConfirmCnt(outstockConfmVO);

                    // result 1이상이면 이미 입고확정 상태임.[처리구분] TB_CM_NMCODE(NMCODE_GRP_CD='113') 10:수주확정 20:출고확정 30:입고확정]
                    if(result <= 0) {
                        // DTL의 진행구분 수정. 출고확정 -> 입고확정
                        result = outstockConfmMapper.updateAutoInstockDtlConfirm(outstockConfmVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // HD의 진행구분 수정. 출고확정 -> 입고확정
                        result = outstockConfmMapper.updateAutoInstock(outstockConfmVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        //PROD 진행구분 수정. 수주확정 -> 출고확정
                        result = outstockConfmMapper.updateAutoInstockProdConfirm(outstockConfmVO);
                    }
                }
            }

            returnResult += result;
        }

        return returnResult;
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(OutstockConfmVO outstockConfmVO) {
        return outstockConfmMapper.getSlipNoInfo(outstockConfmVO);
    }

    /** 출고확정 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockConfmDtlList(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO) {

    	// regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
    	outstockConfmVO = setSessionValue(outstockConfmVO, sessionInfoVO, null);

        return outstockConfmMapper.getOutstockConfmDtlList(outstockConfmVO);
    }

    /** 출고확정 - 출고확정 상세 리스트 저장 */
    @Override
    public int saveOutstockConfmDtl(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String confirmFg = "N";

        // 매장입고 환경변수 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1043");
        String envst1043 = cmmEnvMapper.getHqEnvst(hqEnvstVO);

        OutstockConfmVO outstockConfmHdVO = new OutstockConfmVO();

        //TB_PO_HQ_STORE_OUTSTOCK_PROD Insert or Update에 사용
//        String[] storageCd;
//        String[] storageNm;
//        String[] storageInUnitQty;
//        String[] storageInEtcQty;
//        String[] storageInTotQty;
//        String[] storageInAmt;
//        String[] storageInVat;
//        String[] storageInTot;

        for (OutstockConfmVO outstockConfmVO : outstockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(outstockConfmVO.getConfirmFg());

                outstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                outstockConfmHdVO.setSlipNo(outstockConfmVO.getSlipNo());
                outstockConfmHdVO.setHdRemark(outstockConfmVO.getHdRemark());
                outstockConfmHdVO.setHqRemark(outstockConfmVO.getHqRemark());
                outstockConfmHdVO.setDlvrCd(outstockConfmVO.getDlvrCd());
                outstockConfmHdVO.setOutDate(outstockConfmVO.getOutDate());
                outstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                outstockConfmHdVO.setRegDt(currentDt);
                outstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                outstockConfmHdVO.setModDt(currentDt);

            }

            int slipFg     	= outstockConfmVO.getSlipFg();
            String slipKind	= outstockConfmVO.getSlipKind();
            String occrFg	= (slipKind.equals("1") ? "13" : "13"); //출고 확정은 13
            int outUnitQty 	= (outstockConfmVO.getOutUnitQty() == null ? 0 : outstockConfmVO.getOutUnitQty()) * slipFg;
            int outEtcQty  	= (outstockConfmVO.getOutEtcQty()  == null ? 0 : outstockConfmVO.getOutEtcQty()) * slipFg;
            int outTotQty  	= (outstockConfmVO.getOutTotQty()  == null ? 0 : outstockConfmVO.getOutTotQty()) * slipFg;
            Long outAmt    	= (outstockConfmVO.getOutAmt()     == null ? 0 : outstockConfmVO.getOutAmt()) * slipFg;
            Long outVat    	= (outstockConfmVO.getOutVat()     == null ? 0 : outstockConfmVO.getOutVat()) * slipFg;
            Long outTot    	= (outstockConfmVO.getOutTot()     == null ? 0 : outstockConfmVO.getOutTot()) * slipFg;

            outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockConfmVO.setOutUnitQty(outUnitQty);
            outstockConfmVO.setOutEtcQty(outEtcQty);
            outstockConfmVO.setOutTotQty(outTotQty);
            outstockConfmVO.setOutAmt(outAmt);
            outstockConfmVO.setOutVat(outVat);
            outstockConfmVO.setOutTot(outTot);
            outstockConfmVO.setRegId(sessionInfoVO.getUserId());
            outstockConfmVO.setRegDt(currentDt);
            outstockConfmVO.setModId(sessionInfoVO.getUserId());
            outstockConfmVO.setModDt(currentDt);

            // DTL 수정
            result = outstockConfmMapper.updateOutstockConfmDtl(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            outstockConfmVO.setHqOfficeCd			(outstockConfmHdVO	.getHqOfficeCd	()		);	//본사코드
            outstockConfmVO.setSlipNo				(outstockConfmHdVO	.getSlipNo		()		);	//전표번호
            outstockConfmVO.setProdCd				(outstockConfmVO	.getProdCd		()		);	//상품코드
            outstockConfmVO.setStorageCd			(outstockConfmVO	.getOutStorageCd()		);	//출고창고코드
            outstockConfmVO.setOccrFg				(occrFg										);	//발생구분(13:본사출고)
            outstockConfmVO.setStoreCd		        (sessionInfoVO		.getStoreCd		()		);	//매장코드
            outstockConfmVO.setSlipFg		        (1											);	//전표구분 1:주문 -1:반품

            outstockConfmVO.setConfmYn				(confirmFg.equals("Y")? "Y":"N"				);	//확정여부(Y/N) - Trigger가  'Y'인것만 읽어서 처리하는데 사용

            outstockConfmVO.setInUnitQty		    (outUnitQty									);	//입고수량 주문단위
            outstockConfmVO.setInEtcQty		        (outEtcQty									);	//입고수량 나머지
            outstockConfmVO.setInTotQty		        (outTotQty									);	//입고수량합계 낱개
            outstockConfmVO.setInAmt			    (outAmt										);	//입고금액
            outstockConfmVO.setInVat			    (outVat										);	//입고금액VAT
            outstockConfmVO.setInTot			    (outTot										);	//입고금액합계

        	LOGGER.debug("### getProperties: " + outstockConfmVO.getProperties() );

            if(i == 0) {
                //dtl->prod 시에 dtl(seq)과prod(prod)의 키값이 달라서 전체 삭제후 적제 필요 20200916
                result = outstockConfmMapper.deleteOutstockProdAll(outstockConfmVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

        	result = outstockConfmMapper.mergeInstockConfmProd(outstockConfmVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));


            returnResult += result;
            i++;

            //TB_PO_HQ_STORE_OUTSTOCK_PROD - START
        	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
//            storageCd           = outstockConfmVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
//            storageNm           = outstockConfmVO.getArrStorageNm().split("\\^");
//            storageInUnitQty    = outstockConfmVO.getArrInUnitQty().split("\\^");
//            storageInEtcQty     = outstockConfmVO.getArrInEtcQty ().split("\\^");
//            storageInTotQty     = outstockConfmVO.getArrInTotQty ().split("\\^");
//            storageInAmt        = outstockConfmVO.getArrInAmt	 ().split("\\^");
//            storageInVat        = outstockConfmVO.getArrInVat	 ().split("\\^");
//            storageInTot        = outstockConfmVO.getArrInTot	 ().split("\\^");
//
//            for(int k=0; k<storageCd.length; k++) {
//            	LOGGER.debug("### confmYn         : " + confirmFg			);
//	            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
//	            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
//	            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
//	            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
//	            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
//	            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);
//
//	            outstockConfmVO.setHqOfficeCd			(outstockConfmHdVO	.getHqOfficeCd	()		);	//본사코드
//	            outstockConfmVO.setSlipNo				(outstockConfmHdVO	.getSlipNo		()		);	//전표번호
//	            outstockConfmVO.setProdCd				(outstockConfmVO	.getProdCd		()	);	//상품코드
//	            outstockConfmVO.setStorageCd			(storageCd[k]								);	//창고코드
//	            outstockConfmVO.setOccrFg				(occrFg										);	//발생구분(13:본사출고)
//	            outstockConfmVO.setStoreCd		        (sessionInfoVO		.getStoreCd		()		);	//매장코드
//	            outstockConfmVO.setSlipFg		        (1											);	//전표구분 1:주문 -1:반품
//
//	            outstockConfmVO.setConfmYn				("Y"										);	//확정여부(Y/N) - Trigger가  'Y'인것만 읽어서 처리하는데 사용
//	            outstockConfmVO.setInUnitQty		    (slipKind.equals("1")	?	Integer.parseInt	(storageInUnitQty	[k]) * -1 : Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
//	            outstockConfmVO.setInEtcQty		        (slipKind.equals("1")	?	Integer.parseInt	(storageInEtcQty	[k]) * -1 : Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
//	            outstockConfmVO.setInTotQty		        (slipKind.equals("1")	?	Integer.parseInt	(storageInTotQty	[k]) * -1 : Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
//	            outstockConfmVO.setInAmt			    (slipKind.equals("1")	?	Long.parseLong		(storageInAmt		[k]) * -1 : Long.parseLong		(storageInAmt		[k]));	//입고금액
//	            outstockConfmVO.setInVat			    (slipKind.equals("1")	?	Long.parseLong		(storageInVat		[k]) * -1 : Long.parseLong		(storageInVat		[k]));	//입고금액VAT
//	            outstockConfmVO.setInTot			    (slipKind.equals("1")	?	Long.parseLong		(storageInTot		[k]) * -1 : Long.parseLong		(storageInTot		[k]));	//입고금액합계
//
//	            outstockConfmVO.setRegId			    (sessionInfoVO.getUserId()		);
//	            outstockConfmVO.setRegDt			    (currentDt	);
//	            outstockConfmVO.setModId			    (sessionInfoVO.getUserId()		);
//	            outstockConfmVO.setModDt			    (currentDt	);
//
//            	LOGGER.debug("### getProperties: " + outstockConfmVO.getProperties() );
//
//            	result = outstockConfmMapper.mergeInstockConfmProd(outstockConfmVO);
//                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
//            }
        //TB_PO_HQ_STORE_OUTSTOCK_PROD - END
        }

        // HD 수정
        result = outstockConfmMapper.updateOutstockConfmHd(outstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 출고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            outstockConfmHdVO.setProcFg("10");
            outstockConfmHdVO.setUpdateProcFg("20");

            // DTL의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockDtlConfirm(outstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD의 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockConfirm(outstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            //PROD 진행구분 수정. 수주확정 -> 출고확정
            result = outstockConfmMapper.updateOutstockProdConfirm(outstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 자동입고인 경우 입고로 수정
            if(StringUtil.getOrBlank(envst1043).equals("A")) {
                outstockConfmHdVO.setProcFg("20");
                outstockConfmHdVO.setUpdateProcFg("30");

                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = outstockConfmMapper.updateAutoInstockDtl(outstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //dtl->prod 시에 dtl(seq)과prod(prod)의 키값이 달라서 전체 삭제후 적제 필요 20200916
                result = outstockConfmMapper.deleteAutoInstockProdAll(outstockConfmHdVO);

                result = outstockConfmMapper.mergeAutoInstockConfmProd(outstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                //HD 수정
                result = outstockConfmMapper.updateAutoInstockConfmHd(outstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                // 입고확정상태 확인 PROC_FG > 20
                // 이미 확정한 경우 저장 및 확정하면 안됨 exception 처리 진행.
                result = outstockConfmMapper.getAutoInstockConfirmCnt(outstockConfmHdVO);
                // 이미 입고확정된 내역입니다.
                if(result > 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.modifyFail"));

                // result 1이상이면 이미 입고확정 상태임.[처리구분] TB_CM_NMCODE(NMCODE_GRP_CD='113') 10:수주확정 20:출고확정 30:입고확정]
                if(result <= 0) {
                    // DTL의 진행구분 수정. 출고확정 -> 입고확정
                    result = outstockConfmMapper.updateAutoInstockDtlConfirm(outstockConfmHdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // HD의 진행구분 수정. 출고확정 -> 입고확정
                    result = outstockConfmMapper.updateAutoInstock(outstockConfmHdVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    //PROD 진행구분 수정. 수주확정 -> 출고확정
                    result = outstockConfmMapper.updateAutoInstockProdConfirm(outstockConfmHdVO);
                }
            }
        }

        return returnResult;
    }

    /** 출고확정 이후 저장 */
    @Override
    public int saveOutstockAfter(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        outstockConfmVO.setHdRemark(outstockConfmVO.getHdRemark());
        outstockConfmVO.setHqRemark(outstockConfmVO.getHqRemark());
        outstockConfmVO.setDlvrCd(outstockConfmVO.getDlvrCd());
        outstockConfmVO.setRegId(sessionInfoVO.getUserId());
        outstockConfmVO.setRegDt(currentDt);
        outstockConfmVO.setModId(sessionInfoVO.getUserId());
        outstockConfmVO.setModDt(currentDt);

        // HD 수정
        result = outstockConfmMapper.updateOutstockAfterHd(outstockConfmVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        returnResult += result;

        return returnResult;
    }

    /** regId, regDt, modId, modDt, hqOfficd, storeCd, areaFg 세팅  */
    public OutstockConfmVO setSessionValue(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO, String currentDt) {
        if(StringUtil.getOrBlank(currentDt).equals("")) {
            currentDt = currentDateTimeString();
        }

        outstockConfmVO.setRegId(sessionInfoVO.getUserId());
        outstockConfmVO.setRegDt(currentDt);
        outstockConfmVO.setModId(sessionInfoVO.getUserId());
        outstockConfmVO.setModDt(currentDt);

        outstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        outstockConfmVO.setStoreCd(sessionInfoVO.getStoreCd());
        outstockConfmVO.setAreaFg(sessionInfoVO.getAreaFg());

        return outstockConfmVO;
    }

    /** 출고창고 콤보조회(권한에 상관없이 본사창고 또는 매장창고 조회) */
    @Override
    public List<DefaultMap<String>> getOutStorageCombo2(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO) {

        return outstockConfmMapper.getOutStorageCombo2(outstockConfmVO);
    }

    /** 본사 출고 시, 주문허용여부 확인 */
    @Override
    public String getStoreOrderDateCheckAll(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO) {

        String confirmYn = "";
        String result = "";

        for(OutstockConfmVO outstockConfmVO : outstockConfmVOs) {

            // 본사 출고 시, 주문허용여부 확인
            confirmYn = outstockConfmMapper.getStoreOrderDateCheck(outstockConfmVO);

            if(!confirmYn.equals("Y")){
                result += confirmYn;
            }
        }

        return result;
    }
}
