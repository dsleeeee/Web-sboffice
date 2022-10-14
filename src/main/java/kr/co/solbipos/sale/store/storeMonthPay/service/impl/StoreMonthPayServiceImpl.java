package kr.co.solbipos.sale.store.storeMonthPay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeMonthPay.service.StoreMonthPayService;
import kr.co.solbipos.sale.store.storeMonthPay.service.StoreMonthPayVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreMonthPayServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 점포-월별 결제 수단 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeMonthPayService")
@Transactional
public class StoreMonthPayServiceImpl implements StoreMonthPayService {
    private final StoreMonthPayMapper storeMonthPayMapper;

    public StoreMonthPayServiceImpl(StoreMonthPayMapper storeMonthPayMapper) {
        this.storeMonthPayMapper = storeMonthPayMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreMonthPayList(StoreMonthPayVO storeMonthPayVO, SessionInfoVO sessionInfoVO) {
        storeMonthPayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeMonthPayVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeMonthPayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = storeMonthPayVO.getStoreCds().split(",");
        storeMonthPayVO.setStoreCdList(storeCds);

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeMonthPayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeMonthPayVO.setPivotPayCol(pivotPayCol);
        storeMonthPayVO.setArrPayCol(payCol.split(","));

        return storeMonthPayMapper.getStoreMonthPayList(storeMonthPayVO);
    }

}