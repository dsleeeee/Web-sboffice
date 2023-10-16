package kr.co.solbipos.common.popup.selectDcfg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectDcfg.service.SelectDcfgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SelectDcfgMapper.java
 * @Description : (공통) 할인구분 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SelectDcfgMapper {

    /** 할인구분 공통 - 할인구분 리스트 조회 */
    List<DefaultMap<String>> getSelectDcfgList(SelectDcfgVO selectDcfgVO);
}