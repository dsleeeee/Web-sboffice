package kr.co.solbipos.sale.month.monthMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthMrpizza.service.MonthMrpizzaService;
import kr.co.solbipos.sale.month.monthMrpizza.service.MonthMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 매출분석 > 월별매출현황(채널별)
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
@Service("monthMrpizzaService")
@Transactional
public class MonthMrpizzaServiceImpl implements MonthMrpizzaService {
    
    private final MonthMrpizzaMapper monthMrpizzaMapper;
    private final PopupMapper popupMapper;

    public MonthMrpizzaServiceImpl(MonthMrpizzaMapper monthMrpizzaMapper, PopupMapper popupMapper) {
        this.monthMrpizzaMapper = monthMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 월별매출현황(채널별) 리스트 조회
     * @param monthMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthMrpizzaList(MonthMrpizzaVO monthMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(monthMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthMrpizzaVO.getStoreCds(), 3900));
            monthMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 결제수단 array 값 세팅
        String payCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = monthMrpizzaVO.getPayCol().split(",");
        for (int i = 0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if (!(("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i]))) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        monthMrpizzaVO.setPivotPayCol(pivotPayCol);
        monthMrpizzaVO.setArrPayCol(payCol.split(","));

        // 모바일페이상세 array 값 세팅
        String mpayCol = "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotMpayCol = "";
        String arrMpayCol[] = monthMrpizzaVO.getMpayCol().split(",");
        for (int i = 0; i < arrMpayCol.length; i++) {
            pivotMpayCol += (pivotMpayCol.equals("") ? "" : ",") + "'" + arrMpayCol[i] + "'" + " AS MPAY" + arrMpayCol[i];
            mpayCol += (mpayCol.equals("") ? "" : ",") + arrMpayCol[i];
        }
        monthMrpizzaVO.setPivotMpayCol(pivotMpayCol);
        monthMrpizzaVO.setArrMpayCol(mpayCol.split(","));

        String[] arrDlvrInFgCol = monthMrpizzaVO.getDlvrInFgCol().split(",");

        if (arrDlvrInFgCol.length > 0) {
            if (arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])) {
                monthMrpizzaVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        return monthMrpizzaMapper.getMonthMrpizzaList(monthMrpizzaVO);
    }

    /**
     * 월별매출현황(채널별) 엑셀다운로드 조회
     * @param monthMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthMrpizzaExcelList(MonthMrpizzaVO monthMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(monthMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthMrpizzaVO.getStoreCds(), 3900));
            monthMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return monthMrpizzaMapper.getMonthMrpizzaExcelList(monthMrpizzaVO);
    }
}
