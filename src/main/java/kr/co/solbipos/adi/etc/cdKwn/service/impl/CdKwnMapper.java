package kr.co.solbipos.adi.etc.cdKwn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.cdKwn.service.CdKwnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CdKwnMapper.java
 * @Description : 광운대 > 공통코드 > 공통코드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.0  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CdKwnMapper {

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdKwnList(CdKwnVO cdKwnVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdKwnList(CdKwnVO cdKwnVO);

    /** 코드목록 추가 */
    int getNmcodeCdKwnSaveInsert(CdKwnVO cdKwnVO);

    /** 코드목록 저장 */
    int getNmcodeCdKwnSaveUpdate(CdKwnVO cdKwnVO);

    /** 코드목록 삭제 */
    int getNmcodeCdKwnSaveDelete(CdKwnVO cdKwnVO);
}