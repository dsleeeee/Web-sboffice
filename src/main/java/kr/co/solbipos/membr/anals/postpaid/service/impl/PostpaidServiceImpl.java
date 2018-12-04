package kr.co.solbipos.membr.anals.postpaid.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidService;
import kr.co.solbipos.membr.anals.postpaid.service.enums.PostpaidFg;
import kr.co.solbipos.membr.anals.postpaid.service.enums.PostpaidPayFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PostpaidServiceImpl.java
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
@Service("postpaidService")
@Transactional
public class PostpaidServiceImpl implements PostpaidService {

    private final kr.co.solbipos.membr.anals.postpaid.service.impl.PostpaidMapper mapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PostpaidServiceImpl(
        kr.co.solbipos.membr.anals.postpaid.service.impl.PostpaidMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 후불 회원 외상, 입금 내역 */
    @Override
    public List<DefaultMap<Object>> getPostpaidMemberList(PostpaidStoreVO postpaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        String[] storeCds = postpaidStoreVO.getStoreCds().split(",");
        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        postpaidStoreVO.setStoreCdList(storeCds);

        return mapper.getPostpaidMemberList(postpaidStoreVO);
    }

    /** 후불 대상 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getDepositMemberList(PostpaidStoreVO postpaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        postpaidStoreVO.setDefaultStoreCd(defaultStoreCd);
        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getDepositMemberList(postpaidStoreVO);
    }

    /** 외상 입금 */
    @Override
    public int saveDeposit(PostpaidStoreVO postpaidStoreVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        postpaidStoreVO.setSaleDate(currentDateString());
        postpaidStoreVO.setPostpaidDt(dt);
        postpaidStoreVO.setPostpaidFg(PostpaidFg.DEPOSIT); // 입금
        postpaidStoreVO.setPostpaidPayFg(PostpaidPayFg.CASH); // 현금
        postpaidStoreVO.setNonsaleTypeApprNo("  ");// 비매출 승인번호

        postpaidStoreVO.setRegId(sessionInfoVO.getUserId());
        postpaidStoreVO.setRegDt(dt);
        postpaidStoreVO.setModId(sessionInfoVO.getUserId());
        postpaidStoreVO.setModDt(dt);

        int result = mapper.saveDeposit(postpaidStoreVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
