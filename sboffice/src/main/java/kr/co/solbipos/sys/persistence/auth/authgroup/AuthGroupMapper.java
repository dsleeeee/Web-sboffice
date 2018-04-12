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
     * 등록
     * 
     * @param authGroup
     * @return
     */
    int insertGroup(AuthGroup authGroup);

    /**
     * 수정
     * 
     * @param authGroup
     * @return
     */
    int updateGroup(AuthGroup authGroup);

    /**
     * 삭제
     * 
     * @param authGroup
     * @return
     */
    int deleteGroup(AuthGroup authGroup);
}
