package kr.co.solbipos.common.popup.selectTable.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectTable.service.SelectTableVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SelectTableMapper.java
 * @Description : (공통) 테이블 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SelectTableMapper {

    /** 테이블 공통 - 테이블 리스트 조회 */
    List<DefaultMap<String>> getSelectTableList(SelectTableVO selectTableVO);
}