package kr.co.solbipos.base.price.salePriceResve.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveService;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SalePriceResveServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePriceResveService")
public class SalePriceResveServiceImpl implements SalePriceResveService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SalePriceResveMapper salePriceResveMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceResveServiceImpl(SalePriceResveMapper salePriceResveMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.salePriceResveMapper = salePriceResveMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 가격예약(본사판매가) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceResveVO.setUserId(sessionInfoVO.getUserId());

        return salePriceResveMapper.getHqSalePriceResveList(salePriceResveVO);
    }

    /** 가격예약(본사판매가) 추가 */
    @Override
    public int saveHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getHqSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertHqSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertHqSalePrice(salePriceResveVO);

            // 전매장적용 체크시, 본사 예약 판매가 매장적용
            if(salePriceResveVO.getApplyFg().equals("true")){
                salePriceResveMapper.insertHqSalePriceToStore(salePriceResveVO);
            }
        }
        return result;
    }

    /** 가격예약(본사판매가) 수정 */
    @Override
    public int modHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteHqSalePriceResve(salePriceResveVO);

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getHqSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertHqSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertHqSalePrice(salePriceResveVO);

            // 전매장적용 체크시, 본사 예약 판매가 매장적용
            if(salePriceResveVO.getApplyFg().equals("true")){
                salePriceResveMapper.insertHqSalePriceToStore(salePriceResveVO);
            }
        }
        return result;
    }

    /** 가격예약(본사판매가) 상품가격정보 조회 */
    @Override
    public List<DefaultMap<String>> searchHqSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceResveMapper.searchHqSalePriceInfo(salePriceResveVO);
    }
}
