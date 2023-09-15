package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
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

    /**
     * Constructor Injection
     */
    @Autowired
    public MobilePayInfoServiceImpl(MobilePayInfoMapper mobilePayInfoMapper) {
        this.mobilePayInfoMapper = mobilePayInfoMapper;
    }

    /** 결제수단 신용카드 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileCardList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getStoreCd()).equals("")) {
            mobilePayInfoVO.setArrStoreCd(mobilePayInfoVO.getStoreCd().split(","));
        }

        return mobilePayInfoMapper.getMobileCardList(mobilePayInfoVO);
    }

    /** 결제수단 가승인 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO, SessionInfoVO sessionInfoVO) {

        mobilePayInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobilePayInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobilePayInfoVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(mobilePayInfoVO.getStoreCd()).equals("")) {
            mobilePayInfoVO.setArrStoreCd(mobilePayInfoVO.getStoreCd().split(","));
        }

        return mobilePayInfoMapper.getMobileTemporaryList(mobilePayInfoVO);
    }
}