package kr.co.solbipos.sale.day.dayTimeMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayTimeMrpizza.service.DayTimeMrpizzaService;
import kr.co.solbipos.sale.day.dayTimeMrpizza.service.DayTimeMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayTimeMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 매출분석 > 일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayTimeMrpizzaService")
@Transactional
public class DayTimeMrpizzaServiceImpl implements DayTimeMrpizzaService {

    private final DayTimeMrpizzaMapper dayTimeMrpizzaMapper;
    private final PopupMapper popupMapper;

    public DayTimeMrpizzaServiceImpl(DayTimeMrpizzaMapper dayTimeMrpizzaMapper, PopupMapper popupMapper) {
       this.dayTimeMrpizzaMapper = dayTimeMrpizzaMapper;
       this.popupMapper = popupMapper;
    }

    /**
     * 일별시간대 리스트 조회
     * @param dayTimeMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayTimeMrpizzaList(DayTimeMrpizzaVO dayTimeMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayTimeMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayTimeMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayTimeMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayTimeMrpizzaVO.getStoreCds(), 3900));
            dayTimeMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if(dayTimeMrpizzaVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(dayTimeMrpizzaVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(dayTimeMrpizzaVO.getEndTime());

            String timeCol = "";

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                timeCol += Integer.toString(i);
                if(i != iSaleDateEnd){
                    timeCol += ",";
                }
            }

            String[] arrTimeCol = timeCol.split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    dayTimeMrpizzaVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(dayTimeMrpizzaVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = dayTimeMrpizzaVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    dayTimeMrpizzaVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        return dayTimeMrpizzaMapper.getDayTimeMrpizzaList(dayTimeMrpizzaVO);
    }

}
