package kr.co.solbipos.membr.anals.prepaid.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidService;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidStoreVO;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidFg;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidPayFg;
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
 * @ 2019.08.28  이다솜      선불입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
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

    private final PrepaidMapper mapper;
    private final PopupMapper popupMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PrepaidServiceImpl(PrepaidMapper mapper, PopupMapper popupMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.popupMapper = popupMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 선불 회원 충전, 사용 내역 */
    @Override
    public List<DefaultMap<Object>> getPrepaidMemberList(PrepaidStoreVO prepaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        prepaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prepaidStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }
        prepaidStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidStoreVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prepaidStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prepaidStoreVO.getStoreCds(), 3900));
            prepaidStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mapper.getPrepaidMemberList(prepaidStoreVO);
    }

    /** 선불 회원 충전, 사용 내역(엑셀) */
    @Override
    public List<DefaultMap<Object>> getPrepaidMemberListExcel(PrepaidStoreVO prepaidStoreVO, SessionInfoVO sessionInfoVO) {

        prepaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prepaidStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(prepaidStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prepaidStoreVO.getStoreCds(), 3900));
            prepaidStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mapper.getPrepaidMemberListExcel(prepaidStoreVO);
    }

    /** 선불금 등록 대상 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getChargeMemberList(PrepaidStoreVO prepaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        // 선불은 기본 자점 회원을 대상을 한다.
        // 하지만, 본사가 보나비(아티제)의 경우에는 후불 결제를 위한 회원 구분을 위해
        // 회원 테이블에 본사의 '기본매장'을 사용하고 있어서 그런 경우 REG_STORE_CD가 기본매장인 회원은 제외해야한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        prepaidStoreVO.setDefaultStoreCd(defaultStoreCd);

        prepaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prepaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prepaidStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prepaidStoreVO.setEmpNo(sessionInfoVO.getEmpNo());

        return mapper.getChargeMemberList(prepaidStoreVO);
    }

    /** 선불충전 */
    @Override
    public int saveChargeAmt(PrepaidStoreVO prepaidStoreVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prepaidStoreVO.setSaleDate(currentDateString());
        prepaidStoreVO.setPrepaidDt(dt);
        prepaidStoreVO.setPrepaidFg(PrepaidFg.CHARGE); // 충전
        prepaidStoreVO.setPrepaidPayFg(PrepaidPayFg.CASH); // 현금
        prepaidStoreVO.setNonsaleTypeApprNo(" ");// 비매출 영수증번호

        prepaidStoreVO.setRegId(sessionInfoVO.getUserId());
        prepaidStoreVO.setRegDt(dt);
        prepaidStoreVO.setModId(sessionInfoVO.getUserId());
        prepaidStoreVO.setModDt(dt);

        int result = mapper.saveChargeAmt(prepaidStoreVO);

        //선불입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
        if(result > 0){
            result = mapper.savePaidBalancePrePaid(prepaidStoreVO);
        }

        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;

    }
    /** 선불충전 */
    @Override
    public int saveChargeAmt(PrepaidStoreVO[] prepaidStoreVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();
        int result = 0;
        for(PrepaidStoreVO prepaidStoreVO : prepaidStoreVOs){

            prepaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prepaidStoreVO.setSaleDate(currentDateString());
            prepaidStoreVO.setPrepaidDt(dt);
            prepaidStoreVO.setPrepaidFg(PrepaidFg.CHARGE); // 충전
            prepaidStoreVO.setPrepaidPayFg(PrepaidPayFg.CASH); // 현금
            prepaidStoreVO.setNonsaleTypeApprNo(" ");// 비매출 영수증번호

            prepaidStoreVO.setRegId(sessionInfoVO.getUserId());
            prepaidStoreVO.setRegDt(dt);
            prepaidStoreVO.setModId(sessionInfoVO.getUserId());
            prepaidStoreVO.setModDt(dt);

            result = mapper.saveChargeAmt(prepaidStoreVO);

            //선불입금 시 집계 테이블(TB_MB_MEMBER_PAID_BALANCE)에 금액반영
            if(result > 0){
                result = mapper.savePaidBalancePrePaid(prepaidStoreVO);
            }

            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }
        return result;

    }
}
