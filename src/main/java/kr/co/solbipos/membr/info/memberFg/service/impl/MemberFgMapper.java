package kr.co.solbipos.membr.info.memberFg.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.memberFg.service.MemberFgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MemberFgMapper.java
 * @Description : 회원관리 > 회원정보 > 선불/후불회원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MemberFgMapper {

    /** 회원등급 리스트 조회 */
    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    /** 선불 회원 조회 */
    List<DefaultMap<String>> getMemberPrepaid(MemberFgVO memberFgVO);
    
    /** 미선불 회원 조회 */
    List<DefaultMap<String>> getMemberNoPrepaid(MemberFgVO memberFgVO);

    /** 후불 회원 조회 */
    List<DefaultMap<String>> getMemberPostpaid(MemberFgVO memberFgVO);

    /** 미후불 회원 조회 */
    List<DefaultMap<String>> getMemberNoPostpaid(MemberFgVO memberFgVO);

    /** 선불 회원 등록 */
    int regPrepaid(MemberFgVO memberFgVO);

    /** 후불 회원 등록 */
    int regPostpaid(MemberFgVO memberFgVO);

}
