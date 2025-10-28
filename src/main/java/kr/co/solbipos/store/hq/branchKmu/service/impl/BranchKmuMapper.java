package kr.co.solbipos.store.hq.branchKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.hq.branchKmu.service.BranchKmuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BranchKmuMapper.java
 * @Description : 국민대 > 매장관리 > 그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BranchKmuMapper {

    /** 본사 목록 조회 */
    List<DefaultMap<String>> getBranchKmuList(BranchKmuVO branchKmuVO);

    /** 그룹코드 채번 */
    String setBranchCd(BranchKmuVO branchKmuVO);

    /** 사업자번호 중복체크 */
    int saveBranchKmu(BranchKmuVO branchKmuVO);
}