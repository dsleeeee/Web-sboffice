package kr.co.solbipos.base.pay.gift.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmCodeUtil;
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

    @Autowired
    GiftMapper mapper;

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    @Autowired
    MessageService messageService;

    /** 쿠폰분류 조회 */
    @Override
    public List<DefaultMap<String>> getGiftClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        payMethodClassVO.setPayTypeFg(PayTypeFg.GIFT);
        payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = mapper.getHqGiftClassList(payMethodClassVO);
        }
        // 매장
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = mapper.getStoreGiftClassList(payMethodClassVO);
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

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(dt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(dt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.GIFT);
            payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);
                    procCnt += mapper.insertHqGiftClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqGiftClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqGiftClass(payMethodClassVO);
                }
            }
            // 매장
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);
                    procCnt += mapper.insertStoreGiftClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateStoreGiftClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteStoreGiftClass(payMethodClassVO);
                }
            }
            // 권한 확인 필요
            else {
                throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
            }
        }

        if(procCnt == payMethodClassVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 상품권 조회*/
    @Override
    public List<DefaultMap<String>> getGiftList(GiftVO giftVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        giftVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = mapper.getHqGiftList(giftVO);
        }
        // 매장
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            giftVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = mapper.getStoreGiftList(giftVO);
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

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(GiftVO giftVO : giftVOs) {

            giftVO.setRegDt(dt);
            giftVO.setRegId(sessionInfoVO.getUserId());
            giftVO.setModDt(dt);
            giftVO.setModId(sessionInfoVO.getUserId());
            giftVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            LOGGER.debug(giftVO.getProperties());

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(giftVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = mapper.getGiftCd(giftVO);
                    giftVO.setGiftCd(coupnCd);

                    procCnt += mapper.insertHqGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqGift(giftVO);
                }
            }
            // 매장 통제
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {

                giftVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(giftVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = mapper.getGiftCd(giftVO);
                    giftVO.setGiftCd(coupnCd);

                    procCnt += mapper.insertStoreGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateStoreGift(giftVO);
                }
                else if(giftVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteStoreGift(giftVO);
                }
            }
            // 권한 확인 필요
            else {
                throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
            }
        }

        if(procCnt == giftVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
