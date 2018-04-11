package kr.co.solbipos.sys.persistence.auth.authgroup;

import java.util.List;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
public interface AuthGroupMapper {

    /**
     * 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> selectGroup(AuthGroup authGroup);

    /**
     * 저장
     * 
     * @param authGroup
     * @return
     */
    int save(AuthGroup authGroup);

}
