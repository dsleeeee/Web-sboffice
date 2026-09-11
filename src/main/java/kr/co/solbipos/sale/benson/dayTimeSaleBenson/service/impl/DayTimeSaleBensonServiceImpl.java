package kr.co.solbipos.sale.benson.dayTimeSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.dayTimeSaleBenson.service.DayTimeSaleBensonService;
import kr.co.solbipos.sale.benson.dayTimeSaleBenson.service.DayTimeSaleBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayTimeSaleBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 일별시간대별매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayTimeSaleBensonService")
@Transactional
public class DayTimeSaleBensonServiceImpl implements DayTimeSaleBensonService {
    private final DayTimeSaleBensonMapper dayTimeSaleBensonMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayTimeSaleBensonServiceImpl(DayTimeSaleBensonMapper dayTimeSaleBensonMapper) {
        this.dayTimeSaleBensonMapper = dayTimeSaleBensonMapper;
    }

    /** 일별시간대별매출조회 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDayTimeSaleBensonList(DayTimeSaleBensonVO dayTimeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        dayTimeSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayTimeSaleBensonVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매출 시간대 설정
        int iSaleTimeStart = Integer.parseInt(dayTimeSaleBensonVO.getStartTime());
        int iSaleTimeEnd = Integer.parseInt(dayTimeSaleBensonVO.getEndTime());

        String timeCol = "";
        for(int i = iSaleTimeStart; i <= iSaleTimeEnd; i++) {
            timeCol += (i < 10 ? "0" + i : Integer.toString(i));
            if(i != iSaleTimeEnd){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");

        if(arrTimeCol.length > 0){
            if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                dayTimeSaleBensonVO.setArrTimeCol(arrTimeCol);
            }
        }

        return dayTimeSaleBensonMapper.getDayTimeSaleBensonList(dayTimeSaleBensonVO);
    }
}
