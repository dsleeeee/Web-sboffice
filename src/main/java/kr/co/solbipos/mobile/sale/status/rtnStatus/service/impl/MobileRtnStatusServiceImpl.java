package kr.co.solbipos.mobile.sale.status.rtnStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.rtnStatus.service.MobileRtnStatusService;
import kr.co.solbipos.mobile.sale.status.rtnStatus.service.MobileRtnStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MobileRtnStatusServiceImpl.java
 * @Description : (모바일) 매출현황 > 반품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MobileRtnStatusService")
@Transactional
public class MobileRtnStatusServiceImpl implements MobileRtnStatusService {
    private final MobileRtnStatusMapper mobileRtnStatusMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileRtnStatusServiceImpl(MobileRtnStatusMapper mobileRtnStatusMapper, PopupMapper popupMapper) {
        this.mobileRtnStatusMapper = mobileRtnStatusMapper;
        this.popupMapper = popupMapper;
    }

    /** 반품현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileRtnStatusList(MobileRtnStatusVO mobileRtnStatusVO, SessionInfoVO sessionInfoVO) {

        mobileRtnStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileRtnStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileRtnStatusVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileRtnStatusVO.getSrchStoreCd(), 3900));
            mobileRtnStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileRtnStatusMapper.getMobileRtnStatusList(mobileRtnStatusVO);
    }

    /** 반품현황 상세 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileRtnStatusDtlList(MobileRtnStatusVO mobileRtnStatusVO, SessionInfoVO sessionInfoVO) {
        mobileRtnStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileRtnStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileRtnStatusVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileRtnStatusVO.getSrchStoreCd(), 3900));
            mobileRtnStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileRtnStatusMapper.getMobileRtnStatusDtlList(mobileRtnStatusVO);
    }

}