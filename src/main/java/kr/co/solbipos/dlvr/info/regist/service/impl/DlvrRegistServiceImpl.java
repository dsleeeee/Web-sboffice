package kr.co.solbipos.dlvr.info.regist.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

@Service("dlvrRegistService")
@Transactional
public class DlvrRegistServiceImpl implements DlvrRegistService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrRegistMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrRegistServiceImpl(DlvrRegistMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 배달구역 -대분류 조회 */
    @Override
    public String getDlvrManageList(SessionInfoVO sessionInfoVO) {
        DlvrRegistVO dlvrRegistVO = new DlvrRegistVO();
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
        } else {
            dlvrRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        List<DefaultMap<String>> dlvrManageList = mapper.getDlvrManageList(dlvrRegistVO);
        return convertToJson(dlvrManageList);
    }

    /** 배달구역 -대분류 저장후 조회 */
    @Override
    public List<DefaultMap<String>> dlvrManageList(SessionInfoVO sessionInfoVO) {
        DlvrRegistVO dlvrRegistVO = new DlvrRegistVO();
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
        } else {
            dlvrRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
//        List<DefaultMap<String>> result = mapper.getDlvrManageList(dlvrRegistVO);
        return mapper.getDlvrManageList(dlvrRegistVO);
    }

    /** 배달구역 -대분류 저장*/
    @Override
    public int saveDlvrRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String dt = currentDateTimeString();

        for (DlvrRegistVO dlvrRegistVO : dlvrRegistVOs) {

            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
            } else {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            dlvrRegistVO.setRegDt(dt);
            dlvrRegistVO.setRegId(sessionInfoVO.getUserId());
            dlvrRegistVO.setModDt(dt);
            dlvrRegistVO.setModId(sessionInfoVO.getUserId());
            dlvrRegistVO.setEmpNo(sessionInfoVO.getEmpNo());

            if (dlvrRegistVO.getStatus() == GridDataFg.INSERT) {
                dlvrRegistVO.setDlvrLzoneCd(mapper.getNewDlvrLzoneCd(dlvrRegistVO));
                result = mapper.insertDlvrRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (dlvrRegistVO.getStatus() == GridDataFg.UPDATE) {
                result = mapper.updateDlvrRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                result = mapper.deleteDlvrRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return result;
    }

    /** 배달구역 -중분류 조회*/
    @Override
    public List<DefaultMap<String>> dlvrDetailList(DlvrRegistVO dlvrRegistVO, SessionInfoVO sessionInfoVO) {
        return mapper.dlvrDetailList(dlvrRegistVO);
    }

    /** 배달구역 -중분류 저장*/
    @Override
    public int saveDlvrDetailRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String dt = currentDateTimeString();

        for (DlvrRegistVO dlvrRegistVO : dlvrRegistVOs) {

            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
            } else {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            dlvrRegistVO.setRegDt(dt);
            dlvrRegistVO.setRegId(sessionInfoVO.getUserId());
            dlvrRegistVO.setModDt(dt);
            dlvrRegistVO.setModId(sessionInfoVO.getUserId());
            dlvrRegistVO.setEmpNo(sessionInfoVO.getEmpNo());

            if (dlvrRegistVO.getStatus() == GridDataFg.INSERT) {
                dlvrRegistVO.setDlvrMzoneCd(mapper.getNewDlvrMzoneCd(dlvrRegistVO));
                result = mapper.insertDlvrDetailRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (dlvrRegistVO.getStatus() == GridDataFg.UPDATE) {
                result = mapper.updateDlvrDetailRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                result = mapper.deleteDlvrDetailRegistInfo(dlvrRegistVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return result;
    }


}
