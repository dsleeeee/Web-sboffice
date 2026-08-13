package kr.co.solbipos.sys.auth.smsMenuAuth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.auth.smsMenuAuth.service.SmsMenuAuthService;
import kr.co.solbipos.sys.auth.smsMenuAuth.service.SmsMenuAuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsMenuAuthServiceImpl.java
 * @Description : 시스템관리 > 권한관리 > SMS화면관리(관리자)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("SmsMenuAuthService")
@Transactional
public class SmsMenuAuthServiceImpl implements SmsMenuAuthService {

    private final SmsMenuAuthMapper smsMenuAuthMapper;

    @Autowired
    public SmsMenuAuthServiceImpl(SmsMenuAuthMapper smsMenuAuthMapper) {
        this.smsMenuAuthMapper = smsMenuAuthMapper;
    }

    /** SMS 메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSmsMenuList(SessionInfoVO sessionInfoVO,
                                                   SmsMenuAuthVO smsMenuAuthVO) {
        return smsMenuAuthMapper.getSmsMenuList(smsMenuAuthVO);
    }

    /** SMS 메뉴에 등록된 사용자 조회 */
    @Override
    public List<DefaultMap<String>> getRegUserList(SessionInfoVO sessionInfoVO,
                                                   SmsMenuAuthVO smsMenuAuthVO) {
        return smsMenuAuthMapper.getRegUserList(smsMenuAuthVO);
    }

    /** SMS 메뉴에 미등록된 사용자 조회 */
    @Override
    public List<DefaultMap<String>> getNoRegUserList(SessionInfoVO sessionInfoVO,
                                                     SmsMenuAuthVO smsMenuAuthVO) {
        return smsMenuAuthMapper.getNoRegUserList(smsMenuAuthVO);
    }

    /** 사용자 SMS 메뉴 권한 등록 */
    @Override
    public int insertSmsMenuAuth(SmsMenuAuthVO[] smsMenuAuthVOS, SessionInfoVO sessionInfoVO) {
        if (smsMenuAuthVOS == null || smsMenuAuthVOS.length == 0) {
            return 0;
        }

        int result = 0;
        String currentDt = currentDateTimeString();

        for (SmsMenuAuthVO smsMenuAuthVO : smsMenuAuthVOS) {
            // 권한 상세값은 클라이언트 값과 무관하게 관리자 메뉴 등록 기준으로 고정한다.
            smsMenuAuthVO.setRegDt(currentDt);
            smsMenuAuthVO.setRegId(sessionInfoVO.getUserId());
            smsMenuAuthVO.setModDt(currentDt);
            smsMenuAuthVO.setModId(sessionInfoVO.getUserId());

            result += smsMenuAuthMapper.insertSmsMenuAuth(smsMenuAuthVO);
        }

        return result;
    }

    /** 사용자 SMS 메뉴 권한 삭제 */
    @Override
    public int deleteSmsMenuAuth(SmsMenuAuthVO[] smsMenuAuthVOS, SessionInfoVO sessionInfoVO) {
        if (smsMenuAuthVOS == null || smsMenuAuthVOS.length == 0) {
            return 0;
        }

        int result = 0;

        for (SmsMenuAuthVO smsMenuAuthVO : smsMenuAuthVOS) {
            // 이 화면에서 등록한 관리자 웹 메뉴 권한만 삭제하도록 권한 구분값을 고정한다.
            result += smsMenuAuthMapper.deleteSmsMenuAuth(smsMenuAuthVO);
        }

        return result;
    }
}
