package kr.co.solbipos.membr.info.bbqMemberExcelUpload.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;

/**
 * @Class Name : BbqMemberExcelUploadService.java
 * @Description : 회원관리 > 회원정보 > 회원엑셀업로드(BBQ)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.26  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BbqMemberExcelUploadService {

    /** 회원엑셀업로드(BBQ) 저장 */
    int memberExcelSave (BbqMemberExcelUploadVO[] bbqMemberExcelUploadVOs, RegistVO registVO, SessionInfoVO sessionInfoVO);
}
