package kr.co.solbipos.base.pay.gift.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import kr.co.solbipos.base.pay.gift.service.GiftService;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : GiftServiceImpl.java
 * @Description : 기초관리 > 결제수단 > 상품권등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.18  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("giftService")
public class GiftServiceImpl implements GiftService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final GiftMapper giftMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public GiftServiceImpl(GiftMapper giftMapper, MessageService messageService) {
        this.giftMapper = giftMapper;
        this.messageService = messageService;
    }

    /** 쿠폰분류 조회 */
    @Override
    public List<DefaultMap<String>> getGiftClassList(PayMethodClassVO payMethodClassVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        payMethodClassVO.setPayTypeFg(PayTypeFg.GIFT);
        payMethodClassVO.setOrgnFg(orgnFg);

        // 본사에서 접속시
        if( StringUtil.isEmpties(storeCd)) {

            payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = giftMapper.getHqGiftClassList(payMethodClassVO);
        }
        // 매장에서 접속시
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = giftMapper.getStoreGiftClassList(payMethodClassVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }

        return returnList;
    }

    /** 상품권분류 저장 */
    @Override
    public int saveGiftClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();


        /*
         * 상품권은 프랜차이즈의 경우 무조건 본사에서 등록
         *          단독매장의 경우 무조건 매장에서 등록
         */

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(dt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(dt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.GIFT);
            payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            // 본사에서 접속시
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    payMethodClassVO.setPayClassCd(giftMapper.getPayMethodClassCd(payMethodClassVO));

                    // 본사 분류 등록
                    result = giftMapper.insertHqGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 본사 분류 등록시 매장 분류에도 적용
                    procResult = giftMapper.insertHqGiftClassToStoreGiftClass(payMethodClassVO);

                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {

                    // 본사 분류 수정
                    result = giftMapper.updateHqGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 본사 분류 수정시 매장 분류에도 적용
                    procResult = giftMapper.updateHqGiftClassToStoreGiftClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {

                    // 본사 분류 삭제
                    result = giftMapper.deleteHqGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 본사 분류 삭제시 매장 분류에도 적용
                    procResult = giftMapper.deleteHqGiftClassToStoreGiftClass(payMethodClassVO);
                }
            }
            // 매장에서 접속시
            else {
                payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    payMethodClassVO.setPayClassCd(giftMapper.getPayMethodClassCd(payMethodClassVO));
                    result = giftMapper.insertStoreGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    result = giftMapper.updateStoreGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    result = giftMapper.deleteStoreGiftClass(payMethodClassVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                }
            }
        }

        return result;
    }

    /** 상품권 조회*/
    @Override
    public List<DefaultMap<String>> getGiftList(GiftVO giftVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        giftVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = giftMapper.getHqGiftList(giftVO);
        }
        // 매장
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            giftVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = giftMapper.getStoreGiftList(giftVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }
        return returnList;
    }

    /** 상품권 저장 */
    @Override
    public int saveGiftList(GiftVO[] giftVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String procResult = "";

        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for(GiftVO giftVO : giftVOs) {

            giftVO.setRegDt(dt);
            giftVO.setRegId(sessionInfoVO.getUserId());
            giftVO.setModDt(dt);
            giftVO.setModId(sessionInfoVO.getUserId());
            giftVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            // 본사에서 접속시
            if(StringUtil.isEmpties(storeCd)) {

                giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(giftVO.getStatus() == GridDataFg.INSERT) {

                    giftVO.setGiftCd(giftMapper.getGiftCd(giftVO));

                    // 본사 상품권 테이블 저장
                    result = giftMapper.insertHqGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 매장 상품권 테이블에도 적용
                    procResult = giftMapper.insertHqGiftToStoreGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.UPDATE) {

                    // 본사 상품권 테이블 저장
                    result = giftMapper.updateHqGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 매장 상품권 테이블에도 적용
                    procResult = giftMapper.updateHqGiftToStoreGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.DELETE) {

                    // 본사 상품권 테이블 저장
                    result = giftMapper.deleteHqGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 매장 상품권 테이블에도 적용
                    procResult = giftMapper.deleteHqGiftToStoreGift(giftVO);
                    System.out.println(procResult);
                }
            }
            // 매장에서 접속시
            else {

                giftVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(giftVO.getStatus() == GridDataFg.INSERT) {

                    giftVO.setGiftCd(giftMapper.getGiftCd(giftVO));

                    result = giftMapper.insertStoreGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(giftVO.getStatus() == GridDataFg.UPDATE) {
                    result = giftMapper.updateStoreGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(giftVO.getStatus() == GridDataFg.DELETE) {
                    result = giftMapper.deleteStoreGift(giftVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
        }
        return result;
    }
}
