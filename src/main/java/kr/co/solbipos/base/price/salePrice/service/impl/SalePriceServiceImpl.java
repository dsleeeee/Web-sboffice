package kr.co.solbipos.base.price.salePrice.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceService;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SalePriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePriceService")
public class SalePriceServiceImpl implements SalePriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SalePriceMapper salePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceServiceImpl(SalePriceMapper salePriceMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.salePriceMapper = salePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 상품별 가격정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceMapper.getProdInfo(salePriceVO);
    }

    /** 상품별 매장 판매가 조회 */
    @Override
    public List<DefaultMap<String>> getProdSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(salePriceVO.getStoreCd()).equals("")) {
            salePriceVO.setArrStoreCd(salePriceVO.getStoreCd().split(","));
        }

        return salePriceMapper.getProdSalePriceList(salePriceVO);
    }

    /** 상품별 매장 판매가 저장 */
    @Override
    public int saveProdSalePrice(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDate = currentDateString();
        String currentDt = currentDateTimeString();

        // 판매가 본사 통제여부 체크 - 본사통제 여부가 '매장'일 경우 저장로직 수행하지 않음.
        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        if(priceEnvstVal == PriceEnvFg.STORE){
            return result;
        }

        for(SalePriceVO salePriceVO : salePriceVOs) {

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setPrcCtrlFg("1"); // 본사에서 등록
            salePriceVO.setStartDate(currentDate);
            salePriceVO.setEndDate("99991231");
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            // 판매가 변경 히스토리 등록
            int prodCnt = salePriceMapper.getRegistProdCount(salePriceVO);

            if(prodCnt > 0){
                result = salePriceMapper.updateStoreProdSalePriceHistory(salePriceVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용
//            else {
//                result = salePriceMapper.insertStoreProdSalePriceHistory(salePriceVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }

            // 매장 판매가 변경
            result = salePriceMapper.modifyStoreProdSalePrice(salePriceVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }
        return result;
    }

    /** 매장별 판매가 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceMapper.getStoreSalePriceList(salePriceVO);
    }
}
