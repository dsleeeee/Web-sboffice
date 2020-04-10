package kr.co.solbipos.iostock.order.instockConfm.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmProdVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmService;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmVO;

@Service("instockConfmService")
@Transactional
public class InstockConfmServiceImpl implements InstockConfmService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final InstockConfmMapper instockConfmMapper;
    private final MessageService messageService;

    public InstockConfmServiceImpl(InstockConfmMapper instockConfmMapper, MessageService messageService) {
        this.instockConfmMapper = instockConfmMapper;
        this.messageService = messageService;
    }

    /** 입고확정 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInstockConfmList(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getInstockConfmList(instockConfmVO);
    }

    /** 전표상세 조회 */
    @Override
    public DefaultMap<String> getSlipNoInfo(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getSlipNoInfo(instockConfmVO);
    }

    /** 입고확정 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getInstockConfmDtlList(InstockConfmVO instockConfmVO) {
        return instockConfmMapper.getInstockConfmDtlList(instockConfmVO);
    }

    /** 입고확정 - 입고확정 상세 리스트 저장 */
    @Override
    public int saveInstockConfmDtl(InstockConfmVO[] instockConfmVOs, SessionInfoVO sessionInfoVO) {
        int 	returnResult 	= 0;
        int 	result 			= 0;
        int 	i 				= 0;
        String 	currentDt 		= currentDateTimeString();
        String	userId			= sessionInfoVO.getUserId();
        String 	confirmFg 		= "N";
        int 	instockErrCnt 	= 0;

        InstockConfmVO 		InstockConfmHdVO	= new InstockConfmVO();
        InstockConfmProdVO	prodVO;

        //TB_PO_HQ_STORE_OUTSTOCK_PROD Insert or Update에 사용
        String[] storageCd;
        String[] storageNm;
        String[] storageInUnitQty;
        String[] storageInEtcQty;
        String[] storageInTotQty;
        String[] storageInAmt;
        String[] storageInVat;
        String[] storageInTot;

        for (InstockConfmVO instockConfmVO : instockConfmVOs) {
            //HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(instockConfmVO.getConfirmFg());

                InstockConfmHdVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd());
                InstockConfmHdVO.setSlipNo		(instockConfmVO.getSlipNo());
                InstockConfmHdVO.setHdRemark	(instockConfmVO.getHdRemark());
                InstockConfmHdVO.setInDate		(instockConfmVO.getInDate());
                InstockConfmHdVO.setRegId		(userId);
                InstockConfmHdVO.setRegDt		(currentDt);
                InstockConfmHdVO.setModId		(userId);
                InstockConfmHdVO.setModDt		(currentDt);
            }

            int 	slipFg    	= instockConfmVO.getSlipFg();
            int 	inUnitQty 	= (instockConfmVO.getInUnitQty() == null ? 0 : instockConfmVO.getInUnitQty()) * slipFg;
            int 	inEtcQty  	= (instockConfmVO.getInEtcQty()  == null ? 0 : instockConfmVO.getInEtcQty ()) * slipFg;
            int 	inTotQty  	= (instockConfmVO.getInTotQty()  == null ? 0 : instockConfmVO.getInTotQty ()) * slipFg;
            int		outTotQty 	= (instockConfmVO.getOutTotQty() == null ? 0 : instockConfmVO.getOutTotQty()) * slipFg;
            Long 	inAmt		= (instockConfmVO.getInAmt()     == null ? 0 : instockConfmVO.getInAmt()	) * slipFg;
            Long 	inVat    	= (instockConfmVO.getInVat()     == null ? 0 : instockConfmVO.getInVat()	) * slipFg;
            Long 	inTot    	= (instockConfmVO.getInTot()     == null ? 0 : instockConfmVO.getInTot()	) * slipFg;

            //출고수량과 입고수량이 다르면 카운트
            if(inTotQty != outTotQty) instockErrCnt++;
            LOGGER.debug("### inTotQty        : " + inTotQty			);
            LOGGER.debug("### outTotQty       : " + outTotQty			);
            LOGGER.debug("### instockErrCnt   : " + instockErrCnt		);

            instockConfmVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            instockConfmVO.setInUnitQty	(inUnitQty);
            instockConfmVO.setInEtcQty	(inEtcQty);
            instockConfmVO.setInTotQty	(inTotQty);
            instockConfmVO.setInAmt		(inAmt);
            instockConfmVO.setInVat		(inVat);
            instockConfmVO.setInTot		(inTot);
            instockConfmVO.setRegId		(userId);
            instockConfmVO.setRegDt		(currentDt);
            instockConfmVO.setModId		(userId);
            instockConfmVO.setModDt		(currentDt);

            //DTL 수정
            result = instockConfmMapper.updateInstockConfmDtl(instockConfmVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;

            //TB_PO_HQ_STORE_OUTSTOCK_PROD - START
            	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
	            storageCd           = instockConfmVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
	            storageNm           = instockConfmVO.getArrStorageNm().split("\\^");
	            storageInUnitQty    = instockConfmVO.getArrInUnitQty().split("\\^");
	            storageInEtcQty     = instockConfmVO.getArrInEtcQty	().split("\\^");
	            storageInTotQty     = instockConfmVO.getArrInTotQty	().split("\\^");
	            storageInAmt        = instockConfmVO.getArrInAmt	().split("\\^");
	            storageInVat        = instockConfmVO.getArrInVat	().split("\\^");
	            storageInTot        = instockConfmVO.getArrInTot	().split("\\^");

	            for(int k=0; k<storageCd.length; k++) {
	            	LOGGER.debug("### confmYn         : " + confirmFg			);
		            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
		            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
		            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
		            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
		            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
		            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);

	            	prodVO = new InstockConfmProdVO();	//Key - HQ_OFFICE_CD, SLIP_NO, PROD_CD, STORAGE_CD

	                prodVO.setHqOfficeCd			(InstockConfmHdVO	.getHqOfficeCd	()		);	//본사코드
	                prodVO.setSlipNo				(InstockConfmHdVO	.getSlipNo		()		);	//전표번호
	                prodVO.setProdCd				(instockConfmVO		.getProdCd		()		);	//상품코드
	                prodVO.setStorageCd				(storageCd[k]								);	//창고코드
	                prodVO.setOccrFg				("03"										);	//발생구분(03:매장입고)
	                prodVO.setStoreCd		        (sessionInfoVO		.getStoreCd		()		);	//매장코드
	                prodVO.setSlipFg		        (1											);	//전표구분 1:주문 -1:반품

	                prodVO.setConfmYn				(confirmFg.equals("Y")? "Y":"N"				);	//확정여부(Y/N) - Trigger가  'Y'인것만 읽어서 처리하는데 사용
	                prodVO.setInUnitQty		        (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
	                prodVO.setInEtcQty		        (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
	                prodVO.setInTotQty		        (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
	                prodVO.setInAmt			        (Long.parseLong		(storageInAmt		[k]));	//입고금액
	                prodVO.setInVat			        (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
	                prodVO.setInTot			        (Long.parseLong		(storageInTot		[k]));	//입고금액합계

	            	prodVO.setRegId			        (userId		);
	            	prodVO.setRegDt			        (currentDt	);
	            	prodVO.setModId			        (userId		);
	            	prodVO.setModDt			        (currentDt	);

	            	LOGGER.debug("### getProperties: " + prodVO.getProperties() );

	            	result = instockConfmMapper.mergeInstockConfmProd(prodVO);
	                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
	            }
	        //TB_PO_HQ_STORE_OUTSTOCK_PROD - END
        }

        //HD 수정
        result = instockConfmMapper.updateInstockConfmHd(InstockConfmHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        //입고확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            InstockConfmHdVO.setProcFg("20");		//10:수주확정, 20:출고확정, 30:입고확정
            InstockConfmHdVO.setUpdateProcFg("30");

            //DTL의 진행구분 수정. 출고확정 -> 입고확정
            result = instockConfmMapper.updateInstockDtlConfirm(InstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            //HD의 진행구분 수정. 출고확정 -> 입고확정
            result = instockConfmMapper.updateInstockConfirm(InstockConfmHdVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            //출고수량과 입고수량이 다른 상품이 있는 경우 물량오류로 등록
            if(instockErrCnt > 0) {
                //물량오류 DTL 등록
                result = instockConfmMapper.insertInstockErrDtl(InstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

                //물량오류 HD 등록
                result = instockConfmMapper.insertInstockErrHd(InstockConfmHdVO);
                if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            }
        }

        //For TEST
        //if(1==1)	throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        return returnResult;
    }
}
