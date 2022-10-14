package kr.co.solbipos.sale.store.storeDayPay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeDayPay.service.StoreDayPayService;
import kr.co.solbipos.sale.store.storeDayPay.service.StoreDayPayVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreDayPayServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 점포-일별 결제 수단 매출
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
@Service("storeDayPayService")
@Transactional
public class StoreDayPayServiceImpl implements StoreDayPayService {
    private final StoreDayPayMapper storeDayPayMapper;

    public StoreDayPayServiceImpl(StoreDayPayMapper storeDayPayMapper) {
        this.storeDayPayMapper = storeDayPayMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreDayPayList(StoreDayPayVO storeDayPayVO, SessionInfoVO sessionInfoVO) {
        storeDayPayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeDayPayVO.setEmpNo(sessionInfoVO.getEmpNo());
        storeDayPayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(storeDayPayVO.getStoreCd()).equals("")) {
            storeDayPayVO.setArrStoreCd(storeDayPayVO.getStoreCd().split(","));
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = storeDayPayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        storeDayPayVO.setPivotPayCol(pivotPayCol);
        storeDayPayVO.setArrPayCol(payCol.split(","));

        return storeDayPayMapper.getStoreDayPayList(storeDayPayVO);
    }

}