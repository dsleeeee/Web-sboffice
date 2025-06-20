package kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeDayTime.service.StoreDayTimeVO;
import kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.StoreDayTimeMrpizzaService;
import kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.StoreDayTimeMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreDayTimeMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 매장분석 > 매장-일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeDayTimeMrpizzaService")
@Transactional
public class StoreDayTimeMrpizzaServiceImpl implements StoreDayTimeMrpizzaService {

    private final StoreDayTimeMrpizzaMapper storeDayTimeMrpizzaMapper;
    private final PopupMapper popupMapper;

    public StoreDayTimeMrpizzaServiceImpl(StoreDayTimeMrpizzaMapper storeDayTimeMrpizzaMapper, PopupMapper popupMapper) {
        this.storeDayTimeMrpizzaMapper = storeDayTimeMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 매장-일별시간대 리스트 조회
     * @param storeDayTimeMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getStoreDayTimeMrpizzaList(StoreDayTimeMrpizzaVO storeDayTimeMrpizzaVO, SessionInfoVO sessionInfoVO) {

        storeDayTimeMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeDayTimeMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeDayTimeMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeDayTimeMrpizzaVO.getStoreCds(), 3900));
            storeDayTimeMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if(storeDayTimeMrpizzaVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(storeDayTimeMrpizzaVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(storeDayTimeMrpizzaVO.getEndTime());

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
                    storeDayTimeMrpizzaVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(storeDayTimeMrpizzaVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = storeDayTimeMrpizzaVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    storeDayTimeMrpizzaVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        return storeDayTimeMrpizzaMapper.getStoreDayTimeMrpizzaList(storeDayTimeMrpizzaVO);
    }
}
