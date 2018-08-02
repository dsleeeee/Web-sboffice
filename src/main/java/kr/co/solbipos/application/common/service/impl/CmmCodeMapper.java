package kr.co.solbipos.application.common.service.impl;

import java.util.List;

/**
 * 공통 코드 조회
 * 
 * @author 정용길
 */
public interface CmmCodeMapper {

    /**
     * 공통 코드 조회
     * 
     * @param comCdFg
     * @return
     */
    <E> List<E> selectCmmCodeList(String nmcodeGrpCd);

    /**
     * 환경변수 코드 조회
     * 
     * @param envstCd
     * @return
     */
    <E> List<E> selectEnvCodeList(String envstCd);

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
}
