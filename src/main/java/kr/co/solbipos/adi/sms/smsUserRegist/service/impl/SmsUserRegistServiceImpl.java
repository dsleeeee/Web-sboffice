package kr.co.solbipos.adi.sms.smsUserRegist.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistService;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsUserRegistServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("smsUserRegistService")
@Transactional
public class SmsUserRegistServiceImpl implements SmsUserRegistService {

    private final SmsUserRegistMapper smsUserRegistMapper;
    private final MessageService messageService;

    @Autowired
    public SmsUserRegistServiceImpl(SmsUserRegistMapper smsUserRegistMapper, MessageService messageService) {
        this.smsUserRegistMapper = smsUserRegistMapper;
        this.messageService = messageService;
    }

    /** DI로 기존 등록 사용자아이디 조회 (없으면 null) */
    @Override
    public String getUserIdByDi(SmsUserRegistVO smsUserRegistVO) {
        return smsUserRegistMapper.getUserIdByDi(smsUserRegistVO);
    }

    /** SMS 사용 등록 - 본인인증 완료 후 저장 (신규 등록만, 삭제가 하드 딜리트라 기존 등록 여부 분기 불필요) */
    @Override
    public int saveUserRegist(SmsUserRegistVO smsUserRegistVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        smsUserRegistVO.setUserId(sessionInfoVO.getUserId());
        smsUserRegistVO.setModDt(currentDt);
        smsUserRegistVO.setModId(sessionInfoVO.getUserId());
        smsUserRegistVO.setRegDt(currentDt);
        smsUserRegistVO.setRegId(sessionInfoVO.getUserId());

        return smsUserRegistMapper.insertUserRegist(smsUserRegistVO);
    }

    /** 진입시 체크 - 세션 사용자의 등록정보 조회 (없으면 null) */
    @Override
    public DefaultMap<Object> getUserRegistInfo(SessionInfoVO sessionInfoVO) {
        SmsUserRegistVO smsUserRegistVO = new SmsUserRegistVO();
        smsUserRegistVO.setUserId(sessionInfoVO.getUserId());

        return smsUserRegistMapper.getUserRegistInfo(smsUserRegistVO);
    }

    /** SMS 사용등록 - SMS사용자 삭제 */
    @Override
    public int deleteUserRegist(SmsUserRegistVO smsUserRegistVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsUserRegistVO.setUserId(sessionInfoVO.getUserId());
        // 사용자 삭제 시 발신번호 N 처리
        smsUserRegistVO.setUseYn("N");
        smsUserRegistVO.setModDt(currentDt);
        smsUserRegistVO.setModId(sessionInfoVO.getUserId());

        // 발신번호 N 처리 후 사용자 삭제
        procCnt += smsUserRegistMapper.deleteSmsTelNoManage(smsUserRegistVO);
        procCnt += smsUserRegistMapper.deleteUserRegist(smsUserRegistVO);

        if(procCnt <=0){
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.dltFail"));
        }

        return procCnt;
    }
}
