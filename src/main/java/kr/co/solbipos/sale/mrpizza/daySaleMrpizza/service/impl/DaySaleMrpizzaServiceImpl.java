package kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DaySaleMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 일자별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("daySaleMrpizzaService")
@Transactional
public class DaySaleMrpizzaServiceImpl implements DaySaleMrpizzaService {

    private final DaySaleMrpizzaMapper daySaleMrpizzaMapper;
    private final PopupMapper popupMapper;

    public DaySaleMrpizzaServiceImpl(DaySaleMrpizzaMapper daySaleMrpizzaMapper, PopupMapper popupMapper) {
        this.daySaleMrpizzaMapper = daySaleMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /** 일자별매출 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleMrpizzaList(DaySaleMrpizzaVO daySaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        daySaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleMrpizzaVO.getStoreCds(), 3900));
            daySaleMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        /** PAY_CD = 02 현금,현금영수증 분리 */
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        System.out.println(daySaleMrpizzaVO.getPayCol());
        String arrPayCol[] = daySaleMrpizzaVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        daySaleMrpizzaVO.setPivotPayCol(pivotPayCol);
        daySaleMrpizzaVO.setArrPayCol(payCol.split(","));

        return daySaleMrpizzaMapper.getDaySaleMrpizzaList(daySaleMrpizzaVO);
    }

    /** 일자별매출 상세 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleMrpizzaDtlList(DaySaleMrpizzaVO daySaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        daySaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return daySaleMrpizzaMapper.getDaySaleMrpizzaDtlList(daySaleMrpizzaVO);
    };

}
