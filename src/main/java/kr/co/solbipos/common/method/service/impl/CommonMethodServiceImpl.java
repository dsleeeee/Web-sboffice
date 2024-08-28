package kr.co.solbipos.common.method.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.method.service.CommonMethodService;
import kr.co.solbipos.common.method.service.CommonMethodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CommonMethodServiceImpl.java
 * @Description : (공통) 화면 공통 사용 메소드 모음
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.13  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("commonMethodServiceImpl")
@Transactional
public class CommonMethodServiceImpl implements CommonMethodService {

    private final CommonMethodMapper commonMethodMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public CommonMethodServiceImpl(CommonMethodMapper commonMethodMapper) {
        this.commonMethodMapper = commonMethodMapper;
    }

    /** 사용자 행위 기록 */
    @Override
    public int saveUserAct(CommonMethodVO commonMethodVO, SessionInfoVO sessionInfoVO){

        String dt = currentDateTimeString();

        commonMethodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            commonMethodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        commonMethodVO.setUserId(sessionInfoVO.getUserId());
        commonMethodVO.setLoginIp(sessionInfoVO.getLoginIp());
        commonMethodVO.setRegDt(dt);
        commonMethodVO.setRegId(sessionInfoVO.getUserId());
        commonMethodVO.setvUserId(sessionInfoVO.getvUserId());

        if(commonMethodVO.getExcelPw() != null && commonMethodVO.getExcelPw().equals("c08001")){
            commonMethodVO.setContents("[전체 엑셀다운로드-마스킹제거(TB_LG_MB_MEMBER_DOWNLOAD_LOG)] 버튼 클릭 시");
        }

        return commonMethodMapper.saveUserAct(commonMethodVO);
    }
}
