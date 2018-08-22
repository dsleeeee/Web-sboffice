package kr.co.common.service.code;

import kr.co.common.data.domain.CommonCodeVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * 공통 코드 관련 서비스
 *
 * @author 정용길
 *
 */
public interface CmmCodeService {

    /**
     * 레디스에 해당 공통 리스트를 업데이트
     *
     * @param commonCodeVO {@link CommonCodeVO}
     * @return {@code true} 업데이트 성공 <br> {@code false} 업데이트 실패 이지만 레디스 문제 이므로 해당 코드 사용은 문제 없음
     */
    boolean updateCodeList(CommonCodeVO commonCodeVO);

    /**
     * 레디스에 공통코드 리스트 추가
     *
     * @param commonCodeVO {@link CommonCodeVO}
     * @throws Exception
     */
    void setCodeList(CommonCodeVO commonCodeVO) throws Exception;

    /**
     * 레디스에서 공통 리스트를 가져옴
     *
     * @param comCdFg {@code String} 공통코드 key
     * @return {@link CommonCodeVO} 조회 결과
     * @throws Exception
     */
    CommonCodeVO getCodeList(String comCdFg) throws Exception;

    /**
     * 레디스에 해당 공통 리스트가 있는지 확인
     *
     * @param comCdFg {@code String} 조회 코드
     * @return {@code true} 해당 코드 있음 <br> {@code false} 해당 코드 없음
     */
    boolean hasCode(String comCdFg);

    /**
      * 공통 코드 조회
      *
      * @param nmcodeGrpCd {@code String} 조회 코드
      * @return {@code <E> List<E>} 타입의 {@code List} 로 전달
      */
    <E> List<E> selectCmmCodeList(String nmcodeGrpCd);

    /**
     * 대리점 목록 조회
     * @return
     */
    <E> List<E> getAgencyList();

    /**
     * 벤사 목록 조회
     */
    <E> List<E> getVanList();

    /**
     * 본사 목록 조회
     */
    <E> List<E> getHqOfficeList();

    /**
     * 회원 등급 조회
     */
    <E> List<E> getMemberClassList(SessionInfoVO sessionInfoVO);

}
