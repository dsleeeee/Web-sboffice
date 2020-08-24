package kr.co.solbipos.membr.anals.membrProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;

import java.util.List;

/**
 * @Class Name : MembrProdService.java
 * @Description : 회원관리 > 회원분석 > 회원 상품 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MembrProdService {

    /** 회원 상품 구매내역*/
    List<DefaultMap<Object>> getMembrProdList(MembrProdVO membrProdVO, SessionInfoVO sessionInfoVO);

    DefaultMap<Object> getMembrProdDetail(RegistVO registVO, SessionInfoVO sessionInfoVO);
}
