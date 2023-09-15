package kr.co.solbipos.common.popup.selectSdselGrp.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectSdselGrp.service.SelectSdselGrpVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SelectSdselGrpMapper.java
 * @Description : (공통) 선택그룹 팝업
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
public interface SelectSdselGrpMapper {

    /** 선택그룹 공통 - 선택그룹 리스트 조회 */
    List<DefaultMap<String>> getSelectSdselGrpList(SelectSdselGrpVO selectSdselGrpVO);
}