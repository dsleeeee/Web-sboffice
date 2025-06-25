package kr.co.common.service.treePopup;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : TreePopupService.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface TreePopupService {

    /** 상품분류 트리 조회3 */
    List<TreePopupVO> getProdClassTree3(TreePopupVO treePopupVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 플랫 조회 */
    String getProdClassCdNm(TreePopupVO treePopupVO, SessionInfoVO sessionInfoVO);
}
