package kr.co.solbipos.sys.admin.momsBatch.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.sys.admin.momsBatch.service.MomsBatchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MomsBatchMapper.java
 * @Description : 시스템관리 > 관리자기능 > 맘스터치일괄처리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2023.02.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MomsBatchMapper {

    /** 일괄처리 */
    String batchProc(MomsBatchVO momsBatchVO);

    /** 매장코드 조회 */
    List<DefaultMap<String>> selectStoreList(MomsBatchVO momsBatchVO);
}
