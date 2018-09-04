package kr.co.solbipos.base.pay.coupon.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.code.CmmCodeService;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponService;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CouponServiceImpl.java
 * @Description : 기초관리 > 결제수단 > 쿠폰등록
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
@Service("couponService")
public class CouponServiceImpl implements CouponService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CouponMapper mapper;

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    @Autowired
    MessageService messageService;

    /** 쿠폰분류 조회 */
    @Override
    public List<DefaultMap<String>> getCouponClassList(PayMethodClassVO payMethodAClassVO,
        SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        CoupnEnvFg  coupnEnvFg = payMethodAClassVO.getCoupnEnvstVal();

        // 본사 통제
        if(payMethodAClassVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {

            payMethodAClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());

            returnList = mapper.getHqCouponClassList(payMethodAClassVO);
        }
        // 매장 통제
        else if(payMethodAClassVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {

            payMethodAClassVO.setStoreCd(sessionInfoVO.getStoreCd());

            returnList = mapper.getStoreCouponClassList(payMethodAClassVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }

        return returnList;
    }

    /** 쿠폰분류 저장 */
    @Override
    public int saveCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(dt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(dt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.COUPON);

            LOGGER.debug(payMethodClassVO.getProperties());

            // 본사 통제
            if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {
                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    procCnt += mapper.insertHqCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqCouponClass(payMethodClassVO);
                }
            }
            // 매장 통제
            else if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
                payMethodClassVO.setStoreCd(sessionInfoVO.getOrgnCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {
                    procCnt += mapper.insertStoreCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateStoreCouponClass(payMethodClassVO);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteStoreCouponClass(payMethodClassVO);
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

    /** 쿠폰 조회*/
    @Override
    public List<DefaultMap<String>> getCouponList(CouponVO couponVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        // 본사 통제
        if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {
            couponVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            returnList = mapper.getHqCouponList(couponVO);
        }
        // 매장 통제
        else if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
            couponVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = mapper.getStoreCouponList(couponVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }
        return returnList;
    }

    /** 쿠폰 저장 */
    @Override
    public int saveCouponList(CouponVO[] couponVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(CouponVO couponVO : couponVOs) {

            couponVO.setRegDt(dt);
            couponVO.setRegId(sessionInfoVO.getUserId());
            couponVO.setModDt(dt);
            couponVO.setModId(sessionInfoVO.getUserId());

            LOGGER.debug(couponVO.getProperties());

            // 본사 통제
            if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {

                couponVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());

                if(couponVO.getStatus() == GridDataFg.INSERT) {
                    procCnt += mapper.insertHqCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqCoupon(couponVO);
                }
            }
            // 매장 통제
            else if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {

                couponVO.setStoreCd(sessionInfoVO.getStoreCd());

                if(couponVO.getStatus() == GridDataFg.INSERT) {
                    procCnt += mapper.insertStoreCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateStoreCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteStoreCoupon(couponVO);
                }
            }
            // 권한 확인 필요
            else {
                throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
            }
        }

        if(procCnt == couponVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 상품 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(CouponProdVO couponProdVO) {

        List<DefaultMap<String>> resultProdList = null;

        LOGGER.info(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
        LOGGER.info(" >>>>>>>>>>>>>>>> couponProdVO.getProdRegFg() : "+ couponProdVO.getProdRegFg());
        LOGGER.info(" >>>>>>>>>>>>>>>> couponProdVO.getCoupnEnvstVal() : "+ couponProdVO.getCoupnEnvstVal());

        // 본사권한
        if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {
            resultProdList = mapper.getHqProdList(couponProdVO);
        }
        // 매장권한
        else if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
            resultProdList = mapper.getStoreProdList(couponProdVO);
        }

        return resultProdList;
    }

    /** 쿠폰 적용 상품 등록 */
    @Override
    public int registCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(CouponProdVO couponProdVO : couponProdVOs) {
            couponProdVO.setRegDt(dt);
            couponProdVO.setRegId(sessionInfoVO.getUserId());
            couponProdVO.setModDt(dt);
            couponProdVO.setModId(sessionInfoVO.getUserId());

            if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.HQ ) {
                procCnt += mapper.insertHqCouponProd(couponProdVO);
            } else if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.STORE ) {
                procCnt += mapper.insertStoreCouponProd(couponProdVO);
            }
        }
        return procCnt;
    }

    /** 쿠폰 적용 상품 삭제 */
    @Override
    public int deleteCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(CouponProdVO couponProdVO : couponProdVOs) {
            if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.HQ ) {
                procCnt += mapper.deleteHqCouponProd(couponProdVO);
            } else if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.STORE ) {
                procCnt += mapper.deleteStoreCouponProd(couponProdVO);
            }
        }

        return procCnt;
    }
}
