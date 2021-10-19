package kr.co.solbipos.adi.sms.smsChargeHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistService;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentTimeString;

/**
 * @Class Name : SmsChargeHistServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsChargeHistService")
@Transactional
public class SmsChargeHistServiceImpl implements SmsChargeHistService {
    private final SmsChargeHistMapper smsChargeHistMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeHistServiceImpl(SmsChargeHistMapper smsChargeHistMapper) { this.smsChargeHistMapper = smsChargeHistMapper; }

    /** SMS충전내역 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsChargeHistList(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        smsChargeHistVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        smsChargeHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsChargeHistMapper.getSmsChargeHistList(smsChargeHistVO);
    }

    /** SMS임의충전 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsChargeRegistList(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        smsChargeHistVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        smsChargeHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsChargeHistMapper.getSmsChargeRegistList(smsChargeHistVO);
    }

    /** SMS임의충전 팝업 - 저장 */
    @Override
    public int getSmsChargeRegistSave(SmsChargeHistVO smsChargeHistVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();
        String currentTime = currentTimeString();

        smsChargeHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsChargeHistVO.setRegDt(currentDt);
        smsChargeHistVO.setRegId(sessionInfoVO.getUserId());
        smsChargeHistVO.setModDt(currentDt);
        smsChargeHistVO.setModId(sessionInfoVO.getUserId());

        smsChargeHistVO.setChargeDate(currentDate);
        smsChargeHistVO.setChargeTime(currentTime);
        smsChargeHistVO.setPgresource("*");
        smsChargeHistVO.setChargeAmt("0"); // 충전금액
        smsChargeHistVO.setSuccessYn("Y");
        smsChargeHistVO.setControlno("*");
        smsChargeHistVO.setApprovalnum("");
        smsChargeHistVO.setResultcode("0000");
        smsChargeHistVO.setResultmessage("임의등록");

        procCnt = smsChargeHistMapper.getSmsChargeRegistSaveInsert(smsChargeHistVO);

        // 잔여수량
        int smsQty = Integer.parseInt(smsChargeHistVO.getSmsBaseQty()) + Integer.parseInt(smsChargeHistVO.getSmsChargeQty());
        smsChargeHistVO.setSmsQty(String.valueOf(smsQty));

        // 잔여수량 저장 insert
        procCnt = smsChargeHistMapper.getSmsQtySaveInsert(smsChargeHistVO);

        return procCnt;
    }
}