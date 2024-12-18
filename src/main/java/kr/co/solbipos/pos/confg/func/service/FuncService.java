package kr.co.solbipos.pos.confg.func.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : FuncService.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface FuncService {

    /**
     * 기능구분 상세 조회
     *
     * @param funcVO
     * @return
     */
    List<DefaultMap<String>> list(FuncVO funcVO);

    /**
     * 기능구분상세 저장
     *
     * @param funcVOs
     * @param sessionInfoVO
     * @return
     */
    int save(FuncVO[] funcVOs, SessionInfoVO sessionInfoVO);

    /**
     * 기능구분 등록매장 조회
     *
     * @param funcStoreVO
     * @return
     */
    List<DefaultMap<String>> getFunStoreList(FuncStoreVO funcStoreVO);

    /**
     * 기능구분 적용매장 등록 및 삭제
     *
     * @param funcStoreVOs
     * @return
     */
    int saveFuncStore(FuncStoreVO[] funcStoreVOs, SessionInfoVO sessionInfoVO);

    /**
     * 매장리스트
     *
     * @param funcStoreVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> selectStoreList(FuncStoreVO funcStoreVO, SessionInfoVO sessionInfoVO);

    /**
     * 매장 기본 기능키 셋팅
     *
     * @param funcStoreVO
     * @param sessionInfoVO
     * @return
     */
    int saveDefaultFunc(FuncStoreVO funcStoreVO, SessionInfoVO sessionInfoVO);

    /**
     * 포스기능 등록/미등록 기능키 조회
     *
     * @param funcStoreVO
     * @return
     */
    List<DefaultMap<String>> getFuncKeyList(FuncStoreVO funcStoreVO);

    /**
     * 포스기능 기능키 등록 및 삭제
     *
     * @param funcStoreVOs
     * @return
     */
    int saveFuncKey(FuncStoreVO[] funcStoreVOs, SessionInfoVO sessionInfoVO);
}
