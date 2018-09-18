package kr.co.solbipos.base.pay.mCoupon.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.pay.coupon.service.*;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import kr.co.solbipos.base.pay.mCoupon.service.MobileCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileCouponServiceImpl.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileCouponService")
public class MobileCouponServiceImpl implements MobileCouponService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MobileCouponMapper mapper;

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    @Autowired
    MessageService messageService;

    /** 쿠폰분류 조회 */
    @Override
    public List<DefaultMap<String>> getMobileCouponClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        payMethodClassVO.setPayTypeFg(PayTypeFg.MCOUPON);
        payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = mapper.getHqMobileCouponClassList(payMethodClassVO);
        }
        // 매장
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = mapper.getStoreMobileCouponClassList(payMethodClassVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }

        return returnList;
    }

    /** 쿠폰분류 저장 */
    @Override
    public int saveMobileCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(dt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(dt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.MCOUPON);
            payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);
                    procCnt += mapper.insertHqMobileCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqMobileCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqMobileCouponClass(payMethodClassVO);
                }
            }
            // 매장
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);
                    procCnt += mapper.insertStoreMobileCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateStoreMobileCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteStoreMobileCouponClass(payMethodClassVO);
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

}
