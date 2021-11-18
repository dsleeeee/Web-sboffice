package kr.co.solbipos.mobile.sale.status.voucherNo.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.voucherNo.service.MobileVoucherNoService;
import kr.co.solbipos.mobile.sale.status.voucherNo.service.MobileVoucherNoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name : MobileVoucherNoServiceImpl.java
 * @Description : (모바일) 매출현황 > 최종교환권번호
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.16  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.11.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileVoucherNoService")
@Transactional
public class MobileVoucherNoServiceImpl implements MobileVoucherNoService {
    private final MobileVoucherNoMapper mobileVoucherNoMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileVoucherNoServiceImpl(MobileVoucherNoMapper mobileVoucherNoMapper) {
        this.mobileVoucherNoMapper = mobileVoucherNoMapper;
    }

    /** 최종교환권 번호 */
    @Override
    public String getVoucherNo(MobileVoucherNoVO mobileVoucherNoVO, SessionInfoVO sessionInfoVO){
        mobileVoucherNoVO.setStoreCd(sessionInfoVO.getStoreCd());
        return mobileVoucherNoMapper.getVoucherNo(mobileVoucherNoVO);
    }
}
