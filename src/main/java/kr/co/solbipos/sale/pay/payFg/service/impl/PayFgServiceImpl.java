package kr.co.solbipos.sale.pay.payFg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.pay.payFg.service.PayFgService;
import kr.co.solbipos.sale.pay.payFg.service.PayFgVO;
import kr.co.solbipos.sale.pay.payFg.service.impl.PayFgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PayFgServiceImpl.java
 * @Description : 맘스터치 > 결제수단별 매출 > 결제수단별 일 매출현황
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
@Service("payFgService")
@Transactional
public class PayFgServiceImpl implements PayFgService {
    private final PayFgMapper payFgMapper;

    public PayFgServiceImpl(PayFgMapper payFgMapper) {
        this.payFgMapper = payFgMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPayFgList(PayFgVO payFgVO, SessionInfoVO sessionInfoVO) {

        payFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payFgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = payFgVO.getStoreCds().split(",");
        payFgVO.setStoreCdList(storeCds);

        return payFgMapper.getPayFgList(payFgVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPayFgExcelList(PayFgVO payFgVO, SessionInfoVO sessionInfoVO) {

        payFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            payFgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = payFgVO.getStoreCds().split(",");
        payFgVO.setStoreCdList(storeCds);

        return payFgMapper.getPayFgExcelList(payFgVO);
    }
}