package kr.co.solbipos.pos.confg.mainVerManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MainVerManageService.java
 * @Description : 포스관리 > POS 설정관리 > POS 메인버전관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MainVerManageService {

    /** POS 메인버전관리 - 조회 */
    List<DefaultMap<Object>> getMainVerManageList(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO);

    /** POS 메인버전관리 - 메인버전 삭제 */
    int getMainVerManageDel(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO);

    /** 메인버전 등록 팝업 - 조회 */
    List<DefaultMap<Object>> getMainVerRegistList(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO);

    /** 메인버전 등록 팝업 - 등록 */
    int getMainVerRegistSave(MainVerManageVO[] mainVerManageVOs, SessionInfoVO sessionInfoVO);
}
