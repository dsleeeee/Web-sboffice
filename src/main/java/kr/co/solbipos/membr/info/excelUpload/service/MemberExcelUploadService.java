package kr.co.solbipos.membr.info.excelUpload.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;

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
public interface MemberExcelUploadService {

  List<DefaultMap<Object>> getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO, SessionInfoVO sessionInfoVO);

  int memberExcelSave(MemberExcelUploadVO[] memberExcelUploadVOs,  RegistVO registVO, SessionInfoVO sessionInfoVO);
}
