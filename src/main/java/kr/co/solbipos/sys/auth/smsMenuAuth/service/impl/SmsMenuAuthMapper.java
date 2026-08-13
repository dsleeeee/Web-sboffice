package kr.co.solbipos.sys.auth.smsMenuAuth.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.auth.smsMenuAuth.service.SmsMenuAuthVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SmsMenuAuthMapper.java
 * @Description : 시스템관리 > 권한관리 > SMS화면관리(관리자)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsMenuAuthMapper {

    /** SMS 메뉴 조회 */
    List<DefaultMap<String>> getSmsMenuList(SmsMenuAuthVO smsMenuAuthVO);

    /** SMS 메뉴에 등록된 사용자 조회 */
    List<DefaultMap<String>> getRegUserList(SmsMenuAuthVO smsMenuAuthVO);

    /** SMS 메뉴에 미등록된 사용자 조회 */
    List<DefaultMap<String>> getNoRegUserList(SmsMenuAuthVO smsMenuAuthVO);

    /** 사용자 SMS 메뉴 권한 등록 */
    int insertSmsMenuAuth(SmsMenuAuthVO smsMenuAuthVO);

    /** 사용자 SMS 메뉴 권한 삭제 */
    int deleteSmsMenuAuth(SmsMenuAuthVO smsMenuAuthVO);
}
