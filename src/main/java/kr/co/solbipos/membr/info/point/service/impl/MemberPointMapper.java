package kr.co.solbipos.membr.info.point.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 두러시스템 개발팀 Daniel
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : MmeberPointMapper.java
 * @Description : 회원관리 > 회원관리 > 회원 포인트 조정
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.17  Daniel      최초생성
 * @since 2020.06.17
 */
@Mapper
@Repository
public interface MemberPointMapper {

  /**
   * 회원 포인트 리그트
   */
  

  int adjustAll(DefaultMap<Object> memberPointVO);

  List<DefaultMap<Object>> getMemberPointList(MemberPointVO memberPointVO);

  DefaultMap<Object> getMemberPointListChk(MemberPointVO memberPointVO);

    int updateMemberPoint(MemberPointVO memberPointVO);

  int insertMemberPointHist(MemberPointVO memberPointVO);
}