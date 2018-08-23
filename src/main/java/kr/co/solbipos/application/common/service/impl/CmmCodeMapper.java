package kr.co.solbipos.application.common.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공통 코드 조회
 *
 * @author 정용길
 */
@Mapper
@Repository
public interface CmmCodeMapper {

    /**
     * 공통 코드 조회
     *
     * @param nmcodeGrpCd
     * @return
     */
    <E> List<E> selectCmmCodeList(String nmcodeGrpCd);

    /**
     * 대리점 목록 조회
     *
     * @return
     */
    <E> List<E> selectAgencyList();

    /**
     * 벤사 목록 조회
     * @return
     */
    <E> List<E> selectVanList();

    /**
     * 본사 목록 조회
     * @return
     */
    <E> List<E> selectHqOfficeList();

    /**
     * 회원 등급 조회
     * @return
     */
    <E> List<E> selectMemberClassList(SessionInfoVO sessionInfoVO);

}
