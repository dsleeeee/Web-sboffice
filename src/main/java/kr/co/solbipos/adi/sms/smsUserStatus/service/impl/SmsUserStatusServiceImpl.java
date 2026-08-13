package kr.co.solbipos.adi.sms.smsUserStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsUserStatus.service.SmsUserStatusService;
import kr.co.solbipos.adi.sms.smsUserStatus.service.SmsUserStatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : SmsUserStatusServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("smsUserStatusService")
@Transactional
public class SmsUserStatusServiceImpl implements SmsUserStatusService {

    private final SmsUserStatusMapper smsUserStatusMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SmsUserStatusServiceImpl(SmsUserStatusMapper smsUserStatusMapper) {
        this.smsUserStatusMapper = smsUserStatusMapper;
    }

    /** SMS사용자 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsUserList(SmsUserStatusVO smsUserStatusVO) {
        return smsUserStatusMapper.getSmsUserList(smsUserStatusVO);
    }

    /** 발신번호 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSendTelNoList(SmsUserStatusVO smsUserStatusVO) {
        return smsUserStatusMapper.getSendTelNoList(smsUserStatusVO);
    }

    /** 충전현황 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsChargeStatusList(SmsUserStatusVO smsUserStatusVO) {
        return smsUserStatusMapper.getSmsChargeStatusList(smsUserStatusVO);
    }

    /** 전송이력 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsSendHistList(SmsUserStatusVO smsUserStatusVO) {
        return smsUserStatusMapper.getSmsSendHistList(smsUserStatusVO);
    }
}
