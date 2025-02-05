package kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.sale.cmmSalePopup.payInfo.service.MobilePayInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobilePayInfoMapper.java
 * @Description : (모바일) 공통 결제수단 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobilePayInfoMapper {

    /** 결제수단 신용카드 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileCardList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 현금 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileCashList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 페이코 팝업 - 조회 */
    List<DefaultMap<Object>> getMobilePaycoList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 VMEM 포인트 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileVpointList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 VMEM 전자상품권 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileVchargeList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 모바일페이 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileMpayList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 모바일쿠폰 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileMcoupnList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 포인트 팝업 - 조회 */
    List<DefaultMap<Object>> getMobilePointList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 선불 팝업 - 조회 */
    List<DefaultMap<Object>> getMobilePrepaidList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 후불 팝업 - 조회 */
    List<DefaultMap<Object>> getMobilePostpaidList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 상품권 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileGiftList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 식권 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileFstmpList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 제휴할인 팝업 - 조회 */
    List<DefaultMap<Object>> getMobilePartnerList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 사원카드 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileEmpCardList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 가승인 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileTemporaryList(MobilePayInfoVO mobilePayInfoVO);

    /** 결제수단 스마트오더 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileVorderList(MobilePayInfoVO mobilePayInfoVO);
}