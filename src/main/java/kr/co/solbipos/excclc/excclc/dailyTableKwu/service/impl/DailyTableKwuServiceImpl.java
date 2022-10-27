package kr.co.solbipos.excclc.excclc.dailyTableKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuService;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuVO;
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
 * @Class Name : DailyTableKwuServiceImpl.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dailyTableKwuService")
@Transactional
public class DailyTableKwuServiceImpl implements DailyTableKwuService {
    private final DailyTableKwuMapper dailyTableKwuMapper;

    /**
     * Constructor Injection
     */
    public DailyTableKwuServiceImpl(DailyTableKwuMapper dailyTableKwuMapper) {
        this.dailyTableKwuMapper = dailyTableKwuMapper;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwuList(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("sl", dailyTableKwuMapper.getSaleList(dailyTableKwuVO));             // 매출종합
        rtnMap.put("prodClass", dailyTableKwuMapper.getProdClassList(dailyTableKwuVO)); // 분류
        rtnMap.put("pay", dailyTableKwuMapper.getPayList(dailyTableKwuVO));             // 결제수단
        rtnMap.put("rtn", dailyTableKwuMapper.getRtnList(dailyTableKwuVO));             // 반품출납

//        rtnMap.put("payline", dailyTableKwuMapper.getPayLineList(dailyTableKwuVO));     // 결재라인

        return rtnMap;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwuList1(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("courseStatus", dailyTableKwuMapper.getCourseStatusList(dailyTableKwuVO)); // 수강현황
        rtnMap.put("courseType", dailyTableKwuMapper.getCourseTypeList(dailyTableKwuVO));     // 수강유형
        rtnMap.put("tuition1", dailyTableKwuMapper.getTuition1List(dailyTableKwuVO));         // 수강료현황
        rtnMap.put("tuition2", dailyTableKwuMapper.getTuition2List(dailyTableKwuVO));         // 수강료현황
        rtnMap.put("groupCourse", dailyTableKwuMapper.getGroupCourseList(dailyTableKwuVO));   // 단체수강내역

        return rtnMap;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwuList2(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwuVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("paymentStatus1", dailyTableKwuMapper.getPaymentStatus1List(dailyTableKwuVO)); // 출납현황
        rtnMap.put("paymentStatus2", dailyTableKwuMapper.getPaymentStatus2List(dailyTableKwuVO)); // 출납현황
        rtnMap.put("paymentStatus3", dailyTableKwuMapper.getPaymentStatus3List(dailyTableKwuVO)); // 출납현황

        return rtnMap;
    }
}