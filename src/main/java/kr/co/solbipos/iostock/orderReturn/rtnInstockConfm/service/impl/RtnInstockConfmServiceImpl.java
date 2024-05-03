package kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmProdVO;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.RtnInstockConfmService;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service.RtnInstockConfmVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnInstockConfmService")
public class RtnInstockConfmServiceImpl implements RtnInstockConfmService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RtnInstockConfmMapper rtnInstockConfmMapper;
    private final MessageService messageService;

    public RtnInstockConfmServiceImpl(RtnInstockConfmMapper rtnInstockConfmMapper, MessageService messageService) {
        this.rtnInstockConfmMapper = rtnInstockConfmMapper;
        this.messageService = messageService;
    }

    /** 반품본사입고 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmList(RtnInstockConfmVO rtnInstockConfmVO) {
        return rtnInstockConfmMapper.getRtnInstockConfmList(rtnInstockConfmVO);
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(RtnInstockConfmVO rtnInstockConfmVO) {
        return rtnInstockConfmMapper.getSlipNoInfo(rtnInstockConfmVO);
    }

    /** 반품본사입고 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnInstockConfmDtlList(RtnInstockConfmVO rtnInstockConfmVO, SessionInfoVO sessionInfoVO) {
    	String areaFg	 = sessionInfoVO.getAreaFg();
    	rtnInstockConfmVO.setAreaFg(areaFg);
        return rtnInstockConfmMapper.getRtnInstockConfmDtlList(rtnInstockConfmVO);
    }

    /** 반품본사입고 - 반품본사입고 상세 리스트 저장 */
    @Override
    public int saveRtnInstockConfmDtl(RtnInstockConfmVO[] rtnInstockConfmVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();
        String userId	 = sessionInfoVO.getUserId();
        String confirmFg = "N";
        String penaltyFg = "";
        int instockErrCnt 	= 0;

        RtnInstockConfmVO rtnInstockConfmHdVO = new RtnInstockConfmVO();

        //TB_PO_HQ_STORE_OUTSTOCK_PROD Insert or Update에 사용

        for (RtnInstockConfmVO rtnInstockConfmVO : rtnInstockConfmVOs) {
            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(rtnInstockConfmVO.getConfirmFg());

                rtnInstockConfmHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                rtnInstockConfmHdVO.setSlipNo(rtnInstockConfmVO.getSlipNo());
                rtnInstockConfmHdVO.setHdRemark(rtnInstockConfmVO.getHdRemark());
                rtnInstockConfmHdVO.setInDate(rtnInstockConfmVO.getInDate());
                rtnInstockConfmHdVO.setRegId(sessionInfoVO.getUserId());
                rtnInstockConfmHdVO.setRegDt(currentDt);
                rtnInstockConfmHdVO.setModId(sessionInfoVO.getUserId());
                rtnInstockConfmHdVO.setModDt(currentDt);
            }

            int slipFg      = rtnInstockConfmVO.getSlipFg();
            int inUnitQty   = (rtnInstockConfmVO.getInUnitQty()  == null ? 0 : rtnInstockConfmVO.getInUnitQty()) * slipFg;
            int inEtcQty    = (rtnInstockConfmVO.getInEtcQty()   == null ? 0 : rtnInstockConfmVO.getInEtcQty()) * slipFg;
            int inTotQty    = (rtnInstockConfmVO.getInTotQty()   == null ? 0 : rtnInstockConfmVO.getInTotQty()) * slipFg;
            int	outTotQty 	= (rtnInstockConfmVO.getOutTotQty()  == null ? 0 : rtnInstockConfmVO.getOutTotQty()) * slipFg;
            Long inAmt      = (rtnInstockConfmVO.getInAmt()      == null ? 0 : rtnInstockConfmVO.getInAmt()) * slipFg;
            Long inVat      = (rtnInstockConfmVO.getInVat()      == null ? 0 : rtnInstockConfmVO.getInVat()) * slipFg;
            Long inTot      = (rtnInstockConfmVO.getInTot()      == null ? 0 : rtnInstockConfmVO.getInTot()) * slipFg;
            int penaltyAmt  = (rtnInstockConfmVO.getPenaltyAmt() == null ? 0 : rtnInstockConfmVO.getPenaltyAmt());
            if(penaltyAmt > 0 && !penaltyFg.equals("Y")) {
                penaltyFg = "Y";
            }
            //출고수량과 입고수량이 다르면 카운트
            if(inTotQty != outTotQty) instockErrCnt++;

            rtnInstockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnInstockConfmVO.setInUnitQty(inUnitQty);
            rtnInstockConfmVO.setInEtcQty(inEtcQty);
            rtnInstockConfmVO.setInTotQty(inTotQty);
            rtnInstockConfmVO.setInAmt(inAmt);
            rtnInstockConfmVO.setInVat(inVat);
            rtnInstockConfmVO.setInTot(inTot);
            rtnInstockConfmVO.setRegId(sessionInfoVO.getUserId());
            rtnInstockConfmVO.setRegDt(currentDt);
            rtnInstockConfmVO.setModId(sessionInfoVO.getUserId());
            rtnInstockConfmVO.setModDt(currentDt);
            rtnInstockConfmVO.setInSplyUprc(rtnInstockConfmVO.getOutSplyUprc());

            // DTL 수정
            result = rtnInstockConfmMapper.updateRtnInstockConfmDtl(rtnInstockConfmVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            if(i == 0) {
                //dtl->prod 시에 dtl(seq)과prod(prod)의 키값이 달라서 전체 삭제후 적제 필요
                result = rtnInstockConfmMapper.deleteRtnInstockProdAll(rtnInstockConfmVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

//            rtnInstockConfmVO.setHqOfficeCd			(rtnInstockConfmVO	.getHqOfficeCd	()		);	//본사코드
//            rtnInstockConfmVO.setSlipNo				(rtnInstockConfmVO	.getSlipNo		()		);	//전표번호
//            rtnInstockConfmVO.setProdCd				(rtnInstockConfmVO		.getProdCd		()	);	//상품코드
            rtnInstockConfmVO.setStorageCd		    (rtnInstockConfmVO	.getInStorageCd	()		);	//입고창고코드
            rtnInstockConfmVO.setOccrFg				("02"										);	//발생구분(03:매장입고)
//            rtnInstockConfmVO.setStoreCd		    (rtnInstockConfmVO.getStoreCd()				);	//매장코드
            rtnInstockConfmVO.setSlipFg		        (-1											);	//전표구분 1:주문 -1:반품

            rtnInstockConfmVO.setConfmYn				(confirmFg.equals("Y")? "Y":"N"				);	//확정여부(Y/N) - Trigger가  'Y'인것만 읽어서 처리하는데 사용
//            rtnInstockConfmVO.setInUnitQty		    (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
//            rtnInstockConfmVO.setInEtcQty		    (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
//            rtnInstockConfmVO.setInTotQty		    (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
//            rtnInstockConfmVO.setInAmt			    (Long.parseLong		(storageInAmt		[k]));	//입고금액
//            rtnInstockConfmVO.setInVat			    (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
//            rtnInstockConfmVO.setInTot			    (Long.parseLong		(storageInTot		[k]));	//입고금액합계

            rtnInstockConfmVO.setRegId			    (userId		);
            rtnInstockConfmVO.setRegDt			    (currentDt	);
            rtnInstockConfmVO.setModId			    (userId		);
            rtnInstockConfmVO.setModDt			    (currentDt	);

        	LOGGER.debug("### getProperties: " + rtnInstockConfmVO.getProperties() );

        	result = rtnInstockConfmMapper.mergeInstockConfmProd(rtnInstockConfmVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;

        }

        // HD 수정
        rtnInstockConfmHdVO.setPenaltyFg(penaltyFg);
        result = rtnInstockConfmMapper.updateRtnInstockConfmHd(rtnInstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 입고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            rtnInstockConfmHdVO.setProcFg("20");
            rtnInstockConfmHdVO.setUpdateProcFg("30");

            // 입고확정상태 확인 PROC_FG > 20
            // 이미 확정한 경우 저장 및 확정하면 안됨 exception 처리 진행.
            result = rtnInstockConfmMapper.getInstockConfirmCnt(rtnInstockConfmHdVO);
            // 이미 입고확정된 내역입니다.
            if(result > 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.modifyFail"));

            // result 1이상이면 이미 입고확정 상태임.[처리구분] TB_CM_NMCODE(NMCODE_GRP_CD='113') 10:수주확정 20:출고확정 30:입고확정]
            if(result <= 0)
            {
                // DTL의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnInstockConfmMapper.updateInstockDtlConfirm(rtnInstockConfmHdVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // HD의 진행구분 수정. 출고확정 -> 입고확정
                result = rtnInstockConfmMapper.updateInstockConfirm(rtnInstockConfmHdVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // PROD의 진행구분 수정. 수주확정 -> 출고확정
                result = rtnInstockConfmMapper.updateInstockProdConfirm(rtnInstockConfmHdVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //출고수량과 입고수량이 다른 상품이 있는 경우 물량오류로 등록
                if(instockErrCnt > 0) {
                    //물량오류 DTL 등록
                    result = rtnInstockConfmMapper.insertInstockErrDtl(rtnInstockConfmHdVO);
                    if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                    //물량오류 HD 등록
                    result = rtnInstockConfmMapper.insertInstockErrHd(rtnInstockConfmHdVO);
                    if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
                }
            }
        }

        return returnResult;
    }
}
