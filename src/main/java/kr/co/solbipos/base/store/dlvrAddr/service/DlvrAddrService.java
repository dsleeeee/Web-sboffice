package kr.co.solbipos.base.store.dlvrAddr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrAddrService.java
 * @Description : 기초관리 > 상품관리 > 간편상품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrAddrService {

    /** 등록 배달권역
     * @return*/
    List<DefaultMap<Object>> dlvrAddrList(DlvrAddrVO dlvrAddrVO, SessionInfoVO sessionInfoVO);

    /** 미등록 배달권역
     * @return*/
    List<DefaultMap<Object>> dlvrAddrCodeList(DlvrAddrVO dlvrAddrVO, SessionInfoVO sessionInfoVO);

    /** 배달권역 등록
     * @return*/
    int addDlvrAddr(DlvrAddrVO[] dlvrAddrVOs, SessionInfoVO sessionInfoVO);

    /** 배달권역 등록 삭제
     * @return*/
    int delDlvrAddr(DlvrAddrVO[] dlvrAddrVOs, SessionInfoVO sessionInfoVO);

}