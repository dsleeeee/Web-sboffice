package kr.co.solbipos.base.store.tblpt.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;

import java.util.List;

/**
* @Class Name : TblptService.java
* @Description : 기초관리 > 매장관리 > 테이블속성
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @
*
* @author
* @since
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface TblptService {
    /** 임시패스워드 등록 */
    String tblptOpn(TblptVO tblptVO, SessionInfoVO sessionInfoVO);
}
