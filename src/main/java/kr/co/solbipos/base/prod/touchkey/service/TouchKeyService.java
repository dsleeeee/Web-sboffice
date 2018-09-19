package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TouchKeyService.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.19  노현수      메소드정리/분리
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TouchKeyService {

    /** 상품목록 조회 : 판매터치키에서 사용 */
    List<DefaultMap<String>> getProductListForTouchKey(TouchVO touchVO);

    /** 판매터치키 XML 정보 조회 */
    String getTouchKeyXml(SessionInfoVO sessionInfoVO);

    /** 판매터치키 저장 */
    Result saveTouchkey(SessionInfoVO sessionInfoVO, String xml);

}
