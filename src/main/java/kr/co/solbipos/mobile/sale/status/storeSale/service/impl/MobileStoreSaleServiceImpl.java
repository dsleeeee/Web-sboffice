package kr.co.solbipos.mobile.sale.status.storeSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeSale.service.MobileStoreSaleService;
import kr.co.solbipos.mobile.sale.status.storeSale.service.MobileStoreSaleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileStoreSaleServiceImpl.java
 * @Description : (모바일) 매장매출 > 매장종합
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
@Service("mobileStoreSaleService")
@Transactional
public class MobileStoreSaleServiceImpl implements MobileStoreSaleService {
    private final MobileStoreSaleMapper mobileStoreSaleMapper;

    public MobileStoreSaleServiceImpl(MobileStoreSaleMapper mobileStoreSaleMapper) {
        this.mobileStoreSaleMapper = mobileStoreSaleMapper;
    }

    /** 매장종합 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileStoreSaleList(MobileStoreSaleVO mobileStoreSaleVO, SessionInfoVO sessionInfoVO) {

        mobileStoreSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileStoreSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileStoreSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileStoreSaleVO.setArrStoreCd(mobileStoreSaleVO.getSrchStoreCd().split(","));
        }

        return mobileStoreSaleMapper.getMobileStoreSaleList(mobileStoreSaleVO);
    }

    /** 차트 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileStoreSaleDtlList(MobileStoreSaleVO mobileStoreSaleVO, SessionInfoVO sessionInfoVO) {

        mobileStoreSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileStoreSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mobileStoreSaleMapper.getMobileStoreSaleDtlList(mobileStoreSaleVO);
    }

}