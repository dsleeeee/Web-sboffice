package kr.co.solbipos.base.store.memberTerms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsService;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import kr.co.common.system.BaseEnv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MemberTermsServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 회원약관관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("memberTermsService")
@Transactional
public class MemberTermsServiceImpl implements MemberTermsService {
    private final MemberTermsMapper memberTermsMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MemberTermsServiceImpl(MemberTermsMapper memberTermsMapper) {
        this.memberTermsMapper = memberTermsMapper;
    }

    /** 회원약관관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMemberTermsList(MemberTermsVO memberTermsVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        memberTermsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        memberTermsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            memberTermsVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return memberTermsMapper.getMemberTermsList(memberTermsVO);
    }
}