package kr.co.solbipos.sys.cd.verEnvMng.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.cd.verEnvMng.service.VerEnvMngVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : VerEnvMngMapper.java
 * @Description : 시스템관리 > 코드관리 > 버전별환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.10.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface VerEnvMngMapper {

    /** 버전 리스트 조회 */
    List<DefaultMap<String>> getVerList(VerEnvMngVO verEnvMngVO);

    /** 버전 등록 */
    int saveVer(VerEnvMngVO verEnvMngVO);

    /** 대표명칭 리스트 조회 */
    List<DefaultMap<String>> getEnvstList(VerEnvMngVO verEnvMngVO);

    /** 대표명칭 사용여부 저장 */
    int saveEnvst(VerEnvMngVO verEnvMngVO);

    /** 세부명칭 리스트 조회 */
    List<DefaultMap<String>> getEnvstDtlList(VerEnvMngVO verEnvMngVO);

    /** 세부명칭 초기값여부 저장 */
    int saveEnvstDtl(VerEnvMngVO verEnvMngVO);

    /** 기능구분 리스트 조회 */
    List<DefaultMap<String>> getFuncFgList(VerEnvMngVO verEnvMngVO);

    /** 기능 리스트 조회 */
    List<DefaultMap<String>> getFuncList(VerEnvMngVO verEnvMngVO);

    /** 기능 사용여부 저장 */
    int saveFunc(VerEnvMngVO verEnvMngVO);
}
