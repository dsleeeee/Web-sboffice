package kr.co.solbipos.membr.info.point.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 두러시스템 개발팀 Daniel
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : DayMembrService.java
 * @Description : 회원관리 > 회원관리 > 회원 포이트 조정
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.17  Daniel      최초생성
 * @since 2020.06.17
 */
@Service
public interface MemberPointService {

  /** 회원 포인트 조회 */
//  List<DefaultMap<Object>> getMemberPointList(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO);

  /** 회원 포인트 조정 */
  int getMemberPointSave(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO, HttpServletRequest request);

//  int adjustAll(List<DefaultMap<Object>> result, MemberPointVO[] memberPointVOs, SessionInfoVO sessionInfoVO);

  /** 회원검증 리스트 */
  List<MemberPointVO> getMemberPointListChk(MemberPointVO[] memberPointVOs, RegistVO registVO, SessionInfoVO sessionInfoVO);

  /** 회원 포인트조정 저장 */
  int memberPointSave(MemberPointVO[] memberPointVOs, RegistVO registVO, SessionInfoVO sessionInfoVO);
}
