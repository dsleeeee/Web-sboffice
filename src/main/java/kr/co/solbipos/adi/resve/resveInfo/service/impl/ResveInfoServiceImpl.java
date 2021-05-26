package kr.co.solbipos.adi.resve.resveInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.resve.resveInfo.service.ResveInfoService;
import kr.co.solbipos.adi.resve.resveInfo.service.ResveInfoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ResveInfoServiceImpl.java
 * @Description : 부가서비스 > 게시판 > 일반게시판
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("ResveInfoService")
@Transactional
public class ResveInfoServiceImpl implements ResveInfoService {
    private final ResveInfoMapper resveInfoMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ResveInfoServiceImpl(ResveInfoMapper resveInfoMapper) {
        this.resveInfoMapper = resveInfoMapper;
    }

    /** 예약현황 조회 */
    @Override
    public List<DefaultMap<String>> getResveList(ResveInfoVO resveInfoVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        resveInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        resveInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(resveInfoVO.getStoreCd()).equals("")) {
                resveInfoVO.setArrStoreCd(resveInfoVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            resveInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return resveInfoMapper.getResveList(resveInfoVO);
    }
}
