package kr.co.solbipos.sale.anals.timeSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.timeSaleBenson.service.TimeSaleBensonService;
import kr.co.solbipos.sale.anals.timeSaleBenson.service.TimeSaleBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeSaleBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.20
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleBensonService")
@Transactional
public class TimeSaleBensonServiceImpl implements TimeSaleBensonService {

    private final TimeSaleBensonMapper timeSaleBensonMapper;
    private final PopupMapper popupMapper;

    public TimeSaleBensonServiceImpl(TimeSaleBensonMapper timeSaleBensonMapper, PopupMapper popupMapper) {
        this.timeSaleBensonMapper = timeSaleBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleBensonDayList(TimeSaleBensonVO timeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        setSearchCondition(timeSaleBensonVO, sessionInfoVO);

        return timeSaleBensonMapper.getTimeSaleBensonDayList(timeSaleBensonVO);
    }

    /** 일별 탭 - 조회조건 엑셀다운로드 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleBensonDayExcelList(TimeSaleBensonVO timeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        setSearchCondition(timeSaleBensonVO, sessionInfoVO);

        return timeSaleBensonMapper.getTimeSaleBensonDayExcelList(timeSaleBensonVO);
    }

    /** 월별 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleBensonMonthList(TimeSaleBensonVO timeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        setSearchCondition(timeSaleBensonVO, sessionInfoVO);

        return timeSaleBensonMapper.getTimeSaleBensonMonthList(timeSaleBensonVO);
    }

    /** 월별 탭 - 조회조건 엑셀다운로드 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleBensonMonthExcelList(TimeSaleBensonVO timeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        setSearchCondition(timeSaleBensonVO, sessionInfoVO);

        return timeSaleBensonMapper.getTimeSaleBensonMonthExcelList(timeSaleBensonVO);
    }

    /** 조회조건 공통 세팅 (본사코드, 매장 array 값) */
    private void setSearchCondition(TimeSaleBensonVO timeSaleBensonVO, SessionInfoVO sessionInfoVO) {

        // 본사 세팅
        timeSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(timeSaleBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleBensonVO.getStoreCds(), 3900));
            timeSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
    }
}
