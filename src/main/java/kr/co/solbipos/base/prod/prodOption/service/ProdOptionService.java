package kr.co.solbipos.base.prod.prodOption.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;

import java.util.List;

/**
 * @Class Name : ProdOptionService.java
 * @Description : 기초관리 > 상품관리 > 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdOptionService {

    /** 옵션그룹조회 */
    List<DefaultMap<String>> getProdOptionGroup(ProdOptionVO prodOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션그룹저장 */
    int saveProdOptionGroup(ProdOptionVO[] prodOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션속성조회 */
    List<DefaultMap<String>> getProdOptionVal(ProdOptionVO prodOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션속성저장 */
    int saveProdOptionVal(ProdOptionVO[] prodOptionVO, SessionInfoVO sessionInfoVO);

}