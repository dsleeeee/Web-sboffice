package kr.co.solbipos.mobile.sale.status.storeTimeSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeTimeSale.service.MobileStoreTimeSaleService;
import kr.co.solbipos.mobile.sale.status.storeTimeSale.service.MobileStoreTimeSaleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileStoreTimeSaleServiceImpl.java
 * @Description : (모바일) 매장매출 > 시간대별
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
@Service("mobileStoreTimeSaleService")
@Transactional
public class MobileStoreTimeSaleServiceImpl implements MobileStoreTimeSaleService {
    private final MobileStoreTimeSaleMapper mobileStoreTimeSaleMapper;

    public MobileStoreTimeSaleServiceImpl(MobileStoreTimeSaleMapper mobileStoreTimeSaleMapper) {
        this.mobileStoreTimeSaleMapper = mobileStoreTimeSaleMapper;
    }

    /** 시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileStoreTimeSaleList(MobileStoreTimeSaleVO mobileStoreTimeSaleVO, SessionInfoVO sessionInfoVO) {

        mobileStoreTimeSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileStoreTimeSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileStoreTimeSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileStoreTimeSaleVO.setArrStoreCd(mobileStoreTimeSaleVO.getSrchStoreCd().split(","));
        }

        return mobileStoreTimeSaleMapper.getMobileStoreTimeSaleList(mobileStoreTimeSaleVO);
    }

}