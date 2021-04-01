package kr.co.solbipos.base.pay.coupon.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.pay.coupon.service.*;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnRegFg;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final CouponMapper couponMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public CouponServiceImpl(CouponMapper couponMapper, MessageService messageService) {
        this.couponMapper = couponMapper;
        this.messageService = messageService;
    }

    /** 쿠폰분류 조회 */
    @Override
    public List<DefaultMap<String>> getCouponClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO) {

        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        payMethodClassVO.setHqOfficeCd(hqOfficeCd);
        payMethodClassVO.setStoreCd(storeCd);

        List<DefaultMap<String>> returnList = new ArrayList<DefaultMap<String>>();
        payMethodClassVO.setPayTypeFg(PayTypeFg.COUPON);
        returnList = couponMapper.getCouponClassList(payMethodClassVO);

        return returnList;
    }

    /** 쿠폰분류 저장 */
    @Override
    public int saveCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(currentDt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(currentDt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.COUPON);
            payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사에서 등록
                PayMethodClassVO resultVO = new PayMethodClassVO();
                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if (payMethodClassVO.getStatus() == GridDataFg.INSERT) {

                    String payMethodClassCd = couponMapper.getPayMethodClassCd(payMethodClassVO);
                    payMethodClassVO.setPayClassCd(payMethodClassCd);

                    procCnt = couponMapper.insertHqCouponClass(payMethodClassVO);
                    if (procCnt <= 0) {
                        throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }
                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용.
                    String payMethodClassResult = couponMapper.insertHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                } else if (payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt = couponMapper.updateHqCouponClass(payMethodClassVO);
                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용.
                    String payMethodClassResult = couponMapper.updateHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                } else if (payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                    procCnt = couponMapper.deleteHqCouponClass(payMethodClassVO);
                    // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용. (매장 분류 먼저 삭제)
                    String payMethodClassResult = couponMapper.deleteHqCouponClassToStore(payMethodClassVO);
                    resultVO.setResult(payMethodClassResult);
                }
            } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)  { //매장에서 등록
                payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());

                if (payMethodClassVO.getStoreCd() == null){
                    throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.access.denied"));
                }else {
                    if (payMethodClassVO.getStatus() == GridDataFg.INSERT) {

                        String payMethodClassCd = couponMapper.getPayMethodClassCd(payMethodClassVO);
                        payMethodClassVO.setPayClassCd(payMethodClassCd);

                        procCnt = couponMapper.insertStoreCouponClass(payMethodClassVO);
                        if (procCnt <= 0) {
                            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    } else if(payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
                        procCnt = couponMapper.updateStoreCouponClass(payMethodClassVO);
                    } else if (payMethodClassVO.getStatus() == GridDataFg.DELETE) {
                        procCnt = couponMapper.deleteStoreCouponClass(payMethodClassVO);
                    }
                }
            }
        }

        return procCnt;
    }

    /** 쿠폰분류 매장 적용 */
    @Override
    public int applyCouponClassList(PayMethodClassVO[] payMethodClassVOs,
        SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PayMethodClassVO payMethodClassVO : payMethodClassVOs) {

            payMethodClassVO.setRegDt(dt);
            payMethodClassVO.setRegId(sessionInfoVO.getUserId());
            payMethodClassVO.setModDt(dt);
            payMethodClassVO.setModId(sessionInfoVO.getUserId());
            payMethodClassVO.setPayTypeFg(PayTypeFg.COUPON);
            payMethodClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                PayMethodClassVO resultVO = new PayMethodClassVO();

                payMethodClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰분류 적용.
                String payMethodClassResult = couponMapper.insertHqCouponClassToStore(payMethodClassVO);
                resultVO.setResult(payMethodClassResult);
            }
        }

        return procCnt;
    }

    /** 쿠폰 조회*/
    @Override
    public List<DefaultMap<String>> getCouponList(CouponVO couponVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> returnList = null;

        /*// 본사 통제
        if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.HQ) {
            couponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = couponMapper.getHqCouponList(couponVO);
        }
        // 매장 통제
        else if(couponVO.getCoupnEnvstVal() == CoupnEnvFg.STORE) {
            couponVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = couponMapper.getStoreCouponList(couponVO);
        }
        // 권한 확인 필요
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.access.denied"));
        }*/

        // 본사/매장 권한으로 로그인 시 각각 HQ/MS 테이블의 데이터를 가져오도록 수정 : 2019-08-06 이다솜
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            couponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            returnList = couponMapper.getHqCouponList(couponVO);

        }else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            couponVO.setStoreCd(sessionInfoVO.getStoreCd());
            returnList = couponMapper.getStoreCouponList(couponVO);

        }else {
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

            //본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){

                couponVO.setOrgnFg("HQ");
                couponVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                if(couponVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = couponMapper.getCouponCd(couponVO);
                    couponVO.setCoupnCd(coupnCd);

                    procCnt += couponMapper.insertHqCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += couponMapper.updateHqCoupon(couponVO);
                    // 본사통제여부가 'Y'일 경우, 매장쿠폰 수정.
                    String couponResult = couponMapper.updateHqCouponToStore(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += couponMapper.deleteHqCoupon(couponVO);
                    // 본사통제여부가 'Y'일 경우, 매장쿠폰 삭제.
                    String couponResult = couponMapper.deleteHqCouponToStore01(couponVO);
                }
            }
            // 매장
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                couponVO.setOrgnFg("STORE");
                couponVO.setStoreCd(sessionInfoVO.getStoreCd());
                couponVO.setCoupnRegFg(CoupnRegFg.STORE.getCode());

                if(couponVO.getStatus() == GridDataFg.INSERT) {

                    String coupnCd = couponMapper.getCouponCd(couponVO);
                    couponVO.setCoupnCd(coupnCd);

                    procCnt += couponMapper.insertStoreCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.UPDATE) {
                    procCnt += couponMapper.updateStoreCoupon(couponVO);
                }
                else if(couponVO.getStatus() == GridDataFg.DELETE) {
                    procCnt += couponMapper.deleteStoreCoupon(couponVO);
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

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            couponProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            resultProdList = couponMapper.getHqProdList(couponProdVO);
        }
        // 매장
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            couponProdVO.setStoreCd(sessionInfoVO.getStoreCd());
            resultProdList = couponMapper.getStoreProdList(couponProdVO);
        }

        return resultProdList;
    }

    /** 쿠폰적용상품 등록 */
    @Override
    public int registCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String couponResult;
        CouponProdVO resultVO = new CouponProdVO();

        for (CouponProdVO couponProdVO : couponProdVOs) {
            couponProdVO.setRegDt(currentDt);
            couponProdVO.setRegId(sessionInfoVO.getUserId());
            couponProdVO.setModDt(currentDt);
            couponProdVO.setModId(sessionInfoVO.getUserId());

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                procCnt += couponMapper.insertHqCouponProd(couponProdVO);

                // 본사통제여부가 'Y'일 경우, 매장에도 본사의 쿠폰 적용.
                resultVO = new CouponProdVO();
                couponResult = couponMapper.insertHqCouponProdToStore01(couponProdVO);
                resultVO.setResult(couponResult);

            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                procCnt += couponMapper.insertStoreCouponProd(couponProdVO);
            }
        }
        return procCnt;
    }

    /** 쿠폰적용상품 삭제 */
    @Override
    public int deleteCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String couponResult;
        CouponProdVO resultVO = new CouponProdVO();

        for (CouponProdVO couponProdVO : couponProdVOs) {

            couponProdVO.setModDt(currentDt);
            couponProdVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                procCnt += couponMapper.deleteHqCouponProd(couponProdVO);

                // 본사통제여부가 'Y'일 경우, 매장에도 본사의 쿠폰 적용.
                resultVO = new CouponProdVO();
                couponResult = couponMapper.deleteHqCouponProdToStore01(couponProdVO);
                resultVO.setResult(couponResult);

            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                procCnt += couponMapper.deleteStoreCouponProd(couponProdVO);
            }
        }

        return procCnt;
    }

    /** 쿠폰 적용/미적용 상품 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(CouponStoreVO couponStoreVO, SessionInfoVO sessionInfoVO) {

        couponStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return couponMapper.getStoreList(couponStoreVO);
    }

    /** 쿠폰적용매장 등록 : 해당매장에 쿠폰 + 쿠폰별상품 적용 */
    @Override
    public int registCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        CouponVO resultVO = new CouponVO();
        String couponResult;
        String storeResult;
        for (CouponStoreVO couponStoreVO : couponStoreVOs) {

            couponStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            couponStoreVO.setRegDt(currentDt);
            couponStoreVO.setRegId(sessionInfoVO.getUserId());

            // 쿠폰적용 매장등록 ( TB_HQ_COUPON_STORE )
            procCnt += couponMapper.insertCouponStore(couponStoreVO);

            // 본사통제여부가 'Y'일 경우, 매장의 쿠폰분류에도 본사의 쿠폰 적용.
            // 적용매장 등록 완료된 후, 첫번째 매장의 등록정보로 등록
            couponResult = couponMapper.insertHqCouponToStore(couponStoreVO);

            // 쿠폰별 적용상품 정보도 함께 등록
            resultVO = new CouponVO();
            resultVO.setHqOfficeCd(couponStoreVOs[0].getHqOfficeCd());
            resultVO.setPayClassCd(couponStoreVOs[0].getPayClassCd());
            resultVO.setCoupnCd(couponStoreVOs[0].getCoupnCd());
            resultVO.setStoreCd(couponStoreVO.getStoreCd());
            resultVO.setRegDt(currentDt);
            resultVO.setRegId(sessionInfoVO.getUserId());

            storeResult = couponMapper.insertHqCouponProdToStore02(resultVO);

        }

        return procCnt;
    }

    /** 쿠폰적용매장 삭제 : 해당매장에 쿠폰 + 쿠폰별상품 삭제 */
    @Override
    public int deleteCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        CouponVO resultVO = new CouponVO();
        resultVO.setHqOfficeCd(couponStoreVOs[0].getHqOfficeCd());
        resultVO.setPayClassCd(couponStoreVOs[0].getPayClassCd());
        resultVO.setCoupnCd(couponStoreVOs[0].getCoupnCd());
        resultVO.setModDt(dt);
        resultVO.setModId(sessionInfoVO.getUserId());

        for (CouponStoreVO couponStoreVO : couponStoreVOs) {

            resultVO.setStoreCd(couponStoreVO.getStoreCd());

            // 쿠폰적용 매장 삭제
            couponStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            couponStoreVO.setUseYn(UseYn.N);
            procCnt += couponMapper.deleteCouponStore(couponStoreVO);
            // 상품정보도 함께 삭제
            String prodResult = couponMapper.deleteHqCouponProdToStore02(resultVO);
            // 본사통제여부가 'Y'일 경우, 매장의 쿠폰도 삭제
            String couponResult = couponMapper.deleteHqCouponToStore02(resultVO);
        }

        return procCnt;
    }
}
