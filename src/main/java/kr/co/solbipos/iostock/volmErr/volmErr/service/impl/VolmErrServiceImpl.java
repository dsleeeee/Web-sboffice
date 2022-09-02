package kr.co.solbipos.iostock.volmErr.volmErr.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.order.outstockConfm.service.OutstockConfmVO;
import kr.co.solbipos.iostock.order.outstockConfm.service.impl.OutstockConfmMapper;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrService;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;

@Service("volmErrService")
@Transactional
public class VolmErrServiceImpl implements VolmErrService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final VolmErrMapper volmErrMapper;
    private final MessageService messageService;
    private final OutstockConfmMapper outstockConfmMapper;


    @Autowired
    public VolmErrServiceImpl(VolmErrMapper volmErrMapper, MessageService messageService, OutstockConfmMapper outstockConfmMapper) {
        this.volmErrMapper = volmErrMapper;
        this.messageService = messageService;
        this.outstockConfmMapper = outstockConfmMapper;
    }

    /** 물량오류관리 - 물량오류관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVolmErrList(VolmErrVO volmErrVO) {
        if(!StringUtil.getOrBlank(volmErrVO.getStoreCd()).equals("")) {
            volmErrVO.setArrStoreCd(volmErrVO.getStoreCd().split(","));
        }

        return volmErrMapper.getVolmErrList(volmErrVO);
    }

    /** 물량오류관리 - 물량오류관리 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVolmErrDtlList(VolmErrVO volmErrVO) {
        return volmErrMapper.getVolmErrDtlList(volmErrVO);
    }

    /** 물량오류관리 - 물량오류 상세 저장 */
    @Override
    public int saveVolmErrDtl(VolmErrVO[] volmErrVOs, SessionInfoVO sessionInfoVO) {
        int returnResult 		= 0;
        int result 				= 0;
        String currentDt 		= currentDateTimeString();
        String confirmFg 		= "N";
        String newSlipNoFg      = "N";
        String hqNewAdjustFg    = "N";
        String storeNewAdjustFg = "N";

        VolmErrVO volmErrHdVO = new VolmErrVO();

        int i = 0;
        for (VolmErrVO volmErrVO : volmErrVOs) {
            //HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(volmErrVO.getConfirmFg());

                volmErrHdVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd());
                volmErrHdVO.setSlipNo		(volmErrVO.getSlipNo());
                volmErrHdVO.setSlipFg		(volmErrVO.getSlipFg());
                volmErrHdVO.setStoreCd		(volmErrVO.getStoreCd());
                volmErrHdVO.setProcFg		(volmErrVO.getProcFg());
                volmErrHdVO.setOutDate		(volmErrVO.getOutDate());
                volmErrHdVO.setHdRemark		(volmErrVO.getHdRemark());
                volmErrHdVO.setRegId		(sessionInfoVO.getUserId());
                volmErrHdVO.setRegDt		(currentDt);
                volmErrHdVO.setModId		(sessionInfoVO.getUserId());
                volmErrHdVO.setModDt		(currentDt);
            }
            volmErrVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd());
            volmErrVO.setRegId		(sessionInfoVO.getUserId());
            volmErrVO.setRegDt		(currentDt);
            volmErrVO.setModId		(sessionInfoVO.getUserId());
            volmErrVO.setModDt		(currentDt);

            if(newSlipNoFg		.equals("N") && volmErrVO.getNewSlipNoFg()		.equals("Y")) newSlipNoFg 		= "Y";
            if(hqNewAdjustFg	.equals("N") && volmErrVO.getHqNewAdjustFg()	.equals("Y")) hqNewAdjustFg 	= "Y";
            if(storeNewAdjustFg	.equals("N") && volmErrVO.getStoreNewAdjustFg()	.equals("Y")) storeNewAdjustFg 	= "Y";

            //DTL 수정
            //LOGGER.debug("volmErrVO.getRemark()    : " + volmErrVO.getRemark());
            result = volmErrMapper.updateVolmErrDtl(volmErrVO);
            if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

            returnResult += result;
            i++;
        }

        //HD 수정
        //LOGGER.debug("volmErrHdVO.getHdRemark(): " + volmErrHdVO.getHdRemark());
        result = volmErrMapper.updateVolmErrHd(volmErrHdVO);
        if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));


        //확정여부를 체크한 경우
        if(confirmFg.equals("Y")) {
            volmErrHdVO.setHqNewAdjustFg(hqNewAdjustFg);
            volmErrHdVO.setNewSlipNoFg(newSlipNoFg);
            volmErrHdVO.setAreaFg(sessionInfoVO.getAreaFg());
            volmErrMapper.saveConfirmVolmErr(volmErrHdVO);
            LOGGER.info("SP_INSTOCK_ERR_CONFIRM_IU 결과 : " + volmErrHdVO.getResult());
        }

        return returnResult;
    }

}
