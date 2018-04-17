package kr.co.solbipos.sys.service.auth.authgroup;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.Resrce;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
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
    List<Resrce> listResrce(AuthGroup authGroup, SessionInfo sessionInfo);

    /**
     * 리소스 정보 저장
     * 
     * @param resrceInfo[]
     * @param sessionInfo
     * @return
     */
    int saveResrce(ResrceInfo[] resrceInfo, SessionInfo sessionInfo);

}
