package kr.co.solbipos.base.promotion.artiseePromotion.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.artiseePromotion.service.ArtiseePromotionService;
import kr.co.solbipos.base.promotion.artiseePromotion.service.ArtiseePromotionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ArtiseePromotionServiceImpl.java
 * @Description : 기초관리 - 프로모션관리 - 아티제전용프로모션
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.06.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("ArtiseePromotionService")
public class ArtiseePromotionServiceImpl implements ArtiseePromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ArtiseePromotionMapper artiseePromotionMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ArtiseePromotionServiceImpl(ArtiseePromotionMapper artiseePromotionMapper, MessageService messageService) {
        this.artiseePromotionMapper = artiseePromotionMapper;
        this.messageService = messageService;
    }

    /** 아티제전용프로모션 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        artiseePromotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            artiseePromotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return artiseePromotionMapper.getPromotionList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 등록/수정 */
    @Override
    public String savePromotion(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        artiseePromotionVO.setRegDt(currentDt);
        artiseePromotionVO.setRegId(sessionInfoVO.getUserId());
        artiseePromotionVO.setModDt(currentDt);
        artiseePromotionVO.setModId(sessionInfoVO.getUserId());

        // 신규등록 시, 프로모션 코드 생성
        String promotionCd = "";
        if(!"".equals(artiseePromotionVO.getPromotionCd()) && artiseePromotionVO.getPromotionCd() != null){
            promotionCd = artiseePromotionVO.getPromotionCd();

            // 수정모드 시, 프로모션타입구분, 적용대상 선택값에 따른 적용상품/분류 리스트 데이터 삭제
            //  └ 프로모션타입구분 - 1. 전체%할인, 2. 전체금액할인 인 경우 삭제
            //  └ 적용대상 - 3. 이전에 저장된 값과 다르면(변경하면) 삭제
            // 1~3 중 하나라도 적용되면 삭제
            if("2".equals(artiseePromotionVO.getPromoTypeFg()) || "3".equals(artiseePromotionVO.getPromoTypeFg()) || "Y".equals(artiseePromotionVO.getProdTypeFgChgYn())){
                result = artiseePromotionMapper.deletePromotionProdAll(artiseePromotionVO);
            }

        }else{
            promotionCd = artiseePromotionMapper.getPromotionCode(artiseePromotionVO);
            artiseePromotionVO.setPromotionCd(promotionCd);
        }

        // 프로모션 코드가 있는 경우, 저장
        if(!"".equals(promotionCd) && promotionCd != null){

            // 프로모션 마스터 정보 저장
            result = artiseePromotionMapper.savePromotion(artiseePromotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if(result > 0){
            return promotionCd;
        }else{
            return "";
        }
    }

    /** 아티제전용프로모션 상세 조회 */
    @Override
    public DefaultMap<String> getPromotionDetail(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO){

        artiseePromotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            artiseePromotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return artiseePromotionMapper.getPromotionDetail(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionProdList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        artiseePromotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            artiseePromotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }


        return artiseePromotionMapper.getPromotionProdList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseePromotionMapper.getProdList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용분류 선택팝업 분류리스트 조회 */
    @Override
    public List<DefaultMap<String>> getClassList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO){

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseePromotionMapper.getClassList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용상품, 적용분류 선택팝업 상품추가/수정/삭제 */
    @Override
    public int savePromotionProd(ArtiseePromotionVO[] artiseePromotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( ArtiseePromotionVO artiseePromotionVO : artiseePromotionVOs ) {

            artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            artiseePromotionVO.setRegDt(currentDt);
            artiseePromotionVO.setRegId(sessionInfoVO.getUserId());
            artiseePromotionVO.setModDt(currentDt);
            artiseePromotionVO.setModId(sessionInfoVO.getUserId());

            if ( artiseePromotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용상품, 적용분류 추가
                result = artiseePromotionMapper.insertPromotionProd(artiseePromotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( artiseePromotionVO.getStatus() == GridDataFg.UPDATE ){

                // 프로모션 적용상품, 적용분류 조건수량 수정
                result = artiseePromotionMapper.updatePromotionProd(artiseePromotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( artiseePromotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용상품, 적용분류 삭제
                result = artiseePromotionMapper.deletePromotionProd(artiseePromotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == artiseePromotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 아티제전용프로모션 적용매장 리스트 조회 */
    public List<DefaultMap<String>> getPromotionStoreList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseePromotionMapper.getPromotionStoreList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용매장 선택팝업 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseePromotionMapper.getStoreList(artiseePromotionVO);
    }

    /** 아티제전용프로모션 적용매장 선택팝업 전매장적용 */
    @Override
    public int insertPromotionStoreAll(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        artiseePromotionVO.setRegDt(currentDt);
        artiseePromotionVO.setRegId(sessionInfoVO.getUserId());
        artiseePromotionVO.setModDt(currentDt);
        artiseePromotionVO.setModId(sessionInfoVO.getUserId());

        // 프로모션 전매장적용
        result = artiseePromotionMapper.insertPromotionStoreAll(artiseePromotionVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 아티제전용프로모션 적용매장 선택팝업 매장추가/삭제 */
    @Override
    public int savePromotionStore(ArtiseePromotionVO[] artiseePromotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( ArtiseePromotionVO artiseePromotionVO : artiseePromotionVOs ) {

            artiseePromotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            artiseePromotionVO.setRegDt(currentDt);
            artiseePromotionVO.setRegId(sessionInfoVO.getUserId());
            artiseePromotionVO.setModDt(currentDt);
            artiseePromotionVO.setModId(sessionInfoVO.getUserId());

            if (artiseePromotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용매장 추가
                result = artiseePromotionMapper.insertPromotionStore(artiseePromotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( artiseePromotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용매장 삭제
                result = artiseePromotionMapper.deletePromotionStore(artiseePromotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if (procCnt == artiseePromotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }
}
