package kr.co.solbipos.membr.anals.postpaid.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.enums.StatusFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidService;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidFg;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidPayFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import kr.co.solbipos.membr.anals.postpaid.service.impl.PostpaidMapper;
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
 * @ 2019.08.28  이다솜      외상입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
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

    private final PostpaidMapper mapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PostpaidServiceImpl(PostpaidMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 후불 회원 외상, 입금 내역 */
    @Override
    public List<DefaultMap<Object>> getPostpaidMemberList(PostpaidStoreVO postpaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        String[] storeCds = postpaidStoreVO.getStoreCds().split(",");
        postpaidStoreVO.setStoreCdList(storeCds);

        postpaidStoreVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        postpaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        postpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());

        return mapper.getPostpaidMemberList(postpaidStoreVO);
    }

    /** 후불 대상 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getDepositMemberList(PostpaidStoreVO postpaidStoreVO, SessionInfoVO sessionInfoVO) {

        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        postpaidStoreVO.setDefaultStoreCd(defaultStoreCd);
        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            postpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

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

        // 외상입금하려는 회원의 StoreCd를 가져온다.
        if(postpaidStoreVO.getStoreCd() == null || postpaidStoreVO.getStoreCd() == ""){
            postpaidStoreVO.setStoreCd(mapper.getDepositStoreCd(postpaidStoreVO));
        }

        int result = mapper.saveDeposit(postpaidStoreVO);

        // 외상입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
        if(result > 0){
            result = mapper.savePaidBalancePostPaid(postpaidStoreVO);
        }

        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 세금계산서 요청목록 조회 */
    @Override
    public List<DefaultMap<Object>> getTaxBillList(TaxBillVO taxBillVO, SessionInfoVO sessionInfoVO) {

        taxBillVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        taxBillVO.setStatusFg(StatusFg.REQEUST); // 요청목록만 조회

        return mapper.getTaxBillList(taxBillVO);
    }

    /** 세금계산서 발행 입금 */
    @Override
    public int saveTaxBillComplete(TaxBillVO taxBillVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        // 세금계산서 발행 및 입금 완료 처리
        taxBillVO.setStatusFg(StatusFg.COMPLETE); // 입금 완료
        taxBillVO.setModDt(dt);
        taxBillVO.setModId(sessionInfoVO.getUserId());


        result = mapper.saveTaxBillComplete(taxBillVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));


        // 외상입금 처리 // todo 이거 완료하고 나서 목록에서 매핑되는 세금계산서도 보여줘야 함.
        PostpaidStoreVO postpaidStoreVO = new PostpaidStoreVO();

        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            postpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
//        postpaidStoreVO.setMembrOrgnCd(taxBillVO.getMembrOrgnCd());
        postpaidStoreVO.setMembrNo(taxBillVO.getMembrNo());
        postpaidStoreVO.setSaleDate(currentDateString());
        postpaidStoreVO.setPostpaidDt(dt);
        postpaidStoreVO.setPostpaidFg(PostpaidFg.DEPOSIT); // 입금
        postpaidStoreVO.setPostpaidPayFg(PostpaidPayFg.CASH); // 현금
        postpaidStoreVO.setPostpaidAmt(taxBillVO.getRequestAmt()); // 입금액
        postpaidStoreVO.setNonsaleTypeApprNo("  ");// 비매출 승인번호

        postpaidStoreVO.setRegId(sessionInfoVO.getUserId());
        postpaidStoreVO.setRegDt(dt);
        postpaidStoreVO.setModId(sessionInfoVO.getUserId());
        postpaidStoreVO.setModDt(dt);

        // 외상입금하려는 회원의 StoreCd를 가져온다.
        if(postpaidStoreVO.getStoreCd() == null || postpaidStoreVO.getStoreCd() == ""){
            postpaidStoreVO.setStoreCd(mapper.getDepositStoreCd(postpaidStoreVO));
        }

        result = mapper.saveDeposit(postpaidStoreVO);

        // 외상입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
        if(result > 0){
            result = mapper.savePaidBalancePostPaid(postpaidStoreVO);
        }

        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
