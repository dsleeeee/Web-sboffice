package kr.co.solbipos.sys.persistence.auth.authgroup;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroupVO;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorExceptVO;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthorGrpResrceVO;

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
     * @param authGroupVO
     * @return
     */
    List<DefaultMap<String>> selectGroup(AuthGroupVO authGroupVO);

    /**
     * 등록
     *
     * @param authGroupVO
     * @return
     */
    int insertGroup(AuthGroupVO authGroupVO);

    /**
     * 수정
     *
     * @param authGroupVO
     * @return
     */
    int updateGroup(AuthGroupVO authGroupVO);

    /**
     * 삭제
     *
     * @param authGroupVO
     * @return
     */
    int deleteGroup(AuthGroupVO authGroupVO);

    /**
     * 그룹 기준 리소스 조회
     *
     * @param authGroupVO
     * @return
     */
    List<DefaultMap<String>> selectResrceByGroup(AuthGroupVO authGroupVO);

    /**
     * 아이디 기준 리소스 Tree 정보 조회
     *
     * @param authGroupVO
     * @return
     */
    List<DefaultMap<String>> selectResrceById(AuthGroupVO authGroupVO);

    /**
     * 그룹과 아이디 기준 리소스 조회
     *
     * @param authGroupVO
     * @return
     */
    List<DefaultMap<String>> selectResrceByGroupAndId(AuthGroupVO authGroupVO);

    /**
     * 리소스 저장
     *
     * @param authorGrpResrceVO
     * @return
     */
    int mergeAuthorGrpResrce(AuthorGrpResrceVO authorGrpResrceVO);

    /**
     * 로그인 아이디로 그룹코드 조회
     *
     * @param userId
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
     * @param authorExceptVO
     * @return
     */
    int mergeAuthorExcept(AuthorExceptVO authorExceptVO);

}
