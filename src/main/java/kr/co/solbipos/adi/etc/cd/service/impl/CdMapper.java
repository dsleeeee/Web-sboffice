package kr.co.solbipos.adi.etc.cd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CdMapper.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
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
public interface CdMapper {
    
    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdList(CdVO cdVO);
    
    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdList(CdVO cdVO);
    
    /** 코드목록 추가 */
    int insertNmcodeCdList(CdVO cdVO);
    
    /** 코드목록 저장 */
    int updateNmcodeCdList(CdVO cdVO);
    
    
}
