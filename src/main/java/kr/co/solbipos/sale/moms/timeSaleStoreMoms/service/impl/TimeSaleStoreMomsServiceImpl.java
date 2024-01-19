package kr.co.solbipos.sale.moms.timeSaleStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.timeSaleStoreMoms.service.TimeSaleStoreMomsService;
import kr.co.solbipos.sale.moms.timeSaleStoreMoms.service.TimeSaleStoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : TimeSaleStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleStoreMomsService")
@Transactional
public class TimeSaleStoreMomsServiceImpl implements TimeSaleStoreMomsService {
    private final TimeSaleStoreMomsMapper timeSaleStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleStoreMomsServiceImpl(TimeSaleStoreMomsMapper timeSaleStoreMomsMapper, PopupMapper popupMapper) {
        this.timeSaleStoreMomsMapper = timeSaleStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreMomsList(TimeSaleStoreMomsVO timeSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreMomsVO.getStoreCds(), 3900));
            timeSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreMomsVO.getStoreHqBrandCd() == "" || timeSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreMomsVO.getUserBrands().split(",");
                timeSaleStoreMomsVO.setUserBrandList(userBrandList);
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
                timeSaleStoreMomsVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreMomsVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreMomsVO.getDlvrOrderFg())) {
            timeSaleStoreMomsVO.setDlvrOrderFgList(timeSaleStoreMomsVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreMomsMapper.getTimeSaleStoreMomsList(timeSaleStoreMomsVO);
    }

    /** 시간대매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreMomsExcelList(TimeSaleStoreMomsVO timeSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreMomsVO.getStoreCds(), 3900));
            timeSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreMomsVO.getStoreHqBrandCd() == "" || timeSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreMomsVO.getUserBrands().split(",");
                timeSaleStoreMomsVO.setUserBrandList(userBrandList);
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
                timeSaleStoreMomsVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreMomsVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreMomsVO.getDlvrOrderFg())) {
            timeSaleStoreMomsVO.setDlvrOrderFgList(timeSaleStoreMomsVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreMomsMapper.getTimeSaleStoreMomsExcelList(timeSaleStoreMomsVO);
    }

    /** 시간대매출(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreMomsExcelDivisionList(TimeSaleStoreMomsVO timeSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreMomsVO.getStoreCds(), 3900));
            timeSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreMomsVO.getStoreHqBrandCd() == "" || timeSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreMomsVO.getUserBrands().split(",");
                timeSaleStoreMomsVO.setUserBrandList(userBrandList);
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
                timeSaleStoreMomsVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreMomsVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreMomsVO.getDlvrOrderFg())) {
            timeSaleStoreMomsVO.setDlvrOrderFgList(timeSaleStoreMomsVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreMomsMapper.getTimeSaleStoreMomsExcelDivisionList(timeSaleStoreMomsVO);
    }
}