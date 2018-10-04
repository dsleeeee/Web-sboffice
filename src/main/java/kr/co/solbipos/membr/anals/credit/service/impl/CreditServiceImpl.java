package kr.co.solbipos.membr.anals.credit.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.credit.service.CreditService;
import kr.co.solbipos.membr.anals.credit.service.CreditStoreVO;
import kr.co.solbipos.membr.anals.credit.service.enums.CreditInFg;
import kr.co.solbipos.membr.anals.credit.service.enums.CreditPayFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CreditServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 후불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("creditService")
@Transactional
public class CreditServiceImpl implements CreditService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CreditMapper mapper;

    /** 후불 회원 외상, 입금 내역 */
    @Override
    public List<DefaultMap<Object>> getCreditMemberList(CreditStoreVO creditStoreVO,
        SessionInfoVO sessionInfoVO) {

        String[] storeCds = creditStoreVO.getStoreCds().split(",");
        creditStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        creditStoreVO.setStoreCdList(storeCds);

        return mapper.getCreditMemberList(creditStoreVO);
    }

    /** 후불 대상 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getDepositMemberList(CreditStoreVO creditStoreVO,
        SessionInfoVO sessionInfoVO) {

        creditStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getDepositMemberList(creditStoreVO);
    }

    /** 외상 입금 */
    @Override
    public int saveDeposit(CreditStoreVO creditStoreVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();


        creditStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        creditStoreVO.setSaleDate(currentDateString());
        creditStoreVO.setCreditDt(dt);
        creditStoreVO.setCreditInFg(CreditInFg.DEPOSIT); // 입금
        creditStoreVO.setCreditPayFg(CreditPayFg.CASH); // 현금
        creditStoreVO.setNonsaleBillNo("  ");// 비매출 영수증번호
        creditStoreVO.setSendYn(UseYn.Y);  // TODO 전송여부 YN갑 체크 필요
        creditStoreVO.setSendDt(dt);

        creditStoreVO.setRegId(sessionInfoVO.getUserId());
        creditStoreVO.setRegDt(dt);
        creditStoreVO.setModId(sessionInfoVO.getUserId());
        creditStoreVO.setModDt(dt);

        return mapper.saveDeposit(creditStoreVO);
    }
}
