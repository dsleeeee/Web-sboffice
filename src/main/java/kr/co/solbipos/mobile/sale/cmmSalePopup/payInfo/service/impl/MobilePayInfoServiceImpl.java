package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoService;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobilePayInfoServiceImpl.java
 * @Description : (모바일) 공통 결제수단 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobilePayInfoService")
@Transactional
public class MobilePayInfoServiceImpl implements MobilePayInfoService {
    private final MobilePayInfoMapper mobilePayInfoMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobilePayInfoServiceImpl(MobilePayInfoMapper mobilePayInfoMapper, PopupMapper popupMapper) {
        this.mobilePayInfoMapper = mobilePayInfoMapper;
        this.popupMapper = popupMapper;
    }

    /** 결제수단 신용카드 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileCardList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileCardList(mobilePayInfoVO);
    }

    /** 결제수단 현금 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileCashList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileCashList(mobilePayInfoVO);
    }

    /** 결제수단 페이코 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobilePaycoList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobilePaycoList(mobilePayInfoVO);
    }

    /** 결제수단 VMEM 포인트 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileVpointList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileVpointList(mobilePayInfoVO);
    }

    /** 결제수단 VMEM 전자상품권 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileVchargeList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileVchargeList(mobilePayInfoVO);
    }

    /** 결제수단 모바일페이 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMpayList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileMpayList(mobilePayInfoVO);
    }

    /** 결제수단 모바일쿠폰 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMcoupnList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileMcoupnList(mobilePayInfoVO);
    }

    /** 결제수단 포인트 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobilePointList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobilePointList(mobilePayInfoVO);
    }

    /** 결제수단 선불 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobilePrepaidList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobilePrepaidList(mobilePayInfoVO);
    }

    /** 결제수단 후불 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobilePostpaidList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobilePostpaidList(mobilePayInfoVO);
    }

    /** 결제수단 상품권 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileGiftList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileGiftList(mobilePayInfoVO);
    }

    /** 결제수단 식권 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileFstmpList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileFstmpList(mobilePayInfoVO);
    }

    /** 결제수단 제휴할인 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobilePartnerList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobilePartnerList(mobilePayInfoVO);
    }

    /** 결제수단 사원카드 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileEmpCardList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileEmpCardList(mobilePayInfoVO);
    }

    /** 결제수단 가승인 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileTemporaryList(mobilePayInfoVO);
    }

    /** 결제수단 스마트오더 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileVorderList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobilePayInfoVO.getSrchStoreCd(), 3900));
            mobilePayInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobilePayInfoMapper.getMobileVorderList(mobilePayInfoVO);
    }
}