package kr.co.solbipos.sys.persistence.auth.authgroup;

import java.util.List;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorExcept;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorGrpResrce;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
public interface AuthGroupMapper {

    /**
     * 로그인 아이디 기준 선택 가능 그룹 조회
     * 
     * @param orgnCd
     * @return
     */
    List<DefaultMap<String>> selectAvailGroup(String orgnCd);

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

    /**
     * 그룹 기준 리소스 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> selectResrceByGroup(AuthGroup authGroup);

    /**
     * 아이디 기준 리소스 Tree 정보 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> selectResrceById(AuthGroup authGroup);

    /**
     * 그룹과 아이디 기준 리소스 조회
     * 
     * @param authGroup
     * @return
     */
    List<DefaultMap<String>> selectResrceByGroupAndId(AuthGroup authGroup);

    /**
     * 리소스 저장
     * 
     * @param authorGrpResrce
     * @return
     */
    int mergeAuthorGrpResrce(AuthorGrpResrce authorGrpResrce);

    /**
     * 로그인 아이디로 그룹코드 조회
     * 
     * @param authGroup
     * @return grpCd
     */
    String selectGrpCdById(String userId);

    /**
     * 아이디로 그룹의 리소스 정보 조회
     * 
     * @param userId
     * @return
     */
    List<DefaultMap<String>> selectResrceByGrp(String userId);

    /**
     * 포함/예외 리소스 저장
     * 
     * @param authorGrpResrce
     * @return
     */
    int mergeAuthorExcept(AuthorExcept authorExcept);

}
