package kr.co.solbipos.mobile.sale.status.storeProdSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeProdSale.service.MobileStoreProdSaleService;
import kr.co.solbipos.mobile.sale.status.storeProdSale.service.MobileStoreProdSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileStoreProdSaleServiceImpl.java
 * @Description : 모바일 매장매출 > 일별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileStoreProdSaleService")
@Transactional
public class MobileStoreProdSaleServiceImpl implements MobileStoreProdSaleService {
    private final MobileStoreProdSaleMapper mobileStoreProdSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileStoreProdSaleServiceImpl(MobileStoreProdSaleMapper mobileStoreProdSaleMapper) {
        this.mobileStoreProdSaleMapper = mobileStoreProdSaleMapper;
    }

    /** 일자별 매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileStoreProdSaleDtlList(MobileStoreProdSaleVO mobileStoreProdSaleVO, SessionInfoVO sessionInfoVO) {

        mobileStoreProdSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileStoreProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (!StringUtil.getOrBlank(mobileStoreProdSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileStoreProdSaleVO.setArrStoreCd(mobileStoreProdSaleVO.getSrchStoreCd().split(","));
        }

        return mobileStoreProdSaleMapper.getMobileStoreProdSaleDtlList(mobileStoreProdSaleVO);
    }
}