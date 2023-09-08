package kr.co.solbipos.base.prod.setProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Set;

/**
 * @Class Name : SetProdService.java
 * @Description : 기초관리 > 상품관리 > 세트메뉴구성(BBQ전용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.04  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.09.04
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SetProdService {
    // 상품 조회
    List<DefaultMap<String>> getProdList(SetProdVO setProdVO, SessionInfoVO sessionInfoVO);

    // 상품 사이드상품여부 저장
    int saveSideProdYn(SetProdVO[] setProdVOs, SessionInfoVO sessionInfoVO);

    // 상품 선택그룹 조회
    List<DefaultMap<String>> getSdselGrpList(SetProdVO setProdVO, SessionInfoVO sessionInfoVO);
}
