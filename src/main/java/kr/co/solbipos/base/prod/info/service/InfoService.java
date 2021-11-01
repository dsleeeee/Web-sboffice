package kr.co.solbipos.base.prod.info.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : InfoService.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface InfoService {

    /** 분류 조회 */
    List<ProductClassVO> getProdClsList(ProductClassVO prodClsVO, SessionInfoVO sessionInfoVO);

    /** 분류 저장 */
    int productClassSave(ProductClassVO[] productClassVOs, SessionInfoVO sessionInfoVO);

    /** 해당 분류로 등록된 상품 조회 */
    int chkProdCnt(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO);

    /** 상품분류정보관리(3단계) - 분류 조회 */
    List<DefaultMap<String>> getProdClass(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO);

    /** 상품분류코드 채번방식 조회 */
    String getProdClassCdInputType(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO);

    /** 상품분류코드 중복체크 */
    List<DefaultMap<Object>> getChkProdClassCd(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO);
}
