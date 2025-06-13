package kr.co.solbipos.sale.day.dayMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayMrpizza.service.DayMrpizzaService;
import kr.co.solbipos.sale.day.dayMrpizza.service.DayMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 매출분석 > 일별매출현황(채널별)
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
@Service("dayMrpizzaService")
@Transactional
public class DayMrpizzaServiceImpl implements DayMrpizzaService {
    
    private final DayMrpizzaMapper dayMrpizzaMapper;
    private final PopupMapper popupMapper;

    public DayMrpizzaServiceImpl(DayMrpizzaMapper dayMrpizzaMapper, PopupMapper popupMapper) {
        this.dayMrpizzaMapper = dayMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별매출현황(채널별) 리스트 조회
     * @param dayMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayMrpizzaList(DayMrpizzaVO dayMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dayMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayMrpizzaVO.getStoreCds(), 3900));
            dayMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayMrpizzaVO.getPayCol().split(",");
        for (int i = 0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if (!(("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i]))) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayMrpizzaVO.setPivotPayCol(pivotPayCol);
        dayMrpizzaVO.setArrPayCol(payCol.split(","));

        // 모바일페이상세 array 값 세팅
        String mpayCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = dayMrpizzaVO.getMpayCol().split(",");
        for (int i = 0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        dayMrpizzaVO.setPivotMpayCol(pivotMpayCol);
        dayMrpizzaVO.setArrMpayCol(mpayCol.split(","));

        String[] arrDlvrInFgCol = dayMrpizzaVO.getDlvrInFgCol().split(",");

        if (arrDlvrInFgCol.length > 0) {
            if (arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])) {
                dayMrpizzaVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        return dayMrpizzaMapper.getDayMrpizzaList(dayMrpizzaVO);
    }

    /**
     * 일별매출현황(채널별) 엑셀다운로드 조회
     * @param dayMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayMrpizzaExcelList(DayMrpizzaVO dayMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(dayMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayMrpizzaVO.getStoreCds(), 3900));
            dayMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayMrpizzaMapper.getDayMrpizzaExcelList(dayMrpizzaVO);
    }
}
