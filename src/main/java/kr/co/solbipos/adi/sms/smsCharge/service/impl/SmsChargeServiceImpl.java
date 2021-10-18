package kr.co.solbipos.adi.sms.smsCharge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeService;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeVO;
import kr.co.solbipos.adi.sms.smsChargeHist.service.impl.SmsChargeHistMapper;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistVO;
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

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeServiceImpl(SmsChargeMapper smsChargeMapper, SmsChargeHistMapper smsChargeHistMapper) {
        this.smsChargeMapper = smsChargeMapper;
        this.smsChargeHistMapper = smsChargeHistMapper; // SMS충전내역
    }

    /** 충전결제 저장 */
    @Override
    public int getSmsChargeSaveInsert(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsChargeVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        // 현재 잔여수량
        String smsBaseQty = smsChargeMapper.getSmsBaseQtySelect(smsChargeVO);


        // SmsChargeHistVO
        SmsChargeHistVO smsChargeHistVO = new SmsChargeHistVO();
        smsChargeHistVO.setRegDt(currentDt);
        smsChargeHistVO.setRegId(sessionInfoVO.getUserId());
        smsChargeHistVO.setModDt(currentDt);
        smsChargeHistVO.setModId(sessionInfoVO.getUserId());

        smsChargeHistVO.setSelectOrgnCd(sessionInfoVO.getOrgnCd());
        smsChargeHistVO.setChargeDate(smsChargeVO.getChargeTime().substring(0,8));
        smsChargeHistVO.setChargeTime(smsChargeVO.getChargeTime().substring(8,14));
        smsChargeHistVO.setPgresource(smsChargeVO.getPgresource());
        smsChargeHistVO.setChargeAmt(smsChargeVO.getChargeAmt()); // 충전금액
        smsChargeHistVO.setSuccessYn(smsChargeVO.getSuccessYn());
        smsChargeHistVO.setControlno(smsChargeVO.getControlno());
        smsChargeHistVO.setApprovalnum(smsChargeVO.getApprovalnum());
        smsChargeHistVO.setResultcode(smsChargeVO.getResultcode());
        smsChargeHistVO.setResultmessage(smsChargeVO.getResultmessage());

        smsChargeHistVO.setSmsBaseQty(smsBaseQty); // SMS 기초수량
        smsChargeHistVO.setSmsChargeQty("1"); // SMS 충전수량

        procCnt = smsChargeHistMapper.getSmsChargeRegistSaveInsert(smsChargeHistVO);

        // 잔여수량
        int smsQty = Integer.parseInt(smsChargeHistVO.getSmsBaseQty()) + Integer.parseInt(smsChargeHistVO.getSmsChargeQty());
        smsChargeHistVO.setSmsQty(String.valueOf(smsQty));

        // 잔여수량 저장 insert
        procCnt = smsChargeHistMapper.getSmsQtySaveInsert(smsChargeHistVO);

        return procCnt;
    }

    /** 결제취소 저장 */
    @Override
    public int getSmsChargeSaveUpdate(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        smsChargeVO.setModDt(currentDt);
        smsChargeVO.setModId(sessionInfoVO.getUserId());

        smsChargeVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsChargeVO.setRtnDate(smsChargeVO.getRtnTime().substring(0,8));
        smsChargeVO.setRtnTime(smsChargeVO.getRtnTime().substring(8,14));

        procCnt = smsChargeMapper.getSmsChargeSaveUpdate(smsChargeVO);

        // 현재 잔여수량
        String smsBaseQty = smsChargeMapper.getSmsBaseQtySelect(smsChargeVO);

        // 충전수량
        String smsChargeQty = smsChargeMapper.getSmsChargeQtySelect(smsChargeVO);


        // SmsChargeHistVO
        SmsChargeHistVO smsChargeHistVO = new SmsChargeHistVO();
        smsChargeHistVO.setRegDt(currentDt);
        smsChargeHistVO.setRegId(sessionInfoVO.getUserId());
        smsChargeHistVO.setModDt(currentDt);
        smsChargeHistVO.setModId(sessionInfoVO.getUserId());

        smsChargeHistVO.setSelectOrgnCd(sessionInfoVO.getOrgnCd());

        smsChargeHistVO.setSmsBaseQty(smsBaseQty); // SMS 기초수량
        smsChargeHistVO.setSmsChargeQty(smsChargeQty); // SMS 충전수량

        // 잔여수량
        int smsQty = Integer.parseInt(smsChargeHistVO.getSmsBaseQty()) - Integer.parseInt(smsChargeHistVO.getSmsChargeQty());
        smsChargeHistVO.setSmsQty(String.valueOf(smsQty));

        // 잔여수량 저장 insert
        procCnt = smsChargeHistMapper.getSmsQtySaveInsert(smsChargeHistVO);

        return procCnt;
    }
}