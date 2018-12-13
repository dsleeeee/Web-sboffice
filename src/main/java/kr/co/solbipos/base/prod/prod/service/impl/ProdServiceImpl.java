package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.sidemenu.service.impl.SideMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       생성자 주입, 상품조회 관련 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodService")
public class ProdServiceImpl implements ProdService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final ProdMapper prodMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ProdServiceImpl(ProdMapper prodMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.prodMapper = prodMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodMapper.getProdList(prodVO);
    }

    /** 상품 상세정보 조회 */
    @Override
    public DefaultMap<String> getProdDetail(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        // 소속구분 설정
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 상품상세정보 조회
        result = prodMapper.getProdDetail(prodVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = prodMapper.getLinkedProdList(prodVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    /** 상품정보 저장 */
    @Override
    public int saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        // 소속구분 설정
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        prodVO.setRegDt(currentDt);
        prodVO.setModDt(currentDt);
        prodVO.setRegId(sessionInfoVO.getUserId());
        prodVO.setModId(sessionInfoVO.getUserId());

        // 상품등록 본사 통제여부
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));
        // 판매가 본사 통제여부
        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        int prodExist = 0;

        // 본사일경우, 상품정보 존재여부를 체크하여 프로시져 호출에 사용
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodExist = prodMapper.getProdExistInfo(prodVO);
        }

        // 상품 신규등록일때 상품코드 조회
        if( StringUtil.isEmpties( prodVO.getProdCd())) {
            String prodCd = prodMapper.getProdCd(prodVO);
            prodVO.setProdCd(prodCd);
        }

        // 매장에서 매장상품 등록시에 가격관리 구분 등록
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)  prodVO.setPrcCtrlFg("H"); //본사
        else                                        prodVO.setPrcCtrlFg("S"); //매장


        // 상품정보 저장
        int result = prodMapper.saveProductInfo(prodVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // [상품등록 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && prodEnvstVal == ProdEnvFg.HQ) {

            String procResult;

            if(prodExist == 0) {
                procResult = prodMapper.insertHqProdToStoreProd(prodVO);
            } else {
                procResult = prodMapper.updateHqProdToStoreProd(prodVO);
            }
        }

        // 상품 판매가 저장
        if(priceEnvstVal == PriceEnvFg.HQ)  prodVO.setSalePrcFg("1");
        else                                prodVO.setSalePrcFg("2");

        prodVO.setStartDate(currentDateString());
        prodVO.setEndDate("99991231");

        int salePriceReeulst = prodMapper.saveSalePrice(prodVO);
        if(salePriceReeulst <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 상품 판매가 변경 히스토리 저장
        int hqSalePriceHistResult = prodMapper.saveSalePriceHistory(prodVO);
        if(hqSalePriceHistResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && priceEnvstVal == PriceEnvFg.HQ) {
            String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
            LOGGER.info("storeSalePrice : " + storeSalePriceReeulst);
        }

        return result;
    }

    /** 상품 적용/미적용 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodMapper.getStoreList(prodVO);
    }

    /** 상품적용매장 등록 */
    @Override
    public int insertProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int procCnt = 0;

        for(ProdVO prodVO : prodVOs) {
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

            // 적용 매장 등록
            int result = prodMapper.insertProdStore(prodVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }

            // 해당 매장에 본사 상품 등록
            int hqProdResult = prodMapper.insertProdStoreDetail(prodVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
            String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);

        }
        return procCnt;
    }

    /** 상품적용매장 삭제 */
    @Override
    public int deleteProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int procCnt = 0;

        for(ProdVO prodVO : prodVOs) {
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

            int result = prodMapper.deleteProdStore(prodVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }

            // 해당 상품 삭제
            int hqProdResult = prodMapper.deleteProdStoreDetail(prodVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return procCnt;
    }
}
