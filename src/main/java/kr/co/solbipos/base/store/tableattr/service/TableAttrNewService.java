package kr.co.solbipos.base.store.tableattr.service;

import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TableAttrNewService.java
 * @Description : 기초관리 > 매장관리 > 테이블속성(테이블별 관리)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TableAttrNewService {

    /**
     * 가맹점별 속성값이 없을 때 기본값 조회,
     * @param sessionInfoVO 세션정보
     * @return 기본테이블속성정보
     */
    List<TableAttrVO> selectTableAttrDefault();

    /**
     * 매장 테이블 속성 공통코드 조회
     * @param sessionInfoVO 세션정보
     * @return 매장 테이블 속성 공통코드
     */
    List<TableAttrVO> selectTblAttrCommCode();

    /**
     * 세션의 가맹점코드로 해당 가맹점의 테이블 속성 조회(유형별)
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    String selectTableAttrByStore(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 테이블 속성 조회(테이블별)
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    String selectTableAttrByNum(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO);

    /**
     * 세션의 가맹점코드에 테이블 속성 저장(유형별)
     * @param sessionInfoVO 세션정보
     * @param xml 클라이인트로 부터 받은 xml 문자열
     * @return Result
     */
    Result setTableAttr(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO, String xmlGraph, String xmlPreview);

    /**
     * 세션의 가맹점코드에 테이블 속성 저장(테이블별)
     * @param sessionInfoVO 세션정보
     * @param xml 클라이인트로 부터 받은 xml 문자열
     * @return Result
     */
    Result setTableNumAttr(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO, String xmlGraph, String xmlPreview);


}
