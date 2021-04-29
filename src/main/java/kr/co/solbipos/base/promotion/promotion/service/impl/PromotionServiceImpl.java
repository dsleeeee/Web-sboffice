package kr.co.solbipos.base.promotion.promotion.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotion.service.PromotionService;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PromotionServiceImpl.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("PromotionService")
public class PromotionServiceImpl implements PromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PromotionMapper promotionMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PromotionServiceImpl(PromotionMapper promotionMapper, MessageService messageService) {
        this.promotionMapper = promotionMapper;
        this.messageService = messageService;
    }

    /** 프로모션관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionList(promotionVO);
    }

    /** 프로모션 등록/수정 */
    @Override
    public String savePromotion(PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        promotionVO.setRegDt(currentDt);
        promotionVO.setRegId(sessionInfoVO.getUserId());
        promotionVO.setModDt(currentDt);
        promotionVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            promotionVO.setRegFg(sessionInfoVO.getOrgnFg().getCode()); // 프로모션 등록구분 H:본사 S:매장
        }

        // 신규등록 시, 프로모션 코드 생성
        String promotionCd = "";
        if(!"".equals(promotionVO.getPromotionCd()) && promotionVO.getPromotionCd() != null){
            promotionCd = promotionVO.getPromotionCd();

            // 수정모드 시, 적용상품 체크 여부에 따른 적용상품 리스트 데이터 삭제
            if("N".equals(promotionVO.getProdCdYn())){
                result = promotionMapper.deletePromotionProdAll(promotionVO);
            }

            // 수정모드 시, 혜택유형 선택에 따른 혜택상품 리스트 데이터 삭제
            if("1".equals(promotionVO.getTypeCd()) || "2".equals(promotionVO.getTypeCd())){
                result = promotionMapper.deletePromotionPresentAll(promotionVO);
            }

        }else{
            promotionCd = promotionMapper.getPromotionCode(promotionVO);
            promotionVO.setPromotionCd(promotionCd);
        }

        // 프로모션 코드가 있는 경우, 저장
        if(!"".equals(promotionCd) && promotionCd != null){

            // 프로모션 마스터 정보 저장
            result = promotionMapper.savePromotionH(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 적용조건 정보 저장
            result = promotionMapper.savePromotionCondi(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 적용혜택 정보 저장
            result = promotionMapper.savePromotionBene(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        if(result > 0){
            return promotionCd;
        }else{
            return "";
        }
    }

    /** 프로모션 상세 조회 */
    @Override
    public DefaultMap<String> getPromotionDetail(PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionDetail(promotionVO);
    }

    /** 프로모션 적용상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionProdList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getProdList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 분류리스트 조회 */
    @Override
    public List<DefaultMap<String>> getClassList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getClassList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 상품추가/수정/삭제 */
    @Override
    public int savePromotionProd(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용상품 추가
                result = promotionMapper.insertPromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.UPDATE ){

                // 프로모션 적용상품 조건수량 수정
                result = promotionMapper.updatePromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용상품 삭제
                result = promotionMapper.deletePromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 프로모션 적용매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionStoreList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionStoreList(promotionVO);
    }

    /** 프로모션 적용매장 선택팝업 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getStoreList(promotionVO);
    }

    /** 프로모션 적용매장 선택팝업 전매장적용 */
    @Override
    public int insertPromotionStoreAll(PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        promotionVO.setRegDt(currentDt);
        promotionVO.setRegId(sessionInfoVO.getUserId());
        promotionVO.setModDt(currentDt);
        promotionVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 프로모션 전매장적용
        result = promotionMapper.insertPromotionStoreAll(promotionVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 프로모션 적용매장 선택팝업 매장추가/삭제 */
    @Override
    public int savePromotionStore(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용매장 추가
                result = promotionMapper.insertPromotionStore(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용매장 삭제
                result = promotionMapper.deletePromotionStore(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 프로모션 혜택상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionPresentList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionPresentList(promotionVO);
    }

    /** 프로모션 혜택상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPresentProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPresentProdList(promotionVO);
    }

    /** 프로모션 혜택상품 선택팝업 상품추가/수정/삭제 */
    @Override
    public int savePromotionPresent(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 혜택상품 추가
                result = promotionMapper.insertPromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.UPDATE ){

                // 프로모션 혜택상품 조건수량 수정
                result = promotionMapper.updatePromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 혜택상품 삭제
                result = promotionMapper.deletePromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }
}
