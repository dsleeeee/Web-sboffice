package kr.co.solbipos.sale.benson.timeSaleStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.timeSaleStoreBenson.service.TimeSaleStoreBensonService;
import kr.co.solbipos.sale.benson.timeSaleStoreBenson.service.TimeSaleStoreBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : TimeSaleStoreBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 시간대매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleStoreBensonService")
@Transactional
public class TimeSaleStoreBensonServiceImpl implements TimeSaleStoreBensonService {
    private final TimeSaleStoreBensonMapper timeSaleStoreBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleStoreBensonServiceImpl(TimeSaleStoreBensonMapper timeSaleStoreBensonMapper, PopupMapper popupMapper) {
        this.timeSaleStoreBensonMapper = timeSaleStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreBensonList(TimeSaleStoreBensonVO timeSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreBensonVO.getStoreCds(), 3900));
            timeSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreBensonVO.getStoreHqBrandCd() == "" || timeSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreBensonVO.getUserBrands().split(",");
                timeSaleStoreBensonVO.setUserBrandList(userBrandList);
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
                timeSaleStoreBensonVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreBensonVO.getDlvrOrderFg())) {
            timeSaleStoreBensonVO.setDlvrOrderFgList(timeSaleStoreBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreBensonMapper.getTimeSaleStoreBensonList(timeSaleStoreBensonVO);
    }

    /** 시간대매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreBensonExcelList(TimeSaleStoreBensonVO timeSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreBensonVO.getStoreCds(), 3900));
            timeSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreBensonVO.getStoreHqBrandCd() == "" || timeSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreBensonVO.getUserBrands().split(",");
                timeSaleStoreBensonVO.setUserBrandList(userBrandList);
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
                timeSaleStoreBensonVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreBensonVO.getDlvrOrderFg())) {
            timeSaleStoreBensonVO.setDlvrOrderFgList(timeSaleStoreBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreBensonMapper.getTimeSaleStoreBensonExcelList(timeSaleStoreBensonVO);
    }

    /** 시간대매출(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleStoreBensonExcelDivisionList(TimeSaleStoreBensonVO timeSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleStoreBensonVO.getStoreCds(), 3900));
            timeSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (timeSaleStoreBensonVO.getStoreHqBrandCd() == "" || timeSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleStoreBensonVO.getUserBrands().split(",");
                timeSaleStoreBensonVO.setUserBrandList(userBrandList);
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
                timeSaleStoreBensonVO.setArrTimeCol(arrTimeCol);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleStoreBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleStoreBensonVO.getDlvrOrderFg())) {
            timeSaleStoreBensonVO.setDlvrOrderFgList(timeSaleStoreBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleStoreBensonMapper.getTimeSaleStoreBensonExcelDivisionList(timeSaleStoreBensonVO);
    }
}
