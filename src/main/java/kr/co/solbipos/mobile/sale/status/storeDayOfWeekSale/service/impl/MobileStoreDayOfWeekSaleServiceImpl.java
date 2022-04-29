package kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.MobileStoreDayOfWeekSaleService;
import kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.MobileStoreDayOfWeekSaleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileStoreSaleServiceImpl.java
 * @Description : (모바일) 매장매출 > 요일별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileStoreDayOfWeekSaleService")
@Transactional
public class MobileStoreDayOfWeekSaleServiceImpl implements MobileStoreDayOfWeekSaleService {
    private final MobileStoreDayOfWeekSaleMapper mobileStoreDayOfWeekSaleMapper;

    public MobileStoreDayOfWeekSaleServiceImpl(MobileStoreDayOfWeekSaleMapper mobileStoreDayOfWeekSaleMapper) {
        this.mobileStoreDayOfWeekSaleMapper = mobileStoreDayOfWeekSaleMapper;
    }


    /** 요일별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileStoreDayOfWeekSaleList(MobileStoreDayOfWeekSaleVO mobileStoreDayOfWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileStoreDayOfWeekSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileStoreDayOfWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileStoreDayOfWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileStoreDayOfWeekSaleVO.setArrStoreCd(mobileStoreDayOfWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileStoreDayOfWeekSaleMapper.getMobileStoreDayOfWeekSaleList(mobileStoreDayOfWeekSaleVO);
    }
}