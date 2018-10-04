package kr.co.solbipos.membr.anals.prepaid.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidService;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidStoreVO;
import kr.co.solbipos.membr.anals.prepaid.service.enums.PrepaidInFg;
import kr.co.solbipos.membr.anals.prepaid.service.enums.PrepaidPayFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PrepaidServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 선불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prepaidService")
@Transactional
public class PrepaidServiceImpl implements PrepaidService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PrepaidMapper mapper;

    @Autowired
    CmmEnvUtil cmmEnvUtil;

    /** 선불 회원 충전, 사용 내역 */
    @Override
    public List<DefaultMap<Object>> getPrepaidMemberList(PrepaidStoreVO prepaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        String[] storeCds = prepaidStoreVO.getStoreCds().split(",");
        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prepaidStoreVO.setStoreCdList(storeCds);

        return mapper.getPrepaidMemberList(prepaidStoreVO);
    }

    /** 선불금 등록 대상 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getChargeMemberList(PrepaidStoreVO prepaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        // 선불은 기본 자점 회원을 대상을 한다.
        // 하지만, 본사가 보나비(아티제)의 경우에는 후불 결제를 위한 회원 구분을 위해
        // 회원 테이블에 본사의 '기본매장'을 사용하고 있어서 그런 경우 REG_STORE_CD가 기본매장인 회원은 제외해야한다.
        String defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));

        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prepaidStoreVO.setDefaultStoreCd(defaultStoreCd);

        return mapper.getChargeMemberList(prepaidStoreVO);
    }

    /** 선불충전 */
    @Override
    public int saveChargeAmt(PrepaidStoreVO prepaidStoreVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prepaidStoreVO.setSaleDate(currentDateString());
        prepaidStoreVO.setPrepaidDt(dt);
        prepaidStoreVO.setPrepaidInFg(PrepaidInFg.CHARGE); // 입금
        prepaidStoreVO.setPrepaidPayFg(PrepaidPayFg.CASH); // 현금
        prepaidStoreVO.setNonsaleBillNo(" ");// 비매출 영수증번호
        prepaidStoreVO.setOrgPrepaidNo(" "); // 원거래 충전번호
        prepaidStoreVO.setSendYn(UseYn.Y);  // TODO 전송여부 YN갑 체크 필요
        prepaidStoreVO.setSendDt(dt);

        prepaidStoreVO.setRegId(sessionInfoVO.getUserId());
        prepaidStoreVO.setRegDt(dt);
        prepaidStoreVO.setModId(sessionInfoVO.getUserId());
        prepaidStoreVO.setModDt(dt);

        return mapper.saveChargeAmt(prepaidStoreVO);
    }
}
