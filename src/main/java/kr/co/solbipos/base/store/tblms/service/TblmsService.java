package kr.co.solbipos.base.store.tblms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblms.service.TblmsVO;

import java.util.List;

/**
* @Class Name : TblmsService.java
* @Description : 기초관리 > 매장관리 > 테이블관리
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
public interface TblmsService {
    /** 임시패스워드 등록 */
    String tblmsOpn(TblmsVO tblmsVO, SessionInfoVO sessionInfoVO);
}
