package kr.co.solbipos.common.popup.selectSdselClass.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectSdselClass.service.SelectSdselClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SelectSdselClassMapper.java
 * @Description : (공통) 선택분류 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SelectSdselClassMapper {

    /** 선택분류 공통 - 선택분류 리스트 조회 */
    List<DefaultMap<String>> getSelectSdselClassList(SelectSdselClassVO selectSdselClassVO);
}