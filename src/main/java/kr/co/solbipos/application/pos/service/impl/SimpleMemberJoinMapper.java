package kr.co.solbipos.application.pos.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.pos.service.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : SimpleMemberJoinMapper.java
 * @Description : POS 화면에서 간편 회원가입
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SimpleMemberJoinMapper {

    /** 새로운 회원번호 조회 */
    String getNewMembrNo(SessionInfoVO sessionInfoVO);

    /** 회원 등록 */
    int insertMember(MemberVO memberVO);

    /** 선불회원 등록 */
    int registMemberPrepaid(MemberVO memberVO);

}
