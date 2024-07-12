package kr.co.solbipos.excclc.excclc.dayClose.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
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
    private final PopupMapper popupMapper;
    private final MessageService messageService;

    public DayCloseServiceImpl(DayCloseMapper dayCloseMapper, PopupMapper popupMapper, MessageService messageService) {
        this.dayCloseMapper = dayCloseMapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<String>> getDayCloseList(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        dayCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayCloseVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayCloseVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayCloseVO.getStoreCd(), 3900));
            dayCloseVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayCloseMapper.getDayCloseList(dayCloseVO);
    }

    /** 마감데이터 조회 */
    @Override
    public DefaultMap<String> getDayCloseDtl(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO) {
        dayCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dayCloseMapper.getDayCloseDtl(dayCloseVO);
    }

    /** 마감 */
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

    /** 마감취소 */
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

    /** 광운대일마감 - 저장 */
    @Override
    public int getDayCloseSave(DayCloseVO[] dayCloseVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(DayCloseVO dayCloseVO : dayCloseVOs) {

            dayCloseVO.setModDt(currentDt);
            dayCloseVO.setModId(sessionInfoVO.getUserId());

           if(dayCloseVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = dayCloseMapper.getDayCloseSaveUpdate(dayCloseVO);
           }
        }

        return procCnt;
    }

}