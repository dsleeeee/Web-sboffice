package kr.co.solbipos.base.pay.coupon.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.*;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnRegFg;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public List<DefaultMap<String>> getCouponClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        CoupnEnvFg  coupnEnvFg = payMethodClassVO.getCoupnEnvstVal();

        payMethodClassVO.setPayTypeFg(PayTypeFg.COUPON);

        // 본사 통제
        if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {

            LOGGER.info(">>>>>> sessionInfoVO.getHqOfficeCd() : " + sessionInfoVO.getHqOfficeCd());

            payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            LOGGER.info(">>>> payMethodClassVO.getHqOfficeCd : "+ payMethodClassVO.getHqOfficeCd());

            returnList = mapper.getHqCouponClassList(payMethodClassVO);

        }
        // 매장 통제
        else if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {

            payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());

            returnList = mapper.getStoreCouponClassList(payMethodClassVO);
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

            // 본사 통제
            if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {

                PayMethodClassVO resultVO = new PayMethodClassVO();

                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {

                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);

                    procCnt += mapper.insertHqCouponClass(payMethodClassVO);

                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용.
                    String payMethodClassResult = mapper.insertHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqCouponClass(payMethodClassVO);

                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용.
                    String payMethodClassResult = mapper.updateHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                }
                else if(payMethodClassVO.getStatus() == GridDataFg.DELETE) {

                    procCnt += mapper.deleteHqCouponClass(payMethodClassVO);

                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용. (매장 분류 먼저 삭제)
                    String payMethodClassResult = mapper.deleteHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                }
            }
            // 매장 통제
            else if(payMethodClassVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
                payMethodClassVO.setStoreCd(sessionInfoVO.getOrgnCd());

                if(payMethodClassVO.getStatus() == GridDataFg.INSERT) {

                    String payMethodClassCd = mapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);

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

                couponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(couponVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = mapper.getCouponCd(couponVO);
                    couponVO.setCoupnCd(coupnCd);

                    procCnt += mapper.insertHqCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += mapper.updateHqCoupon(couponVO);
                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용.
                    String couponResult = mapper.updateHqCouponToStore(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += mapper.deleteHqCoupon(couponVO);
                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용.
                    String couponResult = mapper.deleteHqCouponToStore(couponVO);
                }
            }
            // 매장 통제
            else if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {

                couponVO.setStoreCd(sessionInfoVO.getStoreCd());
                couponVO.setCoupnRegFg(CoupnRegFg.STORE.getCode());

//                LOGGER.info("CoupnRegFg.STORE.getCode() : "+ CoupnRegFg.STORE.getCode());

                if(couponVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = mapper.getCouponCd(couponVO);
                    couponVO.setCoupnCd(coupnCd);

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

    /** 쿠폰 적용/미적용 상품 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(CouponProdVO couponProdVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> resultProdList = null;

        // 본사권한
        if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {
            couponProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            resultProdList = mapper.getHqProdList(couponProdVO);
        }
        // 매장권한
        else if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
            couponProdVO.setStoreCd(sessionInfoVO.getStoreCd());
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

                // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용. // TODO
                CouponProdVO resultVO = new CouponProdVO();
                String couponResult = mapper.insertHqCouponProdToStore(couponProdVO);
                resultVO.setResult(couponResult);

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

                // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용.
                CouponProdVO resultVO = new CouponProdVO();
                String couponResult = mapper.deleteHqCouponProdToStore(couponProdVO);
                resultVO.setResult(couponResult);

            } else if(couponProdVO.getCoupnEnvstVal() == CoupnEnvFg.STORE ) {
                procCnt += mapper.deleteStoreCouponProd(couponProdVO);
            }
        }

        return procCnt;
    }

    /** 쿠폰 적용/미적용 상품 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(CouponStoreVO couponStoreVO, SessionInfoVO sessionInfoVO) {

        couponStoreVO.setOrgnCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getStoreList(couponStoreVO);
    }

    /** 쿠폰 적용 매장 등록 */
    @Override
    public int registCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO) {


        int procCnt = 0;
        String dt = currentDateTimeString();

        for(CouponStoreVO couponStoreVO : couponStoreVOs) {
            couponStoreVO.setOrgnCd(sessionInfoVO.getHqOfficeCd());
            couponStoreVO.setRegDt(dt);
            couponStoreVO.setRegId(sessionInfoVO.getUserId());
            couponStoreVO.setModDt(dt);
            couponStoreVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.insertCouponStore(couponStoreVO);
        }

        // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용. //TODO
        // 적용매장 등록 완료된 후, 첫번째 매장의 등록정보로 등록

        LOGGER.info(">>>>>>>>>>>>>>>>>여기다아아아");

        LOGGER.info(couponStoreVOs[0].getHqOfficeCd());
        LOGGER.info(couponStoreVOs[0].getPayClassCd());
        LOGGER.info(couponStoreVOs[0].getCoupnCd());

        CouponStoreVO resultVO = couponStoreVOs[0];
        String couponResult = mapper.insertHqCouponToStore(resultVO);
        resultVO.setResult(couponResult);

        return procCnt;
    }

    /** 쿠폰 적용 매장 삭제 */
    @Override
    public int deleteCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(CouponStoreVO couponStoreVO : couponStoreVOs) {

            // 쿠폰적용 매장 삭제
            couponStoreVO.setOrgnCd(sessionInfoVO.getHqOfficeCd());
            couponStoreVO.setUseYn(UseYn.N);

            procCnt += mapper.deleteCouponStore(couponStoreVO);

        }


        //TODO
        CouponVO resultVO = new CouponVO();
        resultVO.setHqOfficeCd(couponStoreVOs[0].getHqOfficeCd());
        resultVO.setPayClassCd(couponStoreVOs[0].getPayClassCd());
        resultVO.setCoupnCd(couponStoreVOs[0].getCoupnCd());

        // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분에도 본사의 쿠폰 적용.
        String couponResult = mapper.deleteHqCouponToStore(resultVO);


        return procCnt;
    }
}
