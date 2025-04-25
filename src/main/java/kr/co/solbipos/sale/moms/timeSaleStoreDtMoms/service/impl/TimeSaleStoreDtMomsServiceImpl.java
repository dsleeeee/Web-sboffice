package kr.co.solbipos.sale.moms.timeSaleStoreDtMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.timeSaleStoreDtMoms.service.TimeSaleStoreDtMomsService;
import kr.co.solbipos.sale.moms.timeSaleStoreDtMoms.service.TimeSaleStoreDtMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeSaleStoreDtMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출(매장) DT포장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleStoreDtMomsService")
@Transactional
public class TimeSaleStoreDtMomsServiceImpl implements TimeSaleStoreDtMomsService {

    private final TimeSaleStoreDtMomsMapper timeSaleStoreDtMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleStoreDtMomsServiceImpl(TimeSaleStoreDtMomsMapper timeSaleStoreDtMomsMapper, PopupMapper popupMapper) {
        this.timeSaleStoreDtMomsMapper = timeSaleStoreDtMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대매출(매장) DT포장 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreDtMomsList(TimeSaleStoreDtMomsVO timeSaleStoreDtMomsVO, SessionInfoVO sessionInfoVO) {
        timeSaleStoreDtMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreDtMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreDtMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreDtMomsVO.getStoreCds(), 3900));
            timeSaleStoreDtMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreDtMomsVO.getStoreHqBrandCd() == "" || timeSaleStoreDtMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreDtMomsVO.getUserBrands().split(",");
                timeSaleStoreDtMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 시간대
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += (i < 10 ? "0" + i : i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");

        if(arrTimeCol.length > 0){
            if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                timeSaleStoreDtMomsVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreDtMomsVO.getDlvrInFg() != null && !"".equals(timeSaleStoreDtMomsVO.getDlvrInFg())) {
            timeSaleStoreDtMomsVO.setDlvrInFgList(timeSaleStoreDtMomsVO.getDlvrInFg().split(","));
        }
        return timeSaleStoreDtMomsMapper.getTimeSaleStoreDtMomsList(timeSaleStoreDtMomsVO);
    }

    /** 시간대매출(매장) DT포장 - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreMomsExcelDivisionList(TimeSaleStoreDtMomsVO timeSaleStoreDtMomsVO, SessionInfoVO sessionInfoVO) {
        timeSaleStoreDtMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreDtMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreDtMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreDtMomsVO.getStoreCds(), 3900));
            timeSaleStoreDtMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreDtMomsVO.getStoreHqBrandCd() == "" || timeSaleStoreDtMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreDtMomsVO.getUserBrands().split(",");
                timeSaleStoreDtMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 시간대
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += (i < 10 ? "0" + i : i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");

        if(arrTimeCol.length > 0){
            if(arrTimeCol[0] != null && !"".equals(arrTimeCol[0])){
                timeSaleStoreDtMomsVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreDtMomsVO.getDlvrInFg() != null && !"".equals(timeSaleStoreDtMomsVO.getDlvrInFg())) {
            timeSaleStoreDtMomsVO.setDlvrInFgList(timeSaleStoreDtMomsVO.getDlvrInFg().split(","));
        }
        return timeSaleStoreDtMomsMapper.getTimeSaleStoreMomsExcelDivisionList(timeSaleStoreDtMomsVO);
    }


}
