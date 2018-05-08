package kr.co.solbipos.sys.service.auth.authgroup;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroupVO;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorExceptVO;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorGrpResrceVO;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
public interface AuthGroupService {

    /**
     * 로그인 아이디 기준 선택 가능 그룹 조회
     *
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> listAvailGroup(SessionInfoVO sessionInfoVO);

    /**
     * 조회
     *
     * @param authGroupVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> list(AuthGroupVO authGroupVO, SessionInfoVO sessionInfoVO);

    /**
     * 저장
     *
     * @param authGroupVOs[]
     * @param sessionInfoVO
     * @return
     */
    int save(AuthGroupVO[] authGroupVOs, SessionInfoVO sessionInfoVO);

    /**
     * 리소스 정보 조회
     *
     * @param authGroupVO
     * @param sessionInfoVO
     * @return
     */
    List<AuthorGrpResrceVO> listResrce(AuthGroupVO authGroupVO, SessionInfoVO sessionInfoVO);

    /**
     * 리소스 정보 저장
     *
     * @param authorGrpResrceVO[]
     * @param sessionInfoVO
     * @return
     */
    int saveResrce(AuthorGrpResrceVO[] authorGrpResrceVOs, SessionInfoVO sessionInfoVO);

    /**
     * 트리 데이터 생성
     * @param list
     * @param authedList
     * @return
     */
    List<AuthorGrpResrceVO> makeTreeData(List<DefaultMap<String>> list,
            List<DefaultMap<String>> authedList);

        /**
     * 아이디 기준 리소스 정보 조회 - 예외 관리
     *
     * @param userId
     * @param sessionInfoVO
     * @return
     */
    List<AuthorGrpResrceVO> listResrceById(String userId, SessionInfoVO sessionInfoVO);

    /**
     * 아이디 기준 리소스 정보 저장 - 예외관리
     *
     * @param authorExceptVOs[]
     * @param sessionInfoVO
     * @return
     */
    int saveResrceById(AuthorExceptVO[] authorExceptVOs, SessionInfoVO sessionInfoVO);
}
