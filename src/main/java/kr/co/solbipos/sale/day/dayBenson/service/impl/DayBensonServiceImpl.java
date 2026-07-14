package kr.co.solbipos.sale.day.dayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayBenson.service.DayBensonService;
import kr.co.solbipos.sale.day.dayBenson.service.DayBensonVO;
import kr.co.solbipos.sale.day.dayMrpizza.service.DayMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 일별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("dayBensonService")
@Transactional
public class DayBensonServiceImpl implements DayBensonService {

    private final DayBensonMapper dayBensonMapper;
    private final PopupMapper popupMapper;

    public DayBensonServiceImpl(DayBensonMapper dayBensonMapper, PopupMapper popupMapper) {
        this.dayBensonMapper = dayBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별매출현황(채널별) 리스트 조회
     * @param dayBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayBensonList(DayBensonVO dayBensonVO, SessionInfoVO sessionInfoVO) {

        dayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayBensonVO.getStoreCds(), 3900));
            dayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayBensonVO.getPayCol().split(",");
        for (int i = 0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if (!(("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i]))) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayBensonVO.setPivotPayCol(pivotPayCol);
        dayBensonVO.setArrPayCol(payCol.split(","));

        // 모바일페이상세 array 값 세팅
        String mpayCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = dayBensonVO.getMpayCol().split(",");
        for (int i = 0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        dayBensonVO.setPivotMpayCol(pivotMpayCol);
        dayBensonVO.setArrMpayCol(mpayCol.split(","));

        String[] arrDlvrInFgCol = dayBensonVO.getDlvrInFgCol().split(",");

        if (arrDlvrInFgCol.length > 0) {
            if (arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])) {
                dayBensonVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (dayBensonVO.getStoreHqBrandCd() == "" || dayBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayBensonVO.getUserBrands().split(",");
                dayBensonVO.setUserBrandList(userBrandList);
            }
        }

        return dayBensonMapper.getDayBensonList(dayBensonVO);
    }

    /**
     * 일별매출현황(채널별) 엑셀다운로드 조회
     * @param dayBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayBensonExcelList(DayBensonVO dayBensonVO, SessionInfoVO sessionInfoVO) {

        dayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayBensonVO.getStoreCds(), 3900));
            dayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayBensonMapper.getDayBensonExcelList(dayBensonVO);
    }
}
