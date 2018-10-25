package kr.co.common.service.code.impl;

import kr.co.solbipos.sys.etc.vancard.service.VanCmpnyVO;
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
    <E> List<E> selectVanList(VanCmpnyVO vanCmpnyVO);

    /**
     * 본사 목록 조회
     * @return
     */
    <E> List<E> getHqOfficeList();
}
