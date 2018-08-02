package kr.co.solbipos.sys.cd.envconfig.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstDtlVO;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstVO;

/**
 * @Class Name : EnvConfigMapper.java
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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface EnvConfigMapper {
    
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
