package kr.co.solbipos.application.pos.posPostpaid.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.pos.posPostpaid.service.PosPostpaidService;
import kr.co.solbipos.application.pos.posPostpaid.service.PosPostpaidStoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.enums.StatusFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PosPostpaidServiceImpl.java
 * @Description : POS 화면에서 세금계산서 발행요청
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 12.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("posPostpaidService")
public class PosPostpaidServiceImpl implements PosPostpaidService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PosPostpaidMapper posPostpaidMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosPostpaidServiceImpl(PosPostpaidMapper posPostpaidMapper, MessageService messageService) {
        this.posPostpaidMapper = posPostpaidMapper;
        this.messageService = messageService;
    }

    /** 대상 회원목록 조회*/
    @Override
    public List<DefaultMap<String>> getMemberList(PosPostpaidStoreVO posPostpaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        posPostpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 목록 조회
        List<DefaultMap<String>> reultList = posPostpaidMapper.getMemberList(posPostpaidStoreVO);

        return reultList;
    }

    /** 세금계산서 발행 요청 */
    @Override
    public int saveTaxBillRequet(PosPostpaidStoreVO posPostpaidStoreVO,
        SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        posPostpaidStoreVO.setBillDate(currentDateString());
        posPostpaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posPostpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        posPostpaidStoreVO.setRequestDt(currentDt);
        posPostpaidStoreVO.setStatusFg(StatusFg.REQEUST);   //요청
        posPostpaidStoreVO.setRegDt(currentDt);
        posPostpaidStoreVO.setRegId(sessionInfoVO.getUserId());
        posPostpaidStoreVO.setModDt(currentDt);
        posPostpaidStoreVO.setModId(sessionInfoVO.getUserId());



        int result = 0;

        // 세금계산서 발행요청건 등록
        result = posPostpaidMapper.saveTaxBillRequet(posPostpaidStoreVO);
        if ( result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }



}
