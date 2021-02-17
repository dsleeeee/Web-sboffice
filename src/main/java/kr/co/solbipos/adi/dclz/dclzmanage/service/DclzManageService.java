package kr.co.solbipos.adi.dclz.dclzmanage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DclzManageService.java
 * @Description : 부가서비스 > 근태 관리 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DclzManageService {

    /**
     * 근태관리 리스트 조회
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);

    /**
     * 매장 사원 조회
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);

    /**
     * 근태등록
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    int insertDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);

    /**
     * 근태수정
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    int updateDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);

    /**
     * 근태삭제
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    int deleteDclzManage(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);

    /**
     * 근태상세정보 조회
     *
     * @param dclzManageVO
     * @param sessionInfoVO
     * @return
     */
    DefaultMap<String> selectDclzManageDtl(DclzManageVO dclzManageVO, SessionInfoVO sessionInfoVO);
}
