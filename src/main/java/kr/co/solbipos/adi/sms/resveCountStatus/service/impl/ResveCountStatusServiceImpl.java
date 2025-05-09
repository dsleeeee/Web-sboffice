package kr.co.solbipos.adi.sms.resveCountStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.resveCountStatus.service.ResveCountStatusService;
import kr.co.solbipos.adi.sms.resveCountStatus.service.ResveCountStatusVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ResveCountStatusServiceImpl.java
 * @Description : 부가서비스 > SMS분석 > 보나비문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.05.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("resveCountStatusService")
@Transactional
public class ResveCountStatusServiceImpl implements ResveCountStatusService {

    private final ResveCountStatusMapper resveCountStatusMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ResveCountStatusServiceImpl(ResveCountStatusMapper resveCountStatusMapper) {
        this.resveCountStatusMapper = resveCountStatusMapper;
    }

    /** 보나비예약건수현황 - 조회 */
    @Override
    public List<DefaultMap<String>> getResveCountStatusList(ResveCountStatusVO resveCountStatusVO, SessionInfoVO sessionInfoVO) {

        if(resveCountStatusVO.getSmsAmt() == null || resveCountStatusVO.getSmsAmt() == ""){
            resveCountStatusVO.setSmsAmt("1");
        }
        if(resveCountStatusVO.getLmsAmt() == null || resveCountStatusVO.getLmsAmt() == ""){
            resveCountStatusVO.setLmsAmt("1");
        }

        if(resveCountStatusVO.getOption().equals("0")) {
            return resveCountStatusMapper.getResveCountStatusList(resveCountStatusVO);
        }else{
            return resveCountStatusMapper.getStoreResveCountStatusList(resveCountStatusVO);
        }
    }
}
