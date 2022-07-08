package kr.co.solbipos.base.store.memberTerms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MemberTermsMapper.java
 * @Description : 기초관리 > 매장관리 > 회원약관관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MemberTermsMapper {

    /** 회원약관관리 - 조회 */
    List<DefaultMap<Object>> getMemberTermsList(MemberTermsVO memberTermsVO);

    /** 회원약관관리 신규등록 팝업 - 이전내역은 전부 사용여부 N처리 */
    int getMemberTermsRegistPreUpdateSave(MemberTermsVO memberTermsVO);
}