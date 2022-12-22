package kr.co.solbipos.sale.pay.payDay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.payDay.service.PayDayService;
import kr.co.solbipos.sale.pay.payDay.service.PayDayVO;
import kr.co.solbipos.sale.pay.payDay.service.impl.PayDayMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayDayServiceImpl.java
 * @Description : 맘스터치 > 결제수단매출 > 일별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("payDayService")
@Transactional
public class PayDayServiceImpl implements PayDayService {
    private final PayDayMapper payDayMapper;

    public PayDayServiceImpl(PayDayMapper payDayMapper) {
        this.payDayMapper = payDayMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayDayList(PayDayVO payDayVO, SessionInfoVO sessionInfoVO) {
        payDayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payDayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payDayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = payDayVO.getStoreCds().split(",");
        payDayVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payDayVO.getStoreHqBrandCd() == "" || payDayVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payDayVO.getUserBrands().split(",");
                payDayVO.setUserBrandList(userBrandList);
            }
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = payDayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
            payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
        }
        payDayVO.setPivotPayCol(pivotPayCol);
        payDayVO.setArrPayCol(payCol.split(","));

        return payDayMapper.getPayDayList(payDayVO);
    }

}