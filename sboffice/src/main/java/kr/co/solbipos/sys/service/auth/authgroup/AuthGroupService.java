package kr.co.solbipos.sys.service.auth.authgroup;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
public interface AuthGroupService {

    /**
     * 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> list(AuthGroup authGroup);

    /**
     * 저장
     * 
     * @param authGroup
     * @param sessionInfo
     * @return
     */
    int save(AuthGroup authGroup, SessionInfo sessionInfo);

}
