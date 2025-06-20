package kr.co.solbipos.adi.sms.remainAmtChk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.adi.sms.remainAmtChk.service.RemainAmtChkService;
import kr.co.solbipos.adi.sms.remainAmtChk.service.RemainAmtChkVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : RemainAmtChkServiceImpl.java
 * @Description : 부가서비스 > SMS분석 > 잔여금액확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("remainAmtChkService")
@Transactional
public class RemainAmtChkServiceImpl implements RemainAmtChkService {

    private final RemainAmtChkMapper remainAmtChkMapper;

    @Autowired
    public RemainAmtChkServiceImpl(RemainAmtChkMapper remainAmtChkMapper) {
        this.remainAmtChkMapper = remainAmtChkMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<String>> getRemainAmtChkList(RemainAmtChkVO remainAmtChkVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateString();

        remainAmtChkVO.setNowDate(currentDt);

        return remainAmtChkMapper.getRemainAmtChkList(remainAmtChkVO);
    }

    /** 충전/사용내역 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getRemainAmtHistList(RemainAmtChkVO remainAmtChkVO, SessionInfoVO sessionInfoVO) {
        return remainAmtChkMapper.getRemainAmtHistList(remainAmtChkVO);
    }
}
