package kr.co.solbipos.sale.status.serviceTimeAvg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.serviceTimeAvg.service.ServiceTimeAvgService;
import kr.co.solbipos.sale.status.serviceTimeAvg.service.ServiceTimeAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : ServiceTimeAvgServiceImpl.java
 * @Description : 맘스터치 > 매출분석2 > 서비스타임(평균시간)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("serviceTimeAvgService")
@Transactional
public class ServiceTimeAvgServiceImpl implements ServiceTimeAvgService {
    private final ServiceTimeAvgMapper serviceTimeAvgMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ServiceTimeAvgServiceImpl(ServiceTimeAvgMapper serviceTimeAvgMapper, PopupMapper popupMapper) {
        this.serviceTimeAvgMapper = serviceTimeAvgMapper;
        this.popupMapper = popupMapper;
    }

    /** 서비스타임(평균시간) - 조회 */
    @Override
    public List<DefaultMap<Object>> getServiceTimeAvgList(ServiceTimeAvgVO serviceTimeAvgVO, SessionInfoVO sessionInfoVO) {

        serviceTimeAvgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(serviceTimeAvgVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(serviceTimeAvgVO.getStoreCds(), 3900));
            serviceTimeAvgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        serviceTimeAvgVO.setArrStoreCol(serviceTimeAvgVO.getStoreCds().split(",")); // 매장 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotStoreCol = "";
        String arrStoreCol[] = serviceTimeAvgVO.getStoreCds().split(",");
        for(int i=0; i < arrStoreCol.length; i++) {
            pivotStoreCol += (pivotStoreCol.equals("") ? "" : ",") + "'"+arrStoreCol[i]+"'"+" AS STORE_"+arrStoreCol[i];
        }
        serviceTimeAvgVO.setPivotStoreCol(pivotStoreCol);

        return serviceTimeAvgMapper.getServiceTimeAvgList(serviceTimeAvgVO);
    }
}