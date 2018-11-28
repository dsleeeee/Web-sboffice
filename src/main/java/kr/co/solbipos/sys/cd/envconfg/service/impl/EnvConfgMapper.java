package kr.co.solbipos.sys.cd.envconfg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstDtlVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EnvConfgMapper.java
 * @Description : 시스템관리 > 코드관리 > 환경설정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface EnvConfgMapper {

    /** 환경그룹 목록 조회 */
    List<DefaultMap<String>> getEnvstGrpList(String nmcodeGrpCd);

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getEnvstList(EnvstVO envstVO);

    /** 대표명칭 코드 생성 */
    int insertEnvst(EnvstVO envConfigVO);

    /** 대표명칭 코드 수정 */
    int updateEnvst(EnvstVO envConfigVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getEnvstDtlList(EnvstDtlVO envstDtlVO);

    /** 세부명칭 코드 생성 */
    int insertEnvstDtl(EnvstDtlVO envConfigVO);

    /** 세부명칭 코드 수정 */
    int updateEnvstDtl(EnvstDtlVO envConfigVO);
}
