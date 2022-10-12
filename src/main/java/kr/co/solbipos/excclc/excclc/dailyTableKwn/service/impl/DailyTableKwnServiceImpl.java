package kr.co.solbipos.excclc.excclc.dailyTableKwn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnService;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnVO;
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
 * @Class Name : DailyTableKwnServiceImpl.java
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
@Service("dailyTableKwnService")
@Transactional
public class DailyTableKwnServiceImpl implements DailyTableKwnService {
    private final DailyTableKwnMapper dailyTableKwnMapper;

    /**
     * Constructor Injection
     */
    public DailyTableKwnServiceImpl(DailyTableKwnMapper dailyTableKwnMapper) {
        this.dailyTableKwnMapper = dailyTableKwnMapper;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwnList(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwnVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("sl", dailyTableKwnMapper.getSaleList(dailyTableKwnVO));             // 매출종합
        rtnMap.put("prodClass", dailyTableKwnMapper.getProdClassList(dailyTableKwnVO)); // 분류
        rtnMap.put("pay", dailyTableKwnMapper.getPayList(dailyTableKwnVO));             // 결제수단
        rtnMap.put("rtn", dailyTableKwnMapper.getRtnList(dailyTableKwnVO));             // 반품출납

//        rtnMap.put("payline", dailyTableKwnMapper.getPayLineList(dailyTableKwnVO));     // 결재라인

        return rtnMap;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwnList1(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwnVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("courseStatus", dailyTableKwnMapper.getCourseStatusList(dailyTableKwnVO)); // 수강현황
        rtnMap.put("courseType", dailyTableKwnMapper.getCourseTypeList(dailyTableKwnVO));     // 수강유형
        rtnMap.put("tuition1", dailyTableKwnMapper.getTuition1List(dailyTableKwnVO));         // 수강료현황
        rtnMap.put("tuition2", dailyTableKwnMapper.getTuition2List(dailyTableKwnVO));         // 수강료현황
        rtnMap.put("groupCourse", dailyTableKwnMapper.getGroupCourseList(dailyTableKwnVO));   // 단체수강내역

        return rtnMap;
    }

    /** 일일일계표2 - 조회 */
    @Override
    public Map<String, Object> getDailyTableKwnList2(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO) {

        dailyTableKwnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableKwnVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        dailyTableKwnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableKwnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("paymentStatus1", dailyTableKwnMapper.getPaymentStatus1List(dailyTableKwnVO)); // 출납현황
        rtnMap.put("paymentStatus2", dailyTableKwnMapper.getPaymentStatus2List(dailyTableKwnVO)); // 출납현황
        rtnMap.put("paymentStatus3", dailyTableKwnMapper.getPaymentStatus3List(dailyTableKwnVO)); // 출납현황

        return rtnMap;
    }
}