package kr.co.solbipos.base.store.tblpt.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;

import java.util.List;

/**
* @Class Name : TblptService.java
* @Description :
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2020.03.24  조동훤      최초생성
*
* @author 조동훤
* @since 2020.03.24
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface TblptService {

    /** 창고정보 리스트조회 */
    List<DefaultMap<String>> getTblptList(TblptVO tblptVO, SessionInfoVO sessionInfoVO);
    /** 임시패스워드 등록 */
    String tblptOpn(TblptVO tblptVO, SessionInfoVO sessionInfoVO);
}
