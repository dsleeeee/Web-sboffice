package kr.co.solbipos.sys.service.auth.authgroup;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorExcept;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorGrpResrce;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
public interface AuthGroupService {

    /**
     * 로그인 아이디 기준 선택 가능 그룹 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> listAvailGroup(SessionInfo sessionInfo);

    /**
     * 조회
     * 
     * @param authGroup
     * @param sessionInfo
     * @return
     */
    List<DefaultMap<String>> list(AuthGroup authGroup, SessionInfo sessionInfo);

    /**
     * 저장
     * 
     * @param authGroups[]
     * @param sessionInfo
     * @return
     */
    int save(AuthGroup[] authGroups, SessionInfo sessionInfo);

    /**
     * 리소스 정보 조회
     * 
     * @param authGroup
     * @param sessionInfo
     * @return
     */
    List<AuthorGrpResrce> listResrce(AuthGroup authGroup, SessionInfo sessionInfo);

    /**
     * 리소스 정보 저장
     * 
     * @param authorGrpResrce[]
     * @param sessionInfo
     * @return
     */
    int saveResrce(AuthorGrpResrce[] authorGrpResrces, SessionInfo sessionInfo);

    /**
     * 트리 데이터 생성
     * @param list
     * @param authedList
     * @return
     */
    List<AuthorGrpResrce> makeTreeData(List<DefaultMap<String>> list, 
            List<DefaultMap<String>> authedList);

        /**
     * 아이디 기준 리소스 정보 조회 - 예외 관리
     * 
     * @param userId
     * @param sessionInfo
     * @return
     */
    List<AuthorGrpResrce> listResrceById(String userId, SessionInfo sessionInfo);

    /**
     * 아이디 기준 리소스 정보 저장 - 예외관리
     * 
     * @param authorExcepts[]
     * @param sessionInfo
     * @return
     */
    int saveResrceById(AuthorExcept[] authorExcepts, SessionInfo sessionInfo);
}
