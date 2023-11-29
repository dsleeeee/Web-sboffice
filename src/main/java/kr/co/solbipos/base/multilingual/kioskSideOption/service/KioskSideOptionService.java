package kr.co.solbipos.base.multilingual.kioskSideOption.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskSideOptionService.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KioskSideOptionService {

    /** 키오스크(카테고리) 탭 리스트 조회 */
    List<DefaultMap<String>> getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크(카테고리) 영문, 중문, 일문 저장 */
    int saveKioskCategory(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 사이드(선택그룹명) 탭 리스트 조회 */
    List<DefaultMap<String>> getSideSdselGrpList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장 */
    int saveSideSdselGrp(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 사이드(선택분류명) 탭 리스트 조회 */
    List<DefaultMap<String>> getSideSdselClassList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 사이드(선택분류명) 영문, 중문, 일문 저장 */
    int saveSideSdselClass(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 옵션(그룹명) 탭 리스트 조회 */
    List<DefaultMap<String>> getOptionGrpList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션(그룹명) 영문, 중문, 일문 저장 */
    int saveOptionGrp(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 옵션(옵션명) 탭 리스트 조회 */
    List<DefaultMap<String>> getOptionValList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션(옵션명) 영문, 중문, 일문 저장 */
    int saveOptionVal(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);
}
