package kr.co.solbipos.base.store.timeSlot.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankVO;
import kr.co.solbipos.base.store.storeType.service.impl.StoreTypeMapper;
import kr.co.solbipos.base.store.timeSlot.service.TimeSlotService;
import kr.co.solbipos.base.store.timeSlot.service.TimeSlotVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TimeSlotServiceImpl.java
 * @Description : 기초관리 - 매장관리 - 시간대분류관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.20  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("TimeSlotService")
public class TimeSlotServiceImpl implements TimeSlotService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final TimeSlotMapper timeSlotMapper;

    @Autowired
    public TimeSlotServiceImpl(TimeSlotMapper timeSlotMapper) {
        this.timeSlotMapper = timeSlotMapper;
    }

    /** 시간대분류관리 - 시간대분류조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSlot(TimeSlotVO timeSlotVO, SessionInfoVO sessionInfoVO) {
        timeSlotVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        timeSlotVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            timeSlotVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return timeSlotMapper.getTimeSlot(timeSlotVO);
    }

    /** 시간대분류관리 - 시간대분류저장 */
    @Override
    public int saveTimeSlot(TimeSlotVO[] timeSlotVOs, SessionInfoVO sessionInfoVO) {

        int cnt = 0;
        String dt = currentDateTimeString();
        String nmcodeNm = "";
        int itmeCnt = 1;

        for(TimeSlotVO timeSlotVO: timeSlotVOs) {
            timeSlotVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            timeSlotVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
                timeSlotVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            timeSlotVO.setRegDt(dt);
            timeSlotVO.setRegId(sessionInfoVO.getUserId());
            timeSlotVO.setModDt(dt);
            timeSlotVO.setModId(sessionInfoVO.getUserId());

            if(timeSlotVO.getStatus() == GridDataFg.DELETE){
                // 시작시간이 종료시간보다 작은 경우
                if(Integer.parseInt(timeSlotVO.getStartTime()) < Integer.parseInt(timeSlotVO.getEndTime())){
                    for(int i = Integer.parseInt(timeSlotVO.getStartTime()); i < Integer.parseInt(timeSlotVO.getEndTime()); i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        cnt += timeSlotMapper.deleteTimeSlot(timeSlotVO);
                    }
                } else {
                    // 시작시간이 종료시간보다 큰 경우
                    // 시작시간에서 MAX(24)까지
                    for(int i = Integer.parseInt(timeSlotVO.getStartTime()); i < 24; i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        cnt += timeSlotMapper.deleteTimeSlot(timeSlotVO);
                    }
                    // MIN(0)에서 종료시간까지
                    for(int i = 0; i < Integer.parseInt(timeSlotVO.getEndTime()); i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        cnt += timeSlotMapper.deleteTimeSlot(timeSlotVO);
                    }
                }
            } else {
                if(!nmcodeNm.equals(timeSlotVO.getNmcodeNm())){
                    nmcodeNm = timeSlotVO.getNmcodeNm();
                    itmeCnt = 1;
                }

                // 시작시간이 종료시간보다 작은 경우
                if(Integer.parseInt(timeSlotVO.getStartTime()) < Integer.parseInt(timeSlotVO.getEndTime())){
                    for(int i = Integer.parseInt(timeSlotVO.getStartTime()); i < Integer.parseInt(timeSlotVO.getEndTime()); i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        timeSlotVO.setNmcodeItem1(String.format("%02d", (int)itmeCnt));
                        cnt += timeSlotMapper.saveTimeSlot(timeSlotVO);
                        itmeCnt++;
                    }
                } else {
                    // 시작시간이 종료시간보다 큰 경우
                    // 시작시간에서 MAX(24)까지
                    for(int i = Integer.parseInt(timeSlotVO.getStartTime()); i < 24; i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        timeSlotVO.setNmcodeItem1(String.format("%02d", (int)itmeCnt));
                        cnt += timeSlotMapper.saveTimeSlot(timeSlotVO);
                        itmeCnt++;
                    }
                    // MIN(0)에서 종료시간까지
                    for(int i = 0; i < Integer.parseInt(timeSlotVO.getEndTime()); i++){
                        timeSlotVO.setNmcodeCd(String.format("%02d", (int)i));
                        timeSlotVO.setNmcodeItem1(String.format("%02d", (int)itmeCnt));
                        cnt += timeSlotMapper.saveTimeSlot(timeSlotVO);
                        itmeCnt++;
                    }
                }
            }
        }


        return cnt;
    }
}
