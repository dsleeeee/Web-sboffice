package kr.co.solbipos.adi.sms.smsUserRegist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : SmsUserRegistMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsUserRegistMapper {

    /** DI로 기존 등록 사용자아이디 조회 (없으면 null) */
    String getUserIdByDi(SmsUserRegistVO smsUserRegistVO);

    /** SMS 사용 등록 - 신규 등록 insert */
    int insertUserRegist(SmsUserRegistVO smsUserRegistVO);

    /** USER_ID로 등록정보 조회 (진입시 체크용, 없으면 null) */
    DefaultMap<Object> getUserRegistInfo(SmsUserRegistVO smsUserRegistVO);

    /** SMS 사용등록 - SMS사용자 삭제 */
    int deleteUserRegist(SmsUserRegistVO smsUserRegistVO);

    /** SMS 사용등록 - 발신번호 미사용 처리 */
    int deleteSmsTelNoManage(SmsUserRegistVO smsUserRegistVO);
}
