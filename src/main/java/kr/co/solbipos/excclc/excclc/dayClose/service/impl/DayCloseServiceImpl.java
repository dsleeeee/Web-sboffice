package kr.co.solbipos.excclc.excclc.dayClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.dayClose.service.DayCloseService;
import kr.co.solbipos.excclc.excclc.dayClose.service.DayCloseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DayCloseServiceImpl.java
 * @Description : 광운대 > 후방매출등록 > 후방매출등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dayCloseService")
@Transactional
public class DayCloseServiceImpl implements DayCloseService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DayCloseMapper dayCloseMapper;
    private final MessageService messageService;

    public DayCloseServiceImpl(DayCloseMapper dayCloseMapper, MessageService messageService) {
        this.dayCloseMapper = dayCloseMapper;
        this.messageService = messageService;
    }

    /** 마감데이터 조회 */
    @Override
    public List<DefaultMap<String>> getDayCloseList(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        dayCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayCloseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayCloseVO.getStoreCd()).equals("")) {
            dayCloseVO.setArrStoreCd(dayCloseVO.getStoreCd().split(","));
        }

        return dayCloseMapper.getDayCloseList(dayCloseVO);
    }

    @Override
    public DefaultMap<String> getDayCloseDtl(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        dayCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dayCloseMapper.getDayCloseDtl(dayCloseVO);
    }

    @Override
    public int saveClose(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        dayCloseVO.setRegDt(currentDt);
        dayCloseVO.setRegId(sessionInfoVO.getUserId());
        dayCloseVO.setModDt(currentDt);
        dayCloseVO.setModId(sessionInfoVO.getUserId());

        result += dayCloseMapper.saveClose(dayCloseVO);

        return result;
    }

    @Override
    public int closeCancel(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        dayCloseVO.setStoreCd(sessionInfoVO.getStoreCd());

        dayCloseVO.setModDt(currentDt);
        dayCloseVO.setModId(sessionInfoVO.getUserId());

        result += dayCloseMapper.closeCancel(dayCloseVO);

        return result;
    }

}
