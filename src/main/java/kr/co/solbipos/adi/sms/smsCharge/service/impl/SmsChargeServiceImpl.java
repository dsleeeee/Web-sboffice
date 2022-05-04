package kr.co.solbipos.adi.sms.smsCharge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeService;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeVO;
import kr.co.solbipos.adi.sms.smsChargeHist.service.impl.SmsChargeHistMapper;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsChargeServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsChargeService")
@Transactional
public class SmsChargeServiceImpl implements SmsChargeService {
    private final SmsChargeMapper smsChargeMapper;
    private final SmsChargeHistMapper smsChargeHistMapper; // SMS충전내역
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeServiceImpl(SmsChargeMapper smsChargeMapper, SmsChargeHistMapper smsChargeHistMapper, CmmEnvUtil cmmEnvUtil) {
        this.smsChargeMapper = smsChargeMapper;
        this.smsChargeHistMapper = smsChargeHistMapper; // SMS충전내역
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 충전결제 저장 */
    @Override
    public int getSmsChargeSaveInsert(SmsChargeVO smsChargeVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        // 현재 잔여금액
        String smsBaseAmt = smsChargeMapper.getSmsBaseAmtSelect(smsChargeVO);

        LOGGER.info("WEB_SMS >>> 충전결제 >>> 현재 잔여금액 : " + smsBaseAmt);

        // SmsChargeHistVO
        SmsChargeHistVO smsChargeHistVO = new SmsChargeHistVO();
        smsChargeHistVO.setRegDt(currentDt);
        smsChargeHistVO.setRegId(smsChargeVO.getUserId());
        smsChargeHistVO.setModDt(currentDt);
        smsChargeHistVO.setModId(smsChargeVO.getUserId());

        smsChargeHistVO.setSelectOrgnCd(smsChargeVO.getOrgnCd());
        smsChargeHistVO.setChargeDate(smsChargeVO.getChargeDate());
        smsChargeHistVO.setChargeTime(smsChargeVO.getChargeTime());
        smsChargeHistVO.setPgresource(smsChargeVO.getPgresource());
        smsChargeHistVO.setChargeAmt(smsChargeVO.getChargeAmt()); // 충전금액
        smsChargeHistVO.setSuccessYn(smsChargeVO.getSuccessYn());
        smsChargeHistVO.setControlno(smsChargeVO.getControlno());
        smsChargeHistVO.setApprovalnum(smsChargeVO.getApprovalnum());
        smsChargeHistVO.setResultcode(smsChargeVO.getResultcode());
        smsChargeHistVO.setResultmessage(smsChargeVO.getResultmessage());
        smsChargeHistVO.setBaseChargeAmt(smsBaseAmt); // 기초충전금액
        smsChargeHistVO.setChargeTot(smsChargeVO.getChargeTot()); // 결제금액(KCP 실제 거래 금액)

        LOGGER.info("WEB_SMS >>> 충전결제 >>> 결제금액 : " + smsChargeHistVO.getChargeTot());
        LOGGER.info("WEB_SMS >>> 충전결제 >>> 충전할 충전금액 : " + smsChargeHistVO.getChargeAmt());

        procCnt = smsChargeHistMapper.getSmsChargeRegistSaveInsert(smsChargeHistVO);

        // 잔여금액
        int smsAmt = Integer.parseInt(smsChargeHistVO.getBaseChargeAmt()) + Integer.parseInt(smsChargeHistVO.getChargeAmt());
        smsChargeHistVO.setSmsAmt(String.valueOf(smsAmt));

        LOGGER.info("WEB_SMS >>> 충전결제 >>> 수정될 잔여금액 : " + smsBaseAmt);

        // 잔여금액 저장 insert
        procCnt = smsChargeHistMapper.getSmsQtySaveInsert(smsChargeHistVO);

        return procCnt;
    }

    /** 결제취소 저장 */
    @Override
    public int getSmsChargeSaveUpdate(SmsChargeVO smsChargeVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsChargeVO.setModDt(currentDt);
        smsChargeVO.setModId(smsChargeVO.getUserId());

        procCnt = smsChargeMapper.getSmsChargeSaveUpdate(smsChargeVO);

        // 현재 잔여금액
        String smsBaseAmt = smsChargeMapper.getSmsBaseAmtSelect(smsChargeVO);

        LOGGER.info("WEB_SMS >>> 결제취소 >>> 현재 잔여금액 : " + smsBaseAmt);

        // 충전금액
        String smsChargeAmt = smsChargeMapper.getSmsChargeAmtSelect(smsChargeVO);

        LOGGER.info("WEB_SMS >>> 결제취소 >>> 충전했던 충전금액 : " + smsChargeAmt);

        // SmsChargeHistVO
        SmsChargeHistVO smsChargeHistVO = new SmsChargeHistVO();
        smsChargeHistVO.setRegDt(currentDt);
        smsChargeHistVO.setRegId(smsChargeVO.getUserId());
        smsChargeHistVO.setModDt(currentDt);
        smsChargeHistVO.setModId(smsChargeVO.getUserId());

        smsChargeHistVO.setSelectOrgnCd(smsChargeVO.getOrgnCd());

        // 잔여금액
        int smsAmt = Integer.parseInt(smsBaseAmt) - Integer.parseInt(smsChargeAmt);
        smsChargeHistVO.setSmsAmt(String.valueOf(smsAmt));

        LOGGER.info("WEB_SMS >>> 결제취소 >>> 수정될 잔여금액 : " + smsBaseAmt);

        // 잔여금액 저장 insert
        procCnt = smsChargeHistMapper.getSmsQtySaveInsert(smsChargeHistVO);

        return procCnt;
    }

    /** 메세지 건당 가격안내 팝업 - 조회 */
    @Override
    public DefaultMap<String> getMsgOneAmtGuideList(SmsChargeVO smsChargeVO,  SessionInfoVO sessionInfoVO) {

        // SMS 가격 조회시
        smsChargeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        // 알림톡 가격 조회시
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            smsChargeVO.setAlkChargeOrgnCd(sessionInfoVO.getHqOfficeCd());

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            // 환경설정 코드값 조회 [1231 알림톡비용차감]
            String alkChargeEnvstVal1231 = StringUtil.getOrBlank(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1231"));
            System.out.println("WEB_ALIMTALK >>>  가격안내 >>> 환경설정 코드값 [1231 알림톡비용차감] : " + alkChargeEnvstVal1231);

            // 본사
            if(alkChargeEnvstVal1231.equals("1")) {
                smsChargeVO.setAlkChargeOrgnCd(sessionInfoVO.getHqOfficeCd());
            // 매장
            } else {
                smsChargeVO.setAlkChargeOrgnCd(sessionInfoVO.getStoreCd());
            }
        }

        return smsChargeMapper.getMsgOneAmtGuideList(smsChargeVO);
    }
}