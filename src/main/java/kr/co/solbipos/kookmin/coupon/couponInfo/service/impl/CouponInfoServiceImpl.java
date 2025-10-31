package kr.co.solbipos.kookmin.coupon.couponInfo.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.coupon.couponInfo.service.CouponInfoService;
import kr.co.solbipos.kookmin.coupon.couponInfo.service.CouponInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : CouponInfoService.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("CouponInfoService")
@Transactional
public class CouponInfoServiceImpl implements CouponInfoService {

    private final CouponInfoMapper couponInfoMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    public CouponInfoServiceImpl(CouponInfoMapper couponInfoMapper, MessageService messageService) {
        this.couponInfoMapper = couponInfoMapper;
        this.messageService = messageService;
    }


    /** 쿠폰 정보 조회 */
    @Override
    public List<DefaultMap<Object>> getCouponInfoList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        String currentDt = currentDateString();
        couponInfoVO.setSaleDate(currentDt);

        return couponInfoMapper.getCouponInfoList(couponInfoVO);
    }

    /** 쿠폰적용관리 팝업 - 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getCouponSelectProdList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        return couponInfoMapper.getCouponSelectProdList(couponInfoVO);
    }

    /** 쿠폰적용관리 팝업 - 부서 조회 */
    @Override
    public List<DefaultMap<Object>> getcouponSelectPartList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return couponInfoMapper.getcouponSelectPartList(couponInfoVO);
    }

    /** 쿠폰상세정보 조회 */
    @Override
    public List<DefaultMap<Object>> getCouponInfoDtlList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String currentDt = currentDateString();
        couponInfoVO.setSaleDate(currentDt);
        return couponInfoMapper.getCouponInfoDtlList(couponInfoVO);
    }

    /** 쿠폰 등록 */
    @Override
    public int getCouponRegist(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        int result = 0;

        String currentDt = currentDateTimeString();
        couponInfoVO.setRegDt(currentDt);
        couponInfoVO.setRegId(sessionInfoVO.getUserId());
        couponInfoVO.setModDt(currentDt);
        couponInfoVO.setModId(sessionInfoVO.getUserId());

        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 수정 시 
        if(couponInfoVO.getCoupnCd() != null && !"".equals(couponInfoVO.getCoupnCd())) {
            couponInfoVO.setPayClassCd(couponInfoVO.getCoupnCd().substring(0,3));
            couponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd().substring(3));
        }else{
            // 등록 시 쿠폰 MAX값 조회
            DefaultMap<Object> maxCoupnCd = couponInfoMapper.getMaxCoupnCd(couponInfoVO);

            if (maxCoupnCd != null) {
                couponInfoVO.setCoupnCd(maxCoupnCd.getStr("coupnCd"));
                couponInfoVO.setPayClassCd(maxCoupnCd.getStr("payClassCd"));
            }
        }
        // 쿠폰정보 등록
        result = couponInfoMapper.getCouponRegist(couponInfoVO);
        if(result < 0) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }else{
            // 쿠폰상세정보 등록
            result = couponInfoMapper.getCouponInfoRegist(couponInfoVO);
            if(result < 0){
                throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
            }
        }
        return procCnt;
    }

    /** 쿠폰정보관리 TMP테이블 제거 */
    @Override
    public int getDeleteTmpData(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        couponInfoVO.setSessionId(sessionInfoVO.getSessionId());
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(couponInfoVO.getCoupnCd() != null && !"".equals(couponInfoVO.getCoupnCd())) {
            couponInfoVO.setPayClassCd(couponInfoVO.getCoupnCd().substring(0,3));
            couponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd().substring(3));
        }
        return couponInfoMapper.getDeleteTmpData(couponInfoVO);
    }


    /** 쿠폰정보관리 쿠폰발행 엑셀업로드 */
    @Override
    public int getCouponIssueExcelUpload(CouponInfoVO[] couponInfoVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        int coupnCount = 0;
        String currentDt = currentDateTimeString();

        CouponInfoVO delCouponInfoVO = new CouponInfoVO();

        delCouponInfoVO.setSessionId(sessionInfoVO.getSessionId());
        delCouponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        for(CouponInfoVO couponInfoVO : couponInfoVOs){

            couponInfoVO.setSessionId(sessionInfoVO.getSessionId());
            couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            couponInfoVO.setRegId(sessionInfoVO.getUserId());
            couponInfoVO.setRegDt(currentDt);
            couponInfoVO.setModId(sessionInfoVO.getUserId());
            couponInfoVO.setModDt(currentDt);

            if(couponInfoVO.getCoupnCd() != null && !"".equals(couponInfoVO.getCoupnCd())) {
                couponInfoVO.setPayClassCd(couponInfoVO.getCoupnCd().substring(0,3));
                couponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd().substring(3));
            }

            // 난수 설정 - 난수 생성했는데 중복 시 반복문
            while (true){
                String rndNm = "";

                for (int i = 0; i < 10; i++) {
                    int rndVal = (int) (Math.random() * 10);
                    rndNm += rndVal;
                }
                // prefix + payClassCd + coupnCd + 10자리 난수
                String coupnSerNo = "S25" + couponInfoVO.getPayClassCd() + couponInfoVO.getCoupnCd() + rndNm;
                couponInfoVO.setCoupnSerNo(coupnSerNo);
                int dupChk = couponInfoMapper.getCoupnSerNoDupChk(couponInfoVO);

                if(dupChk == 0){
                    break;
                }
            }

            // 학번 중복 체크
            procCnt += couponInfoMapper.saveCouponIssueTmp(couponInfoVO);

            coupnCount = Integer.parseInt(couponInfoVO.getCoupnCount());
            delCouponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd());
            delCouponInfoVO.setPayClassCd(couponInfoVO.getPayClassCd());
        }

        return procCnt;
    }

    /** 쿠폰정보관리 쿠폰발행 저장 */
    @Override
    public int saveCouponIssue(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        couponInfoVO.setSessionId(sessionInfoVO.getSessionId());
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(couponInfoVO.getCoupnCd() != null && !"".equals(couponInfoVO.getCoupnCd())) {
            couponInfoVO.setPayClassCd(couponInfoVO.getCoupnCd().substring(0,3));
            couponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd().substring(3));
        }

        // 발행수량과 업로드 건수 다르면 exception
        if(Integer.parseInt(couponInfoVO.getCoupnCount()) != couponInfoMapper.getCountCouponIssueTmp(couponInfoVO)){
            throw new JsonException(Status.SERVER_ERROR, messageService.get("couponInfo.msg.cntDiff"));
        }

        String currentDay = currentDateString();
        String currentDt = currentDateTimeString();
        couponInfoVO.setIssueDate(currentDay);
        couponInfoVO.setRegId(sessionInfoVO.getUserId());
        couponInfoVO.setRegDt(currentDt);
        couponInfoVO.setModId(sessionInfoVO.getUserId());
        couponInfoVO.setModDt(currentDt);


        // 쿠폰 발행 처리
        procCnt += couponInfoMapper.saveCouponIssue(couponInfoVO);

        if(procCnt > 0){
            // 쿠폰 상태 저장
            couponInfoMapper.updateCouponStatus(couponInfoVO);
            // TMP 테이블 제거
            couponInfoMapper.getDeleteTmpData(couponInfoVO);
        }else{
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        return procCnt;
    }

    /** 쿠폰적용관리 회수쿠폰 조회 */
    @Override
    public List<DefaultMap<Object>> getSelectSaleCouponList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO) {
        couponInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String currentDt = currentDateString();
        couponInfoVO.setSaleDate(currentDt);
        if(couponInfoVO.getCoupnCd() != null && !"".equals(couponInfoVO.getCoupnCd())) {
            couponInfoVO.setPayClassCd(couponInfoVO.getCoupnCd().substring(0,3));
            couponInfoVO.setCoupnCd(couponInfoVO.getCoupnCd().substring(3));
        }
        return couponInfoMapper.getSelectSaleCouponList(couponInfoVO);
    }
}
