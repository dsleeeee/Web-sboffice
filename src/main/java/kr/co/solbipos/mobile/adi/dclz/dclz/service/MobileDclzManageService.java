package kr.co.solbipos.mobile.adi.dclz.dclz.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileDclzManageService.java
 * @Description : (모바일) 부가서비스 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.09  권지현     최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileDclzManageService {

    /**
     * 근태관리 리스트 조회
     *
     * @param mobileDclzManageVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getDclzManage(MobileDclzManageVO mobileDclzManageVO, SessionInfoVO sessionInfoVO);

}
