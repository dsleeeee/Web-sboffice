package kr.co.solbipos.sale.store.storeDayTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeDayTime.service.StoreDayTimeService;
import kr.co.solbipos.sale.store.storeDayTime.service.StoreDayTimeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreDayTimeServiceImpl.java
 * @Description : 맘스터치 > 매장분석 > 매장-일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.25  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeDayTimeService")
@Transactional
public class StoreDayTimeServiceImpl implements StoreDayTimeService {
    private final StoreDayTimeMapper storeDayTimeMapper;

    public StoreDayTimeServiceImpl(StoreDayTimeMapper storeDayTimeMapper) {
        this.storeDayTimeMapper = storeDayTimeMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreDayTimeList(StoreDayTimeVO storeDayTimeVO, SessionInfoVO sessionInfoVO) {

        storeDayTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeDayTimeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = storeDayTimeVO.getStoreCds().split(",");
        storeDayTimeVO.setStoreCdList(storeCds);

        if(storeDayTimeVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(storeDayTimeVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(storeDayTimeVO.getEndTime());

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
                    storeDayTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
        } else if(storeDayTimeVO.getOptionFg().equals("timeSlot")){

            String[] arrTimeCol = storeDayTimeVO.getTimeCol().replace("~","").split(",");

            if(arrTimeCol.length > 0){
                if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                    storeDayTimeVO.setArrTimeCol(arrTimeCol);
                }
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeDayTimeVO.getStoreHqBrandCd() == "" || storeDayTimeVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeDayTimeVO.getUserBrands().split(",");
                storeDayTimeVO.setUserBrandList(userBrandList);
            }
        }

        return storeDayTimeMapper.getStoreDayTimeList(storeDayTimeVO);
    }

}