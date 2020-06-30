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
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public String getDlvrManageList(SessionInfoVO sessionInfoVO) {
        DlvrRegistVO dlvrRegistVO = new DlvrRegistVO();
        List<DefaultMap<String>> dlvrManageList = mapper.getDlvrManageList(dlvrRegistVO);
        return convertToJson(dlvrManageList);
    }

    @Override
    public int saveDlvrRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        /*
         * 상품권은 프랜차이즈의 경우 무조건 본사에서 등록
         *          단독매장의 경우 무조건 매장에서 등록
         */

        for (DlvrRegistVO dlvrRegistVO : dlvrRegistVOs) {
            dlvrRegistVO.setRegDt(dt);
            dlvrRegistVO.setRegId(sessionInfoVO.getUserId());
            dlvrRegistVO.setModDt(dt);
            dlvrRegistVO.setModId(sessionInfoVO.getUserId());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
                dlvrRegistVO.setDlvrLzoneCd(mapper.getNewDlvrLzoneCd(dlvrRegistVO));
            } else {
                dlvrRegistVO.setStoreCd(sessionInfoVO.getOrgnCd());
                dlvrRegistVO.setDlvrLzoneCd(mapper.getNewDlvrLzoneCd(dlvrRegistVO));
            }

            if (dlvrRegistVO.getStatus() == GridDataFg.INSERT) {
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
}
