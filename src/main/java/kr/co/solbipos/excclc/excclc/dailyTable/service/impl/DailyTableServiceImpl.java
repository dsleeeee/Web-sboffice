package kr.co.solbipos.excclc.excclc.dailyTable.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableService;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableService;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableVO;
import kr.co.solbipos.excclc.excclc.dailyTable.service.impl.DailyTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.getDay;

/**
 * @Class Name : DailyTableServiceImpl.java
 * @Description : 광운대 > 후방매출등록 > 일일일계표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dailyTableService")
@Transactional
public class DailyTableServiceImpl implements DailyTableService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DailyTableMapper dailyTableMapper;
    private final MessageService messageService;

    public DailyTableServiceImpl(DailyTableMapper dailyTableMapper, MessageService messageService) {
        this.dailyTableMapper = dailyTableMapper;
        this.messageService = messageService;
    }

    /** 조회 */
    @Override
    public Map<String, Object> getDailyTableList(DailyTableVO dailyTableVO, SessionInfoVO sessionInfoVO) {
        dailyTableVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dailyTableVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dailyTableVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

        rtnMap.put("sl",        dailyTableMapper.getSaleList(dailyTableVO));        //매출종합
        rtnMap.put("prodClass", dailyTableMapper.getProdClassList(dailyTableVO));   //분류
        rtnMap.put("pay",       dailyTableMapper.getPayList(dailyTableVO));         //결제수단
        rtnMap.put("rtn",       dailyTableMapper.getRtnList(dailyTableVO));         //반품출납

        rtnMap.put("payline",	dailyTableMapper.getPayLineList	(dailyTableVO));   //결재라인
        
        rtnMap.put("courseStatus",	dailyTableMapper.getCourseStatusList(dailyTableVO));    //수강현황
        rtnMap.put("courseType",	dailyTableMapper.getCourseTypeList(dailyTableVO));      //수강유형
        rtnMap.put("tuition1",	    dailyTableMapper.getTuition1List(dailyTableVO));        //수강료현황
        rtnMap.put("tuition2",	    dailyTableMapper.getTuition2List(dailyTableVO));       //수강료현황
        rtnMap.put("groupCourse",   dailyTableMapper.getGroupCourseList(dailyTableVO));     //단체수강내역

        rtnMap.put("paymentStatus1",   dailyTableMapper.getPaymentStatus1List(dailyTableVO));   //출납현황
        rtnMap.put("paymentStatus2",   dailyTableMapper.getPaymentStatus2List(dailyTableVO));   //출납현황
        rtnMap.put("paymentStatus3",   dailyTableMapper.getPaymentStatus3List(dailyTableVO));   //출납현황

        return rtnMap;
    }

}
