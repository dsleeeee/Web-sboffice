package kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.CouponIssueStatusService;
import kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.CouponIssueStatusVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : CouponIssueStatusServiceImpl.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰상태관리(관리자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("CouponIssueStatusService")
@Transactional
public class CouponIssueStatusServiceImpl implements CouponIssueStatusService {

    private final CouponIssueStatusMapper couponIssueStatusMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    public CouponIssueStatusServiceImpl(CouponIssueStatusMapper couponIssueStatusMapper, MessageService messageService) {
        this.couponIssueStatusMapper = couponIssueStatusMapper;
        this.messageService = messageService;
    }

    /** 쿠폰 정보 조회 */
    @Override
    public List<DefaultMap<Object>> getCouponIssueStatusList(CouponIssueStatusVO couponIssueStatusVO, SessionInfoVO sessionInfoVO) {
        couponIssueStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        String currentDay = currentDateString();
        couponIssueStatusVO.setSaleDate(currentDay);
        return couponIssueStatusMapper.getCouponIssueStatusList(couponIssueStatusVO);
    }

    /** 쿠폰상태변환 저장 */
    @Override
    public int saveCouponIssueStatus(CouponIssueStatusVO[] couponIssueStatusVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDay = currentDateString();

        for (CouponIssueStatusVO couponIssueStatusVO : couponIssueStatusVOs) {
            couponIssueStatusVO.setRegDt(currentDt);
            couponIssueStatusVO.setRegId(sessionInfoVO.getUserId());
            couponIssueStatusVO.setModDt(currentDt);
            couponIssueStatusVO.setModId(sessionInfoVO.getUserId());

            couponIssueStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 회수 처리 시에 일자 설정, 나머진 NULL
            if(couponIssueStatusVO.getCoupnStatusFg() != null && couponIssueStatusVO.getCoupnStatusFg().equals("2")) {
                couponIssueStatusVO.setCoupnUseDate(currentDay);
            }else{
                couponIssueStatusVO.setCoupnUseDate("");
            }

            procCnt += couponIssueStatusMapper.saveCouponIssueStatus(couponIssueStatusVO);
        }
        return 0;
    }
}
