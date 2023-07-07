package kr.co.solbipos.store.hq.brandTerminal.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BrandTerminalService.java
 * @Description : 기초관리 > 본사정보관리 > 브랜드별 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BrandTerminalService {

    /** 벤더 목록 */
    List<DefaultMap<String>> getVendorList();

    /** 브랜드 조회 */
    List<DefaultMap<String>> getBrandList(BrandTerminalVO brandTerminalVO, SessionInfoVO sessionInfoVO);

    /** 포스 터미널 목록 조회 */
    List<DefaultMap<String>> getTerminalList(BrandTerminalVO brandTerminalVO, SessionInfoVO sessionInfoVO);

    /** 포스 터미널 정보 저장 */
    int saveTerminalInfo(BrandTerminalVO[] brandTerminalVOs, SessionInfoVO sessionInfoVO);

}
