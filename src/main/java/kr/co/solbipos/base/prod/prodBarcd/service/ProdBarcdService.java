package kr.co.solbipos.base.prod.prodBarcd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BarcdService.java
 * @Description : 기초관리 - 상품관리 - 상품바코드등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.01  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdBarcdService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param prodBarcdVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회(엑셀다운로드용)
     * @param prodBarcdVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdExcelList(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 상세 조회
     * @param prodBarcdVO 세션정보
     * @return XML_String
     */
    DefaultMap<String> getProdDetail(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);

    /** 바코드 중복체크 */
    List<DefaultMap<String>> chkBarCd(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);
    /** 바코드 중복체크 */
    List<DefaultMap<String>> chkBarCds(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO);

    /** 바코드 저장 */
    int saveBarcd(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getExcelUploadCheckDeleteAll(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);
    
    /** 검증결과 저장 */
    int getExcelUploadCheckSave(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO);

    /** 임시테이블 정보 조회 */
    List<DefaultMap<String>> getExcelList(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO);

    /** 엑셀 바코드 저장 */
    int saveBarcdExcel(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO);
}
