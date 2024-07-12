package kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2Service;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : DailyTableKwu2ServiceImpl.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표3
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dailyTableKwu2Service")
@Transactional
public class DailyTableKwu2ServiceImpl implements DailyTableKwu2Service {
    private final DailyTableKwu2Mapper dailyTableKwu2Mapper;

    /**
     * Constructor Injection
     */
    public DailyTableKwu2ServiceImpl(DailyTableKwu2Mapper dailyTableKwu2Mapper) {
        this.dailyTableKwu2Mapper = dailyTableKwu2Mapper;
    }

    /** 일일일계표3 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwu2List(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO) {

        dailyTableKwu2VO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwu2VO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwu2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwu2VO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("sl", dailyTableKwu2Mapper.getSaleList(dailyTableKwu2VO));             // 매출종합
        rtnMap.put("prodClass", dailyTableKwu2Mapper.getProdClassList(dailyTableKwu2VO)); // 분류
        rtnMap.put("pay", dailyTableKwu2Mapper.getPayList(dailyTableKwu2VO));             // 결제수단
        rtnMap.put("rtn", dailyTableKwu2Mapper.getRtnList(dailyTableKwu2VO));             // 반품출납

//        rtnMap.put("payline", dailyTableKwu2Mapper.getPayLineList(dailyTableKwu2VO));     // 결재라인

        return rtnMap;
    }

    /** 일일일계표3 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwu2List1(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO) {

        dailyTableKwu2VO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwu2VO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwu2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwu2VO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("courseType", dailyTableKwu2Mapper.getCourseTypeList(dailyTableKwu2VO));     // 수강유형
        rtnMap.put("tuition1", dailyTableKwu2Mapper.getTuition1List(dailyTableKwu2VO));         // 수강료현황
        rtnMap.put("tuition2", dailyTableKwu2Mapper.getTuition2List(dailyTableKwu2VO));         // 수강료현황
        rtnMap.put("groupCourse", dailyTableKwu2Mapper.getGroupCourseList(dailyTableKwu2VO));   // 후방매출내역
        rtnMap.put("status", dailyTableKwu2Mapper.getStatusList(dailyTableKwu2VO));   // 출납현황
        rtnMap.put("accountStatus", dailyTableKwu2Mapper.getAccountStatusList(dailyTableKwu2VO));   // 계좌현황

        return rtnMap;
    }

    /** 일일일계표3 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwu2List2(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO) {

        dailyTableKwu2VO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwu2VO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwu2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwu2VO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("paymentStatus1", dailyTableKwu2Mapper.getPaymentStatus1List(dailyTableKwu2VO)); // 출납현황
        rtnMap.put("paymentStatus2", dailyTableKwu2Mapper.getPaymentStatus2List(dailyTableKwu2VO)); // 출납현황
        rtnMap.put("paymentStatus3", dailyTableKwu2Mapper.getPaymentStatus3List(dailyTableKwu2VO)); // 출납현황

        return rtnMap;
    }
}