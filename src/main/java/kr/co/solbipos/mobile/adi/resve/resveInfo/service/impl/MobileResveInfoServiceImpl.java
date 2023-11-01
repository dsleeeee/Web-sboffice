package kr.co.solbipos.mobile.adi.resve.resveInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.resve.resveInfo.service.MobileResveInfoService;
import kr.co.solbipos.mobile.adi.resve.resveInfo.service.MobileResveInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

/**
 * @Class Name : MobileResveInfoServiceImpl.java
 * @Description : (모바일) 부가서비스 > 예약현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("MobileResveInfoService")
@Transactional
public class MobileResveInfoServiceImpl implements MobileResveInfoService {
    private final MobileResveInfoMapper mobileResveInfoMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileResveInfoServiceImpl(MobileResveInfoMapper mobileResveInfoMapper, PopupMapper popupMapper) {
        this.mobileResveInfoMapper = mobileResveInfoMapper;
        this.popupMapper = popupMapper;
    }

    /** 예약현황 조회 */
    @Override
    public List<DefaultMap<String>> getResveList(MobileResveInfoVO mobileResveInfoVO, SessionInfoVO sessionInfoVO) {
        mobileResveInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileResveInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileResveInfoVO.getSrchStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileResveInfoVO.getSrchStoreCd(), 3900));
            mobileResveInfoVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileResveInfoMapper.getResveList(mobileResveInfoVO);
    }
}
