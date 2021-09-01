package kr.co.solbipos.mobile.sale.status.orderChannelSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleService;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileOrderChannelSaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 주문채널별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.30  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileOrderChannelSaleService")
@Transactional
public class MobileOrderChannelSaleServiceImpl implements MobileOrderChannelSaleService {

    private final MobileOrderChannelSaleMapper mobileOrderChannelSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileOrderChannelSaleServiceImpl(MobileOrderChannelSaleMapper mobileOrderChannelSaleMapper) {
        this.mobileOrderChannelSaleMapper = mobileOrderChannelSaleMapper;
    }

    /** 모바일 매출현황 - 주문채널 구분자 조회 */
    @Override
    public List<DefaultMap<String>> getDlvrInFgColList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO, SessionInfoVO sessionInfoVO) {
        mobileOrderChannelSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileOrderChannelSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mobileOrderChannelSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mobileOrderChannelSaleMapper.getDlvrInFgColList(mobileOrderChannelSaleVO);
    }

    /** 모바일 매출현황 - 주문채널별 현황 조회*/
    @Override
    public List<DefaultMap<String>> getOrderChannelSalePayList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO, SessionInfoVO sessionInfoVO) {

        mobileOrderChannelSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileOrderChannelSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mobileOrderChannelSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(mobileOrderChannelSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileOrderChannelSaleVO.setArrStoreCd(mobileOrderChannelSaleVO.getSrchStoreCd().split(","));
        }

        return mobileOrderChannelSaleMapper.getOrderChannelSalePayList(mobileOrderChannelSaleVO);
    }

    /** 모바일 매출현황 - 주문채널 일자별 매출현황 조회*/
    @Override
    public List<DefaultMap<String>> getOrderChannelSaleDtlList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO, SessionInfoVO sessionInfoVO) {

        mobileOrderChannelSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileOrderChannelSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mobileOrderChannelSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(mobileOrderChannelSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileOrderChannelSaleVO.setArrStoreCd(mobileOrderChannelSaleVO.getSrchStoreCd().split(","));
        }

        // 주문채널 구분자 array 값 세팅
        mobileOrderChannelSaleVO.setArrDlvrInFgCol(mobileOrderChannelSaleVO.getDlvrInFgCol().split(","));

        return mobileOrderChannelSaleMapper.getOrderChannelSaleDtlList(mobileOrderChannelSaleVO);
    }
}
