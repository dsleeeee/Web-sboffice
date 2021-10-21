package kr.co.solbipos.adi.sms.smsChargeHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SmsChargeHistMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsChargeHistMapper {

    /** SMS충전내역 - 조회 */
    List<DefaultMap<Object>> getSmsChargeHistList(SmsChargeHistVO smsChargeHistVO);

    /** SMS임의충전 팝업 - 조회 */
    List<DefaultMap<Object>> getSmsChargeRegistList(SmsChargeHistVO smsChargeHistVO);

    /** SMS임의충전 팝업 - 저장 insert */
    int getSmsChargeRegistSaveInsert(SmsChargeHistVO smsChargeHistVO);

    /** 잔여금액 저장 insert */
    int getSmsQtySaveInsert(SmsChargeHistVO smsChargeHistVO);
}