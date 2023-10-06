package kr.co.solbipos.sale.day.dayPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayPeriod.service.DayPeriodService;
import kr.co.solbipos.sale.day.dayPeriod.service.DayPeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

import java.util.List;

/**
 * @Class Name : DayOfWeekServiceImpl.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 설정기간별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.01.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dayPeriodService")
@Transactional
public class DayPeriodServiceImpl implements DayPeriodService {
    private final DayPeriodMapper dayPeriodMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayPeriodServiceImpl(DayPeriodMapper dayPeriodMapper, PopupMapper popupMapper) {
        this.dayPeriodMapper = dayPeriodMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대별탭 - 시간대별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodTimeList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodTimeList(dayPeriodVO);
    }

    /** 시간대별탭 - 시간대별 매출상세조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodTimeDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodTimeDetailList(dayPeriodVO);
    }

    /** 상품분류별탭 - 상품분류별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodProdClassList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodProdClassList(dayPeriodVO);
    }

    /** 상품분류별탭 - 상품분류별 매출상세조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodProdClassDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodProdClassDetailList(dayPeriodVO);
    }

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodTableList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {

        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dayPeriodMapper.getDayPeriodTableList(dayPeriodVO);
    }

    /** 코너별탭 - 코너별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodCornerList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodCornerList(dayPeriodVO);
    }

    /** 코너별탭 - 코너별 매출상세조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodCornerDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {

        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return dayPeriodMapper.getDayPeriodCornerDetailList(dayPeriodVO);
    }

    /** 상품권별탭 - 상품권별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodGiftList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {
        dayPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayPeriodVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayPeriodVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayPeriodVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayPeriodVO.getStoreCds(), 3900));
            dayPeriodVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayPeriodMapper.getDayPeriodGiftList(dayPeriodVO);
    }

    /** 상품권별탭 - 상품권별 매출상세조회 */
    @Override
    public List<DefaultMap<Object>> getDayPeriodGiftDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO) {

        dayPeriodVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return dayPeriodMapper.getDayPeriodGiftDetailList(dayPeriodVO);
    }
}