package kr.co.solbipos.membr.info.chgBatch.service.impl;


import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.chgBatch.service.ChgBatchService;
import kr.co.solbipos.membr.info.chgBatch.service.ChgBatchVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

@Service("chgBatchService")
@Transactional
public class ChgBatchServiceImpl implements ChgBatchService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ChgBatchMapper mapper;
    private final RegistMapper rMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ChgBatchServiceImpl(ChgBatchMapper mapper, RegistMapper rMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.rMapper = rMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    @Override
    public String getMembrChgBatchList(SessionInfoVO sessionInfoVO) {
        ChgBatchVO chgBatchVO = new ChgBatchVO();
        List<DefaultMap<String>> chgBatchList = mapper.getMembrChgBatchList(chgBatchVO);
        return convertToJson(chgBatchList);
    }

    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {
        MembrClassVO membrClassVO = new MembrClassVO();
        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> resultList = mapper.getMemberClassList(membrClassVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    @Override
    public List<DefaultMap<String>> getMemberChgBatchList(ChgBatchVO chgBatchVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        chgBatchVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        chgBatchVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        chgBatchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgBatchVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (!StringUtil.isEmpties(chgBatchVO.getRegStoreCd())) {
            chgBatchVO.setRegStoreCds(chgBatchVO.getRegStoreCd().split(","));
        }

        LOGGER.info("AnvStartDate ::: {}",chgBatchVO.getAnvStartDate());

        return mapper.getMemberChgBatchList(chgBatchVO);
    }

    @Override
    public int saveChgBatchList(ChgBatchVO[] chgBatchVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (ChgBatchVO chgBatchVO : chgBatchVOs) {

            chgBatchVO.setRegDt(dt);
            chgBatchVO.setRegId(sessionInfoVO.getUserId());
            chgBatchVO.setModDt(dt);
            chgBatchVO.setModId(sessionInfoVO.getUserId());
            chgBatchVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

            if (chgBatchVO.getStatus() == GridDataFg.UPDATE) {
                result = mapper.updateChgBatchInfo(chgBatchVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return result;
    }

}