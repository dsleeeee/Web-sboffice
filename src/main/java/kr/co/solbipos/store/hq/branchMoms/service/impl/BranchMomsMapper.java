package kr.co.solbipos.store.hq.branchMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.hq.branchMoms.service.BranchMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BranchMomsMapper.java
 * @Description : 가맹점관리 > 본사정보 > 본사-지사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.23  권지현      최초생성
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2022.09.23
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface BranchMomsMapper {

    /** 본사 목록 조회 */
    List<DefaultMap<String>> getBranchMomsList(BranchMomsVO branchMomsVO);

    /** 지사코드 채번 */
    String setBranchCd(BranchMomsVO branchMomsVO);

    /** 사업자번호 중복체크 */
    int saveBranchMoms(BranchMomsVO branchMomsVO);

}
