package kr.co.solbipos.adi.sms.smsCharge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SmsChargeMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsChargeMapper {

    /** 잔여금액 조회 */
    String getSmsBaseAmtSelect(SmsChargeVO smsChargeVO);

    /** 결제취소 저장 */
    int getSmsChargeSaveUpdate(SmsChargeVO smsChargeVO);

    /** 충전금액 조회 */
    String getSmsChargeAmtSelect(SmsChargeVO smsChargeVO);

    /** 메세지 건당 가격안내 팝업 - 조회 */
    DefaultMap<String> getMsgOneAmtGuideList(SmsChargeVO smsChargeVO);
}