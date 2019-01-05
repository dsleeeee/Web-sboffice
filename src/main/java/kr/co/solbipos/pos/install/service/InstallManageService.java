package kr.co.solbipos.pos.install.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.func.service.FuncVO;

import java.util.List;

/**
 * @Class Name : InstallManageService.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2019.01.02
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface InstallManageService {

    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstallRequestList(InstallVO installVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(InstallVO installVO);

    /** 설치요청 등록 */
    int saveInstallRequest(InstallVO[] installVOs, SessionInfoVO sessionInfoVO );

    //    /**
//     * 기능구분 상세 조회
//     *
//     * @param funcVO
//     * @return
//     */
//    List<DefaultMap<String>> list(FuncVO funcVO);
//
//    /**
//     * 기능구분상세 저장
//     *
//     * @param funcVOs
//     * @param sessionInfoVO
//     * @return
//     */
//    int save(FuncVO[] funcVOs, SessionInfoVO sessionInfoVO);
//
//    /**
//     * 기능구분 등록매장 조회
//     *
//     * @param funcStoreVO
//     * @return
//     */
//    Map<String, Object> getFunStoreList(FuncStoreVO funcStoreVO);
//
//    /**
//     * 기능구분 적용매장 등록 및 삭제
//     *
//     * @param funcStoreVOs
//     * @return
//     */
//    int saveFuncStore(FuncStoreVO[] funcStoreVOs, SessionInfoVO sessionInfoVO);
}
